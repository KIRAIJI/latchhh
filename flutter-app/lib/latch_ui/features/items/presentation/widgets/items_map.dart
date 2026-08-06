import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../../../../core/map/latch_google_map.dart';
import '../../../../data/models/latch_models.dart';

class ItemsMap extends StatefulWidget {
  const ItemsMap({super.key, required this.items});

  final List<LatchItem> items;

  @override
  State<ItemsMap> createState() => _ItemsMapState();
}

class _ItemsMapState extends State<ItemsMap> {
  GoogleMapController? _controller;

  List<({LatchItem item, LatLng point})> get _locatedItems {
    return widget.items
        .where((item) => item.location.hasCoordinates)
        .map(
          (item) => (
            item: item,
            point: LatLng(item.location.latitude!, item.location.longitude!),
          ),
        )
        .toList(growable: false);
  }

  @override
  void didUpdateWidget(ItemsMap oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (_locationSignature(oldWidget.items) !=
        _locationSignature(widget.items)) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        unawaited(_fitItems());
      });
    }
  }

  String _locationSignature(List<LatchItem> items) {
    final entries =
        items
            .where((item) => item.location.hasCoordinates)
            .map(
              (item) =>
                  '${item.id}:${item.location.latitude}:${item.location.longitude}',
            )
            .toList()
          ..sort();
    return entries.join('|');
  }

  Future<void> _fitItems() async {
    final controller = _controller;
    if (!mounted || controller == null) return;

    final points = _locatedItems.map((entry) => entry.point).toList();
    if (points.isEmpty) return;

    if (points.length == 1) {
      await controller.animateCamera(
        CameraUpdate.newLatLngZoom(points.single, 16),
      );
      return;
    }

    final bounds = LatchGoogleMap.boundsFor(points);
    if (bounds != null) {
      await controller.animateCamera(CameraUpdate.newLatLngBounds(bounds, 56));
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    _controller = controller;
    WidgetsBinding.instance.addPostFrameCallback((_) {
      unawaited(_fitItems());
    });
  }

  @override
  void dispose() {
    _controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final locatedItems = _locatedItems;
    final initialCenter = locatedItems.isEmpty
        ? LatchGoogleMap.fallbackCenter
        : locatedItems.first.point;

    return Semantics(
      label: 'Map showing item locations',
      child: GoogleMap(
        initialCameraPosition: CameraPosition(
          target: initialCenter,
          zoom: locatedItems.length == 1 ? 16 : 13,
        ),
        onMapCreated: _onMapCreated,
        markers: {
          for (final entry in locatedItems)
            Marker(
              markerId: MarkerId('item-${entry.item.id}'),
              position: entry.point,
              icon: BitmapDescriptor.defaultMarkerWithHue(
                BitmapDescriptor.hueOrange,
              ),
              infoWindow: InfoWindow(
                title: entry.item.itemName,
                snippet: _locationLabel(entry.item.location.type),
              ),
            ),
        },
        compassEnabled: true,
        mapToolbarEnabled: false,
        myLocationButtonEnabled: false,
        zoomControlsEnabled: false,
      ),
    );
  }

  String _locationLabel(String type) {
    return switch (type) {
      'current' => 'Current location',
      'last_known' => 'Last known location',
      _ => 'Location unavailable',
    };
  }
}
