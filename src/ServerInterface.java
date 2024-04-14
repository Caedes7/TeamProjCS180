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
     * Retrieves the following list of a specified user.
     * @param username The username whose following list is to be retrieved.
     * @return A list of NewUser objects representing the users being followed.
     */
    List<NewUser> getFollowingList(String username);

    /**
     * Blocks a specified user.
     * @param username The username of the user performing the block.
     * @param targetUsername The username of the user to be blocked.
     * @return true if the user is successfully blocked, false otherwise.
     */
    boolean blockUser(String username, String targetUsername);

    /**
     * Retrieves the last 100 messages of a specified user.
     *
     * @param username The username whose messages are to be retrieved.
     * @return A list of String objects representing the messages.
     */
    String getMessages(String username);
}