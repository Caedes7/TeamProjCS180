public class newUser implements User {
    private String name;
    private String username;
    private int age;
    private String password;
    private String email;

    public User(String name, String username, int age, String password) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.password = password;
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
    
}