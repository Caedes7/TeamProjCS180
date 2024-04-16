import java.io.*;

public class SerializationTest {
    public static void main(String[] args) {
        NewUser user = new NewUser("John Doe", "johndoe", 30, "pass123", "john@example.com");
        try {
            FileOutputStream fileOut = new FileOutputStream("test.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
            System.out.println("Object serialized successfully");

            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            NewUser userRead = (NewUser) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Object deserialized: " + userRead);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }
}