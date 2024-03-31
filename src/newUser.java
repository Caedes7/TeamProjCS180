
public class newUser implements User {
    private String name;
    private String username;
    private int age;
    private String password;
    private String email;

    private Database data = new Database(,); // string as both arguments

    public newUser(String name, String username, int age, String password, String email) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
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
    public boolean checkUsernameExists(String username) {
//        for (String dbUsername : usernameDatabase) {
//            if (dbUsername.equals(username)) {
//                System.out.println("Username is valid and exists in the database.");
//                return true;
//            }
//        }
//        System.out.println("Invalid username.");
//        return false;
        if (data.searchUsers(username) != null) {
            return false;
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

}