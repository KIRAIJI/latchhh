import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/presentation/widgets/epaper_status_banner.dart';

void main() {
  test('classifies a safe X1 restart as processing', () {
    final EpaperStatusPresentation presentation =
        EpaperStatusPresentation.fromStatus(
          status: 'Connection restored. Restarting the update safely…',
          isBusy: true,
          isProcessing: false,
        );

    expect(presentation.kind, EpaperStatusKind.processing);
  });

  test('classifies a refresh-completion check as processing', () {
    final EpaperStatusPresentation presentation =
        EpaperStatusPresentation.fromStatus(
          status: 'Connection restored. Checking the display refresh…',
          isBusy: true,
          isProcessing: false,
        );

    expect(presentation.kind, EpaperStatusKind.processing);
  });

  test('classifies a pending refresh retry as an NFC action', () {
    final EpaperStatusPresentation
    presentation = EpaperStatusPresentation.fromStatus(
      status:
          'Image transferred. Reposition your phone, then tap Retry Refresh.',
      isBusy: false,
      isProcessing: false,
    );

    expect(presentation.kind, EpaperStatusKind.nfc);
  });
}
