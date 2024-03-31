import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

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
        // Set the input
        // Separate each input with a newline (\n). 
        String input = "Line One\nLine Two\n";

        // Pair the input with the expected result
        String expected = "Insert the expected output here";

        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method
        receiveInput(input);
        if (Database.outputDatabase()) {

        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct. 
        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("Error message if output is incorrect, customize as needed",
                expected.trim(), stuOut.trim());


}

    private String getOutput() {
    }

    private void receiveInput(String input) {
    }
