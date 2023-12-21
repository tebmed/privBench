# Custom permission elevating with Activity Recognition permission

 - In this scenario, the custom permission elevating vulnerability is 
 present in the **vulnerable** app. 
 An implicite pending intent is created throw a internal class and used to send a broadcast message. The issue is that every application on the device can read this message.
 
 ````java

    private Intent CreateBasicIndent(){
        Intent intent = InnerIntentAction.getInnerIntent(this);

        intent.setAction("emptyPendingIntent.sender.send");

        return intent;
    }

    private static class InnerIntentAction{
        static Intent getInnerIntent(Context context) {
            Intent intent = new Intent();

            intent.putExtra("Data","Message de base");
            
            return intent;
        }
    }


 ````

