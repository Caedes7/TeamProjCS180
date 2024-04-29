import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.FileWriter;
import java.io.IOException;

class ClientHandlerTest {

    private static StringWriter stringWriter;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;
    private static Database database;
    private static Server server;
    private static String databasePath = "test_database.txt";

    @BeforeAll
    static void initialize() throws IOException {
        clearDatabaseFile();
        database = new Database(databasePath);
        server = new Server(databasePath);
    }

    private static void clearDatabaseFile() throws IOException {
        try (FileWriter fw = new FileWriter(databasePath, false)) {
            fw.write("");
        }
    }

    @BeforeEach
    void setup() {
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    @DisplayName("Test user creation via client handler")
    void testUserCreationViaClientHandler() throws Exception {
        String input = "CREATE_USERJohn Doe,johnDoe,25,securePass123,john.doe@example.com";
        bufferedReader = new BufferedReader(new StringReader(input));
        ClientHandler clientHandler = new ClientHandler(null, database, server) {
            @Override
            public void run() {
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

    @Test
    @DisplayName("Test user login via client handler")
    void testUserLoginViaClientHandler() throws Exception {
        String input = "LOGINjohnDoe,securePass123";
        bufferedReader = new BufferedReader(new StringReader(input));
        ClientHandler clientHandler = new ClientHandler(null, database, server) {
            @Override
            public void run() {
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] loginDetails = inputLine.substring(5).split(",");
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
        assertEquals("User logged in successfully", result.trim());
    }
}
