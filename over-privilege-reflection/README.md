# Over-Privilege using reflection in Android Applications

This project demonstrates a critical security concern in Android applications: over-privilege. It showcases how apps can inadvertently request more permissions than needed and the potential exploitation of these permissions.

## Exploitation Scenario

The scenario involves three components:

- Vulnerable App: Uses StringLibrary to reverse a string but mistakenly requests READ_SMS permission, without using the 2nd service.

- StringLibrary Module : Offers two services - reversing a string (no permission needed) and listing SMS titles (requires READ_SMS permission). This module is contained in the vulnerable application.

- Malicious App: Exploits the READ_SMS permission granted to the Benign App. This exploit enables the malicious app to read and display SMS messages.

The vulnerable application implements a module which offers 2 services : reversing a string (no permission needed) and listing SMS titles (requires READ_SMS permission). The application does not uses the 2nd service but mistakenly asks for READ_SMS permission and run an intent gathering the messages in background.
The malicious application then listen to the intent listing SMS titles. As a result, it can recover the SMS titles without asking for the user's consent.

## API Levels

The over privilege vulnerability has been successfully exploited in Android versions ranging from API level 24 to 30.

## Running Scenario

- install and run **malicious app**  
  <img src="./screenshots/install_run_malicious.png" alt="Alt text">

- install and run **vulnerable app**  
  <img src="./screenshots/install_run_benign.png" alt="Alt text">

- **vulnerable** app ask one permission at installation  
  <img src="./screenshots/ask_permission.png" alt="Alt text">

- Go back on the **malicious** app wihich now prints all the messages  
  <img src="./screenshots/messages.png" alt="Alt text">

You should see that the sms of the user are displayed on the **malicious app** screen.

## Code Smells

This scenario is designed to highlight the importance of requesting only essential permissions.
The unessential permission requested by the benign application allowed a malicious application to gain access to sensitive data.

## References

[1]. https://www.baeldung.com/java-reflection

[2]. https://benhermann.eu/publications/hhl+17hardening.pdf
