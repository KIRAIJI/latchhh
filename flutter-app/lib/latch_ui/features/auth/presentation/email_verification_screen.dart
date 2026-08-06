import 'package:flutter/material.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';

class EmailVerificationScreen extends StatefulWidget {
  const EmailVerificationScreen({
    super.key,
    required this.controller,
    required this.onVerified,
    required this.onSignedOut,
  });

  final LatchController controller;
  final Future<void> Function() onVerified;
  final VoidCallback onSignedOut;

  @override
  State<EmailVerificationScreen> createState() =>
      _EmailVerificationScreenState();
}

class _EmailVerificationScreenState extends State<EmailVerificationScreen> {
  bool _checking = false;
  bool _resending = false;

  Future<void> _check() async {
    setState(() => _checking = true);
    try {
      await widget.onVerified();
    } finally {
      if (mounted) setState(() => _checking = false);
    }
  }

  Future<void> _resend() async {
    setState(() => _resending = true);
    try {
      await widget.controller.resendEmailVerification();
      if (!mounted) return;
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Verification email sent.')));
    } on Object {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Could not resend verification email.')),
        );
      }
    } finally {
      if (mounted) setState(() => _resending = false);
    }
  }

  Future<void> _logout() async {
    await widget.controller.logout();
    widget.onSignedOut();
  }

  @override
  Widget build(BuildContext context) {
    final email = widget.controller.user?.email ?? '';
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                const Icon(Icons.outgoing_mail, size: 64),
                const SizedBox(height: AppSpacing.md),
                Text(
                  'Verify your email',
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                const SizedBox(height: AppSpacing.sm),
                Text(
                  'We sent a verification link to\n$email',
                  textAlign: TextAlign.center,
                  style: Theme.of(context).textTheme.bodyLarge,
                ),
                const SizedBox(height: AppSpacing.sm),
                Text(
                  'Open the link on this device, then return here. Check your '
                  'spam folder if it is not in your inbox.',
                  textAlign: TextAlign.center,
                  style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                    color: AppColors.textSecondary,
                  ),
                ),
                const SizedBox(height: AppSpacing.lg),
                LatchButton(
                  label: 'I Have Verified My Email',
                  fullWidth: true,
                  isLoading: _checking,
                  onPressed: _check,
                ),
                const SizedBox(height: AppSpacing.sm),
                LatchButton(
                  label: 'Resend Verification Email',
                  variant: LatchButtonVariant.secondary,
                  fullWidth: true,
                  isLoading: _resending,
                  onPressed: _resend,
                ),
                const SizedBox(height: AppSpacing.sm),
                TextButton(
                  onPressed: _logout,
                  child: const Text('Sign in with another account'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
