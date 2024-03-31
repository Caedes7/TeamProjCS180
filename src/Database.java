import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private ArrayList<newUser> users;
    private String databaseOutput = "data_Output.txt";

    public Database(String databaseOutput) {
        this.databaseOutput = databaseOutput;
        this.users = new ArrayList<>(0);
    }

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
                for (newUser recipient :  users) {
                    if (!userData.equals(recipient)) {
                        if (userData.getMessages(userData, recipient) != null) {
                            line += userData.getMessages(userData, recipient) + "\n";
                        }
                    }
                }
                bw.write(line);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean validateCredentials(newUser user) {

        newUser user1 = searchUsers(user.getName(), user.getUsername(),
                user.getAge(), user.getPassword(), user.getEmail());

        if (user1 == null) {
            return true;
        }

        return false;
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