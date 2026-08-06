import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/presentation/widgets/epaper_status_banner.dart';

void main() {
  group('EpaperStatusPresentation', () {
    test('classifies end-user e-paper states', () {
      expect(
        EpaperStatusPresentation.fromStatus(
          status: 'Image ready.',
          isBusy: false,
          isProcessing: false,
        ).kind,
        EpaperStatusKind.ready,
      );
      expect(
        EpaperStatusPresentation.fromStatus(
          status: 'Updating E-Paper… 42%',
          isBusy: true,
          isProcessing: true,
        ).kind,
        EpaperStatusKind.processing,
      );
      expect(
        EpaperStatusPresentation.fromStatus(
          status:
              'Connection interrupted. Place your phone back on the device '
              'to continue.',
          isBusy: true,
          isProcessing: false,
        ).kind,
        EpaperStatusKind.nfc,
      );
      expect(
        EpaperStatusPresentation.fromStatus(
          status: 'Update failed. Keep the phone in place and try again.',
          isBusy: false,
          isProcessing: false,
        ).kind,
        EpaperStatusKind.error,
      );
    });
  });
}
