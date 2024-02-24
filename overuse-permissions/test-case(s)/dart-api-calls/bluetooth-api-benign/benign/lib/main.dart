import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:flutter_bluetooth_serial/flutter_bluetooth_serial.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Bluetooth Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  BluetoothState _bluetoothState = BluetoothState.UNKNOWN;

  @override
  void initState() {
    super.initState();
    initBluetooth();
    // Listen for Bluetooth state changes
    FlutterBluetoothSerial.instance
        .onStateChanged()
        .listen((BluetoothState state) {
      setState(() {
        _bluetoothState = state;
      });
    });
  }

  Future<void> initBluetooth() async {
    // Request Bluetooth permission
    Map<Permission, PermissionStatus> permissions = await [
      Permission.bluetooth,
      Permission.bluetoothConnect
    ].request();

    // Check if permissions are denied
    if (!permissions.values.every((status) => status.isGranted)) {
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: Text('Bluetooth Permissions Required'),
          content: Text(
              'This app requires Bluetooth permissions to work properly.'),
          actions: [
            TextButton(
              onPressed: () => SystemNavigator.pop(),
              child: Text('Close App'),
            ),
          ],
        ),
      );
      return;
    }

    // Check Bluetooth state
    final bluetoothState = await FlutterBluetoothSerial.instance.state;

    setState(() {
      _bluetoothState = bluetoothState;
    });
  }

  void _toggleBluetooth() async {
    try {
      if (_bluetoothState == BluetoothState.STATE_OFF) {
        // Request to enable Bluetooth
        await FlutterBluetoothSerial.instance.requestEnable();
      } else {
        // Request to disable Bluetooth
        final isEnabled = await FlutterBluetoothSerial.instance.isEnabled;
        if (isEnabled != null  && isEnabled) {
          await FlutterBluetoothSerial.instance.requestDisable();
          // Update Bluetooth state
          setState(() {
            _bluetoothState = BluetoothState.STATE_OFF;
          });
        }
      }
    } on PlatformException catch (e) {
      print('Failed to toggle Bluetooth: ${e.message}');
    }
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bluetooth Demo'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Bluetooth State: $_bluetoothState',
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _toggleBluetooth,
              child: Text(
                _bluetoothState == BluetoothState.STATE_OFF
                    ? 'Enable Bluetooth'
                    : 'Disable Bluetooth',
              ),
            ),
          ],
        ),
      ),
    );
  }

}
