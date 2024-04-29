
public interface IServer {
    void run();
    NewUser loginUser(String username, String password);
}
