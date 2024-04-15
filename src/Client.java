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

                

                //add part if inputs are invalid after checking with db methods

                

                StringBuilder titlesBuild = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.equals("EOF")) {
                    titlesBuild.append(line).append("\n");
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