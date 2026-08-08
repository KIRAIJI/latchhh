package com.latch.mobile

import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "com.latch.mobile/device",
        ).setMethodCallHandler { call, result ->
            if (call.method != "name") {
                result.notImplemented()
                return@setMethodCallHandler
            }

            val manufacturer = Build.MANUFACTURER.trim()
            val model = Build.MODEL.trim()
            val name = when {
                manufacturer.isEmpty() -> model
                model.isEmpty() -> manufacturer
                model.startsWith(manufacturer, ignoreCase = true) -> model
                else -> manufacturer.replaceFirstChar { it.uppercase() } + " " + model
            }
            result.success(name.ifEmpty { "Android device" })
        }
    }
}
