/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: Server
 * Contains all the commands and interactions that the server is required to handle.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IServer {
    void run();
    NewUser loginUser(String username, String password);
}
