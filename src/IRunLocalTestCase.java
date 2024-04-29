import java.io.IOException;
/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: IRunLocalTestCase
 * Defines a contract for managing the execution of unit tests within the Direct Messaging application.
 * This interface ensures the implementation of essential test functionalities critical for verifying the
 * integrity and correctness of the application components.
 *
 * Responsibilities include:
 * - Initializing the testing environment, which comprises setting up a test-specific database and server instances.
 * - Executing test cases to verify the application's ability to handle user creation, login, messaging, and user input validation.
 * - Ensuring that each component under test performs as expected both in isolation and in integrated scenarios.
 *
 * The methods defined in this interface correspond to key actions and checks that simulate user interactions
 * and system processes within the application, promoting robustness and reliability through comprehensive testing.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IRunLocalTestCase {
    static void initialize() throws IOException {

    }
    void setup();
    void testUserCreationViaClientHandler() throws Exception;
    void testUserLoginViaClientHandler() throws Exception;
    void testMessageCreation();
    void testMessageToString();
    void testNewUserCreation();
    void testValidUsername();
    void testValidEmail();
    void testValidPassword();
    void testMessageHandling();
}
