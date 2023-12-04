# Dangling Custom-Permission with Signature Protection Level
 - In this scenario, the dangling custom permission vulnerability is present in the **vulnerable** app. The custom permission is set at a signature protection level and is explicitly declared in the manifest.xml file as follows:
 ````xml
  <permission  
	  android:name="com.danglingpermission.vulnerable.CUSTOM_PERMISSION"    
	  android:protectionLevel="signature"/>
 ````

