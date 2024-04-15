import java.util.ArrayList;
import java.util.List;

public interface IUser {
    String getName();
    String getUsername();
    int getAge();
    String getPassword();
    String getEmail();
    List<NewUser> getBlocked();
    List<NewUser> getFriends();
    List<NewUser> getFollowing();
    List<Message> getMessagesWithUser(String otherUser);

    void setName(String name);
    void setUsername(String username);
    void setAge(int age);
    void setPassword(String password);
    void setEmail(String email);
    void setBlocked(ArrayList<NewUser> blocked);
    void setFriends(ArrayList<NewUser> friends);
    void setFollowing(ArrayList<NewUser> following);
    void addMessage(Message message);
}
