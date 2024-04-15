public interface IMessage {
    String getSender();
    String getReceiver();
    String getContent();
    long getTimestamp();
    void setContent(String content);
    String toString();
}
