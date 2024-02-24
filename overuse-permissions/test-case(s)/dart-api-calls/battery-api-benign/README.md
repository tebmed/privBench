# Overprivileged Application - Manipulating Batterie using Dart

In this case, the application **benign** makes use of Dart programming language to display statistics related to the device batterie

<img src="screenshots/battery-dart.png">


The code snippet below demonstrates how the app invokes the Batterie API using Dart Programming Language:

````dart
//See lib/main.dart for more details
import 'package:battery/battery.dart';
.
.
Battery _battery = Battery();
final int batteryLevel = await _battery.batteryLevel;
````
No permission is required for the app to function correctly.

## API Level: 
This app has been tested with API Level 29 (Android version 9)


## References
[1]. https://stackoverflow.com/questions/8225926/android-permission-battery-stats-usage
