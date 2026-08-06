import 'package:flutter/material.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/components/inputs/latch_text_field.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import '../../../core/validation/input_validation.dart';
import '../../../data/api/latch_api.dart';

class ForgotPasswordScreen extends StatefulWidget {
  const ForgotPasswordScreen({super.key, required this.controller});

  final LatchController controller;

  @override
  State<ForgotPasswordScreen> createState() => _ForgotPasswordScreenState();
}

class _ForgotPasswordScreenState extends State<ForgotPasswordScreen> {
  final _emailController = TextEditingController();
  bool _loading = false;
  bool _sent = false;
  String? _emailError;
  String? _error;

  @override
  void dispose() {
    _emailController.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    final email = _emailController.text.trim();
    final validation = InputValidation.email(email);
    if (validation != null) {
      setState(() => _emailError = validation);
      return;
    }

    setState(() {
      _loading = true;
      _emailError = null;
      _error = null;
    });
    try {
      await widget.controller.forgotPassword(email);
      if (mounted) setState(() => _sent = true);
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _emailError = error.fieldError('email');
        _error = error.formMessage;
      });
    } on Object {
      if (mounted) {
        setState(() => _error = 'Could not reach the LATCH server.');
      }
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(title: const Text('Reset Password')),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
          children: [
            const Icon(Icons.mark_email_read_outlined, size: 56),
            const SizedBox(height: AppSpacing.md),
            Text(
              _sent ? 'Check your email' : 'Forgot your password?',
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.headlineSmall,
            ),
            const SizedBox(height: AppSpacing.sm),
            Text(
              _sent
                  ? 'If an account exists for that address, we sent a secure '
                        'reset link. The same message is shown for unknown '
                        'addresses to protect account privacy.'
                  : 'Enter your account email and we will send a time-limited '
                        'password reset link.',
              textAlign: TextAlign.center,
              style: Theme.of(
                context,
              ).textTheme.bodyMedium?.copyWith(color: AppColors.textSecondary),
            ),
            const SizedBox(height: AppSpacing.lg),
            if (!_sent) ...[
              LatchTextField(
                controller: _emailController,
                label: 'Email',
                keyboardType: TextInputType.emailAddress,
                textInputAction: TextInputAction.done,
                errorText: _emailError,
              ),
              if (_error != null) ...[
                const SizedBox(height: AppSpacing.sm),
                Text(_error!, style: const TextStyle(color: AppColors.error)),
              ],
              const SizedBox(height: AppSpacing.md),
              LatchButton(
                label: 'Send Reset Link',
                fullWidth: true,
                isLoading: _loading,
                onPressed: _submit,
              ),
            ] else
              LatchButton(
                label: 'Back to Sign In',
                fullWidth: true,
                onPressed: () => Navigator.pop(context),
              ),
          ],
        ),
      ),
    );
  }
}
