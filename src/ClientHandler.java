import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Database database;

    public ClientHandler(Socket socket, Database database) {
        this.clientSocket = socket;
        this.database = database;
    }


    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.startsWith("CREATE_USER")) {


                    String[] userDetails = inputLine.split(",");
                    String name = userDetails[1];
                    String username = userDetails[2];
                    int age = Integer.parseInt(userDetails[3]);
                    String password = userDetails[4];
                    String email = userDetails[5];

                    boolean success = database.createUser(name, username, age, password, email);
                    if (success) {
                        out.println("User created successfully");
                    } else {
                        out.println("Failed to create user");
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
