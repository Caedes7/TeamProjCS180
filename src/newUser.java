import java.util.ArrayList;

public class newUser implements User {
    private String name;
    private String username;
    private int age;
    private String password;
    private String email;
    private ArrayList<newUser> blocked;
    private ArrayList<newUser> friends;

    //private Database data = new Database(,"data_Output.txt"); // string as both arguments

    public newUser(String name, String username, int age, String password, String email,
                   ArrayList<newUser> blocked, ArrayList<newUser> friends) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
        this.blocked = blocked;
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    public ArrayList<newUser> getBlocked() {
        return blocked;
    }
    public ArrayList<newUser> getFriends() {
        return friends;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                return false;
            }
        }

        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.contains("@");
    }


    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != '@') {
                return false;
            }
        }

        return true;
    }

    public boolean isEqual(newUser otherUser) {
        if (otherUser == null) {
            return false;
        }

        return this.name.equals(otherUser.name) &&
                this.username.equals(otherUser.username) &&
                this.age == otherUser.age &&
                this.password.equals(otherUser.password) &&
                this.email.equals(otherUser.email);
    }

    public String getMessages (newUser user1, newUser user2) {
        return "Implementation for getMessages will be completed in future phases";
    }

}