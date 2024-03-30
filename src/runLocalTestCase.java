import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import java.util.Scanner;

public class runLocalTestCase {
    private final String nameInput = "What name would you like to use?";
    private final String usernameInput = "What username would you like to use?";
    private final String ageInput = "What age would you like to use?";
    private final String ageError = "Please enter a valid age.";
    private final String passwordInput = "What password would you like to use?";
    private final String emailInput = "What email would you like to use?";
    private final String emailError = "Please enter a valid email (use @ symbol).";

    @Test(timeout = 1000)
    public void testExpectedOne() {
        Scanner s = new Scanner(System.in);

        System.out.println(nameInput);
        String name = s.nextLine();

        System.out.println(usernameInput);
        String username = s.nextLine();

        System.out.println(ageInput);
        int age = 0;

        do {
            try {
                age = Integer.parseInt(s.nextLine());

                if (age <= 0) {
                    System.out.println(ageError);
                }
            } catch (NumberFormatException e) {
                System.out.println(ageError);
            }
        } while (age <= 0);

        System.out.println(passwordInput);
        String password = s.nextLine();
        String email = "";

        do {
            System.out.println(emailInput);
            email = s.nextLine();

            if (!email.contains("@")) {
                email = "";
                System.out.println(emailError);
            }
        } while (email.isEmpty());

        newUser user = new newUser(name, username, age, password, email);
        System.out.println("After storing this information inside of our user class, this is the result: ");
        System.out.println(user.getName() + "\n" + user.getUsername() + "\n" +
                user.getAge() + "\n" + user.getPassword() + "\n" + user.getEmail());

        if (user.getName().equals(name) && user.getUsername().equals(username) &&
                user.getAge() == age && user.getPassword().equals(password)) {
            System.out.println("Test passed!");
        } else {
            System.out.println("Test Failed! Improper Storage of Data");
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
