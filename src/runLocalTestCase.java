import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import java.util.Scanner;

public class runLocalTestCase {
    private final String nameInput = "What name would you like to use?";
    private final String usernameInput = "What username would you like to use?";
    private final String ageInput = "What age would you like to use?";
    private final String ageError = "Please enter a valid age.";
    private final String passwordInput = "What password would you like to use?";

    @Test(timeout = 1000)
    public void testExpectedOne() {
        Scanner s = new Scanner(System.in);

        System.out.println(nameInput);
        String name = s.nextLine();

        System.out.println(usernameInput);
        String username = s.nextLine();

        System.out.println(ageInput);
        int ageInput = 0;
        do {
            try {
                ageInput = Integer.parseInt(s.nextLine());
                if (ageInput > 0) {
                    System.out.println(ageError);
                }
            } catch (NumberFormatException e) {
                System.out.println(ageError);
            }
        } while (ageInput <= 0);

        System.out.println(passwordInput);
        String password = s.nextLine();

        User newUser = new User(name, username, ageInput, password);
        System.out.println("After storing this information inside of our user class, this is the result: ");
        System.out.println(newUser.getName() + "\n" + newUser.getUsername() + "\n" +
                newUser.getAge() + "\n" + newUser.getPassword());

        if (newUser.getName().equals(name) && newUser.getUsername().equals(username) &&
                newUser.getAge() == age && newUser.getPassword().equals(password)) {
            System.out.println("Test passed!");
        }

        /*
        String input = "Line One\nLine Two\n";

        // Pair the input with the expected result
        String expected = "Insert the expected output here";

        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method
        receiveInput(input);
        TestProgram.main(new String[0]);

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("Error message if output is incorrect, customize as needed",
                expected.trim(), stuOut.trim());
        */
    }


}
