import java.io.IOException;

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
