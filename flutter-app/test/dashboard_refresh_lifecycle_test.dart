import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/application/latch_controller.dart';
import 'package:latch/latch_ui/integration/latch_integrated_shell.dart';
import 'package:package_info_plus/package_info_plus.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('initial dashboard refresh starts after the first frame', (
    tester,
  ) async {
    PackageInfo.setMockInitialValues(
      appName: 'LATCH',
      packageName: 'com.latch.mobile',
      version: '1.0.0',
      buildNumber: '1',
      buildSignature: '',
    );
    final controller = _SynchronousRefreshController();
    addTearDown(controller.dispose);

    await tester.pumpWidget(
      MaterialApp(
        home: LatchIntegratedShell(controller: controller, onSignedOut: () {}),
      ),
    );
    await tester.pump();

    expect(controller.refreshCount, 1);
    expect(tester.takeException(), isNull);
  });

  testWidgets('silently refreshes map items every five seconds', (tester) async {
    PackageInfo.setMockInitialValues(
      appName: 'LATCH',
      packageName: 'com.latch.mobile',
      version: '1.0.0',
      buildNumber: '1',
      buildSignature: '',
    );
    final controller = _SynchronousRefreshController();
    addTearDown(controller.dispose);

    await tester.pumpWidget(
      MaterialApp(
        home: LatchIntegratedShell(controller: controller, onSignedOut: () {}),
      ),
    );
    await tester.pump();
    await tester.pump(const Duration(seconds: 5));

    expect(controller.silentItemsRefreshCount, 1);
    expect(tester.takeException(), isNull);
  });
}

class _SynchronousRefreshController extends LatchController {
  int refreshCount = 0;
  int silentItemsRefreshCount = 0;

  @override
  bool get isAuthenticated => true;

  @override
  Future<void> refreshDashboard() async {
    refreshCount += 1;
    notifyListeners();
  }

  @override
  Future<void> refreshItemsSilently() async {
    silentItemsRefreshCount += 1;
    notifyListeners();
  }
}
