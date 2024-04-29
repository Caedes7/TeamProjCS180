import java.util.List;

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
