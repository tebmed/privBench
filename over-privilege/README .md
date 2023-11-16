# **Over-Privilege in Android Applications**

This project demonstrates a critical security concern in Android applications: over-privilege. It showcases how apps can inadvertently request more permissions than needed and the potential exploitation of these permissions.

## Exploitation Scenario

### Overview

The scenario involves three components:

* StringLibrary App: Offers two services - reversing a string (no permission needed) and listing SMS titles (requires READ_SMS permission).

* Benign App: Utilizes StringLibrary to reverse a string but mistakenly requests READ_SMS permission.

* Malicious App: Exploits the granted READ_SMS permission to the Benign App, enabling it to read and display SMS messages.

### Purpose

This scenario is designed to educate about the risks of over-privileged apps, highlighting the importance of requesting only essential permissions.

## API Levels

Tested on API Levels: 23-29

## Running Scenario

- install and run **benign app**
    
    <img src="./Capture d'écran/Benignapp2.png" alt="Alt text" title="Optional title">
    
- **benign** app ask one permission at installation.
    
    <img src="./Capture d'écran/Benignapp1.png" alt="Alt text" title="Optional title">

- install and run **StringLibrary app**
    
    <img src="./Capture d'écran/StringLibrary1.png" alt="Alt text" title="Optional title">
    <img src="./Capture d'écran/StringLibrary2.png" alt="Alt text" title="Optional title">
    
- install and run **malicous app**

- show that the sms of the user are displayed on the **malicous app** screen.

    <img src="./Capture d'écran/Malicious.png" alt="Alt text" title="Optional title">



## References

[1]. https://stackoverflow.com/questions/25823358/what-is-the-need-of-privileged-block-in-reflection

[2]. https://benhermann.eu/publications/hhl+17hardening.pdf
