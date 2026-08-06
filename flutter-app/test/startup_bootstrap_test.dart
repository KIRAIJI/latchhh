import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/core/services/push_notification_service.dart';
import 'package:latch/latch_ui/features/startup/opening_gif_duration.dart';
import 'package:latch/latch_ui/features/startup/presentation/app_loading_screen.dart';
import 'package:latch/main.dart';

void main() {
  testWidgets('renders the opening screen while Firebase is still starting', (
    tester,
  ) async {
    final pendingFirebase = Completer<PushNotificationService?>();

    await tester.pumpWidget(
      LatchStartupBootstrap(pushNotifications: pendingFirebase.future),
    );

    expect(find.byType(AppLoadingScreen), findsOneWidget);
    expect(find.byType(Image), findsOneWidget);
    expect(find.byType(LatchStartupBootstrap), findsOneWidget);
  });

  testWidgets('opening animation stays within the startup budget', (
    tester,
  ) async {
    final timing = await OpeningGifTiming.resolve();

    expect(
      timing.totalDuration,
      lessThanOrEqualTo(AppLoadingScreen.maximumPlaybackDuration),
      reason:
          'The opening GIF currently lasts ${timing.totalDuration.inMilliseconds} ms.',
    );
  });
}
