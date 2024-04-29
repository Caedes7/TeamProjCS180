import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Class: Server
 * Main server class that initializes the server on a specified port and listens for incoming connections.
 * Once a connection is made, it delegates handling of the client to a ClientHandler. This class also
 * manages user sessions and supports functionalities like login, retrieving friends, and messaging.
 * The server can handle multiple client connections concurrently using a thread pool.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
@SuppressWarnings("FieldMayBeFinal")
public class Server implements Runnable, Serializable {
    private static final int PORT = 1113;
    private ExecutorService threadPool; // For handling client requests concurrently
    private Database database;

    public Server(String databaseFile) {
        this.database = new Database(databaseFile);
        this.threadPool = Executors.newCachedThreadPool();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, database, this);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (threadPool != null && !threadPool.isShutdown()) {
                threadPool.shutdown();
            }
        }
    }

    public NewUser loginUser(String username, String password) {
        NewUser user = database.searchUsers(username);
        if (user == null || !user.getPassword().equals(password)) {
            user = null;
        }
        return user;
    }

    public static void main(String[] args) {
        Server server = new Server("data_Output.txt"); // Adjust the filename as per your serialization file
        new Thread(server).start();
    }
}