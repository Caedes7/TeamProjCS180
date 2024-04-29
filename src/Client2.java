import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


@SuppressWarnings("FieldMayBeFinal")
public class Client2 extends Thread implements Serializable {
    @SuppressWarnings("FieldMayBeFinal")
    private static final int PORT = 1113;
    private static final String END_OF_TRANSMISSION = "EOT";
    private ExecutorService threadPool;
    private String name;
    private JFrame loginFrame, openFrame, newUserFrame, mainFrame;
    private JTextField usernameField, emailField, ageField, nameField;
    private JButton loginButton, createUserButton;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JPasswordField passwordField;

    public Client2(String name) {
        this.threadPool = Executors.newCachedThreadPool();
        this.name = name;
    }

    @Override
    public void run() {
        runClient();
    }

    private void runClient() {
        String hostname = "localhost";
        try {
            socket = new Socket(hostname, PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            SwingUtilities.invokeLater(this::createInitialFrame);
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void createInitialFrame() {
        openFrame = new JFrame("Welcome!");
        loginButton = new JButton("Login");
        createUserButton = new JButton("Create New User");

        loginButton.addActionListener(e -> {
            openFrame.dispose();
            createLoginFrame();
        });

        createUserButton.addActionListener(e -> {
            openFrame.dispose();
            createNewUserFrame();
        });

        openFrame.setLayout(new FlowLayout());
        openFrame.add(loginButton);
        openFrame.add(createUserButton);
        openFrame.setSize(1000, 800);
        openFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        openFrame.setVisible(true);
    }

    private void createLoginFrame() {
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
            threadPool.execute(() -> {
                try {
                    String message = "RE" + usernameField.getText() + "," + new String(passwordField.getPassword());
                    writer.println(message);
                    String response = reader.readLine();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(loginFrame, response);
                        loginFrame.dispose();
                        if (response.startsWith("User logged in successfully")) {
                            createMainGUI();
                        } else {
                            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password");
                            createLoginFrame();
                        }
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
        //loginFrame.pack();
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    private void createNewUserFrame() {
        newUserFrame = new JFrame("New User Frame");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        newUserFrame.setSize(1000, 800);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
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
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(newUserFrame, response);
                    newUserFrame.dispose();
                    if (response.startsWith("User created successfully")) {
                        createMainGUI();
                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> newUserFrame.dispose());

        panel.add(createButton);
        panel.add(cancelButton);

        newUserFrame.add(panel);
        newUserFrame.pack();
        newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newUserFrame.setVisible(true);
    }

    private void createMainGUI() {
        mainFrame = new JFrame("Client Operations");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(400, 600);
        mainFrame.setLayout(new GridLayout(10, 1));

        // Create buttons for each operation
        String[] options = {
                "Search user", "Block user", "Add friend", "Message a friend",
                "View Received Messages", "View Sent Messages", "Unblock user",
                "Remove friend", "Make Account Public/Private", "Exit"
        };

        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> performAction(option));
            mainFrame.add(button);
        }

        mainFrame.setVisible(true);
    }

    private void performAction(String action) {
        switch (action) {
            case "Search user":
                promptAndSend("Enter the username you want to search:", "1");
                break;
            case "Block user":
                promptAndSend("Enter the username you want to block:", "2");
                break;
            case "Add friend":
                promptAndSend("Enter the username you want to add as a friend:", "3");
                break;
            case "Message a friend":
                String username = JOptionPane.showInputDialog(mainFrame, "Enter the friend's username:");
                if (username != null && !username.isEmpty()) {
                    promptAndSend("Type your message for " + username + ":", "4" + username + "~");
                }
                break;
            case "View Received Messages":
                sendCommand("5");
                break;
            case "View Sent Messages":
                sendCommand("6");
                break;
            case "Unblock user":
                promptAndSend("Enter the username of the user you want to unblock:", "7");
                break;
            case "Remove friend":
                promptAndSend("Enter the username you want to remove as a friend:", "8");
                break;
            case "Make Account Public/Private":
                JOptionPane.showMessageDialog(null, "Changed Account Status!");
                break;
            case "Exit":
                sendCommand("0");
                mainFrame.dispose();
                try {
                    openFrame.dispose();
                    loginFrame.dispose();
                    newUserFrame.dispose();

                } catch (NullPointerException e) {
                    cleanupAndExit();
                }
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    cleanupAndExit();
                }
                cleanupAndExit();
                break;
            default:
                JOptionPane.showMessageDialog(mainFrame, "Invalid option selected.");
                break;
        }
    }

    private void cleanupAndExit() {
        threadPool.shutdown();
        threadPool.shutdownNow();
        try {
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Error closing network socket: " + ex.getMessage());
        }
    }


    private void promptAndSend(String prompt, String commandPrefix) {
        String input = JOptionPane.showInputDialog(mainFrame, prompt);
        if (input != null && !input.isEmpty()) {
            sendCommand(commandPrefix + input);
        }
    }

    private void sendCommand(String command) {
        threadPool.execute(() -> {
            try {
                writer.println(command);
                String response;
                while (true) {
                    response = reader.readLine();
                    if (response == null || response.trim().isEmpty()) {
                        break;  // Break if the end signal (an empty line) is received
                    }
                    String finalResponse = response; // Final variable for use in lambda
                    if (!finalResponse.equals(END_OF_TRANSMISSION)) {
                        SwingUtilities.invokeAndWait(() -> JOptionPane.showMessageDialog(mainFrame, finalResponse));
                    }
                }
            } catch (IOException ex) {
                String errorMsg = "Network error: " + ex.getMessage();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(mainFrame, errorMsg));
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();  // Handle exceptions thrown by invokeAndWait
            }
        });
    }


    public static void main(String[] args) {
        String clientName = "Client2";
        Client client = new Client(clientName);
        client.start();
    }
}
