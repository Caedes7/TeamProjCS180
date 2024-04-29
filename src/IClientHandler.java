import java.io.PrintWriter;
/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: ClientHandler
 * Handles the client and server communications and takes in puts from the user and converts to commands for the server
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IClientHandler {
    void run();
    void processChoice(String choiceString, PrintWriter out, NewUser user);
    void handleViewSentMessages(PrintWriter out, NewUser user);
    void handleViewReceivedMessages(PrintWriter out, NewUser user);
}
