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

    }


}
