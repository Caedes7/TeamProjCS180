import java.util.List;

public interface ServerInterface {

    /**
     * Authenticates a user with the given username and password.
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @return true if authentication is successful, false otherwise.
     */
    boolean loginUser(String username, String password);

    /**
     * Retrieves the friends list of a specified user.
     * @param username The username whose friends list is to be retrieved.
     * @return A list of NewUser objects representing the friends.
     */
    List<NewUser> getFriendsList(String username);

    /**
     * Retrieves the list of users blocked by a specified user.
     * @param username The username whose blocked list is to be retrieved.
     * @return A list of NewUser objects representing the blocked users.
     */
    List<NewUser> getBlockedUsersList(String username);

    /**
     * Retrieves the formatted messages involving a specified user, showing the last 100 messages with each contact.
     * @param username The username whose messages are to be retrieved.
     * @return A string representation of the messages.
     */
    String getMessages(String username);
}