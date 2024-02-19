# Dangling Custom-Permission with Signature Protection Level
 - In this scenario, the dangling custom permission vulnerability is present in the **vulnerable** app. The custom permission is set at a signature protection level. It is declared as String in the string.xml file then referenciated in the manifest file as follows:

 ## String.xml
  ````xml
  <resources>
  	<string name="my_custom_permission_name">
	     com.danglingpermission.vulnerable.CUSTOM_PERMISSION
	</string>
</resources>
 ````

 ## AndroidManifest.xml
 ````xml
  <permission  
	  android:name="@string/my_custom_permission_name"    
	  android:protectionLevel="signature"/>
 ````

