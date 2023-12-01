package overPrivilege.reflection.stringlibrary;

public class StringService {
    public static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
