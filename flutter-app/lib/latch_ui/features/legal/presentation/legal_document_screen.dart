import 'package:flutter/material.dart';

import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';

enum LegalDocumentType { privacy, terms, dataSafety }

class LegalDocumentScreen extends StatelessWidget {
  const LegalDocumentScreen({super.key, required this.type});

  final LegalDocumentType type;

  static const supportEmail = String.fromEnvironment(
    'LATCH_SUPPORT_EMAIL',
    defaultValue: '',
  );

  @override
  Widget build(BuildContext context) {
    final document = _document(type);
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(title: Text(document.title)),
      body: SafeArea(
        child: SelectionArea(
          child: ListView(
            padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
            children: [
              Text(
                'Effective July 26, 2026',
                style: Theme.of(
                  context,
                ).textTheme.bodySmall?.copyWith(color: AppColors.textSecondary),
              ),
              const SizedBox(height: AppSpacing.md),
              for (final section in document.sections) ...[
                Text(
                  section.heading,
                  style: Theme.of(context).textTheme.titleMedium,
                ),
                const SizedBox(height: AppSpacing.xs),
                Text(
                  section.body,
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
                const SizedBox(height: AppSpacing.lg),
              ],
              if (supportEmail.isNotEmpty)
                Text(
                  'Contact: $supportEmail',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
            ],
          ),
        ),
      ),
    );
  }
}

typedef _LegalSection = ({String heading, String body});
typedef _LegalDocument = ({String title, List<_LegalSection> sections});

_LegalDocument _document(LegalDocumentType type) {
  return switch (type) {
    LegalDocumentType.privacy => (
      title: 'Privacy Policy',
      sections: const [
        (
          heading: 'Information we process',
          body:
              'LATCH processes your name, email address, password hash, optional '
              'profile photo, claimed tracker identifiers, precise tracker '
              'locations, geofences, tracker telemetry, notification history, '
              'device push token, and security/activity records. When production '
              'crash reporting is enabled, technical diagnostics such as app '
              'version, device type, stack traces, and a pseudonymous account ID '
              'may also be processed.',
        ),
        (
          heading: 'Why we use it',
          body:
              'We use this information to authenticate your account, show and '
              'synchronize your trackers, evaluate geofences, deliver alerts, '
              'support account recovery, secure the service, diagnose failures, '
              'and comply with legal obligations. LATCH does not use this data '
              'for advertising or sell personal information.',
        ),
        (
          heading: 'Service providers',
          body:
              'Tracker data is obtained through the configured tracker provider. '
              'Firebase Cloud Messaging delivers system notifications, and '
              'Firebase Crashlytics may process production diagnostics. Hosting, '
              'email, and storage providers process data only to operate LATCH.',
        ),
        (
          heading: 'Retention and deletion',
          body:
              'Current backend defaults retain precise location history for 30 '
              'days and activity history for 365 days. Notifications remain until '
              'you delete them or delete your account. Releasing an item removes '
              'claim-scoped telemetry from that item. Permanent account deletion '
              'removes the account, profile photo, geofences, notifications, '
              'positions, activity, sessions, and push tokens, subject to limited '
              'security logs and backup-expiry obligations.',
        ),
        (
          heading: 'Your choices and security',
          body:
              'You can disable all alerts or individual alert categories, remove '
              'your photo, release trackers, delete notifications, and permanently '
              'delete your account in the app. Data is transmitted over HTTPS in '
              'production, authentication tokens use secure device storage, and '
              'push tokens are encrypted at rest on the backend.',
        ),
      ],
    ),
    LegalDocumentType.terms => (
      title: 'Terms of Service',
      sections: const [
        (
          heading: 'Using LATCH',
          body:
              'You must provide accurate account information, protect your '
              'credentials, and use only trackers you own or are authorized to '
              'monitor. You are responsible for complying with privacy, property, '
              'and tracking laws that apply where you use the service.',
        ),
        (
          heading: 'Safety limitations',
          body:
              'LATCH is a convenience tracking service, not an emergency, medical, '
              'life-safety, or guaranteed theft-recovery system. Locations can be '
              'delayed or inaccurate because of GNSS reception, connectivity, '
              'battery state, provider outages, or device hardware.',
        ),
        (
          heading: 'Acceptable use',
          body:
              'Do not use LATCH for stalking, unauthorized surveillance, abuse, '
              'illegal activity, interference with the service, credential theft, '
              'or attempts to access another user’s data. Accounts may be limited '
              'or terminated when necessary to protect users or the service.',
        ),
        (
          heading: 'Availability and changes',
          body:
              'Features may change as tracker firmware, map services, mobile '
              'platforms, and infrastructure evolve. Planned retention or material '
              'terms changes should be communicated before they take effect. '
              'Service availability is not guaranteed during maintenance or '
              'events outside the operator’s reasonable control.',
        ),
        (
          heading: 'Account termination',
          body:
              'You may permanently delete your account in Settings. Deletion '
              'releases claimed trackers and removes associated account data as '
              'described in the Privacy Policy. These terms require final review '
              'for the operator’s legal identity, governing law, warranties, and '
              'liability terms before public release.',
        ),
      ],
    ),
    LegalDocumentType.dataSafety => (
      title: 'Data Safety',
      sections: const [
        (
          heading: 'Collected for app functionality',
          body:
              'Name and email, optional profile photo, precise tracker location, '
              'tracker identifiers and telemetry, notification preferences and '
              'history, account activity, and authentication data.',
        ),
        (
          heading: 'Collected for reliability and security',
          body:
              'Push registration token, app version, device platform, security '
              'events, and—when Firebase is enabled—crash and performance '
              'diagnostics. Advertising identifiers and contacts are not used.',
        ),
        (
          heading: 'Protection and control',
          body:
              'Production traffic must use HTTPS. Tokens are stored securely on '
              'the phone; backend push tokens are encrypted. Users can delete '
              'notifications and permanently delete their account in the app. '
              'The Play Console Data safety form must be reviewed and submitted '
              'by the developer account owner whenever app or SDK behavior changes.',
        ),
      ],
    ),
  };
}
