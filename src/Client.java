import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

/**
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project 1 -- Direct Messaging
 * Class: Client
 * Manages the client-side functionality for a direct messaging application. This class is responsible for
 * establishing a connection to the server, handling user inputs, and processing server responses. It allows
 * users to create a new account or log into an existing one and perform various actions like searching for other
 * users, blocking/unblocking users, adding/removing friends, and sending messages. The Client class utilizes
 * a thread pool to manage its tasks and maintains an interactive command line interface for user interaction.
 *
 * @author Jeeaan Ahmmed, Ishaan Krishna Agrawal, Pranav Yerram, Michael Joseph Vetter
 * @version April 29, 2024
 */
public class Client extends Thread implements Serializable {
    private static final int PORT = 1113;
    private ExecutorService threadPool;
    private Scanner sc = new Scanner(System.in);
    private String name;
    private JFrame loginFrame, openFrame, newUserFrame;
    private JTextField usernameField, passwordField, emailField, ageField, nameField;
    private JButton loginButton, createUserButton;

    public Client(String name) {
        this.threadPool = Executors.newCachedThreadPool();
        this.name = name;
    }

    public void runClient() {
        String hostname = "localhost";
        try {
            Socket socket = new Socket(hostname, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            SwingUtilities.invokeLater(() -> createInitialFrame(writer, reader));
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
        }
    }

    private void createInitialFrame(PrintWriter writer, BufferedReader reader) {
        openFrame = new JFrame("Welcome!");
        loginButton = new JButton("Login");
        createUserButton = new JButton("Create New User");

        loginButton.addActionListener(e -> {
            openFrame.dispose();
            createLoginFrame(writer, reader);
        });

        createUserButton.addActionListener(e -> {
            openFrame.dispose();
            createNewUserFrame(writer, reader);
        });

        openFrame.setLayout(new FlowLayout());
        openFrame.add(loginButton);
        openFrame.add(createUserButton);
        openFrame.setSize(1000, 800);
        openFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        openFrame.setVisible(true);
    }

    private void createLoginFrame(PrintWriter writer, BufferedReader reader) {
        loginFrame = new JFrame("Login");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        loginFrame.setSize(1000, 800);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> {
            threadPool.execute(() -> { // Execute network operations on a separate thread
                try {
                    String message = "RE" + usernameField.getText() + "," + new String(((JPasswordField) passwordField).getPassword());
                    writer.println(message);
                    String response = reader.readLine();
                    SwingUtilities.invokeLater(() -> { // Ensure GUI updates are on the EDT
                        JOptionPane.showMessageDialog(loginFrame, response);
                        loginFrame.dispose();
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(loginFrame, "Error communicating with the server."));
                }
            });
        });

        cancelButton.addActionListener(e -> loginFrame.dispose());

        panel.add(loginButton);
        panel.add(cancelButton);

        loginFrame.add(panel);
        loginFrame.pack();
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    private void createNewUserFrame(PrintWriter writer, BufferedReader reader) {
        newUserFrame = new JFrame("New User Frame");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        newUserFrame.setSize(1000, 800);
        usernameField = new JTextField(15);
        passwordField = new JTextField(15);
        nameField = new JTextField(15);
        emailField = new JTextField(20);
        ageField = new JTextField(15);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        JButton createButton = new JButton("Create User");
        JButton cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            String message = "CREATE_USER" + nameField.getText() + "," +
                    usernameField.getText() + "," +
                    ageField.getText() + "," +
                    passwordField.getText() + "," +
                    emailField.getText();
            writer.println(message);
            try {
                String response = reader.readLine();
                System.out.println(response); // Output server response
                System.out.println("Create user not working"); //delete later
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            newUserFrame.dispose();
        });

        cancelButton.addActionListener(e -> newUserFrame.dispose());

        panel.add(createButton);
        panel.add(cancelButton);

        newUserFrame.add(panel);
        newUserFrame.pack();
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newUserFrame.setVisible(true);
    }

    public static void main(String[] args) {
        String clientName = "Client1";
        Client client = new Client(clientName);
        client.runClient();
    }
}
