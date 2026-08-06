import 'package:shared_preferences/shared_preferences.dart';

/// Persists whether the first-launch onboarding has been completed.
abstract final class OnboardingStorage {
  static const String _completedKey = 'latch_onboarding_completed';

  static Future<bool> isCompleted() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getBool(_completedKey) ?? false;
  }

  static Future<void> markCompleted() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool(_completedKey, true);
  }

  /// Clears the flag so onboarding shows again (useful while testing).
  static Future<void> reset() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_completedKey);
  }
}
