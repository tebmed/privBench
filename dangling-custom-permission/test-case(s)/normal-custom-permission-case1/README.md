# Dangling Custom-Permission with Normal Protection Level
 - In this scenario, the dangling custom permission vulnerability is present in the **vulnerable** app. The custom permission is set at a normal protection level and is explicitly declared in the manifest.xml file as follows:
 ````xml
  <permission  
	  android:name="com.danglingpermission.vulnerable.CUSTOM_PERMISSION"    
	  android:protectionLevel="normal"/>
 ````

