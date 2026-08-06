import 'package:flutter/material.dart';

class AuthLegalLink extends StatelessWidget {
  const AuthLegalLink({
    super.key,
    required this.label,
    required this.onPressed,
    this.style,
  });

  final String label;
  final VoidCallback? onPressed;
  final TextStyle? style;

  @override
  Widget build(BuildContext context) {
    final effectiveStyle =
        style ??
        Theme.of(context).textTheme.labelSmall?.copyWith(
          color: Theme.of(context).colorScheme.onSurface,
          fontWeight: FontWeight.w700,
          decoration: TextDecoration.underline,
        );

    return Semantics(
      link: true,
      child: InkWell(
        onTap: onPressed,
        borderRadius: BorderRadius.circular(4),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 2, vertical: 2),
          child: Text(label, style: effectiveStyle),
        ),
      ),
    );
  }
}

class AuthLegalNotice extends StatelessWidget {
  const AuthLegalNotice({
    super.key,
    required this.onTermsPressed,
    required this.onPrivacyPressed,
  });

  final VoidCallback? onTermsPressed;
  final VoidCallback? onPrivacyPressed;

  @override
  Widget build(BuildContext context) {
    final textStyle = Theme.of(context).textTheme.labelSmall;
    final linkStyle = textStyle?.copyWith(
      color: Theme.of(context).colorScheme.onSurface,
      fontWeight: FontWeight.w700,
      decoration: TextDecoration.underline,
    );

    return Wrap(
      alignment: WrapAlignment.center,
      crossAxisAlignment: WrapCrossAlignment.center,
      spacing: 2,
      runSpacing: 0,
      children: [
        Text('By continuing, you agree to the', style: textStyle),
        AuthLegalLink(
          label: 'Terms',
          onPressed: onTermsPressed,
          style: linkStyle,
        ),
        Text('and', style: textStyle),
        AuthLegalLink(
          label: 'Privacy Policy',
          onPressed: onPrivacyPressed,
          style: linkStyle,
        ),
      ],
    );
  }
}
