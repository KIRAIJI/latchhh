import 'dart:math' as math;
import 'dart:typed_data';
import 'dart:ui' as ui;
import 'package:flutter/material.dart';
import 'epaper_display_spec.dart';

enum EpaperTemplate {
  contact('Contact Information', [
    'Name',
    'Contact number',
    'Email (optional)',
    'Message (optional)',
  ]),
  emergency('Emergency Contact', [
    'Name',
    'Emergency contact',
    'Contact number',
    'Message (optional)',
  ]),
  identification('Identification', [
    'Name',
    'ID / reference',
    'Contact information',
    'Organization / company (optional)',
  ]),
  message('Custom Message', ['Message']);

  const EpaperTemplate(this.title, this.fields);
  final String title;
  final List<String> fields;

  EpaperLayout create(List<String> values) {
    final layers = <EpaperLayer>[
      EpaperLayer(text: title, x: 16, y: 20, width: 208, fontSize: 21),
    ];
    var y = layers.first.bounds.bottom + 22;
    for (var i = 0; i < fields.length; i++) {
      final value = values[i].trim();
      if (value.isEmpty) continue;
      final layer = EpaperLayer(
        text: '${fields[i].replaceAll(' (optional)', '')}\n$value',
        x: 16,
        y: y,
        width: 208,
        fontSize: 16,
      );
      layers.add(layer);
      y = layer.bounds.bottom + 16;
    }
    if (y > EpaperDisplaySpec.pixelHeight - 16) {
      final scale = (EpaperDisplaySpec.pixelHeight - 32) / y;
      for (final layer in layers) {
        layer.y *= scale;
        layer.fontSize *= scale;
      }
    }
    return EpaperLayout(layers);
  }
}

class EpaperLayer {
  EpaperLayer({
    this.text,
    this.imageBytes,
    this.imageAspectRatio = 1,
    this.x = 16,
    this.y = 16,
    this.width = 208,
    this.fontSize = 18,
  });
  String? text;
  final Uint8List? imageBytes;
  final double imageAspectRatio;
  double x;
  double y;
  double width;
  double fontSize;

  TextPainter get textPainter => TextPainter(
    text: TextSpan(
      text: text,
      style: TextStyle(
        color: Colors.black,
        fontSize: fontSize,
        height: 1.2,
        fontFamily: 'Roboto',
      ),
    ),
    textDirection: TextDirection.ltr,
  )..layout(maxWidth: width);

  Rect get bounds {
    final painter = text == null ? null : textPainter;
    final height = painter?.height ?? width / imageAspectRatio;
    painter?.dispose();
    return Rect.fromLTWH(x, y, width, height);
  }

  void keepOnCanvas() {
    final rect = bounds;
    x = x
        .clamp(0, math.max(0, EpaperDisplaySpec.pixelWidth - rect.width))
        .toDouble();
    y = y
        .clamp(0, math.max(0, EpaperDisplaySpec.pixelHeight - rect.height))
        .toDouble();
  }

  EpaperLayer copy() => EpaperLayer(
    text: text,
    imageBytes: imageBytes,
    imageAspectRatio: imageAspectRatio,
    x: x,
    y: y,
    width: width,
    fontSize: fontSize,
  );
}

class EpaperLayout {
  EpaperLayout([List<EpaperLayer>? layers]) : layers = layers ?? [];
  final List<EpaperLayer> layers;
  EpaperLayout copy() =>
      EpaperLayout(layers.map((layer) => layer.copy()).toList());

  void paint(Canvas canvas, Map<Uint8List, ui.Image> images) {
    canvas.save();
    canvas.clipRect(
      const Rect.fromLTWH(
        0,
        0,
        EpaperDisplaySpec.pixelWidth * 1.0,
        EpaperDisplaySpec.pixelHeight * 1.0,
      ),
    );
    canvas.drawColor(Colors.white, BlendMode.src);
    for (final layer in layers) {
      if (layer.text != null) {
        final painter = layer.textPainter;
        painter.paint(canvas, Offset(layer.x, layer.y));
        painter.dispose();
      } else {
        final image = images[layer.imageBytes];
        if (image != null) {
          canvas.drawImageRect(
            image,
            Rect.fromLTWH(
              0,
              0,
              image.width.toDouble(),
              image.height.toDouble(),
            ),
            layer.bounds,
            Paint()..filterQuality = FilterQuality.high,
          );
        }
      }
    }
    canvas.restore();
  }

  Future<Uint8List> render(Map<Uint8List, ui.Image> images) async {
    final recorder = ui.PictureRecorder();
    paint(Canvas(recorder), images);
    final picture = recorder.endRecording();
    final image = await picture.toImage(
      EpaperDisplaySpec.pixelWidth,
      EpaperDisplaySpec.pixelHeight,
    );
    try {
      final data = await image.toByteData(format: ui.ImageByteFormat.png);
      if (data == null) throw StateError('Could not prepare the layout.');
      return data.buffer.asUint8List();
    } finally {
      image.dispose();
      picture.dispose();
    }
  }
}

class EpaperLayoutResult {
  const EpaperLayoutResult(this.layout, this.pngBytes);
  final EpaperLayout layout;
  final Uint8List pngBytes;
}
