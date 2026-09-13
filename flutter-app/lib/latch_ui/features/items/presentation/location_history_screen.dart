import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/feedback/latch_empty_state.dart';
import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/feedback/latch_loading_state.dart';
import '../../../core/map/latch_google_map.dart';
import '../../../core/services/location_address_resolver.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import '../../../data/api/latch_api.dart';
import '../../../data/models/latch_models.dart';

class LocationHistoryScreen extends StatefulWidget {
  const LocationHistoryScreen({
    super.key,
    required this.controller,
    required this.item,
  });

  final LatchController controller;
  final LatchItem item;

  @override
  State<LocationHistoryScreen> createState() => _LocationHistoryScreenState();
}

class _LocationHistoryScreenState extends State<LocationHistoryScreen> {
  final _addressResolver = LocationAddressResolver.shared;
  GoogleMapController? _mapController;
  List<LatchPosition>? _positions;
  String? _error;
  int? _selectedPositionIndex;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    _mapController = null;
    setState(() {
      _positions = null;
      _error = null;
    });
    try {
      final positions = await widget.controller.loadLocationHistory(
        widget.item.id,
      );
      positions.sort(
        (left, right) => left.recordedAt.compareTo(right.recordedAt),
      );
      if (mounted) {
        setState(() {
          _positions = positions;
          _selectedPositionIndex = positions.isEmpty
              ? null
              : positions.length - 1;
        });
      }
    } on Object catch (error) {
      if (!mounted) return;
      setState(
        () => _error = error is LatchApiException
            ? error.message
            : 'Could not load location history.',
      );
    }
  }

  @override
  void dispose() {
    _mapController?.dispose();
    super.dispose();
  }

  Future<void> _focusPosition(int index) async {
    final positions = _positions;
    final controller = _mapController;
    if (positions == null ||
        controller == null ||
        index < 0 ||
        index >= positions.length) {
      return;
    }

    final position = positions[index];
    setState(() => _selectedPositionIndex = index);
    await controller.animateCamera(
      CameraUpdate.newLatLngZoom(
        LatLng(position.latitude, position.longitude),
        17,
      ),
    );
  }

  Future<void> _fitHistory() async {
    final controller = _mapController;
    final positions = _positions;
    if (!mounted ||
        controller == null ||
        positions == null ||
        positions.isEmpty) {
      return;
    }

    final points = positions
        .map((position) => LatLng(position.latitude, position.longitude))
        .toList(growable: false);
    if (points.length == 1) {
      await controller.animateCamera(
        CameraUpdate.newLatLngZoom(points.single, 17),
      );
      return;
    }

    final bounds = LatchGoogleMap.boundsFor(points);
    if (bounds != null) {
      await controller.animateCamera(CameraUpdate.newLatLngBounds(bounds, 36));
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    _mapController = controller;
    WidgetsBinding.instance.addPostFrameCallback((_) {
      unawaited(_fitHistory());
    });
  }

  String _formatTime(DateTime value) {
    final local = value.toLocal();
    final month = local.month.toString().padLeft(2, '0');
    final day = local.day.toString().padLeft(2, '0');
    final hour = local.hour.toString().padLeft(2, '0');
    final minute = local.minute.toString().padLeft(2, '0');
    return '${local.year}-$month-$day $hour:$minute';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: Text('${widget.item.itemName} History'),
        actions: [
          IconButton(
            onPressed: _positions == null ? null : _load,
            tooltip: 'Refresh history',
            icon: const Icon(Icons.refresh_rounded),
          ),
        ],
      ),
      body: SafeArea(child: _buildBody()),
    );
  }

  Widget _buildBody() {
    if (_positions == null && _error == null) {
      return const LatchLoadingState(message: 'Loading real location history…');
    }
    if (_error != null) {
      return LatchErrorState(message: _error!, onRetryPressed: _load);
    }

    final positions = _positions!;
    if (positions.isEmpty) {
      return const LatchEmptyState(
        icon: Icons.route_outlined,
        title: 'No recorded positions in the last 24 hours.',
      );
    }

    final points = positions
        .map((position) => LatLng(position.latitude, position.longitude))
        .toList(growable: false);

    return Column(
      children: [
        Expanded(
          flex: 3,
          child: Semantics(
            label: 'Map showing recorded device locations',
            child: GoogleMap(
              initialCameraPosition: CameraPosition(
                target: points.last,
                zoom: points.length == 1 ? 17 : 13,
              ),
              onMapCreated: _onMapCreated,
              minMaxZoomPreference: const MinMaxZoomPreference(3, 19),
              polylines: {
                Polyline(
                  polylineId: const PolylineId('location-history'),
                  points: points,
                  color: AppColors.trackerAccent,
                  width: 4,
                ),
              },
              circles: {
                for (var index = 0; index < points.length; index++)
                  Circle(
                    circleId: CircleId('history-position-$index'),
                    center: points[index],
                    radius: index == _selectedPositionIndex ? 5 : 3,
                    fillColor: index == _selectedPositionIndex
                        ? AppColors.trackerAccent
                        : AppColors.trackerAccent.withValues(alpha: 0.45),
                    strokeColor: AppColors.surface,
                    strokeWidth: 1,
                    consumeTapEvents: true,
                    onTap: () => unawaited(_focusPosition(index)),
                  ),
              },
              markers: {
                if (points.length > 1)
                  Marker(
                    markerId: const MarkerId('history-start'),
                    position: points.first,
                    icon: BitmapDescriptor.defaultMarkerWithHue(
                      BitmapDescriptor.hueGreen,
                    ),
                    infoWindow: const InfoWindow(title: 'Route start'),
                  ),
                Marker(
                  markerId: const MarkerId('history-end'),
                  position: points.last,
                  infoWindow: const InfoWindow(title: 'Latest recorded point'),
                ),
              },
              compassEnabled: true,
              mapToolbarEnabled: false,
              myLocationButtonEnabled: false,
              zoomControlsEnabled: false,
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(
            AppSpacing.md,
            AppSpacing.xs,
            AppSpacing.md,
            0,
          ),
          child: Text(
            'Recorded GPS samples are connected directly and are not road-snapped.',
            style: Theme.of(
              context,
            ).textTheme.labelSmall?.copyWith(color: AppColors.textSecondary),
            textAlign: TextAlign.center,
          ),
        ),
        Expanded(
          flex: 2,
          child: ListView.separated(
            padding: const EdgeInsets.all(AppSpacing.md),
            itemCount: positions.length,
            separatorBuilder: (_, _) => const Divider(),
            itemBuilder: (context, index) {
              final positionIndex = positions.length - index - 1;
              final position = positions[positionIndex];
              return ListTile(
                dense: true,
                selected: positionIndex == _selectedPositionIndex,
                onTap: () => unawaited(_focusPosition(positionIndex)),
                leading: const Icon(Icons.location_on_outlined),
                title: Text(_formatTime(position.recordedAt)),
                subtitle: FutureBuilder<String>(
                  key: ValueKey('${position.latitude},${position.longitude}'),
                  future: _addressResolver.resolve(
                    position.latitude,
                    position.longitude,
                  ),
                  builder: (context, snapshot) => Text(
                    snapshot.data ?? 'Finding address…',
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}
