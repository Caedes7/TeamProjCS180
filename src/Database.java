import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is the database that will store information about all the NewUser objects created.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public class Database implements DbInterface, Serializable {
    private static final long serialVersionUID = 1L; // Serialization UID

    private ArrayList<NewUser> users;
    private String databaseOutputFile;

    public Database(String databaseOutput) {
        this.databaseOutputFile = databaseOutput;
        this.users = new ArrayList<>();
    }

    public boolean createUser(String name, String username, int age, String password, String email,
                              ArrayList<NewUser> blocked,  ArrayList<NewUser> friends) {
        NewUser user = new NewUser(name, username, age, password, email, blocked, friends);
        for (NewUser existingUser : users) {
            if (existingUser.equals(user)) {
                return false; //user already exists, so return false
            }
        }
        users.add(user); //add user
        return true;
    }
    public boolean deleteUser(String name, String username, int age, String password, String email,
                              ArrayList<NewUser> blocked,  ArrayList<NewUser> friends) { //to delete the account/user
        NewUser user  = new NewUser(name, username, age, password, email, blocked, friends);
        for (NewUser existingUser : users) {
            if (existingUser.equals(user)) {
                users.remove(user);
                return true; //user was deleted, successfully
            }
        }
        return false; //user wasn't deleted
    }

    public boolean outputDatabase() {
        StringBuilder dataToWrite = new StringBuilder();

        // Loop through each user and compile information
        for (NewUser user : users) {
            // Append basic user info
            dataToWrite.append(user.toString()).append("\n");

            // Loop through each user again to collect and append message data
            for (NewUser recipient : users) {
                if (!user.equals(recipient)) {
                    List<Message> messages = user.getMessagesWithUser(recipient.getUsername());
                    if (messages != null) {
                        for (Message message : messages) {
                            dataToWrite.append(message.toString()).append("\n");
                        }
                    }
                }
            }
        }

        // Attempt to write the compiled data to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseOutputFile))) {
            bw.write(dataToWrite.toString());
        } catch (IOException e) {
            System.err.println("Failed to write database to file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean validateCredentials(NewUser user) {

        NewUser user1 = searchUsers(user.getName(), user.getUsername(),
                user.getAge(), user.getPassword(), user.getEmail(), user.getBlocked(), user.getFriends());

        return user1 == null;
    }

    public NewUser searchUsers(String name, String username, int age, String password, String email,
                               ArrayList<NewUser> blocked,  ArrayList<NewUser> friends) {
        boolean found = false;
        NewUser searchingUser = new NewUser(name, username, age, password, email, blocked, friends);
        for (NewUser lookingUser : users) {
            if (searchingUser.getUsername().equalsIgnoreCase(lookingUser.getUsername())) {
                found = true;
                return lookingUser; //return the user if the username was found

            }
        }
        return null;
    }

    public void viewUsers() {
        for (NewUser user : users) {
            System.out.println(user.toString()); //replace with GUI alternative late
        }
    }
}