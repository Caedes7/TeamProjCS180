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