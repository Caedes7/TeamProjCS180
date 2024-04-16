import java.util.List;

public interface IDatabase {
    boolean createUser(String name, String username, int age, String password, String email);
    boolean deleteUser(String username);
    boolean outputDatabase();
    boolean addFriend(String username, String friendUsername);
    boolean blockUser(String usernameBlocker, String usernameBlocked);
    NewUser searchUsers(String username);
    void viewUsers();
}
