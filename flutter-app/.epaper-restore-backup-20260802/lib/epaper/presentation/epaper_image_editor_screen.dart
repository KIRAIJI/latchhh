import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:pro_image_editor/pro_image_editor.dart';

/// Temporary full-screen image editor used only by the E-Paper upload flow.
///
/// Returns edited image bytes on Done, or `null` when Cancel/Back is used.
class EpaperImageEditorScreen extends StatelessWidget {
  const EpaperImageEditorScreen({super.key, required this.imageBytes});

  final Uint8List imageBytes;

  static Future<Uint8List?> open(
    BuildContext context, {
    required Uint8List imageBytes,
  }) {
    return Navigator.of(context).push<Uint8List>(
      MaterialPageRoute(
        fullscreenDialog: true,
        builder: (_) => EpaperImageEditorScreen(imageBytes: imageBytes),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return ProImageEditor.memory(
      imageBytes,
      configs: const ProImageEditorConfigs(
        mainEditor: MainEditorConfigs(
          enableZoom: true,
          boundaryMargin: EdgeInsets.all(double.infinity),
        ),
        cropRotateEditor: CropRotateEditorConfigs(
          initAspectRatio: 240 / 416,
          aspectRatios: <AspectRatioItem>[
            AspectRatioItem(text: 'E-Paper', value: 240 / 416),
          ],
          tools: <CropRotateTool>[
            CropRotateTool.rotate,
            CropRotateTool.flip,
            CropRotateTool.reset,
          ],
        ),
        // PNG avoids JPG re-encode loss when the same image is edited again.
        imageGeneration: ImageGenerationConfigs(
          outputFormat: OutputFormat.png,
          maxOutputSize: Size(480, 832),
        ),
      ),
      callbacks: ProImageEditorCallbacks(
        onImageEditingComplete: (Uint8List editedBytes) async {
          if (!context.mounted) {
            return;
          }
          Navigator.of(context).pop(editedBytes);
        },
      ),
    );
  }
}
