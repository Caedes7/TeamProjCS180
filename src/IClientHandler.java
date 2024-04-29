import java.io.PrintWriter;

public interface IClientHandler {
    void run();
    void processChoice(String choiceString, PrintWriter out, NewUser user);
    void handleViewSentMessages(PrintWriter out, NewUser user);
    void handleViewReceivedMessages(PrintWriter out, NewUser user);
}
