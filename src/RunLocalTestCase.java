import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is test cases class that will test if different functionalities of the program have valid outputs.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */

public class RunLocalTestCase {
    private Database database;

    public RunLocalTestCase() {
        // Initialize the database with a test output file.
        database = new Database("test_data_Output.txt");
    }

    @Test(timeout = 1000)
    public void testUserCreationFromFile() throws IOException {
        // Test user creation based on data read from a file.
        String name = "", username = "", password = "", email = "";
        int age = 0;
        ArrayList<NewUser> blocked = new ArrayList<>();
        ArrayList<NewUser> friends = new ArrayList<>();

        try (BufferedReader bfr = new BufferedReader(new FileReader("C:\\Users\\jeeaa\\Desktop\\ece\\TeamProjCS180\\src\\input"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                switch (line.charAt(0)) {
                    case '1':
                        name = line.substring(1).trim();  // Trims any leading or trailing whitespace
                        break;
                    case '2':
                        username = line.substring(1).trim();
                        break;
                    case '3':
                        try {
                            age = Integer.parseInt(line.substring(1).trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Age - Ensure age is an Int > 0 and rerun");
                            return;
                        }
                        break;
                    case '4':
                        password = line.substring(1).trim();
                        break;
                    case '5':
                        if (line.substring(1).contains("@")) {
                            email = line.substring(1).trim();
                        } else {
                            System.out.println("Invalid Email - Ensure email contains '@' and rerun");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Unrecognized input format.");
                }
            }
        } catch (IOException e) {
            throw new AssertionError("Failed to read input file", e);
        }

        // Create user from the read data.
        assertTrue("User should be created successfully", database.createUser(name, username, age, password, email));

        // Verify the output of the database with the expected result.
        String expectedOutput = name + "\n" + username + "\n" + age + "\n" + password + "\n" + email;
        String actualOutput = database.getUserDetailsForTesting();
        assertEquals("Expected user details should match the actual details", expectedOutput, actualOutput);
    }

    @Test
    public void testDatabaseFunctions() {
        // Additional tests for database functionality such as deletion and output.
        assertTrue("User should be deleted successfully", database.deleteUser("John Doe", "john_doe", 30, "securePass123", "john@example.com"));
        assertFalse("Attempting to create an already existing user should fail", database.createUser("John Doe", "john_doe", 30, "securePass123", "john@example.com"));
        assertTrue("Outputting database to file should succeed", database.outputDatabase());
    }
}
