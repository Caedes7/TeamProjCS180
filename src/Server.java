import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server implements Runnable {
    private static final int PORT = 1112;
    private ExecutorService threadPool; // For handling client requests concurrently
    private Database database;

    public Server(String databaseFile) {
        this.database = new Database(databaseFile);
        this.threadPool = Executors.newCachedThreadPool(); // Adjust based on your expected server load
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

    public boolean loginUser(String username, String password) {
        NewUser user = database.searchUsers(username);
        return user != null && user.getPassword().equals(password);
    }

    public static void main(String[] args) {
        Server server = new Server("data_Output.ser"); // Adjust the filename as per your serialization file
        new Thread(server).start();
    }
}