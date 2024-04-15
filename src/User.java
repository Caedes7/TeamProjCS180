import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This interface contains all the setters and getters of the NewUser class.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public interface User extends Serializable {
    String getName();
    String getUsername();
    int getAge();
    String getPassword();
    String getEmail();
    ArrayList<NewUser> getBlocked();
    ArrayList<NewUser> getFriends();
    ArrayList<NewUser> getFollowing();
    List<Message> getMessagesWithUser(String otherUser);
    Map<String, List<Message>> getMessages();

    void setName(String name);
    void setUsername(String username);
    void setAge(int age);
    void setPassword(String password);
    void setEmail(String email);
    void setBlocked(ArrayList<NewUser> blocked);
    void setFriends(ArrayList<NewUser> friends);
    void setFollowing(ArrayList<NewUser> following);
    void addMessage(Message message);

    boolean isValidUsername(String userName);
    boolean isValidEmail(String eMail);
    boolean isValidPassword(String passWord);
    boolean isEqual(NewUser otherUser);

    String toString();
}