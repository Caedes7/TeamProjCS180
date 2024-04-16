import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            NewUser newSingleUser = new NewUser(null);

            do {
                inputLine = in.readLine();

                if (inputLine.startsWith("CREATE_USER")) {

                    inputLine = inputLine.substring(11);
                    String[] userDetails = inputLine.split(",");
                    String name = userDetails[0];
                    String username = userDetails[1];
                    int age = Integer.parseInt(userDetails[2]);
                    String password = userDetails[3];
                    String email = userDetails[4];

                    boolean success = database.createUser(name, username, age, password, email);

                    if (success) {
                        newSingleUser = new NewUser(name, username, age, password, email);
                        out.println("User created successfully");
                    } else {
                        out.println("Failed to create user");
                    }
                } else if (inputLine.startsWith("RE")) {
                    inputLine = inputLine.substring(2);
                    String[] userDetails = inputLine.split(",");
                    String username = userDetails[0];
                    String password = userDetails[1];

                    boolean success = server.loginUser(username, password);
                    if (success) {
                        out.println("User logged in successfully");
                    } else {
                        out.println("User does not exist");
                    }
                }

                String choiceString = "";
                int choice = -1;

                while (choice != 0) {
                    choiceString = in.readLine();
                    System.out.println(choiceString);
                    choice = Integer.parseInt(choiceString.substring(0,1));
                    switch (choice) {
                        case 1:
                            NewUser found;
                            found = database.searchUsers(choiceString.substring(1));
                            if (found != null) {
                                String output = found.toStringSearch() + "\nFurther implementation requires GUI";
                                out.println(output);
                            } else {
                                out.println("User not found");
                            }
                            out.flush();
                            break;
                        case 2:
                            boolean blocked;
                            blocked = database.blockUser(newSingleUser.getUsername(), choiceString.substring(1));
                            if (!blocked) {
                                out.println("Could not block user.");
                            } else {
                                out.println("User blocked!");
                            }
                            break;
                        case 3:
                            boolean friend;
                            friend = database.addFriend(newSingleUser.getUsername(), choiceString.substring(1));
                            if (!friend) {
                                out.println("Could not add user as friend.");
                            } else {
                                out.println("User friended!");
                            }
                            break;
                        case 4:
                            String message = "Messages will be implemented later with the GUI.";
                            out.println(message);
                            break;
                        case 0:
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
                break;
            }  while ((inputLine = in.readLine()) != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}