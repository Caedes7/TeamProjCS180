import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private ArrayList<User> users;
    private String databaseOutput;
    private String messages;
    public Database(String messages, String databaseOutput) {
        users = new ArrayList<User>();
        this.databaseOutput = databaseOutput;
        this.messages = messages;
    }

    public boolean createUser(String data) {
        User user = new User(data);
        for (existingUser : users) {
            if(existingUser.equals(user)) {
                return false; //user already exists, so return false
            }
        }
        users.add(user); //add user
        return true;
    }
    public boolean deleteUser(String data) { //to delete the account/user
        User user  = new User(data);
        for (existingUser : users) {
            if (existingUser.equals(user)) {
                users.remove(user);
                return true; //user was deleted, successfully
            }
        }
        return false; //user wasn't deleted
    }

    public boolean outputDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseOutput))) {
            String line = "";
            for (User userData : users) {
                line = userData.toString();
                bw.write(line);
                line = "";
                if (userData.getMessages() == null) {
                    line = "No Messages";
                } else {
                    line += userData.getMessages();
                }
                bw.write(line);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public User searchUsers(String data) {
        boolean found = false;
        User searchingUser = new User(data);
        for (lookingUser : users) {
            if (searchingUser.getUsername().equalsIgnoreCase(lookingUser.getUsername())) {
                found = true;
                return lookingUser; //return the user if the username was found

            }
        }
        return null;
    }

    public void viewUsers() {
        for (user : users) {
            System.out.println(user.toString()); //replace with GUI alternative late
        }
    }
}