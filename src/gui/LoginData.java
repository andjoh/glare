package gui;
 
public class LoginData {
 
    public static boolean authenticate(String username, String password) {
       
        if (username.equals("root") && password.equals("root")) {
            return true;
        }
        return false;
    }
}
