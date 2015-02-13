package models;

import java.util.Map;
import java.util.TreeMap;

public class User {

    private static Map<Long, User> users = new TreeMap<>();

    static {
        users.put(1L, new User(1L, "Emver", "Hidalgo", "emver.hidalgo@play.com", true));
        users.put(2L, new User(2L, "Joe", "Smith", "joe.smith@play.com", false));
    }

    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public Boolean isAdmin;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, Boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public static boolean authenticated(String email) {
        User user = findByEmail(email);

        return user != null;
    }

    public static User findByEmail(String email) {
        for (User user : users.values()) {
            if (user.email.toLowerCase().equals(email.toLowerCase())) {
                return user;
            }
        }
        return null;
    }
}
