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

public class Database implements IDatabase, Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<NewUser> users;
    private String databaseOutputFile;

    public Database(String databaseOutput) {
        this.databaseOutputFile = databaseOutput;
        this.users = new ArrayList<>();
        loadDatabase();
    }

    public boolean createUser(String name, String username, int age, String password, String email) {
        if (searchUsers(username) != null) {
            return false; // User already exists
        }
        NewUser user = new NewUser(name, username, age, password, email);
        users.add(user);
        return outputDatabase(); // Save changes
    }

    public boolean deleteUser(String username) {
        boolean removed = users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        return removed && outputDatabase(); // Save changes if user is successfully removed
    }

    public NewUser searchUsers(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
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
        NewUser userBlocker = searchUsers(usernameBlocker);
        NewUser userBlocked = searchUsers(usernameBlocked);
        if (userBlocker != null && userBlocked != null && !userBlocker.getBlocked().contains(userBlocked)) {
            userBlocker.getBlocked().add(userBlocked);
            return outputDatabase(); // Save changes
        }
        return false;
    }

    public void viewUsers() {
        users.forEach(user -> System.out.println(user));
    }

    public boolean sendMessage(String senderUsername, String receiverUsername, String messageContent) {
        NewUser sender = searchUsers(senderUsername);
        NewUser receiver = searchUsers(receiverUsername);
        if (sender != null && receiver != null) {
            Message message = new Message(senderUsername, receiverUsername, messageContent, System.currentTimeMillis());
            sender.getSentMessages().computeIfAbsent(receiverUsername, k -> new ArrayList<>()).add(message);
            receiver.getReceivedMessages().computeIfAbsent(senderUsername, k -> new ArrayList<>()).add(message);
            return outputDatabase(); // Save changes
        }
        return false;
    }

    public boolean outputDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(databaseOutputFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Failed to write database to file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean loadDatabase() {
        File file = new File(databaseOutputFile);
        if (!file.exists()) {
            return false; // No database file to load from
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(databaseOutputFile))) {
            users = (ArrayList<NewUser>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load database from file: " + e.getMessage());
            return false;
        }
        return true;
    }
}