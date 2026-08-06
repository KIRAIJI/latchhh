import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_spacing.dart';
import '../../presentation/auth_screen_layout.dart';
import '../../presentation/auth_legal_links.dart';
import '../../presentation/auth_welcome_header.dart';

typedef RegisterSubmitCallback =
    void Function(
      String name,
      String email,
      String password,
      String passwordConfirmation,
      bool acceptedTerms,
      bool acknowledgedPrivacy,
    );
typedef GoogleRegisterCallback =
    void Function(bool acceptedTerms, bool acknowledgedPrivacy);

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({
    super.key,
    required this.onRegisterPressed,
    required this.onGooglePressed,
    required this.onSignInPressed,
    required this.onTermsPressed,
    required this.onPrivacyPressed,
    this.showGoogleSignIn = false,
    this.isLoading = false,
    this.errorMessage,
    this.nameError,
    this.emailError,
    this.passwordError,
    this.passwordConfirmationError,
    this.termsError,
    this.privacyError,
  });

  final RegisterSubmitCallback? onRegisterPressed;
  final GoogleRegisterCallback? onGooglePressed;
  final VoidCallback? onSignInPressed;
  final VoidCallback? onTermsPressed;
  final VoidCallback? onPrivacyPressed;
  final bool showGoogleSignIn;
  final bool isLoading;
  final String? errorMessage;
  final String? nameError;
  final String? emailError;
  final String? passwordError;
  final String? passwordConfirmationError;
  final String? termsError;
  final String? privacyError;

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _passwordConfirmationController =
      TextEditingController();
  bool _obscurePassword = true;
  bool _obscurePasswordConfirmation = true;
  bool _acceptedTerms = false;
  bool _acknowledgedPrivacy = false;

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    _passwordConfirmationController.dispose();
    super.dispose();
  }

  void _submit() {
    if (widget.isLoading) return;
    widget.onRegisterPressed?.call(
      _nameController.text,
      _emailController.text,
      _passwordController.text,
      _passwordConfirmationController.text,
      _acceptedTerms,
      _acknowledgedPrivacy,
    );
  }

  @override
  Widget build(BuildContext context) {
    return PopScope(
      canPop: false,
      onPopInvokedWithResult: (didPop, result) {
        if (!didPop) {
          widget.onSignInPressed?.call();
        }
      },
      child: AuthScreenLayout(
        form: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const AuthWelcomeHeader(
              title: 'Join LATCH',
              subtitle: 'Create your account and get started today.',
            ),
            const SizedBox(height: AppSpacing.lg),
            LatchTextField(
              controller: _nameController,
              label: 'Name',
              textInputAction: TextInputAction.next,
              errorText: widget.nameError,
              prefixIcon: const Icon(AppIcons.profile),
            ),
            const SizedBox(height: AppSpacing.sm),
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
              helperText:
                  '8–72 characters with uppercase, lowercase, number, and symbol.',
              obscureText: _obscurePassword,
              textInputAction: TextInputAction.next,
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
            const SizedBox(height: AppSpacing.sm),
            LatchTextField(
              controller: _passwordConfirmationController,
              label: 'Confirm Password',
              obscureText: _obscurePasswordConfirmation,
              textInputAction: TextInputAction.done,
              errorText: widget.passwordConfirmationError,
              prefixIcon: const Icon(AppIcons.lock),
              suffixIcon: IconButton(
                onPressed: () {
                  setState(
                    () => _obscurePasswordConfirmation =
                        !_obscurePasswordConfirmation,
                  );
                },
                icon: Icon(
                  _obscurePasswordConfirmation
                      ? Icons.visibility_outlined
                      : Icons.visibility_off_outlined,
                  color: AppColors.textSecondary,
                ),
                tooltip: _obscurePasswordConfirmation
                    ? 'Show password'
                    : 'Hide password',
              ),
            ),
            CheckboxListTile(
              contentPadding: EdgeInsets.zero,
              value: _acceptedTerms,
              onChanged: widget.isLoading
                  ? null
                  : (value) => setState(() => _acceptedTerms = value ?? false),
              title: Wrap(
                crossAxisAlignment: WrapCrossAlignment.center,
                children: [
                  const Text('I accept the '),
                  AuthLegalLink(
                    label: 'Terms of Service',
                    onPressed: widget.onTermsPressed,
                    style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                      fontWeight: FontWeight.w700,
                      decoration: TextDecoration.underline,
                    ),
                  ),
                ],
              ),
              subtitle: widget.termsError == null
                  ? null
                  : Text(
                      widget.termsError!,
                      style: const TextStyle(color: AppColors.error),
                    ),
              controlAffinity: ListTileControlAffinity.leading,
            ),
            CheckboxListTile(
              contentPadding: EdgeInsets.zero,
              value: _acknowledgedPrivacy,
              onChanged: widget.isLoading
                  ? null
                  : (value) =>
                        setState(() => _acknowledgedPrivacy = value ?? false),
              title: Wrap(
                crossAxisAlignment: WrapCrossAlignment.center,
                children: [
                  const Text('I have read the '),
                  AuthLegalLink(
                    label: 'Privacy Policy',
                    onPressed: widget.onPrivacyPressed,
                    style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                      fontWeight: FontWeight.w700,
                      decoration: TextDecoration.underline,
                    ),
                  ),
                ],
              ),
              subtitle: widget.privacyError == null
                  ? null
                  : Text(
                      widget.privacyError!,
                      style: const TextStyle(color: AppColors.error),
                    ),
              controlAffinity: ListTileControlAffinity.leading,
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
              label: 'Create Account',
              fullWidth: true,
              isLoading: widget.isLoading,
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
                onPressed: () => widget.onGooglePressed?.call(
                  _acceptedTerms,
                  _acknowledgedPrivacy,
                ),
              ),
            ],
          ],
        ),
        footerPrompt: 'Already have an account? ',
        footerActionLabel: 'Sign in',
        onFooterAction: widget.onSignInPressed,
      ),
    );
  }
}
