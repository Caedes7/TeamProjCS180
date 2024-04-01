import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is the database that will store information about all the NewUser objects created.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public class Database implements DbInterface{
    private ArrayList<NewUser> users;
    private String databaseOutputFile = "data_Output.txt";


    public Database(String databaseOutput) {
        this.databaseOutputFile = databaseOutput;
        this.users = new ArrayList<>(0);
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseOutputFile))) {
            String line = "";
            for (NewUser userData : users) {
                line = userData.toString();
                bw.write(line);
                line = "";
                for (NewUser recipient :  users) {
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