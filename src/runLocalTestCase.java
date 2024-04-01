import org.junit.Test;

import javax.swing.text.Style;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class runLocalTestCase {
    @Test(timeout = 1000)
    public void testExpectedOne() {
        String name = "";
        String username = "";
        int age = 0;
        String password = "";
        String email = "";
        ArrayList<newUser> blocked = new ArrayList<newUser>(0);
        ArrayList<newUser> friends = new ArrayList<newUser>(0);


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

        newUser test = new newUser(name, username, age, password, email, blocked, friends);

        // Pair the input with the expected result
        String expected = receiveInput(name, username, age, password, email, blocked, friends);

        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method


        // Retrieves the output from the program
        String stuOut = getOutput(test);

        // Trims the output and verifies it is correct.
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

    private String getOutput(newUser test) {
        String name = test.getName();
        String username = test.getUsername();
        int age = test.getAge();
        String password = test.getPassword();
        String email = test.getEmail();
        ArrayList<newUser> blocked = test.getBlocked();
        ArrayList<newUser> friends = test.getFriends();

        return name + "\n" + username + "\n" + age + "\n" + password + "\n" + email + "\n" + blocked + "\n" + friends;
    }

    private String receiveInput(String name, String username, int age, String password, String email,
                                ArrayList<newUser> blocked, ArrayList<newUser> friends) {
        String expected = name + "\n" + username + "\n" + age + "\n" + password
                + "\n" + email + "\n" + blocked + "\n" + friends;

        return expected;
    }
}