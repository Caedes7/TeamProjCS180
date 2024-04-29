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
import java.util.Date;
/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Class: RunLocalTestCase
 * Manages the execution of unit tests for the Direct Messaging application. This class is responsible for
 * initializing the testing environment, including setting up a database and a server instance, and executing
 * various test scenarios such as user creation, login, and messaging functionalities.
 * Each test ensures that the application logic is correctly managing user data and interactions.
 * Tests include:
 * - User creation via client handler
 * - User login via client handler
 * - Message creation and formatting
 * - Validation of user input (username, email, and password)
 * - Handling of sent and received messages between users
 * This class uses JUnit for managing test lifecycles and assertions, ensuring that each component functions
 * as expected in isolation.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */

public class RunLocalTestCase {

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
            fw.write(""); // Clear the file content for future tests (necessary to run this file correctly)
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
                        boolean success = databaseCreate.createUser(userDetails[0], userDetails[1],
                                Integer.parseInt(userDetails[2]), userDetails[3], userDetails[4]);
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
        System.out.println("Test checks the creation of a user and has passed correctly.");
    }

    @Test
    @DisplayName("Test user login via client handler")
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
        System.out.println("Test checks user login and has passed correctly.");
    }

    @Test
    void testMessageCreation() {
        long timestamp = System.currentTimeMillis();
        Message message = new Message("sender", "receiver", "Hello!", timestamp);
        assertEquals("sender", message.getSender());
        assertEquals("receiver", message.getReceiver());
        assertEquals("Hello!", message.getContent());
        assertEquals(timestamp, message.getTimestamp());
        System.out.println("Test checks message creation and has passed correctly.");
    }

    @Test
    void testMessageToString() {
        long timestamp = 1609459200000L; // Example timestamp for January 1, 2021
        Message message = new Message("sender", "receiver", "Happy New Year!", timestamp);
        String expected = "From: sender To: receiver at " + new Date(timestamp) + ": Happy New Year!";
        assertEquals(expected, message.toString());
        System.out.println("Test checks the toString output of a message and has passed correctly.");
    }

    @Test
    void testNewUserCreation() {
        NewUser user = new NewUser("John Doe", "johndoe", 30,
                "securePass123", "john.doe@example.com");
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe", user.getUsername());
        assertEquals(30, user.getAge());
        assertEquals("securePass123", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        System.out.println("Test checks NewUser creation and has passed correctly.");
    }

    @Test
    void testValidUsername() {
        assertTrue(NewUser.isValidUsername("johndoe_123"));
        assertFalse(NewUser.isValidUsername("john~doe"));
        assertFalse(NewUser.isValidUsername(null));
        assertFalse(NewUser.isValidUsername(""));
        System.out.println("Test checks username validation and has passed correctly.");
    }

    @Test
    void testValidEmail() {
        assertTrue(NewUser.isValidEmail("john.doe@example.com"));
        assertFalse(NewUser.isValidEmail("john.doe"));
        assertFalse(NewUser.isValidEmail(null));
        System.out.println("Test checks email validation and has passed correctly.");
    }

    @Test
    void testValidPassword() {
        assertTrue(NewUser.isValidPassword("mypassword123"));
        assertFalse(NewUser.isValidPassword(" "));
        assertFalse(NewUser.isValidPassword(null));
        System.out.println("Test checks password validation and has passed correctly.");
    }

    @Test
    void testMessageHandling() {
        NewUser user = new NewUser("John Doe", "johndoe",
                30, "securePass123", "john.doe@example.com");
        Message sentMessage = new Message("johndoe", "janedoe",
                "Hello, Jane!", System.currentTimeMillis());
        Message receivedMessage = new Message("janedoe", "johndoe",
                "Hi, John!", System.currentTimeMillis());

        user.addSentMessage("janedoe", sentMessage);
        user.addReceivedMessage("janedoe", receivedMessage);

        assertEquals(1, user.getSentMessages().get("janedoe").size());
        assertEquals(sentMessage, user.getSentMessages().get("janedoe").get(0));
        assertEquals(1, user.getReceivedMessages().get("janedoe").size());
        assertEquals(receivedMessage, user.getReceivedMessages().get("janedoe").get(0));
        System.out.println("Test checks handling of messages (sent and received) and has passed correctly.");
    }
}
