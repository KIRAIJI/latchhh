import 'dart:math' as math;
import 'dart:typed_data';
import 'dart:ui' as ui;
import 'package:flutter/material.dart';
import 'package:flutter/gestures.dart';
import 'package:image_picker/image_picker.dart';
import 'package:pro_image_editor/pro_image_editor.dart';
import '../../../core/theme/app_colors.dart';
import 'epaper_display_spec.dart';
import 'epaper_layout.dart';

/// Edits layers in the uploader's existing portrait raster space.
class EpaperImageEditorScreen extends StatefulWidget {
  const EpaperImageEditorScreen({super.key, this.imageBytes, this.layout});
  final Uint8List? imageBytes;
  final EpaperLayout? layout;

  static Future<EpaperLayoutResult?> open(
    BuildContext context, {
    Uint8List? imageBytes,
    EpaperLayout? layout,
  }) => Navigator.of(context).push<EpaperLayoutResult>(
    MaterialPageRoute(
      fullscreenDialog: true,
      builder: (_) =>
          EpaperImageEditorScreen(imageBytes: imageBytes, layout: layout),
    ),
  );

  @override
  State<EpaperImageEditorScreen> createState() =>
      _EpaperImageEditorScreenState();
}

class _EpaperImageEditorScreenState extends State<EpaperImageEditorScreen> {
  late final EpaperLayout _layout = widget.layout?.copy() ?? EpaperLayout();
  final Map<Uint8List, ui.Image> _images = {};
  EpaperLayer? _selected;
  bool _loading = true;
  bool _saving = false;
  String? _error;
  double _canvasScale = 1;

  @override
  void initState() {
    super.initState();
    _loadImages();
  }

  Future<ui.Image> _decode(Uint8List bytes) async {
    final codec = await ui.instantiateImageCodec(bytes);
    try {
      return (await codec.getNextFrame()).image;
    } finally {
      codec.dispose();
    }
  }

  Future<void> _loadImages() async {
    try {
      for (final layer in _layout.layers) {
        final bytes = layer.imageBytes;
        if (bytes != null && !_images.containsKey(bytes)) {
          final image = await _decode(bytes);
          if (!mounted) {
            image.dispose();
            return;
          }
          _images[bytes] = image;
        }
      }
      if (widget.imageBytes != null) await _addImage(widget.imageBytes!);
    } on Object {
      if (mounted) {
        _error = 'Could not open this image. Try another JPG or PNG.';
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  void dispose() {
    for (final image in _images.values) {
      image.dispose();
    }
    super.dispose();
  }

  Future<void> _addImage(Uint8List bytes) async {
    final image = await _decode(bytes);
    if (!mounted) {
      image.dispose();
      return;
    }
    final ratio = image.width / image.height;
    final width = math.min(208.0, 300 * ratio);
    final layer = EpaperLayer(
      imageBytes: bytes,
      imageAspectRatio: ratio,
      width: width,
      x: (240 - width) / 2,
      y: (416 - width / ratio) / 2,
    );
    setState(() {
      _images[bytes] = image;
      _layout.layers.add(layer);
      _selected = layer;
    });
  }

  Future<void> _pickImage() async {
    try {
      final file = await ImagePicker().pickImage(
        source: ImageSource.gallery,
        maxWidth: 1664,
        maxHeight: 1664,
        imageQuality: 100,
        requestFullMetadata: false,
      );
      if (file == null || !mounted) return;
      await _addImage(await file.readAsBytes());
    } on Object {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Could not add this image. Try another JPG or PNG.'),
          ),
        );
      }
    }
  }

  Future<void> _editText([EpaperLayer? layer]) async {
    final text = await showDialog<String>(
      context: context,
      builder: (_) => _TextDialog(initialText: layer?.text),
    );
    if (!mounted || text == null) return;
    setState(() {
      final target = layer ?? EpaperLayer();
      target.text = text;
      if (layer == null) _layout.layers.add(target);
      target.keepOnCanvas();
      _selected = target;
    });
  }

  Future<void> _editPhoto(EpaperLayer layer) async {
    final bytes = await Navigator.of(context).push<Uint8List>(
      MaterialPageRoute(
        fullscreenDialog: true,
        builder: (context) => ProImageEditor.memory(
          layer.imageBytes!,
          configs: const ProImageEditorConfigs(
            imageGeneration: ImageGenerationConfigs(
              outputFormat: OutputFormat.png,
              maxOutputSize: Size(1664, 1664),
            ),
          ),
          callbacks: ProImageEditorCallbacks(
            onImageEditingComplete: (bytes) async {
              if (context.mounted) Navigator.pop(context, bytes);
            },
          ),
        ),
      ),
    );
    if (!mounted || bytes == null) return;
    try {
      final image = await _decode(bytes);
      if (!mounted) {
        image.dispose();
        return;
      }
      final ratio = image.width / image.height;
      final replacement = EpaperLayer(
        imageBytes: bytes,
        imageAspectRatio: ratio,
        x: layer.x,
        y: layer.y,
        width: math.min(layer.width, 416 * ratio),
      )..keepOnCanvas();
      setState(() {
        final index = _layout.layers.indexOf(layer);
        _layout.layers[index] = replacement;
        _images[bytes] = image;
        _images.remove(layer.imageBytes)?.dispose();
        _selected = replacement;
      });
    } on Object {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Could not apply the image edit.')),
        );
      }
    }
  }

  Future<void> _finish() async {
    if (_layout.layers.any(
      (layer) => layer.bounds.bottom > 416 || layer.bounds.right > 240,
    )) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Some content is outside the canvas. Reduce its size before previewing.',
          ),
        ),
      );
      return;
    }
    setState(() => _saving = true);
    try {
      final png = await _layout.render(_images);
      if (mounted) Navigator.pop(context, EpaperLayoutResult(_layout, png));
    } on Object {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Could not prepare the preview. Please try again.'),
          ),
        );
      }
    } finally {
      if (mounted) setState(() => _saving = false);
    }
  }

  void _selectAt(Offset point) {
    final position = point / _canvasScale;
    setState(
      () => _selected = _layout.layers.reversed
          .where((layer) => layer.bounds.inflate(3).contains(position))
          .firstOrNull,
    );
  }

  @override
  Widget build(BuildContext context) {
    final selected = _selected;
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('E-Paper layout'),
        actions: [
          TextButton(
            onPressed: _loading || _saving || _error != null ? null : _finish,
            child: Text(_saving ? 'Preparing…' : 'Preview'),
          ),
        ],
      ),
      body: SafeArea(
        child: Column(
          children: [
            const Padding(
              padding: EdgeInsets.all(12),
              child: Text(
                'Tap to select. Drag to move. Use the controls to adjust size.',
              ),
            ),
            Expanded(
              child: _loading
                  ? const Center(child: CircularProgressIndicator())
                  : _error != null
                  ? Center(child: Text(_error!))
                  : LayoutBuilder(
                      builder: (context, constraints) {
                        _canvasScale = math.min(
                          (constraints.maxWidth - 32) / 240,
                          (constraints.maxHeight - 16) / 416,
                        );
                        return Center(
                          child: SizedBox(
                            width: 240 * _canvasScale,
                            height: 416 * _canvasScale,
                            child: DecoratedBox(
                              decoration: BoxDecoration(
                                border: Border.all(color: AppColors.border),
                              ),
                              child: GestureDetector(
                                key: const ValueKey('epaper-canvas'),
                                dragStartBehavior: DragStartBehavior.down,
                                onTapDown: (details) =>
                                    _selectAt(details.localPosition),
                                onPanStart: (details) =>
                                    _selectAt(details.localPosition),
                                onPanUpdate: (details) {
                                  final layer = _selected;
                                  if (layer == null || _saving) return;
                                  setState(() {
                                    layer.x += details.delta.dx / _canvasScale;
                                    layer.y += details.delta.dy / _canvasScale;
                                    layer.keepOnCanvas();
                                  });
                                },
                                onDoubleTap: selected?.text == null
                                    ? null
                                    : () => _editText(selected),
                                child: CustomPaint(
                                  painter: _LayoutPainter(
                                    _layout,
                                    _images,
                                    selected,
                                  ),
                                ),
                              ),
                            ),
                          ),
                        );
                      },
                    ),
            ),
            if (selected != null && !_loading)
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Row(
                      children: [
                        Text(
                          selected.text == null ? 'Image size' : 'Text size',
                        ),
                        Expanded(
                          child: Slider(
                            key: const ValueKey('epaper-layer-size'),
                            min: selected.text == null
                                ? math
                                      .min(12, 416 * selected.imageAspectRatio)
                                      .toDouble()
                                : 6,
                            max: selected.text == null
                                ? math
                                      .min(240, 416 * selected.imageAspectRatio)
                                      .toDouble()
                                : 48,
                            value: selected.text == null
                                ? selected.width
                                : selected.fontSize.clamp(6, 48),
                            onChanged: _saving
                                ? null
                                : (value) => setState(() {
                                    if (selected.text == null) {
                                      selected.width = value;
                                    } else {
                                      selected.fontSize = value;
                                    }
                                    selected.keepOnCanvas();
                                  }),
                          ),
                        ),
                        if (selected.text != null)
                          IconButton(
                            tooltip: 'Edit text',
                            onPressed: _saving
                                ? null
                                : () => _editText(selected),
                            icon: const Icon(Icons.edit_outlined),
                          ),
                        if (selected.imageBytes != null)
                          IconButton(
                            tooltip: 'Photo tools',
                            onPressed: _saving
                                ? null
                                : () => _editPhoto(selected),
                            icon: const Icon(Icons.edit_outlined),
                          ),
                        IconButton(
                          tooltip: 'Delete selected',
                          onPressed: _saving
                              ? null
                              : () => setState(() {
                                  _layout.layers.remove(selected);
                                  final bytes = selected.imageBytes;
                                  if (bytes != null &&
                                      !_layout.layers.any(
                                        (layer) =>
                                            identical(layer.imageBytes, bytes),
                                      )) {
                                    _images.remove(bytes)?.dispose();
                                  }
                                  _selected = null;
                                }),
                          icon: const Icon(Icons.delete_outline),
                        ),
                      ],
                    ),
                    if (selected.text != null)
                      Row(
                        children: [
                          const Text('Text width'),
                          Expanded(
                            child: Slider(
                              min: 40,
                              max: 240,
                              value: selected.width,
                              onChanged: _saving
                                  ? null
                                  : (value) => setState(() {
                                      selected.width = value;
                                      selected.keepOnCanvas();
                                    }),
                            ),
                          ),
                        ],
                      ),
                  ],
                ),
              ),
            Padding(
              padding: const EdgeInsets.all(12),
              child: Wrap(
                spacing: 12,
                children: [
                  OutlinedButton.icon(
                    onPressed: _loading || _saving ? null : _editText,
                    icon: const Icon(Icons.text_fields),
                    label: const Text('Add text'),
                  ),
                  OutlinedButton.icon(
                    onPressed: _loading || _saving ? null : _pickImage,
                    icon: const Icon(Icons.add_photo_alternate_outlined),
                    label: const Text('Add image'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _TextDialog extends StatefulWidget {
  const _TextDialog({this.initialText});
  final String? initialText;
  @override
  State<_TextDialog> createState() => _TextDialogState();
}

class _TextDialogState extends State<_TextDialog> {
  late final _controller = TextEditingController(text: widget.initialText);
  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => AlertDialog(
    title: Text(widget.initialText == null ? 'Add text' : 'Edit text'),
    content: TextField(
      controller: _controller,
      autofocus: true,
      minLines: 2,
      maxLines: 6,
      maxLength: 500,
      decoration: const InputDecoration(labelText: 'Text'),
    ),
    actions: [
      TextButton(
        onPressed: () => Navigator.pop(context),
        child: const Text('Cancel'),
      ),
      TextButton(
        onPressed: () {
          if (_controller.text.trim().isNotEmpty) {
            Navigator.pop(context, _controller.text.trim());
          }
        },
        child: const Text('Save'),
      ),
    ],
  );
}

class _LayoutPainter extends CustomPainter {
  _LayoutPainter(this.layout, this.images, this.selected);
  final EpaperLayout layout;
  final Map<Uint8List, ui.Image> images;
  final EpaperLayer? selected;
  @override
  void paint(Canvas canvas, Size size) {
    canvas.save();
    canvas.scale(size.width / EpaperDisplaySpec.pixelWidth);
    layout.paint(canvas, images);
    if (selected != null) {
      canvas.drawRect(
        selected!.bounds,
        Paint()
          ..color = AppColors.primary
          ..style = PaintingStyle.stroke
          ..strokeWidth = 1,
      );
    }
    canvas.restore();
  }

  @override
  bool shouldRepaint(covariant _LayoutPainter oldDelegate) => true;
}
