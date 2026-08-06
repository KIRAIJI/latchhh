import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_spacing.dart';
import '../../presentation/auth_screen_layout.dart';
import '../../presentation/auth_legal_links.dart';
import '../../presentation/auth_welcome_header.dart';

typedef LoginSubmitCallback = void Function(String email, String password);

class LoginScreen extends StatefulWidget {
  const LoginScreen({
    super.key,
    required this.onLoginPressed,
    required this.onGooglePressed,
    required this.onRegisterPressed,
    required this.onForgotPasswordPressed,
    required this.onTermsPressed,
    required this.onPrivacyPressed,
    this.showGoogleSignIn = false,
    this.isLoading = false,
    this.errorMessage,
    this.emailError,
    this.passwordError,
    this.retryAfterSeconds = 0,
  });

  final LoginSubmitCallback? onLoginPressed;
  final VoidCallback? onGooglePressed;
  final VoidCallback? onRegisterPressed;
  final VoidCallback? onForgotPasswordPressed;
  final VoidCallback? onTermsPressed;
  final VoidCallback? onPrivacyPressed;
  final bool showGoogleSignIn;
  final bool isLoading;
  final String? errorMessage;
  final String? emailError;
  final String? passwordError;
  final int retryAfterSeconds;

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscurePassword = true;

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _submit() {
    if (widget.isLoading || widget.retryAfterSeconds > 0) return;
    widget.onLoginPressed?.call(
      _emailController.text,
      _passwordController.text,
    );
  }

  @override
  Widget build(BuildContext context) {
    final isRateLimited = widget.retryAfterSeconds > 0;

    return AuthScreenLayout(
      form: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          const AuthWelcomeHeader(
            title: 'Welcome back!',
            subtitle: 'Sign in to continue your LATCH journey.',
          ),
          const SizedBox(height: AppSpacing.lg),
          LatchTextField(
            controller: _emailController,
            label: 'Email',
            keyboardType: TextInputType.emailAddress,
            textInputAction: TextInputAction.next,
            errorText: widget.emailError,
            prefixIcon: const Icon(AppIcons.email),
          ),
          const SizedBox(height: AppSpacing.sm),
          LatchTextField(
            controller: _passwordController,
            label: 'Password',
            obscureText: _obscurePassword,
            textInputAction: TextInputAction.done,
            errorText: widget.passwordError,
            prefixIcon: const Icon(AppIcons.lock),
            suffixIcon: IconButton(
              onPressed: () {
                setState(() => _obscurePassword = !_obscurePassword);
              },
              icon: Icon(
                _obscurePassword
                    ? Icons.visibility_outlined
                    : Icons.visibility_off_outlined,
                color: AppColors.textSecondary,
              ),
              tooltip: _obscurePassword ? 'Show password' : 'Hide password',
            ),
          ),
          Align(
            alignment: Alignment.centerRight,
            child: TextButton(
              onPressed: widget.isLoading
                  ? null
                  : widget.onForgotPasswordPressed,
              child: const Text('Forgot password?'),
            ),
          ),
          if (widget.errorMessage != null) ...[
            const SizedBox(height: AppSpacing.sm),
            Text(
              widget.errorMessage!,
              style: Theme.of(
                context,
              ).textTheme.bodySmall?.copyWith(color: AppColors.error),
            ),
          ],
          const SizedBox(height: AppSpacing.md),
          LatchButton(
            label: isRateLimited
                ? 'Try again in ${widget.retryAfterSeconds}s'
                : 'Sign In',
            fullWidth: true,
            isLoading: widget.isLoading,
            enabled: !isRateLimited,
            onPressed: _submit,
          ),
          if (widget.showGoogleSignIn) ...[
            const SizedBox(height: AppSpacing.md),
            const Row(
              children: [
                Expanded(child: Divider()),
                Padding(
                  padding: EdgeInsets.symmetric(horizontal: AppSpacing.sm),
                  child: Text('or'),
                ),
                Expanded(child: Divider()),
              ],
            ),
            const SizedBox(height: AppSpacing.md),
            LatchButton(
              label: 'Continue with Google',
              variant: LatchButtonVariant.secondary,
              fullWidth: true,
              isLoading: widget.isLoading,
              onPressed: widget.onGooglePressed,
            ),
            const SizedBox(height: AppSpacing.sm),
            AuthLegalNotice(
              onTermsPressed: widget.onTermsPressed,
              onPrivacyPressed: widget.onPrivacyPressed,
            ),
          ],
        ],
      ),
      footerPrompt: 'New to LATCH? ',
      footerActionLabel: 'Create an account',
      onFooterAction: widget.onRegisterPressed,
    );
  }
}
