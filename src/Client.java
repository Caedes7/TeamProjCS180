import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;
/**

 Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 Class: Client
 Manages the client-side functionality for a direct messaging application. This class is responsible for
 establishing a connection to the server, handling user inputs, and processing server responses. It allows
 users to create a new account or log into an existing one and perform various actions like searching for other
 users, blocking/unblocking users, adding/removing friends, and sending messages. The Client class utilizes
 a thread pool to manage its tasks and maintains an interactive command line interface for user interaction.
 *
 @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 @version April 15, 2024
 */

public class Client implements IClient, Serializable {
    private static final int PORT = 1112;
    private ExecutorService threadPool;
    Scanner sc = new Scanner(System.in);
    private String name;

    public Client(String name) {
        this.threadPool = Executors.newCachedThreadPool();
        this.name = name;
    }



    public void runClient() {
        String hostname = "localhost";

        try (Socket socket = new Socket(hostname, PORT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            //login or create user
            boolean repeat = false;
            do {

                System.out.println("Are you a new user or returning? Type either 'new' or 'returning': ");
                String initResponse = sc.nextLine();

                if (initResponse.equalsIgnoreCase("new")) {

                    System.out.println("This is the new user page. Enter in your username: ");
                    String username = sc.nextLine();

                    System.out.println("Enter in your password: ");
                    String pw = sc.nextLine();

                    System.out.println("Enter in your name: ");
                    String name = sc.nextLine();

                    System.out.println("Enter in your age: ");
                    String age = sc.nextLine();

                    System.out.println("Enter in your email: ");
                    String email = sc.nextLine();

                    writer.println("CREATE_USER" + name + "," + username + "," + age + "," + pw + "," + email);

                } else if (initResponse.equalsIgnoreCase("returning")) {

                    System.out.println("This is the login page. Enter in your username: ");
                    String username = sc.nextLine();

                    System.out.println("Enter in your password: ");
                    String pw = sc.nextLine();

                    writer.println("RE" + username + "," + pw);

                } else {
                    System.out.println("Invalid! Enter either new or returning: ");
                    repeat = true;
                    continue;
                }

                String response = reader.readLine();
                System.out.println(response);

                if (response.startsWith("User created successfully") || response.startsWith("User logged in successfully")) { //create/login successful
                    break;
                } else if (response.startsWith("Failed to create user")) { //create user failed
                    System.out.println("Could not Create the user.");
                    repeat = true;
                } else if (response.startsWith("User does not exist")) {  //login failed
                    System.out.println("Login failed.");
                    repeat = true;
                } else {
                    repeat = true;
                }

            } while (repeat);

            int choice;
            do {
                choice = -1;
                menu();
                System.out.print("Enter your choice: ");
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                    choice = -1;
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println("Enter the username you want to search: ");
                        String toSearch = sc.nextLine();
                        writer.println("1" + toSearch);
                        System.out.println(reader.readLine());
                        break;
                    case 2:
                        System.out.println("Enter the username you want to block: ");
                        String toBlock = sc.nextLine();
                        writer.println("2" + toBlock);
                        System.out.println(reader.readLine());
                        break;
                    case 3:
                        System.out.println("Enter the username you want to add as a friend: ");
                        String toFriend = sc.nextLine();
                        writer.println("3" + toFriend);
                        System.out.println(reader.readLine());
                        break;
                    case 4:
                        System.out.println("Enter the username you want to message: ");
                        String toMessage = sc.nextLine();
                        String message = sc.nextLine();
                        writer.println("4" + toMessage + "~" + message);
                        System.out.println(reader.readLine());
                        break;
                    case 5:
                        writer.println("5");
                        System.out.println(reader.readLine());
                        break;
                    case 6:
                        writer.println("6");
                        System.out.println(reader.readLine());
                        break;
                    case 0:
                        writer.println("0");
                        System.out.println(reader.readLine());
                        break;
                    default:
                        System.out.println(reader.readLine());
                }

            } while (choice != 0);
        } catch (IOException e) {
            System.err.println("IO exception");
        }
    }

    public static void menu() {
        System.out.println("Welcome! Please select an option:");
        System.out.println("1. Search user");
        System.out.println("2. Block user");
        System.out.println("3. Add friend");
        System.out.println("4. Message a friend");
        System.out.println("5. View Received Messages");
        System.out.println("6. View Sent Messages");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) {
        String clientName = "Client1";
        Client client = new Client(clientName);
        client.runClient();

    }
}