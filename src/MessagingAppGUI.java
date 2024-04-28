import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessagingAppGUI {
    private JFrame loginFrame;
    private JFrame mainFrame;
    private JTabbedPane tabbedPane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MessagingAppGUI().createLoginGUI());
    }

    private void createLoginGUI() {
        loginFrame = new JFrame("Login");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> authenticateUser());
        cancelButton.addActionListener(e -> loginFrame.dispose());

        panel.add(loginButton);
        panel.add(cancelButton);

        loginFrame.add(panel);
        loginFrame.pack();
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    private void authenticateUser() {
        // TODO: Implement user authentication logic
        // If authentication fails, display error message and re-enable login GUI
        // If authentication succeeds, proceed to createMainGUI()
        createMainGUI(); // This should only be called if authentication is successful
    }

    private void createMainGUI() {
        loginFrame.dispose(); // Close login window
        mainFrame = new JFrame("Messaging Application");
        tabbedPane = new JTabbedPane();

        JPanel dmPanel = new JPanel(new FlowLayout()); // Direct Messages Panel
        JPanel friendsPanel = new JPanel(new FlowLayout()); // Friends List Panel

        // TODO: Implement loading and displaying messages in the Direct Messages Panel
        dmPanel.add(new JLabel("Your messages will appear here..."));

        // TODO: Implement loading and displaying friend list in the Friends List Panel
        friendsPanel.add(new JLabel("Your friends will be listed here..."));

        tabbedPane.addTab("Direct Messages", dmPanel);
        tabbedPane.addTab("Friends List", friendsPanel);

        // Switch between public and private modes
        JToggleButton modeToggle = new JToggleButton("Private Mode");
        modeToggle.addItemListener(e -> updateVisibility(modeToggle.isSelected()));

        // TODO: Implement the logic to handle switch between public and private mode

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(modeToggle, BorderLayout.WEST);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> openSearchGUI());
        bottomPanel.add(searchButton, BorderLayout.EAST);

        mainFrame.add(tabbedPane, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void updateVisibility(boolean isPrivate) {
        // TODO: Implement visibility switch logic (hide/show messages from non-friends)
    }

    private void openSearchGUI() {
        // TODO: Implement search function GUI
        JFrame searchFrame = new JFrame("Search User");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch(searchField.getText()));
        JPanel panel = new JPanel();
        panel.add(searchField);
        panel.add(searchButton);
        searchFrame.add(panel);
        searchFrame.pack();
        searchFrame.setVisible(true);
    }

    private void performSearch(String username) {
        // TODO: Implement the search functionality and results display
        // Possible actions: Friend, Block, Message, depending on search results
    }
}
