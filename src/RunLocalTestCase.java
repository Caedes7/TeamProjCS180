import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
    private static Database databaseCreate;
    private static Database databaseLogin;
    private static Server serverCreate;
    private static Server serverLogin;
    private static String databasePathCreate = "test_database.txt";
    private static String databasePathLogin = "data_Output.txt";

    @BeforeAll
    static void initialize() throws IOException {
        clearDatabaseFile(databasePathCreate);
        databaseCreate = new Database(databasePathCreate);
        serverCreate = new Server(databasePathCreate);
        databaseLogin = new Database(databasePathLogin);
        serverLogin = new Server(databasePathLogin);
    }

    private static void clearDatabaseFile(String path) throws IOException {
        try (FileWriter fw = new FileWriter(path, false)) {
            fw.write(""); // Clear the file content
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
        String input = "CREATE_USER John Doe, johnDoe, 25, securePass123, john.doe@example.com";
        bufferedReader = new BufferedReader(new StringReader(input));
        ClientHandler createHandler = new ClientHandler(null, databaseCreate, serverCreate) {
            @Override
            public void run() {
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] userDetails = inputLine.substring(11).split(", ");
                        boolean success = databaseCreate.createUser(userDetails[0], userDetails[1], Integer.parseInt(userDetails[2]), userDetails[3], userDetails[4]);
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
        createHandler.run();
        printWriter.flush();
        String result = stringWriter.toString();
        assertEquals("User created successfully", result.trim());
    }

    @Test
    @DisplayName("Test user login via client handler using a different database")
    void testUserLoginViaClientHandler() throws Exception {
        String loginInput = "LOGIN test, test";
        bufferedReader = new BufferedReader(new StringReader(loginInput));
        ClientHandler loginHandler = new ClientHandler(null, databaseLogin, serverLogin) {
            @Override
            public void run() {
                try {
                    String inputLine = bufferedReader.readLine();
                    if (inputLine != null) {
                        String[] loginDetails = inputLine.substring(5).split(", ");
                        NewUser user = serverLogin.loginUser(loginDetails[0], loginDetails[1]);
                        if (user != null) {
                            printWriter.println("User logged in successfully");
                        } else {
                            printWriter.println("Login failed. User/password combination does not exist.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        loginHandler.run();
        printWriter.flush();
        String result = stringWriter.toString();
        assertEquals("Login failed. User/password combination does not exist.", result.trim());
    }
}
