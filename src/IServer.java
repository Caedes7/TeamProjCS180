import java.util.List;

public interface IServer {
    NewUser loginUser(String username, String password);
    void run();
}
