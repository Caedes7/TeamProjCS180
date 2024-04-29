/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: IClient
 * Defines the methods for the client-server communication, including user registration, login,
 * user search and management, friend management, and messaging.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IClient {
    void run();
    void runClient();
    void createInitialFrame();
    void createLoginFrame();
    void createNewUserFrame();
    void createMainGUI();
    void performAction(String action);
    void cleanupAndExit();
    void promptAndSend(String prompt, String commandPrefix);
    void sendCommand(String command);
}
