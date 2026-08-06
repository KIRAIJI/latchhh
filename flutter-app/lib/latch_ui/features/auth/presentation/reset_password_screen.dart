import 'package:flutter/material.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/components/inputs/latch_text_field.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import '../../../core/validation/input_validation.dart';
import '../../../data/api/latch_api.dart';

class ResetPasswordScreen extends StatefulWidget {
  const ResetPasswordScreen({
    super.key,
    required this.controller,
    required this.email,
    required this.token,
    this.isPasswordSetup = false,
  });

  final LatchController controller;
  final String email;
  final String token;
  final bool isPasswordSetup;

  @override
  State<ResetPasswordScreen> createState() => _ResetPasswordScreenState();
}

class _ResetPasswordScreenState extends State<ResetPasswordScreen> {
  final _passwordController = TextEditingController();
  final _confirmationController = TextEditingController();
  bool _obscure = true;
  bool _loading = false;
  String? _passwordError;
  String? _confirmationError;
  String? _error;

  @override
  void dispose() {
    _passwordController.dispose();
    _confirmationController.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    final password = _passwordController.text;
    final confirmation = _confirmationController.text;
    final passwordError = InputValidation.newPassword(password);
    final confirmationError = InputValidation.passwordConfirmation(
      password,
      confirmation,
    );
    if (passwordError != null || confirmationError != null) {
      setState(() {
        _passwordError = passwordError;
        _confirmationError = confirmationError;
      });
      return;
    }

    setState(() {
      _loading = true;
      _passwordError = null;
      _confirmationError = null;
      _error = null;
    });
    try {
      await widget.controller.resetPassword(
        email: widget.email,
        token: widget.token,
        password: password,
        passwordConfirmation: confirmation,
      );
      if (!mounted) return;
      final messenger = ScaffoldMessenger.of(context);
      Navigator.pop(context);
      messenger.showSnackBar(
        SnackBar(
          content: Text(
            widget.isPasswordSetup
                ? 'Password set. Sign in with Google or your new password.'
                : 'Password reset. Sign in with your new password.',
          ),
        ),
      );
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _passwordError = error.fieldError('password');
        _confirmationError = error.fieldError('password_confirmation');
        _error = error.fieldError('token') ?? error.formMessage;
      });
    } on Object {
      if (mounted) {
        setState(
          () => _error = widget.isPasswordSetup
              ? 'Could not set the password.'
              : 'Could not reset the password.',
        );
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: Text(
          widget.isPasswordSetup ? 'Set Password' : 'Choose New Password',
        ),
      ),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
          children: [
            Text(widget.email, style: Theme.of(context).textTheme.titleMedium),
            const SizedBox(height: AppSpacing.lg),
            LatchTextField(
              controller: _passwordController,
              label: widget.isPasswordSetup ? 'Password' : 'New Password',
              helperText:
                  '8–72 characters with uppercase, lowercase, number, and symbol.',
              obscureText: _obscure,
              errorText: _passwordError,
              textInputAction: TextInputAction.next,
              suffixIcon: IconButton(
                onPressed: () => setState(() => _obscure = !_obscure),
                icon: Icon(
                  _obscure
                      ? Icons.visibility_outlined
                      : Icons.visibility_off_outlined,
                ),
              ),
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchTextField(
              controller: _confirmationController,
              label: widget.isPasswordSetup
                  ? 'Confirm Password'
                  : 'Confirm New Password',
              obscureText: _obscure,
              errorText: _confirmationError,
              textInputAction: TextInputAction.done,
            ),
            if (_error != null) ...[
              const SizedBox(height: AppSpacing.sm),
              Text(_error!, style: const TextStyle(color: AppColors.error)),
            ],
            const SizedBox(height: AppSpacing.md),
            LatchButton(
              label: widget.isPasswordSetup ? 'Set Password' : 'Reset Password',
              fullWidth: true,
              isLoading: _loading,
              onPressed: _submit,
            ),
          ],
        ),
      ),
    );
  }
}
