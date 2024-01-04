In this scenario, the vulnerable application calls StringService using Java reflection.
Thoses calls are writen in the reverseText function :

```java
Method method = StringService.class.getMethod("reverseString", String.class);
        Object reversedText = method.invoke(null, inputText);

        if (reversedText != null){
            textViewResult.setText((String) reversedText);
        }
        else {
            textViewResult.setText("null");
        }
```
