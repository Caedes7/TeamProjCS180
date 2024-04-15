import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        this.database = new Database("data_Output");
        this.threadPool = Executors.newCachedThreadPool();
    }


    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected, " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, database, this);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (threadPool != null) {
                threadPool.shutdown();
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server("data_Output");
        new Thread(server).start();
    }
    

    public synchronized boolean loginUser(String username, String password) {
        // Check if the username or password is null to prevent unnecessary processing
        if (username == null || username.isEmpty() || password == null) {
            return false;
        }

        // Search for the user using the username. Assuming that the password, age, email, blocked, and friends
        // are not relevant for the login search and can be passed as null or default values.
        NewUser user = searchUsers(username);

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
        NewUser user = searchUsers(username);

        // If the user is found, return their friends list
        if (user != null) {
            return user.getFriends();
        }

        // If no user is found, return an empty list
        return new ArrayList<>();
    }

    public synchronized List<NewUser> getBlockedUsersList(String username) {
        if (username == null || username.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if no valid username is provided
        }

        NewUser user = searchUsers(username);
        if (user != null) {
            return new ArrayList<>(user.getBlocked()); // Return a copy of the blocked list
        }

        return new ArrayList<>(); // Return an empty list if user is not found
    }

    public synchronized String getMessages(String username) {
        NewUser user = searchUsers(username);
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