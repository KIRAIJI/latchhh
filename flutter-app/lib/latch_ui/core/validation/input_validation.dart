import 'dart:convert';

abstract final class InputValidation {
  static String? name(String value) {
    final normalized = value.trim();
    if (normalized.isEmpty) return 'Name is required.';
    if (normalized.length > 100) return 'Name must be 100 characters or fewer.';
    return null;
  }

  static String? email(String value) {
    final normalized = value.trim();
    if (normalized.isEmpty) return 'Email is required.';
    if (normalized.length > 255 ||
        !RegExp(r'^[^@\s]+@[^@\s]+\.[^@\s]+$').hasMatch(normalized)) {
      return 'Enter a valid email address.';
    }
    return null;
  }

  static String? requiredPassword(String value) {
    return value.isEmpty ? 'Password is required.' : null;
  }

  static String? newPassword(String value) {
    final byteLength = utf8.encode(value).length;
    final hasRequiredCharacters =
        RegExp('[a-z]').hasMatch(value) &&
        RegExp('[A-Z]').hasMatch(value) &&
        RegExp('[0-9]').hasMatch(value) &&
        RegExp(r'[^A-Za-z0-9]').hasMatch(value);

    if (byteLength < 8 || byteLength > 72 || !hasRequiredCharacters) {
      return 'Use 8–72 characters with uppercase, lowercase, number, and symbol.';
    }
    return null;
  }

  static String? passwordConfirmation(String password, String confirmation) {
    if (confirmation.isEmpty) return 'Please confirm your password.';
    if (password != confirmation) return 'Passwords do not match.';
    return null;
  }

  static String? deviceUid(String value) {
    if (!RegExp(r'^LATCH-[A-Z0-9]{4}-[A-Z0-9]{4}$').hasMatch(value)) {
      return 'Use the format LATCH-XXXX-XXXX.';
    }
    return null;
  }

  static String? itemName(String value) {
    final normalized = value.trim();
    if (normalized.isEmpty) return 'Item name is required.';
    if (normalized.length > 100) {
      return 'Item name must be 100 characters or fewer.';
    }
    return null;
  }

  static String? geofenceName(String value) {
    final normalized = value.trim();
    if (normalized.isEmpty) return 'Geofence name is required.';
    if (normalized.length > 100) {
      return 'Geofence name must be 100 characters or fewer.';
    }
    return null;
  }

  static String? geofenceRadius(double? value) {
    if (value == null || value < 50 || value > 5000) {
      return 'Radius must be between 50 and 5,000 meters.';
    }
    return null;
  }
}
