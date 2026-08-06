import 'dart:async';
import 'dart:developer' as developer;
import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

abstract final class AppErrorReporter {
  static bool _initialized = false;
  static Future<void> Function(
    Object error,
    StackTrace stackTrace, {
    required bool fatal,
    required String context,
  })?
  remoteSink;

  static void initialize() {
    if (_initialized) return;
    _initialized = true;

    FlutterError.onError = (details) {
      record(
        details.exception,
        details.stack ?? StackTrace.current,
        context: details.context?.toDescription() ?? 'Flutter framework',
      );
      if (kDebugMode) {
        FlutterError.presentError(details);
      }
    };

    PlatformDispatcher.instance.onError = (error, stackTrace) {
      record(error, stackTrace, context: 'Uncaught platform/async error');
      return true;
    };

    ErrorWidget.builder = (_) => const AppErrorFallback();
  }

  static void record(
    Object error,
    StackTrace stackTrace, {
    required String context,
  }) {
    developer.log(
      context,
      name: 'latch.error',
      error: error,
      stackTrace: stackTrace,
      level: 1000,
    );
    final sink = remoteSink;
    if (sink != null) {
      unawaited(sink(error, stackTrace, fatal: true, context: context));
    }
  }
}

class AppErrorFallback extends StatelessWidget {
  const AppErrorFallback({super.key});

  @override
  Widget build(BuildContext context) {
    return const ColoredBox(
      color: Color(0xFFF7F8FA),
      child: SafeArea(
        child: Center(
          child: Padding(
            padding: EdgeInsets.all(24),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(
                  Icons.error_outline_rounded,
                  size: 40,
                  color: Color(0xFFB42318),
                ),
                SizedBox(height: 12),
                Text(
                  'Something went wrong',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w700,
                    color: Color(0xFF182230),
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  'Please return to the previous screen and try again.',
                  textAlign: TextAlign.center,
                  style: TextStyle(color: Color(0xFF475467)),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
