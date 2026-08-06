import 'package:flutter/material.dart';

class LatchTextField extends StatelessWidget {
  const LatchTextField({
    super.key,
    this.controller,
    this.label,
    this.hint,
    this.helperText,
    this.errorText,
    this.prefixIcon,
    this.suffixIcon,
    this.obscureText = false,
    this.keyboardType,
    this.textInputAction,
    this.enabled = true,
    this.onChanged,
    this.validator,
  });

  final TextEditingController? controller;
  final String? label;
  final String? hint;
  final String? helperText;
  final String? errorText;
  final Widget? prefixIcon;
  final Widget? suffixIcon;
  final bool obscureText;
  final TextInputType? keyboardType;
  final TextInputAction? textInputAction;
  final bool enabled;
  final ValueChanged<String>? onChanged;
  final FormFieldValidator<String>? validator;

  @override
  Widget build(BuildContext context) {
    final decoration = InputDecoration(
      labelText: label,
      hintText: hint,
      helperText: helperText,
      helperMaxLines: 3,
      errorText: errorText,
      errorMaxLines: 3,
      prefixIcon: prefixIcon,
      suffixIcon: suffixIcon,
    );

    if (validator != null) {
      return TextFormField(
        controller: controller,
        decoration: decoration,
        obscureText: obscureText,
        keyboardType: keyboardType,
        textInputAction: textInputAction,
        enabled: enabled,
        onChanged: onChanged,
        validator: validator,
      );
    }

    return TextField(
      controller: controller,
      decoration: decoration,
      obscureText: obscureText,
      keyboardType: keyboardType,
      textInputAction: textInputAction,
      enabled: enabled,
      onChanged: onChanged,
    );
  }
}
