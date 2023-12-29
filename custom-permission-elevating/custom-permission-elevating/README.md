# Custom permission elevating with Activity Recognition permission

 - In this scenario, the custom permission elevating vulnerability is 
 present in the **vulnerable** app. A custom permission is created with normal protection level and is explicitly declared in the manifest.xml file as follows:
 
 ````xml
    <permission 
	android:name="android.permission.ACTIVITY_RECOGNITION" 			android:protectionLevel="normal"
	/>
 ````

