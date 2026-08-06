import 'package:flutter/material.dart';

import '../../theme/app_colors.dart';

/// Visual cellular signal strength with ascending bars (0–4).
class CellularSignalStrengthIndicator extends StatelessWidget {
  const CellularSignalStrengthIndicator({
    super.key,
    required this.barCount,
    this.label,
    this.activeColor = AppColors.textPrimary,
    this.inactiveColor = AppColors.divider,
    this.barWidth = 3,
    this.gap = 2,
  }) : assert(barCount >= 0 && barCount <= 4);

  final int barCount;
  final String? label;
  final Color activeColor;
  final Color inactiveColor;
  final double barWidth;
  final double gap;

  static const List<double> _barHeights = [5, 8, 11, 14];

  static String labelForBarCount(int barCount) {
    return switch (barCount) {
      0 => 'No Signal',
      1 => 'Weak',
      2 => 'Fair',
      3 => 'Good',
      4 => 'Strong',
      _ => 'No Signal',
    };
  }

  String get _resolvedLabel => label ?? labelForBarCount(barCount);

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final valueStyle = textTheme.bodyMedium?.copyWith(
      fontWeight: FontWeight.w500,
    );

    return Semantics(
      label: 'Network signal, $_resolvedLabel',
      child: SizedBox(
        width: double.infinity,
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            _SignalBars(
              barCount: barCount,
              activeColor: activeColor,
              inactiveColor: inactiveColor,
              barWidth: barWidth,
              gap: gap,
              barHeights: _barHeights,
            ),
            const Spacer(),
            Text(
              _resolvedLabel,
              style: valueStyle,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
          ],
        ),
      ),
    );
  }
}

class _SignalBars extends StatelessWidget {
  const _SignalBars({
    required this.barCount,
    required this.activeColor,
    required this.inactiveColor,
    required this.barWidth,
    required this.gap,
    required this.barHeights,
  });

  final int barCount;
  final Color activeColor;
  final Color inactiveColor;
  final double barWidth;
  final double gap;
  final List<double> barHeights;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: barHeights.last,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.end,
        children: List.generate(barHeights.length, (index) {
          final filled = barCount >= index + 1;
          return Padding(
            padding: EdgeInsets.only(left: index == 0 ? 0 : gap),
            child: DecoratedBox(
              decoration: BoxDecoration(
                color: filled ? activeColor : inactiveColor,
                borderRadius: BorderRadius.circular(1),
              ),
              child: SizedBox(width: barWidth, height: barHeights[index]),
            ),
          );
        }),
      ),
    );
  }
}
