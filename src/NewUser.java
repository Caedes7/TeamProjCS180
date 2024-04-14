import java.util.ArrayList;
/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class creates NewUser objects and contains methods for acquiring information about the user.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public class NewUser implements User {
    private String name;
    private String username;
    private int age;
    private String password;
    private String email;
    private ArrayList<NewUser> blocked;
    private ArrayList<NewUser> friends;

    public NewUser(String name, String username, int age, String password, String email) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
        this.blocked = new ArrayList<>();
        this.friends = new ArrayList<>();
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
    public ArrayList<NewUser> getBlocked() {
        return blocked;
    }
    public ArrayList<NewUser> getFriends() {
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

    public boolean isValidUsername(String userName) {
        if (userName == null || userName.isEmpty()) {
            return false;
        }

        for (int i = 0; i < userName.length(); i++) {
            char ch = userName.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                return false;
            }
        }

        return true;
    }

    public boolean isValidEmail(String eMail) {
        if (eMail == null) {
            return false;
        }
        return eMail.contains("@");
    }


    public boolean isValidPassword(String passWord) {
        if (passWord == null || passWord.isEmpty()) {
            return false;
        }

        for (int i = 0; i < passWord.length(); i++) {
            char ch = passWord.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != '@') {
                return false;
            }
        }

        return true;
    }

    public boolean isEqual(NewUser otherUser) {
        if (otherUser == null) {
            return false;
        }

        return this.name.equals(otherUser.name) &&
                this.username.equals(otherUser.username) &&
                this.age == otherUser.age &&
                this.password.equals(otherUser.password) &&
                this.email.equals(otherUser.email);
    }

    public String getMessages(NewUser user1, NewUser user2) {
        return "Implementation for getMessages will be completed in future phases";
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Username: " + username + ", Age: " + age + ", Email: " + email;
    }
}