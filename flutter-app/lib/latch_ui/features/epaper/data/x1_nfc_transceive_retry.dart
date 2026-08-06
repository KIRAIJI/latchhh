import 'dart:async';
import 'dart:typed_data';

typedef X1FragmentTransceive = Future<Uint8List> Function(Uint8List command);
typedef X1TransientErrorPredicate = bool Function(Object error);
typedef X1RetryDelay = Future<void> Function(Duration duration);
typedef X1RetryObserver =
    void Function(int retry, int maxRetries, Duration delay, Object error);

Future<Uint8List> transceiveX1WriteFragment({
  required Uint8List command,
  required X1FragmentTransceive transceive,
  required X1TransientErrorPredicate isTransientError,
  int maxRetries = 4,
  Duration baseDelay = const Duration(milliseconds: 120),
  X1RetryDelay delay = _defaultDelay,
  X1RetryObserver? onRetry,
}) async {
  var retries = 0;
  while (true) {
    try {
      return await transceive(command);
    } catch (error) {
      if (!isTransientError(error) || retries >= maxRetries) {
        rethrow;
      }
      retries++;
      final Duration retryDelay = Duration(
        milliseconds: baseDelay.inMilliseconds * retries,
      );
      onRetry?.call(retries, maxRetries, retryDelay, error);
      await delay(retryDelay);
    }
  }
}

Future<void> _defaultDelay(Duration duration) => Future<void>.delayed(duration);
