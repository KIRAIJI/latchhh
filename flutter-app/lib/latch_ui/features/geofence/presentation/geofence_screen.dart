import 'package:flutter/material.dart';

import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/components/inputs/latch_text_field.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_radius.dart';
import '../../../core/theme/app_spacing.dart';
import '../domain/geofence_radius.dart';

typedef GeofenceSaveCallback =
    void Function(
      String geofenceName,
      String radius,
      bool notifyOnEnter,
      bool notifyOnExit,
      bool isActive,
    );

enum _GeofenceRadiusMode { suggested, custom }

class GeofenceScreen extends StatefulWidget {
  const GeofenceScreen({
    super.key,
    required this.itemName,
    required this.mapContent,
    required this.onSavePressed,
    required this.onBackPressed,
    this.initialGeofenceName = '',
    this.initialRadius = '',
    this.initialNotifyOnEnter = false,
    this.initialNotifyOnExit = false,
    this.initialIsActive = false,
    this.hasExistingGeofence = false,
    this.isLoading = false,
    this.geofenceNameError,
    this.radiusError,
    this.syncErrorMessage,
    this.onDeletePressed,
    this.onRetryPressed,
    this.onRadiusChanged,
  });

  final String itemName;
  final Widget mapContent;
  final String initialGeofenceName;
  final String initialRadius;
  final bool initialNotifyOnEnter;
  final bool initialNotifyOnExit;
  final bool initialIsActive;
  final bool hasExistingGeofence;
  final bool isLoading;
  final String? geofenceNameError;
  final String? radiusError;
  final String? syncErrorMessage;
  final GeofenceSaveCallback? onSavePressed;
  final VoidCallback? onDeletePressed;
  final VoidCallback? onRetryPressed;
  final VoidCallback? onBackPressed;
  final ValueChanged<double>? onRadiusChanged;

  @override
  State<GeofenceScreen> createState() => _GeofenceScreenState();
}

class _GeofenceScreenState extends State<GeofenceScreen> {
  late final TextEditingController _geofenceNameController;
  late final TextEditingController _radiusController;
  late bool _notifyOnEnter;
  late bool _notifyOnExit;
  late bool _isActive;
  late _GeofenceRadiusMode _radiusMode;
  late double _radiusMeters;
  bool _suppressRadiusFieldSync = false;

  @override
  void initState() {
    super.initState();
    _geofenceNameController = TextEditingController(
      text: widget.initialGeofenceName,
    );
    _notifyOnEnter = widget.initialNotifyOnEnter;
    _notifyOnExit = widget.initialNotifyOnExit;
    _isActive = widget.initialIsActive;

    final parsed = double.tryParse(widget.initialRadius.trim());
    if (widget.initialRadius.trim().isEmpty ||
        (parsed != null && parsed == GeofenceRadius.suggestedMeters)) {
      _radiusMode = _GeofenceRadiusMode.suggested;
      _radiusMeters = GeofenceRadius.suggestedMeters;
    } else {
      _radiusMode = _GeofenceRadiusMode.custom;
      _radiusMeters = GeofenceRadius.clamp(
        parsed ?? GeofenceRadius.suggestedMeters,
      );
    }

    _radiusController = TextEditingController(
      text: GeofenceRadius.format(_effectiveRadiusMeters),
    );

    WidgetsBinding.instance.addPostFrameCallback((_) {
      widget.onRadiusChanged?.call(_effectiveRadiusMeters);
    });
  }

  @override
  void dispose() {
    _geofenceNameController.dispose();
    _radiusController.dispose();
    super.dispose();
  }

  double get _effectiveRadiusMeters {
    return _radiusMode == _GeofenceRadiusMode.suggested
        ? GeofenceRadius.suggestedMeters
        : _radiusMeters;
  }

  void _syncRadiusField(double radiusMeters) {
    _suppressRadiusFieldSync = true;
    _radiusController.text = GeofenceRadius.format(radiusMeters);
    _suppressRadiusFieldSync = false;
  }

  void _publishRadius() {
    widget.onRadiusChanged?.call(_effectiveRadiusMeters);
  }

  void _setCustomRadius(double radiusMeters) {
    final clamped = GeofenceRadius.clamp(radiusMeters);
    setState(() {
      _radiusMeters = clamped;
    });
    _syncRadiusField(clamped);
    _publishRadius();
  }

  void _handleRadiusModeChanged(_GeofenceRadiusMode? mode) {
    if (mode == null || mode == _radiusMode || !fieldsEnabled) {
      return;
    }

    setState(() {
      _radiusMode = mode;
      if (mode == _GeofenceRadiusMode.suggested) {
        _radiusMeters = GeofenceRadius.suggestedMeters;
      }
    });
    _syncRadiusField(_effectiveRadiusMeters);
    _publishRadius();
  }

  void _handleRadiusFieldChanged(String value) {
    if (_suppressRadiusFieldSync || _radiusMode != _GeofenceRadiusMode.custom) {
      return;
    }

    final parsed = double.tryParse(value.trim());
    if (parsed == null) {
      return;
    }

    _setCustomRadius(parsed);
  }

  void _handleSave() {
    if (widget.isLoading) {
      return;
    }

    widget.onSavePressed?.call(
      _geofenceNameController.text,
      GeofenceRadius.format(_effectiveRadiusMeters),
      _notifyOnEnter,
      _notifyOnExit,
      _isActive,
    );
  }

  bool get fieldsEnabled => !widget.isLoading;

  Widget _buildRadiusSection(TextTheme textTheme) {
    final bodyMedium = textTheme.bodyMedium;
    final labelStyle = textTheme.titleSmall;
    final mutedStyle = bodyMedium?.copyWith(color: AppColors.textSecondary);

    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Text('Radius', style: labelStyle),
        const SizedBox(height: AppSpacing.xs),
        RadioGroup<_GeofenceRadiusMode>(
          groupValue: _radiusMode,
          onChanged: fieldsEnabled ? _handleRadiusModeChanged : (_) {},
          child: Column(
            children: [
              RadioListTile<_GeofenceRadiusMode>(
                contentPadding: EdgeInsets.zero,
                title: const Text('Suggested Radius'),
                subtitle: Text(
                  GeofenceRadius.formatWithUnit(GeofenceRadius.suggestedMeters),
                  style: mutedStyle,
                ),
                value: _GeofenceRadiusMode.suggested,
                enabled: fieldsEnabled,
              ),
              RadioListTile<_GeofenceRadiusMode>(
                contentPadding: EdgeInsets.zero,
                title: const Text('Custom Radius'),
                value: _GeofenceRadiusMode.custom,
                enabled: fieldsEnabled,
              ),
            ],
          ),
        ),
        if (_radiusMode == _GeofenceRadiusMode.custom) ...[
          const SizedBox(height: AppSpacing.sm),
          Row(
            children: [
              Text(
                GeofenceRadius.formatWithUnit(GeofenceRadius.minMeters),
                style: mutedStyle,
              ),
              Expanded(
                child: Slider(
                  value: _radiusMeters,
                  min: GeofenceRadius.minMeters,
                  max: GeofenceRadius.maxMeters,
                  onChanged: fieldsEnabled ? _setCustomRadius : null,
                ),
              ),
              Text(
                GeofenceRadius.formatWithUnit(GeofenceRadius.maxMeters),
                style: mutedStyle,
              ),
            ],
          ),
          LatchTextField(
            controller: _radiusController,
            label: 'Radius (${GeofenceRadius.unitLabel})',
            hint: GeofenceRadius.format(GeofenceRadius.suggestedMeters),
            errorText: widget.radiusError,
            keyboardType: const TextInputType.numberWithOptions(decimal: true),
            textInputAction: TextInputAction.done,
            enabled: fieldsEnabled,
            suffixIcon: Padding(
              padding: const EdgeInsets.only(right: AppSpacing.md),
              child: Text(
                GeofenceRadius.unitLabel,
                style: bodyMedium?.copyWith(color: AppColors.textSecondary),
              ),
            ),
            onChanged: _handleRadiusFieldChanged,
          ),
        ] else ...[
          const SizedBox(height: AppSpacing.sm),
          Text(
            GeofenceRadius.formatWithUnit(GeofenceRadius.suggestedMeters),
            style: bodyMedium,
          ),
        ],
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final bottomInset = MediaQuery.viewInsetsOf(context).bottom;

    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        leading: BackButton(
          onPressed: widget.isLoading ? null : widget.onBackPressed,
        ),
        title: const Text('Geofence'),
      ),
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(
                AppSpacing.screenHorizontal,
                AppSpacing.sm,
                AppSpacing.screenHorizontal,
                AppSpacing.sm,
              ),
              child: Text(
                widget.itemName,
                style: textTheme.titleLarge,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: AppSpacing.screenHorizontal,
              ),
              child: LayoutBuilder(
                builder: (context, constraints) {
                  final mapHeight = (constraints.maxWidth * 9 / 16).clamp(
                    160.0,
                    220.0,
                  );

                  return SizedBox(
                    height: mapHeight,
                    child: ClipRRect(
                      borderRadius: const BorderRadius.all(
                        Radius.circular(AppRadius.large),
                      ),
                      child: widget.mapContent,
                    ),
                  );
                },
              ),
            ),
            Expanded(
              child: SingleChildScrollView(
                padding: EdgeInsets.fromLTRB(
                  AppSpacing.screenHorizontal,
                  AppSpacing.lg,
                  AppSpacing.screenHorizontal,
                  AppSpacing.lg + bottomInset,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Text(
                      'Move the map or marker to choose the geofence center. '
                      'You can set the center manually even when no item location '
                      'is available.',
                      style: textTheme.bodyMedium?.copyWith(
                        color: AppColors.textSecondary,
                      ),
                    ),
                    const SizedBox(height: AppSpacing.lg),
                    LatchTextField(
                      controller: _geofenceNameController,
                      label: 'Geofence Name',
                      hint: 'Home Zone',
                      errorText: widget.geofenceNameError,
                      textInputAction: TextInputAction.next,
                      enabled: fieldsEnabled,
                      prefixIcon: const Icon(AppIcons.location),
                    ),
                    const SizedBox(height: AppSpacing.md),
                    _buildRadiusSection(textTheme),
                    const SizedBox(height: AppSpacing.sm),
                    SwitchListTile.adaptive(
                      contentPadding: EdgeInsets.zero,
                      title: const Text('Notify when item enters'),
                      value: _notifyOnEnter,
                      onChanged: fieldsEnabled
                          ? (value) => setState(() => _notifyOnEnter = value)
                          : null,
                    ),
                    SwitchListTile.adaptive(
                      contentPadding: EdgeInsets.zero,
                      title: const Text('Notify when item exits'),
                      value: _notifyOnExit,
                      onChanged: fieldsEnabled
                          ? (value) => setState(() => _notifyOnExit = value)
                          : null,
                    ),
                    SwitchListTile.adaptive(
                      contentPadding: EdgeInsets.zero,
                      title: const Text('Active'),
                      value: _isActive,
                      onChanged: fieldsEnabled
                          ? (value) => setState(() => _isActive = value)
                          : null,
                    ),
                    if (widget.syncErrorMessage != null) ...[
                      const SizedBox(height: AppSpacing.md),
                      LatchErrorState(
                        message: widget.syncErrorMessage!,
                        onRetryPressed: widget.isLoading
                            ? null
                            : widget.onRetryPressed,
                        compact: true,
                      ),
                    ],
                    const SizedBox(height: AppSpacing.lg),
                    LatchButton(
                      label: 'Save',
                      fullWidth: true,
                      isLoading: widget.isLoading,
                      onPressed: _handleSave,
                    ),
                    if (widget.hasExistingGeofence) ...[
                      const SizedBox(height: AppSpacing.sm),
                      LatchButton(
                        label: 'Delete Geofence',
                        variant: LatchButtonVariant.destructive,
                        fullWidth: true,
                        enabled: !widget.isLoading,
                        onPressed: widget.onDeletePressed,
                      ),
                    ],
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
