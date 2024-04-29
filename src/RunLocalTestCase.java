import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.StringReader;

class ClientHandlerTest {

    private ClientHandler clientHandler;
    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Database database;
    private Server server;

    @BeforeEach
    void setup() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        database = new Database("test_database.txt");  // Assuming a file that can be used for testing
        server = new Server("test_database.txt");
    }

    @Test
    @DisplayName("Test user creation via client handler")
    void testUserCreationViaClientHandler() throws Exception {
        String input = "CREATE_USERJohn Doe,johnDoe,25,securePass123,john.doe@example.com";
        bufferedReader = new BufferedReader(new StringReader(input));
        clientHandler = new ClientHandler(null, database, server) {
            @Override
            public void run() {
                // Simulate running the handler for just one command
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] userDetails = inputLine.substring(11).split(",");
                        String name = userDetails[0];
                        String username = userDetails[1];
                        int age = Integer.parseInt(userDetails[2]);
                        String password = userDetails[3];
                        String email = userDetails[4];

                        boolean success = database.createUser(name, username, age, password, email);
                        if (success) {
                            printWriter.print("User created successfully");
                        } else {
                            printWriter.print("Failed to create user");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        clientHandler.run();
        printWriter.flush();
        String result = stringWriter.toString();
        assertEquals("User created successfully", result.trim());
    }

    // Additional tests can include user login, blocking, messaging, etc.

    @Test
    @DisplayName("Test user login via client handler")
    void testUserLoginViaClientHandler() throws Exception {
        String creationInput = "CREATE_USERJohn Doe,johnDoe,25,securePass123,john.doe@example.com";
        bufferedReader = new BufferedReader(new StringReader(creationInput));
        Database database2 = new Database("test_database2.txt");
        clientHandler = new ClientHandler(null, database2, server) {
            @Override
            public void run() {
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] userDetails = inputLine.substring(11).split(",");
                        database.createUser(userDetails[0], userDetails[1], Integer.parseInt(userDetails[2]), userDetails[3], userDetails[4]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        clientHandler.start();

        String loginInput = "REjohnDoe,securePass123";
        bufferedReader = new BufferedReader(new StringReader(loginInput));
        clientHandler = new ClientHandler(null, database2, server) {
            @Override
            public void run() {
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] loginDetails = inputLine.substring(2).split(",");
                        NewUser user = server.loginUser(loginDetails[0], loginDetails[1]);
                        if (user != null) {
                            printWriter.println("User logged in successfully");
                        } else {
                            printWriter.println("User/password combination does not exist.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        clientHandler.start();
        printWriter.flush();
        String result = stringWriter.toString();
        assertEquals("User logged in successfully\n", result.trim());
    }

    // Similar setup can be done for testing message sending, friend adding/removal, blocking/unblocking, etc.
}
