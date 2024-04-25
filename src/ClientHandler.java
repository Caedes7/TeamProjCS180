import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
/**

 Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 Class: ClientHandler
 Handles incoming client requests by reading commands from the client and processing them accordingly.
 It can create new user profiles, log users in, and perform other actions based on client commands.
 This class is designed to run on a separate thread for each client connection to ensure concurrent handling.
 *
 @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 @version April 15, 2024
 */
public class ClientHandler extends Thread implements Serializable {
    private Socket clientSocket;
    private Database database;
    private Server server;

    public ClientHandler(Socket socket, Database database, Server server) {
        this.clientSocket = socket;
        this.database = database;
        this.server = server;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;
            NewUser newSingleUser = null;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("CREATE_USER")) {
                    String[] userDetails = inputLine.substring(11).split(",");
                    String name = userDetails[0];
                    String username = userDetails[1];
                    int age = Integer.parseInt(userDetails[2]);
                    String password = userDetails[3];
                    String email = userDetails[4];

                    if (!NewUser.isValidUsername(username)) {
                        out.println("Failed to create user: Invalid username");
                        continue;
                    }
                    if (!NewUser.isValidPassword(password)) {
                        out.println("Failed to create user: Invalid password");
                        continue;
                    }
                    if (!NewUser.isValidEmail(email)) {
                        out.println("Failed to create user: Invalid email");
                        continue;
                    }

                    boolean success = database.createUser(name, username, age, password, email);
                    if (success) {
                        newSingleUser = new NewUser(name, username, age, password, email);
                        out.println("User created successfully");
                    } else {
                        out.println("Failed to create user");
                    }
                } else if (inputLine.startsWith("RE")) {
                    String[] loginDetails = inputLine.substring(2).split(",");
                    String username = loginDetails[0];
                    String password = loginDetails[1];

                    newSingleUser = server.loginUser(username, password);
                    if (newSingleUser != null) {
                        out.println("User logged in successfully");
                    } else {
                        out.println("User/password combination does not exist.");
                    }
                } else {
                    processChoice(inputLine, out, newSingleUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processChoice(String choiceString, PrintWriter out, NewUser user) {
        int choice = Integer.parseInt(choiceString.substring(0, 1));
        String message = (choiceString.contains("~")) ? choiceString.substring(choiceString.indexOf("~")+1) : null;
        String optionData = "";
        if (message != null) {
            optionData = (choiceString.length() > 1) ? choiceString.substring(1,choiceString.indexOf("~")) : null;
        } else {
            optionData = (choiceString.length() > 1) ? choiceString.substring(1) : null;
        }




        switch (choice) {
            case 1: // Search User
                NewUser found = database.searchUsers(optionData);
                if (found != null) {
                    String output = found.toStringSearch();
                    out.println(output);
                } else {
                    out.println("User not found");
                }
                break;
            case 2: // Block User
                boolean blocked = database.blockUser(user.getUsername(), optionData);
                out.println(blocked ? "User blocked!" : "Could not block user.");
                StringBuilder blockedUsernames = new StringBuilder();
                for (NewUser eachUser : user.getBlocked()) {
                    if (!blockedUsernames.equals("")) {
                        blockedUsernames.append(", ");
                    }
                    blockedUsernames.append(eachUser.getUsername());
                }
                String concatenatedUsernames = blockedUsernames.toString();
                out.println(concatenatedUsernames);
                break;
            case 3: // Add Friend
                boolean friend = database.addFriend(user.getUsername(), optionData);
                out.println(friend ? "User friended!" : "Could not add user as friend.");
                StringBuilder friendUsernames = new StringBuilder();
                for (NewUser eachUser : user.getFriends()) {
                    if (!friendUsernames.equals("")) {
                        friendUsernames.append("~");
                    }
                    friendUsernames.append(eachUser.getUsername());
                }
                String concatenatedUsernamesfriends = friendUsernames.toString();
                out.println(concatenatedUsernamesfriends);
                break;
            case 4: // Send Message
                boolean sentMessage = database.sendMessage(user.getUsername(),optionData, message);
                out.println(sentMessage ? "Message Sent!" : "Could not send message.");
                break;
            case 5: // View Received Messages
                out.println(user.getReceivedMessages());
                break;
            case 6: // View Sent Messages
                handleViewSentMessages(out, user);
                out.println("eof");
                break;
            case 7:
                //out.println(user.getBlocked());
                boolean unBlocked = database.unblockUser(user.getUsername(), optionData);
                out.println(unBlocked ? "User unBlocked!" : "Could not unblock user.");
                break;
            case 8:
                boolean removeFriend = database.removeFriend(user.getUsername(), optionData);
                out.println(removeFriend ? "User unfriended!" : "Could not remove user as friend.");
                break;
            case 0: // Exit
                out.println("Exiting.");
                break;
            default:
                out.println("Invalid choice. Please try again.");
                break;
        }
        out.flush();
    }

    public void handleViewSentMessages(PrintWriter out, NewUser user) {
        if (user.getSentMessages().isEmpty()) {
            out.println("No messages sent.");
        } else {
            user.getSentMessages().forEach((receiver, messages) -> {
                messages.forEach(message -> out.println(message.toString()));
            });
        }
    }

}
