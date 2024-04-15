public interface IDatabase {
    boolean createUser(String name, String username, int age, String password, String email);
    boolean deleteUser(String name, String username, int age, String password, String email);
    boolean outputDatabase();
    NewUser searchUsers(String name, String username, int age, String password, String email);
    void viewUsers();
    String getUserDetailsForTesting();
}
