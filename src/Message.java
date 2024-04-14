import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // Serialization UID

    private String sender;
    private String receiver;
    private String content;
    private long timestamp;

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

    public String toString() {
        return "From: " + sender + " To: " + receiver + " at " + new java.util.Date(timestamp) + ": " + content;
    }
}