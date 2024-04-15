import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;


public class Client {
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

                writer.write("CREATE_USER" + name + "," + username + "," + age + "," + pw + "," + email);
                writer.flush();

                } else if (initResponse.equalsIgnoreCase("returning")) {

                System.out.println("This is the login page. Enter in your username: ");
                String username = sc.nextLine();
                
                System.out.println("Enter in your password: ");
                String pw = sc.nextLine();

                writer.write("RE" + username + "," + pw);
                writer.flush();

                } else {
                    System.out.println("Invalid! Enter either new or returning: ");
                    repeat = true;
                }
            } while (repeat);
            System.out.println("User Created; Phase 2 implementation ends here without GUI");
            String response = reader.readLine();
            System.out.println(response);

            if (response.startsWith("Success")) {
                System.out.println("Welcome! Please select an option:");
                System.out.println("1. Search user");
                System.out.println("2. Block user");
                System.out.println("3. Unblock user");
                System.out.println("4. Add friend");
                System.out.println("5. Remove a friend");
                System.out.println("6. Message a friend");
                System.out.println("0. Exit");

                int choice = -1;
                while (choice != 0) {
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine(); 

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the username you want to search: ");
                            String toSearch = sc.nextLine();
                            writer.write("1" + toSearch);
                            break;
                        case 2:
                            System.out.println("Enter the username you want to block: ");
                            String toBlock = sc.nextLine();
                            writer.write("2" + toBlock);
                            break;
                        case 3:
                            System.out.println("Enter the username you want to unblock: ");
                            String toUnblock = sc.nextLine();
                            writer.write("3" + toUnblock);
                            break;
                        case 4:
                            System.out.println("Enter the username you want to add as a friend: ");
                            String toFriend = sc.nextLine();
                            writer.write("4" + toFriend);
                            break;
                        case 5:
                            System.out.println("Enter the username you want to unfriend: ");
                            String toUnfriend = sc.nextLine();
                            writer.write("5" + toUnfriend);
                            break;
                        case 6:
                            System.out.println("Enter the username you want to message: ");
                            String toMessage = sc.nextLine();
                            writer.write("6" + toMessage);
                            break;
                        case 0:
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else {
                System.out.println("Could not login or create new user.");
            }

            } catch (IOException e) {
                System.err.println("IO exception");
            }
            } 
   public static void main(String[] args) {
    String clientName = "Client1"; 
    Client client = new Client(clientName);
    client.runClient();

   }
}