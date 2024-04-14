import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    @Before
    public void setup() {
        // Initialize the server before each test.
        server = new Server("test_data_Output.txt");
    }

    @Test
    public void testUserCreationFromFile() throws IOException {
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

        assertTrue("User should be created successfully", server.createUser(name, username, age, password, email));
    }

    @Test
    public void testConcurrentLogins() {
        server.createUser("testUser", "testLogin", 25, "123456", "test@login.com");

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            service.submit(() -> assertTrue("Concurrent login should succeed",
                    server.loginUser("testLogin", "123456")));
        }
        service.shutdown();
        try {
            assertTrue("Failed to complete concurrent logins in time",
                    service.awaitTermination(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            fail("Test was interrupted");
        }
    }

    @Test
    public void testUserDeletion() {
        server.createUser("deleteUser", "deleteUser123", 30, "pass123", "delete@user.com");
        assertTrue("User should be deleted successfully",
                server.deleteUser("deleteUser", "deleteUser123", 30, "pass123", "delete@user.com"));
    }

    //Test
    //public void testMessagingSystem() {
    //    server.createUser("user1", "user1", 25, "user1pass", "user1@example.com");
     //   server.createUser("user2", "user2", 25, "user2pass", "user2@example.com");

        //server.sendMessage("user1", "user2", "Hello from user1!");
        //assertFalse("Messages list should not be empty", server.getMessages("user2").isEmpty());
   // }

    @Test
    public void testDatabaseOutput() {
        server.createUser("outputUser", "outputUser123", 30, "pass789", "output@user.com");
        assertTrue("Outputting database to file should succeed", server.outputDatabase());
    }
}
