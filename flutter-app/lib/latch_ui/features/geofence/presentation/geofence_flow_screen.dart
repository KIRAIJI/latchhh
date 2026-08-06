import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../../../core/validation/input_validation.dart';
import '../../../data/api/latch_api.dart';
import '../../../data/models/latch_models.dart';
import '../domain/geofence_radius.dart';
import 'geofence_screen.dart';
import 'widgets/geofence_map.dart';

typedef GeofenceFlowSaveCallback =
    Future<void> Function(
      String name,
      double radiusMeters,
      bool notifyOnEnter,
      bool notifyOnExit,
      bool isActive,
      LatLng center,
    );

class GeofenceFlowScreen extends StatefulWidget {
  const GeofenceFlowScreen({
    super.key,
    required this.itemName,
    required this.initialCenter,
    this.devicePoint,
    this.initialGeofence,
    this.onSavePressed,
    this.onDeletePressed,
    this.onBackPressed,
  });

  final String itemName;
  final LatLng initialCenter;
  final LatLng? devicePoint;
  final LatchGeofence? initialGeofence;
  final GeofenceFlowSaveCallback? onSavePressed;
  final Future<void> Function()? onDeletePressed;
  final VoidCallback? onBackPressed;

  @override
  State<GeofenceFlowScreen> createState() => _GeofenceFlowScreenState();
}

class _GeofenceFlowScreenState extends State<GeofenceFlowScreen> {
  late double _radiusMeters;
  late LatLng _center;
  bool _isLoading = false;
  String? _nameError;
  String? _radiusError;
  String? _syncError;

  @override
  void initState() {
    super.initState();
    final parsed = widget.initialGeofence?.radiusMeters;
    _radiusMeters = parsed == null
        ? GeofenceRadius.suggestedMeters
        : GeofenceRadius.clamp(parsed);
    _center = widget.initialCenter;
  }

  Future<void> _save(
    String name,
    String radius,
    bool notifyOnEnter,
    bool notifyOnExit,
    bool isActive,
  ) async {
    final parsedRadius = double.tryParse(radius);
    final nextNameError = InputValidation.geofenceName(name);
    final nextRadiusError = InputValidation.geofenceRadius(parsedRadius);
    if (nextNameError != null || nextRadiusError != null) {
      setState(() {
        _nameError = nextNameError;
        _radiusError = nextRadiusError;
        _syncError = null;
      });
      return;
    }
    setState(() {
      _isLoading = true;
      _nameError = null;
      _radiusError = null;
      _syncError = null;
    });
    try {
      await widget.onSavePressed?.call(
        name,
        parsedRadius!,
        notifyOnEnter,
        notifyOnExit,
        isActive,
        _center,
      );
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _nameError = error.fieldError('name');
        _radiusError = error.fieldError('radius_meters');
        _syncError = error.formMessage;
      });
    } on Object {
      if (mounted) {
        setState(() => _syncError = 'Could not save the geofence.');
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _delete() async {
    setState(() {
      _isLoading = true;
      _syncError = null;
    });
    try {
      await widget.onDeletePressed?.call();
    } on LatchApiException catch (error) {
      if (mounted) setState(() => _syncError = error.message);
    } on Object {
      if (mounted) {
        setState(() => _syncError = 'Could not delete the geofence.');
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  void _handleRadiusChanged(double radiusMeters) {
    if (_radiusMeters == radiusMeters) {
      return;
    }
    setState(() => _radiusMeters = radiusMeters);
  }

  @override
  Widget build(BuildContext context) {
    return GeofenceScreen(
      itemName: widget.itemName,
      mapContent: GeofenceMap(
        initialCenter: widget.initialCenter,
        devicePoint: widget.devicePoint,
        radiusMeters: _radiusMeters,
        onCenterChanged: (center) => _center = center,
      ),
      initialGeofenceName: widget.initialGeofence?.name ?? '',
      initialRadius:
          widget.initialGeofence?.radiusMeters.toStringAsFixed(0) ?? '',
      initialNotifyOnEnter: widget.initialGeofence?.notifyOnEnter ?? false,
      initialNotifyOnExit: widget.initialGeofence?.notifyOnExit ?? false,
      initialIsActive: widget.initialGeofence?.isActive ?? true,
      hasExistingGeofence: widget.initialGeofence != null,
      isLoading: _isLoading,
      geofenceNameError: _nameError,
      radiusError: _radiusError,
      syncErrorMessage: _syncError,
      onSavePressed: _save,
      onDeletePressed: _delete,
      onRetryPressed: () => setState(() => _syncError = null),
      onBackPressed: widget.onBackPressed,
      onRadiusChanged: _handleRadiusChanged,
    );
  }
}
