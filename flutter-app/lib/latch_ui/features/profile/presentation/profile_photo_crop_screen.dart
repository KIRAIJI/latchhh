import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:pro_image_editor/pro_image_editor.dart';

class ProfilePhotoCropScreen extends StatelessWidget {
  const ProfilePhotoCropScreen({super.key, required this.imageBytes});

  final Uint8List imageBytes;

  static Future<Uint8List?> open(
    BuildContext context, {
    required Uint8List imageBytes,
  }) => Navigator.of(context).push<Uint8List>(
    MaterialPageRoute(
      fullscreenDialog: true,
      builder: (_) => ProfilePhotoCropScreen(imageBytes: imageBytes),
    ),
  );

  @override
  Widget build(BuildContext context) {
    return ProImageEditor.memory(
      imageBytes,
      configs: const ProImageEditorConfigs(
        mainEditor: MainEditorConfigs(
          enableZoom: true,
          tools: <SubEditorMode>[SubEditorMode.cropRotate],
        ),
        cropRotateEditor: CropRotateEditorConfigs(
          initAspectRatio: 1,
          aspectRatios: <AspectRatioItem>[
            AspectRatioItem(text: 'Square', value: 1),
          ],
          tools: <CropRotateTool>[
            CropRotateTool.rotate,
            CropRotateTool.flip,
            CropRotateTool.reset,
          ],
        ),
        imageGeneration: ImageGenerationConfigs(
          outputFormat: OutputFormat.jpg,
          jpegQuality: 90,
          maxOutputSize: Size(1024, 1024),
        ),
      ),
      callbacks: ProImageEditorCallbacks(
        onImageEditingComplete: (bytes) async {
          if (context.mounted) Navigator.of(context).pop(bytes);
        },
      ),
    );
  }
}
