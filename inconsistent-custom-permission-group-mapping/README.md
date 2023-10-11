# **Inconsistent-custom-permission-group-mapping**

The use of the `UNDEFINED` group inside a custom permission declaration can lead to privilege escalation attack.

## Exploitation Scenario

Suppose there is a benign application named **benign_app** that includes several dangerous permission including the access to the camera. 

Depending on the user's decision, the authorization to the camera will be granted or denied. Because this authorization is necessary to the use of the application, the user will grant the access of the camera to the application.

The application is then updated by the developer in an update called **malicious_app**, adding a custom permission belonging to the `UNDEFINED` permission group. After downloading the updated app, the developer realizes all the permissions belonging to the “dangerous” group are automatically accepted, without asking the consent of the final user. 

Therefore, this scenario represents a privilege escalation case.

## API Levels

Tested on API Levels: 29

## Running Scenario

- Build & Run **benign** app
    
    <img src="./screenshots/bengin_app.png" alt="Alt text" title="Optional title">
    
- **benign** app has no permission at installation.
    
    <img src="./screenshots/bengin_app_permission.png" alt="Alt text" title="Optional title">
    
- Click on the button and grant the Camera Permission
    
    <img src="./screenshots/bengin_app_ask.png" alt="Alt text" title="Optional title">
    
    <img src="./screenshots/bengin_app_granted.png" alt="Alt text" title="Optional title">
    
- The app has now the Camera Permission
- Build & Run **malicious** app
    
    <img src="./screenshots/malicious_app.png" alt="Alt text" title="Optional title">
    
- The app has now the SMS and Storage permission
    
    <img src="./screenshots/malicious_app_permission.png" alt="Alt text" title="Optional title">
    

## References

[1]. https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2020-0418

[2]. Li, R., Diao, W., Li, Z., Du, J., & Guo, S. (2021, May). Android custom permissions demystified: From privilege escalation to design shortcomings. In 2021 IEEE Symposium on Security and Privacy (SP) (pp. 70-86). IEEE

[3]. https://sites.google.com/view/custom-permission