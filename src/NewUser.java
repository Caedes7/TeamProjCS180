import java.util.ArrayList;

public class NewUser implements User {
    private String name;
    private String username;
    private int age;
    private String password;
    private String email;
    private ArrayList<NewUser> blocked;
    private ArrayList<NewUser> friends;

    //private Database data = new Database(,"data_Output.txt"); // string as both arguments

    public NewUser(String name, String username, int age, String password, String email,
                   ArrayList<NewUser> blocked, ArrayList<NewUser> friends) {
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
    public void setBlocked(ArrayList<NewUser> blocked) {
        this.blocked = blocked;
    }
    public void setFriends(ArrayList<NewUser> friends) {
        this.friends = friends;
    }

    public boolean isValidUsername(String Username) {
        if (Username == null || Username.isEmpty()) {
            return false;
        }

        for (int i = 0; i < Username.length(); i++) {
            char ch = Username.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                return false;
            }
        }

        return true;
    }

    public boolean isValidEmail(String Email) {
        if (Email == null) {
            return false;
        }
        return Email.contains("@");
    }


    public boolean isValidPassword(String Password) {
        if (Password == null || Password.isEmpty()) {
            return false;
        }

        for (int i = 0; i < Password.length(); i++) {
            char ch = Password.charAt(i);

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

}