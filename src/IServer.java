import java.util.List;

public interface IServer {
    boolean loginUser(String username, String password);
    List<NewUser> getFriendsList(String username);
    List<NewUser> getBlockedUsersList(String username);
    String getMessages(String username);
}
