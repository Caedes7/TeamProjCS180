import java.io.Serializable;
import java.util.Date;
/**

 Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 Class: Message
 Represents a message sent between users. It contains sender and receiver information, the message content,
 and the timestamp when the message was sent. This class is part of the data structure that handles messaging
 in the direct messaging application.
 *
 @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 @version April 15, 2024
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // Serialization UID
    private String sender;
    private String receiver;
    private String content;
    private long timestamp;

    // Constructor
    public Message(String sender, String receiver, String content, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Useful for displaying message details in a readable format
    @Override
    public String toString() {
        return "From: " + sender + " To: " + receiver + " at " + new Date(timestamp) + ": " + content;
    }
}
