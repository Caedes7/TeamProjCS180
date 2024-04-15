import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server extends Database implements ServerInterface, Runnable {
    private static final int MAX_MESSAGES = 100; // Store last 100 messages per user
    private ExecutorService executorService; // For handling client requests concurrently
    private final int PORT  = 1112;
    private Database database;
    private ExecutorService threadPool;


    public Server(String databaseFile) {
        super(databaseFile);
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.database = new Database("data_Output.txt");
        this.threadPool = Executors.newCachedThreadPool();
    }
    

     public void run() {
        while (true) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected, " + clientSocket);
                Server server = new Server("data_Output");        
                ClientHandler clientHandler = new ClientHandler(clientSocket, database, server);
                //threadPool.execute(clientHandler);
                clientHandler.start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (threadPool != null) {
                threadPool.shutdown();
            }
        }
    }
    }

    public static void main(String[] args) {
        Server server = new Server("data_Output.txt");
        new Thread(server).start();
    }
    

    public synchronized boolean loginUser(String username, String password) {
        // Check if the username or password is null to prevent unnecessary processing
        if (username == null || username.isEmpty() || password == null) {
            return false;
        }

        // Search for the user using the username. Assuming that the password, age, email, blocked, and friends
        // are not relevant for the login search and can be passed as null or default values.
        NewUser user = searchUsers(null, username, 0, password, null);

        // Check if a user was found and the password matches the password of the found user
        if (user != null && user.getPassword().equals(password)) {
            return true; // Authentication successful
        }

        return false; // Authentication failed
    }

    public synchronized List<NewUser> getFriendsList(String username) {
        // Check if the username is null or empty
        if (username == null || username.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if no valid username is provided
        }

        // Search for the user by username. Assume that other details are not required for this search
        NewUser user = searchUsers(null, username, 0, null, null);

        // If the user is found, return their friends list
        if (user != null) {
            return user.getFriends();
        }

        // If no user is found, return an empty list
        return new ArrayList<>();
    }

    public synchronized List<NewUser> getFollowingList(String username) {
        // Validate the username input
        if (username == null || username.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if the username is invalid
        }

        // Search for the user using the username
        NewUser user = searchUsers(null, username, 0, null, null);

        // If the user is found, return their following list
        if (user != null) {
            return user.getFollowing();
        }

        // If no user is found, return an empty list
        return new ArrayList<>();
    }

    public synchronized boolean blockUser(String usernameBlocker, String usernameBlocked) {
        if (usernameBlocker == null || usernameBlocked == null || usernameBlocker.isEmpty() || usernameBlocked.isEmpty()) {
            return false; // Invalid input
        }

        // Ensure a user cannot block themselves
        if (usernameBlocker.equalsIgnoreCase(usernameBlocked)) {
            return false; // Cannot block oneself
        }

        // Find the user who wants to block another user
        NewUser userBlocker = searchUsers(null, usernameBlocker, 0, null, null);
        if (userBlocker == null) {
            return false; // User who wants to block does not exist
        }

        // Find the user to be blocked
        NewUser userBlocked = searchUsers(null, usernameBlocked, 0, null, null);
        if (userBlocked == null) {
            return false; // User to be blocked does not exist
        }

        // Check if the user is already blocked
        if (userBlocker.getBlocked().contains(userBlocked)) {
            return false; // User is already blocked
        }

        // Add the blocked user to the blocker's blocked list
        userBlocker.getBlocked().add(userBlocked);
        return true;
    }

    public synchronized String getMessages(String username) {
        NewUser user = searchUsers(null, username, 0, null, null);
        if (user == null) {
            return "User not found";
        }

        StringBuilder allMessages = new StringBuilder();
        Map<String, List<Message>> userMessages = user.getMessages();
        userMessages.forEach((otherUser, messages) -> {
            // Sort messages by timestamp in descending order
            messages.sort((m1, m2) -> Long.compare(m2.getTimestamp(), m1.getTimestamp()));
            // Limit the number of messages to the last 100
            List<Message> recentMessages = messages.stream().limit(MAX_MESSAGES).collect(Collectors.toList());
            // Append user information and messages
            allMessages.append("Chat with ").append(otherUser).append(":\n");
            recentMessages.forEach(message -> allMessages.append(message.toString()).append("\n"));
        });

        return allMessages.toString();
    }
}