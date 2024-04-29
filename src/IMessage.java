/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Interface: Message
 * Defines the methods for managing message-related data, including getting and setting
 * the sender, receiver, content, and timestamp of a message.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public interface IMessage {
    String getSender();
    String getReceiver();
    String getContent();
    long getTimestamp();
    void setContent(String content);
    String toString();
}
