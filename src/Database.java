import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is the database that will store information about all the NewUser objects created.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */

public class Database implements DbInterface, Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<NewUser> users;
    private String databaseOutputFile;

    public Database(String databaseOutput) {
        this.databaseOutputFile = databaseOutput;
        this.users = new ArrayList<>();
        loadDatabase();
    }

    public boolean createUser(String name, String username, int age, String password, String email) {
        NewUser user = new NewUser(name, username, age, password, email);
        for (NewUser existingUser : users) {
            if (existingUser.getUsername().equalsIgnoreCase(username)) {
                return false; // User already exists
            }
        }
        users.add(user);
        return outputDatabase(); // Save changes
    }
    public boolean deleteUser(String name, String username, int age, String password, String email) {
        boolean removed = users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        if (removed) {
            return outputDatabase(); // Save changes
        }
        return false;
    }

    public boolean outputDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseOutputFile))) {
            for (NewUser user : users) {
                bw.write(user.toString() + System.lineSeparator());
                bw.write("Friends: " + user.getFriends().stream().map(NewUser::getUsername).collect(Collectors.joining(", ")) + System.lineSeparator());
                bw.write("Blocked: " + user.getBlocked().stream().map(NewUser::getUsername).collect(Collectors.joining(", ")) + System.lineSeparator());
            }
            bw.flush();
        } catch (IOException e) {
            System.err.println("Failed to write database to file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean addFriend(String username, String friendUsername) {
        NewUser user = searchUsers(username);
        NewUser friend = searchUsers(friendUsername);
        if (user != null && friend != null && !user.getFriends().contains(friend)) {
            user.getFriends().add(friend);
            return outputDatabase();  // Save changes
        }
        return false;
    }

    public boolean blockUser(String usernameBlocker, String usernameBlocked) {
        if (usernameBlocker == null || usernameBlocked == null || usernameBlocker.isEmpty() || usernameBlocked.isEmpty()) {
            return false; // Invalid input
        }

        if (usernameBlocker.equalsIgnoreCase(usernameBlocked)) {
            return false; // Cannot block oneself
        }

        NewUser userBlocker = searchUsers(usernameBlocker);
        NewUser userBlocked = searchUsers(usernameBlocked);

        if (userBlocker != null && userBlocked != null) {
            if (!userBlocker.getBlocked().contains(userBlocked)) {
                userBlocker.getBlocked().add(userBlocked);
                return outputDatabase(); // Save changes
            }
        }
        return false; // Block operation failed
    }

    public NewUser searchUsers(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    public void viewUsers() {
        for (NewUser user : users) {
            System.out.println(user.toString()); //replace with GUI alternative late
        }
    }

    public boolean loadDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader(databaseOutputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assume each user's data is separated by a special line or stored in a specific format
                String[] userData = line.split(", "); // Example format: "Name, Username, Age, Password, Email"
                if (userData.length >= 5) {
                    int age = Integer.parseInt(userData[2]);
                    NewUser user = new NewUser(userData[0], userData[1], age, userData[3], userData[4]);
                    this.users.add(user);
                    // Handle loading friends and blocked users if they're also stored
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load database from file: " + e.getMessage());
            return false;
        }
        return true;
    }

}