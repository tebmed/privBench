# Overprivileged Application - Audio Recording - Java

In this scenario, the **vulnerable** application utilizes the MediaRecorder API to capture audio from the device's microphone.

The code snippet below illustrates how this API is employed to initiate and cease audio recording upon button clicks:

````java
MediaRecorder mediaRecorder = new MediaRecorder();

//Start Recording
mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
mediaRecorder.setOutputFile(getExternalCacheDir().getAbsolutePath() + "/recording.3gp");
mediaRecorder.prepare();
mediaRecorder.start();

//Stop Recording
mediaRecorder.stop();
mediaRecorder.release();
mediaRecorder = null;
````

For the proper functionality of this application, the following permissions are required (as specified in the AndroidManifest.xml):

 ````xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 ````
However, the vulnerable app unnecessarily declares the following permission:

 ````xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 ````

This unnecessary permission might pose potential security risks by enabling the app to share files via the internet.

## References

[1]. https://developer.android.com/reference/android/media/MediaRecorder
