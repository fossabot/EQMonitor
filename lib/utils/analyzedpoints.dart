import 'package:flutter/material.dart' show Color;
import 'package:get/get.dart';

class AnalyzedPoint extends GetxController {
  final String code;
  final String name;
  final String pref;
  final double lat;
  final double lon;
  final int x;
  final int y;
  Color color;
  double? shindo;
  double zoomLevel;
  AnalyzedPoint({
    required this.code,
    required this.name,
    required this.pref,
    required this.lat,
    required this.lon,
    required this.x,
    required this.y,
    required this.color,
    required this.shindo,
    required this.zoomLevel,
  });
}
