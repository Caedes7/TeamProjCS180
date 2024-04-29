README

TEAM PROJECT - Direct Messaging

TEAM MEMBERS:

    Jeeaan Ahmmed
    Ishaan Krishna Agrawal
    Pranav Yerram
    Michael Joseph Vetter

1. How to Compile and Run the Project

To run the program, run Server first. Then, Client can be run. Client2, which is an exact clone of Client1,
is also included to run multiple configurations for testing of multiple logins as well. Also, To test the
program, you can compile it through the runLocalTestCase.java class.

2. Submissions List

    Phase 1: Jeeaan Ahmmed
    Phase 2: Michael Vetter
    Phase 3: Ishaan Agrawal

3. Detailed Description of Each Class

Database

    createUser: Adds a new user to the database if the user does not already exist.
    deleteUser: Removes a user from the database based on username.
    outputDatabase: Writes the current state of the database, including user details and their relationships,
    to a specified output file.
    blockUser: Blocks another user from being able to interact.
    searchUsers: Searches for a user in the database using their username.
    addFriend: Adds another user to the current user's friends list if not already added.
    viewUsers: Displays a list of all users in the database for administrative purposes.

NewUser

    Variables: Name, Username, Age, Password, Email
    Constructor: NewUser(String name, String username, int age, String password, String email) initializes a new user
    with the given parameters.
    isValidUsername: Checks to see if the username is valid (only contains letters, digits, and '_').
    isValidEmail: Checks if the email contains '@'.
    isEqual: Checks if two users are the same based on their attributes.

runLocalTestCase

    This class tests the functionality of the user creation from a file called input.txt.
    Provides methods to test user creation, login functionality, and the output of the user data to a system file.

Server

    Handles initial client request and starts the Client Handler, which handles the processing of the clients.

    Variables:
        executorService: Handles client requests concurrently.
        PORT: Port number of the server.
    Methods:
        run: Maintains the server's operation, accepting client connections and processing requests.
        main: Entry point to start the server.

Message

    Represents a message sent between users. It contains sender and receiver information, the message content, and the
    timestamp when the message was sent. This class is part of the data structure that handles messaging in the
    direct messaging application.

Client

    Manages the client-side functionality for a direct messaging application. This class is responsible for establishing
     a connection to the server, handling user inputs, through a full GUI, and processing server responses. It allows users to create a new
     account or log into an existing one and perform various actions like searching for other users, blocking/unblocking
      users, adding/removing friends, and sending messages. The Client class utilizes a thread pool to manage its tasks
      and maintains an interactive command line interface for user interaction.

ClientHandler

    Handles incoming client requests by reading commands from the client and processing them accordingly. It can create
    new user profiles, log users in, and perform other actions based on client commands. This class is designed to run
    on a separate thread for each client connection to ensure concurrent handling.