import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class runLocalTestCase {
    @Test(timeout = 1000)
    public void testExpectedOne() {
        // Set the input
        // Separate each input with a newline (\n).
        String name;
        String username;
        int age;
        String password;
        String email;
        try (BufferedReader bfr = new BufferedReader(new FileReader("input.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                switch(line.charAt(0)) {
                    case 1 ->
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
