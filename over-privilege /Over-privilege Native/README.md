# Over-Privilege using Native Code in Android Applications

This project demonstrates a critical security concern in Android applications: over-privilege. It showcases how apps can inadvertently request more permissions than needed and the potential exploitation of these permissions. 

## Exploitation Scenario

The scenario involves three components:

- Vulnerable App: Uses to reverse a string but mistakenly requests READ_SMS permission. It uses a native call


- Malicious App: Exploits the native call to just display a character string

The vulnerable application implements a module which offers one service : reversing a string and Read SMS permission. The application mistakenly asks for READ_SMS permission.

The malicious application display character string

## API Levels

The over privilege vulnerability has been successfully exploited in Android versions ranging from API level 24 to 30.

## Running Scenario

- install and run **vulnerable app**  

  <img src="./screenshots/install run vulnerable native.png" alt="Alt text">

 

- install and run **malicious app**  
  <img src="./screenshots/install run malicious native.png" alt="Alt text">






## Code Smells

This scenario shows that we can also implement native calls in overprivilege attacks.

## References

[1]. https://www.jmdoudoux.fr/java/dej/chap-jni.htm

[2]. https://benhermann.eu/publications/hhl+17hardening.pdf

[3]. https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/master/Permission/UnnecesaryPerms-PrivEscalation-Lean/
