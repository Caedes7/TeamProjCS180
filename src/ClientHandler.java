import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread implements Runnable  {
    private Socket clientSocket;
    private Database database;
    private Server server;

    public ClientHandler(Socket socket, Database database, Server server) {
        this.clientSocket = socket;
        this.database = database;
        this.server = server;
    }


    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;
            do {
                System.out.println("Client connected");

                inputLine = in.readLine();
                System.out.println("Client connected1");

                if (inputLine.startsWith("CREATE_USER")) {
                    System.out.println("Client connected2");

                    inputLine = inputLine.substring(11);
                    String[] userDetails = inputLine.split(",");
                    String name = userDetails[0];
                    String username = userDetails[1];
                    int age = Integer.parseInt(userDetails[2]);
                    String password = userDetails[3];
                    String email = userDetails[4];

                    boolean success = database.createUser(name, username, age, password, email);
                    if (success) {
                        System.out.println("Client connected3");

                        out.println("User created successfully");
                    } else {
                        out.println("Failed to create user");
                        System.out.println("Client connected4");

                    }
                } else if (inputLine.startsWith("RE")) {
                    System.out.println("Client connected5");

                    inputLine = inputLine.substring(2);
                    String[] userDetails = inputLine.split(",");
                    String username = userDetails[0];
                    String password = userDetails[1];

                    boolean success = server.loginUser(username, password);
                    if (success) {
                        System.out.println("Client connected6");

                        out.println("User logged in successfully");
                    } else {
                        System.out.println("Client connected7");

                        out.println("Failed to login user");
                    }
                    out.flush();
                }
            }  while ((inputLine = in.readLine()) != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }