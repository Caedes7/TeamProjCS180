import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private ArrayList<newUser> users;
    private String databaseOutput;
    private String messages;
    public Database(String messages, String databaseOutput) {
        users = new ArrayList<newUser>();
        this.databaseOutput = databaseOutput;
        this.messages = messages;
    }

    //Jeeaan: temporary changed the data string to the actual inputs directly, less work rn, removes errors
    public boolean createUser(String name, String username, int age, String password, String email) {
        newUser user = new newUser(name, username, age, password, email);
        for (newUser existingUser : users) {
            if(existingUser.equals(user)) {
                return false; //user already exists, so return false
            }
        }
        users.add(user); //add user
        return true;
    }
    public boolean deleteUser(String name, String username, int age, String password, String email) { //to delete the account/user
        newUser user  = new newUser(name, username, age, password, email);
        for (newUser existingUser : users) {
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
            for (newUser userData : users) {
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

    public newUser searchUsers(String name, String username, int age, String password, String email) {
        boolean found = false;
        newUser searchingUser = new newUser(name, username, age, password, email);
        for (newUser lookingUser : users) {
            if (searchingUser.getUsername().equalsIgnoreCase(lookingUser.getUsername())) {
                found = true;
                return lookingUser; //return the user if the username was found

            }
        }
        return null;
    }

    public void viewUsers() {
        for (newUser user : users) {
            System.out.println(user.toString()); //replace with GUI alternative late
        }
    }
}