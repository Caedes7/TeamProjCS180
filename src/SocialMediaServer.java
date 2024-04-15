import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocialMediaServer implements Runnable {
    private final int port;
    private Database database;
    private ExecutorService threadPool;

    public SocialMediaServer(int port) {
        this.port = port;
        this.database = new Database("data_Output.txt");
        this.threadPool = Executors.newCachedThreadPool();
    }


    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected, " + clientSocket);
                





                //creates new user from client
                Server server = new Server("data_Output");
                ClientHandler clientHandler = new ClientHandler(clientSocket, database, server);
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
        int port = 8000;
        SocialMediaServer server = new SocialMediaServer(port);
        new Thread(server).start();
    }
}
