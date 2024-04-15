/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 *
 * Interface: IClient
 *
 * Defines the methods for the client-server communication, including user registration, login,
 * user search and management, friend management, and messaging.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 15, 2024
 */
public interface IClient {

    void createUser(String name, String username, int age, String password, String email);
    boolean login(String username, String password);


    boolean searchUser(String username);
    boolean blockUser(String username);
    boolean unblockUser(String username);


    boolean addFriend(String username);
    boolean removeFriend(String username);


    boolean sendMessage(String username, String message);
}