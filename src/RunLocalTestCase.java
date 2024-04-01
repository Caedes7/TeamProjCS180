import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;
/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This class is test cases class that will test if different functionalities of the program have valid outputs.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public class RunLocalTestCase {
    @Test(timeout = 1000)
    public void testExpectedOne() {
        String name = "";
        String username = "";
        int age = 0;
        String password = "";
        String email = "";
        ArrayList<NewUser> blocked = new ArrayList<NewUser>(0);
        ArrayList<NewUser> friends = new ArrayList<NewUser>(0);

        try (BufferedReader bfr = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                switch (line.charAt(0)) {
                    case '1' -> name = line.substring(1);
                    case '2' -> username = line.substring(1);
                    case '3' -> {
                        try {
                            if (Integer.parseInt(line.substring(1)) > 0) {
                                age = Integer.parseInt(line.substring(1));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Age - Ensure age is an Int > 0 and rerun");
                            return;
                        }
                    }
                    case '4' -> password = line.substring(1);
                    case '5' -> {
                        if (line.contains("@")) {
                            email = line.substring(1);
                        } else {
                            System.out.println("Invalid Email - Ensure email contains '@' and rerun");
                            return;
                        }
                    }
                    default -> System.out.println("Input file read.");
                }
            }
        } catch (IOException e) {
            throw new AssertionError("Failed to read input file", e);
        }

        NewUser test = new NewUser(name, username, age, password, email, blocked, friends);
        String expected = receiveInput(name, username, age, password, email, blocked, friends);
        String stuOut = getOutput(test);

        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("Error message if output is incorrect, customize as needed",
                expected, stuOut);

        if (stuOut.equals(expected)) {
            System.out.println("Output Matched Exactly. Test Case Passed");
            System.out.println("Expected based on input.txt: \n" + expected + "\n");
            System.out.println("Output from creating user object: \n" + stuOut);
        } else
            System.out.println("Test Failed!");
    }

    private String getOutput(NewUser test) {
        String name = test.getName();
        String username = test.getUsername();
        int age = test.getAge();
        String password = test.getPassword();
        String email = test.getEmail();
        ArrayList<NewUser> blocked = test.getBlocked();
        ArrayList<NewUser> friends = test.getFriends();

        return name + "\n" + username + "\n" + age + "\n" + password + "\n" + email + "\n" + blocked + "\n" + friends;
    }

    private String receiveInput(String name, String username, int age, String password, String email,
                                ArrayList<NewUser> blocked, ArrayList<NewUser> friends) {
        String expected = name + "\n" + username + "\n" + age + "\n" + password
                + "\n" + email + "\n" + blocked + "\n" + friends;

        return expected;
    }
}