import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static junit.framework.TestCase.*;

/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is test cases class that will test if different functionalities of the program have valid outputs.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public class RunLocalTestCase {
    private Server server;

    public RunLocalTestCase() {
        // Initialize the server with a test output file.
        server = new Server("test_data_Output.txt");
    }

    @Test(timeout = 1000)
    public void testUserCreationFromFile() throws IOException {
        // Test user creation based on data read from a file.
        String name = "", username = "", password = "", email = "";
        int age = 0;

        try (BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\jeeaa\\Desktop\\ece\\TeamProjCS180\\src\\input"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                switch (line.charAt(0)) {
                    case '1':
                        name = line.substring(1).trim();
                        break;
                    case '2':
                        username = line.substring(1).trim();
                        break;
                    case '3':
                        age = Integer.parseInt(line.substring(1).trim());
                        break;
                    case '4':
                        password = line.substring(1).trim();
                        break;
                    case '5':
                        email = line.substring(1).trim();
                        break;
                    default:
                        System.out.println("Unrecognized input format.");
                }
            }
        }

        // Assert that the user can be successfully created.
        assertTrue("User should be created successfully", server.createUser(name, username, age, password, email));
    }

    @Test
    public void testLoginAndMessaging() {
        // Testing user login
        assertTrue("Login should be successful", server.loginUser("johndoe", "securePass123"));

        // Testing messaging functionality
        //server.addMessage(new Message("johndoe", "janedoe", "Hello, Jane!", System.currentTimeMillis()));
        //assertFalse("Messages should not be empty", server.getMessages("johndoe").isEmpty());
    }

    @Test
    public void testDatabaseOutput() {
        // Test that the database correctly outputs to a file.
        assertTrue("Outputting database to file should succeed", server.outputDatabase());
    }

    @Test
    public void testUserDeletion() {
        // Ensure the user can be deleted.
        assertTrue("User should be deleted successfully", server.deleteUser("John Doe", "john_doe", 30, "securePass123", "john.doe@email.com"));
    }
}
