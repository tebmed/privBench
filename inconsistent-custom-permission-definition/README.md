# Inconsistent-custom-permission-definition

Creating a custom permission with `normal` privileges and updating it with `dangerous` privileges can potentially result in a privilege escalation attack.

## To know before reading

- A custom permission in Android is a developer-defined access control mechanism regulating specific app functionalities or components.

- Normal protection levels enable access to designated resources without requiring explicit user consent at install-time.

- Dangerous protection level enable access to designated resources but it requires an explicit user consent at the first use.

- The granting of dangerous permissions is group-based in Android, .i.e. when an app requests a permission that belongs to a dangerous group, the user is prompted to grant the entire permission group rather than individual permissions within that group.

## Exploitation Scenario

Consider a benign application called benignv1 that initially uses a custom permission with `normal` privileges. In this app, this permission does not serve any purpose, but it could be used to do something not sensitive.

Later, the app is updated by the developer, resulting in a new version called benignv2. In this update, the developer modifies the custom permission, changes its privileges to `dangerous` and adds it to the `storage` group. This modified permission could be used to write the time in a file in the phone storage. Additionnally, the developer requests the permission to read the phone storage, permission which also belongs to the `storage` group.
This group is one of Android's standard permission categories which contains all `dangerous` permissions related to the storage of the phone.

However, upon downloading this updated app, an unexpected issue arises. After the restart of the phone, all permissions categorized under the "storage" group, including the custom permission and the permission to read the storage, are automatically accepted without requiring the user's explicit consent.

As a result, this scenario exemplifies a privilege escalation case where the app gains access to potentially sensitive permissions without the user's explicit authorization, presenting a security concern.

## API Levels

The inconsistent custom permission definition vulnerability has been successfully exploited in Android versions ranging from API level 26 to 30.

## Running Scenario

- Build and add to the phone storage the .apk of the **benignv1** and the **benignv2** app  
  <img src="./screenshots/download_builds.png" alt="Alt text" title="Optional title">

- Install and run **benignv1** app with the benignv1 apk  
  <img src="./screenshots/benign_v1.png" alt="Alt text" title="Optional title">

- The app has no permission at the install  
  <img src="./screenshots/permissions.png" alt="Alt text" title="Optional title">

- Install **benignv2** with the benignv2 apk and restart the phone  
  <img src="./screenshots/restart.png" alt="Alt text" title="Optional title">

- All dangerous permissions belonging to the same group as the custom permission have been automatically granted to the benignv2 app without requesting the user's consent.  
  <img src="./screenshots/updated_permissions.png" alt="Alt text" title="Optional title">

## Code Smells

The developper created a custom permission with a `normal` privilege level.
He later updated it to a `dangerous` privilege level and put it into a standart Android group.

## Recommendation

- To ensure the successful execution of the attack scenario, use one of the specified API levels mentioned above.

- You can execute the apps by building the open-source project using an IDE plugin (such as Android Studio) or by directly utilizing the APK files found in the 'apks' folder.

## References

[1]. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-0317

[2]. Li, R., Diao, W., Li, Z., Du, J., & Guo, S. (2021, May). Android custom permissions demystified: From privilege escalation to design shortcomings. In 2021 IEEE Symposium on Security and Privacy (SP) (pp. 70-86). IEEE

[3]. https://sites.google.com/view/custom-permission
