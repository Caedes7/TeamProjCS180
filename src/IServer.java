import java.util.List;

public interface IServer {
    void run();
    boolean loginUser(String username, String password);
    List<NewUser> getFriendsList(String username);
    List<NewUser> getFollowingList(String username);
    boolean blockUser(String usernameBlocker, String usernameBlocked);
    String getMessages(String username);
}
