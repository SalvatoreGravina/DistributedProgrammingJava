package it.dp.g5.userservice;

import it.dp.g5.backend.Database;
import java.util.HashMap;
import java.util.Map;

public class LoginUtils {

    private static Database db = Database.getInstance();

    private static Map<String, String> loggedMap = new HashMap<>();

    public static boolean login(String email, String password, String token) {
        if (password.equals(db.getPassword(email))) {
            loggedMap.put(email, token);
            return true;
        } else {
            return false;
        }
    }

    public static String getUserToken(String email) {
        return loggedMap.get(email);
    }

    public static boolean logout(String email) {
        if (isLogged(email)) {
            loggedMap.remove(email);
            return true;
        }
        return false;
    }

    private static boolean isLogged(String email) {
        return loggedMap.containsKey(email);
    }

}
