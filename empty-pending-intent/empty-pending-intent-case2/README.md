# Custom permission elevating with Activity Recognition permission

 - In this scenario, the custom permission elevating vulnerability is 
 present in the **vulnerable** app. 
 An implicite pending intent is created and used to send a broadcast message. The issue is that every application on the device can read this message.
 
 ````java

    private Intent intent;

    ....

    private Intent CreateBasicIndent(){
        intent = new Intent();
        
        intent.setAction("emptyPendingIntent.sender.send");
        intent.putExtra("Data","Message de base");

        return intent;
    }
 ````

