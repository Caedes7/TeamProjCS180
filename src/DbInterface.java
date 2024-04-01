import java.util.ArrayList;
/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This interface contains all the methods required in Database.java
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public interface DbInterface {
    boolean createUser(String name, String username, int age, String password, String email,
                    ArrayList<NewUser> blocked, ArrayList<NewUser> friends);
    boolean deleteUser(String name, String username, int age, String password, String email,
                       ArrayList<NewUser> blocked,  ArrayList<NewUser> friends);
    boolean outputDatabase();
    boolean validateCredentials(NewUser user);
    NewUser searchUsers(String name, String username, int age, String password, String email,
                        ArrayList<NewUser> blocked,  ArrayList<NewUser> friends);
    void viewUsers();
}
