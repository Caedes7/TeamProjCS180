/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: Database
 * This handles storing and writing information in the database and does some basic processing regarding users.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IDatabase {
    boolean createUser(String name, String username, int age, String password, String email);
    boolean deleteUser(String username);
    NewUser searchUsers(String username);
    boolean addFriend(String username, String friendUsername);
    boolean removeFriend(String username, String friendUsername);
    boolean blockUser(String usernameBlocker, String usernameBlocked);
    boolean unblockUser(String usernameBlocker, String usernameBlocked);
    void viewUsers();
    boolean sendMessage(String senderUsername, String receiverUsername, String messageContent);
    boolean outputDatabase();
    boolean loadDatabase();
}
