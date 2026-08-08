import Flutter
import GoogleMaps
import UIKit

@main
@objc class AppDelegate: FlutterAppDelegate, FlutterImplicitEngineDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    if let mapsApiKey = Bundle.main.object(forInfoDictionaryKey: "GMSApiKey") as? String,
       !mapsApiKey.isEmpty,
       mapsApiKey != "DEFAULT_API_KEY" {
      GMSServices.provideAPIKey(mapsApiKey)
    }
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }

  func didInitializeImplicitFlutterEngine(_ engineBridge: FlutterImplicitEngineBridge) {
    GeneratedPluginRegistrant.register(with: engineBridge.pluginRegistry)
    if let registrar = engineBridge.pluginRegistry.registrar(forPlugin: "LatchDeviceInfo") {
      FlutterMethodChannel(
        name: "com.latch.mobile/device",
        binaryMessenger: registrar.messenger()
      ).setMethodCallHandler { call, result in
        guard call.method == "name" else {
          result(FlutterMethodNotImplemented)
          return
        }
        let deviceName = UIDevice.current.name.trimmingCharacters(in: .whitespacesAndNewlines)
        result(deviceName.isEmpty ? UIDevice.current.model : deviceName)
      }
    }
  }
}
