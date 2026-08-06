import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../../../../core/theme/app_colors.dart';

class GeofenceMap extends StatefulWidget {
  const GeofenceMap({
    super.key,
    required this.initialCenter,
    required this.radiusMeters,
    this.devicePoint,
    this.onCenterChanged,
  });

  final LatLng initialCenter;
  final LatLng? devicePoint;
  final double radiusMeters;
  final ValueChanged<LatLng>? onCenterChanged;

  @override
  State<GeofenceMap> createState() => _GeofenceMapState();
}

class _GeofenceMapState extends State<GeofenceMap> {
  GoogleMapController? _mapController;
  late LatLng _center;
  bool _userAdjustedCenter = false;

  @override
  void initState() {
    super.initState();
    _center = widget.initialCenter;
    WidgetsBinding.instance.addPostFrameCallback((_) {
      widget.onCenterChanged?.call(_center);
    });
  }

  @override
  void didUpdateWidget(GeofenceMap oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (!_userAdjustedCenter &&
        oldWidget.initialCenter != widget.initialCenter) {
      _setCenter(widget.initialCenter, fromUser: false, moveMap: true);
    }
  }

  void _setCenter(
    LatLng center, {
    required bool fromUser,
    bool moveMap = false,
  }) {
    if (_center != center) {
      setState(() {
        _center = center;
        if (fromUser) _userAdjustedCenter = true;
      });
    } else if (fromUser) {
      _userAdjustedCenter = true;
    }

    if (moveMap) {
      final controller = _mapController;
      if (controller != null) {
        unawaited(controller.animateCamera(CameraUpdate.newLatLng(center)));
      }
    }

    widget.onCenterChanged?.call(center);
  }

  void _onMapCreated(GoogleMapController controller) {
    _mapController = controller;
  }

  @override
  void dispose() {
    _mapController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final devicePoint = widget.devicePoint;
    final showDeviceMarker = devicePoint != null && devicePoint != _center;

    return Semantics(
      label: 'Interactive geofence map',
      child: GoogleMap(
        initialCameraPosition: CameraPosition(target: _center, zoom: 13),
        onMapCreated: _onMapCreated,
        onTap: (point) => _setCenter(point, fromUser: true),
        circles: {
          Circle(
            circleId: const CircleId('geofence-radius'),
            center: _center,
            radius: widget.radiusMeters,
            fillColor: AppColors.trackerAccent.withValues(alpha: 0.18),
            strokeColor: AppColors.trackerAccent,
            strokeWidth: 2,
          ),
        },
        markers: {
          if (showDeviceMarker)
            Marker(
              markerId: const MarkerId('device-location'),
              position: devicePoint,
              icon: BitmapDescriptor.defaultMarkerWithHue(
                BitmapDescriptor.hueAzure,
              ),
              infoWindow: const InfoWindow(title: 'Device location'),
            ),
          Marker(
            markerId: const MarkerId('geofence-center'),
            position: _center,
            draggable: true,
            onDragEnd: (point) => _setCenter(point, fromUser: true),
            infoWindow: const InfoWindow(title: 'Geofence center'),
          ),
        },
        compassEnabled: true,
        mapToolbarEnabled: false,
        myLocationButtonEnabled: false,
        zoomControlsEnabled: false,
      ),
    );
  }
}
