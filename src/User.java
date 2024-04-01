/** Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * This is a program that will allow direct messaging, simultaneously, between several users.
 * This interface contains all the setters and getters of the NewUser class.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 1, 2024
 *
 */
public interface User {
    // Setters
    void setName(String name);
    void setUsername(String username);
    void setAge(int age);
    void setPassword(String password);
    void setEmail(String email);

    // Getters
    String getName();
    String getUsername();
    int getAge();
    String getPassword();
    String getEmail();
}