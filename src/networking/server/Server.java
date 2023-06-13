package networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import networking.utils.Message;
import utils.Constants;
import utils.Logger;

/**
 * Server class
 *
 * @author sageTendo
 */
public class Server {

  static final ConcurrentHashMap<String, ClientThread> connectedUsers = new ConcurrentHashMap<>();
  static final ConcurrentHashMap<String, GameSession> gameSessions = new ConcurrentHashMap<>();
  ServerSocket serverSocket;

  /**
   * Default Constructor
   */
  public Server() {
    this(Constants.DEFAULT_PORT);
  }

  /**
   * Constructor
   *
   * @param port the port to listen on
   */
  public Server(int port) {
    try {
      serverSocket = new ServerSocket(port);
      Logger.console("Server", "Server started on port " + port);
      listener();
    } catch (IOException e) {
      Logger.console("SERVER ERR", e.getMessage());
    }
  }

  /**
   * Listener for incoming connections and starts a new thread for each
   */
  private void listener() {
    new Thread(() -> {
      while (true) {
        try {
          Socket clientSocket = serverSocket.accept();
          Logger.console("Server",
              "Client connected [" + clientSocket.getInetAddress() + "]");
          new ClientThread(clientSocket).start();
        } catch (IOException e) {
          Logger.console("SERVER ERR", e.getMessage());
        }
      }
    }).start();
  }

  /**
   * Add user to the server
   *
   * @param username     the username to register
   * @param clientThread the client thread of the user to register
   */
  public static void addUser(String username, ClientThread clientThread) {
    connectedUsers.put(username, clientThread);
  }

  /**
   * Check if user with username exists
   *
   * @param username the username to check
   * @return true if user exists
   */
  public static boolean hasUser(String username) {
    return connectedUsers.containsKey(username);
  }

  /**
   * @param username the username of the user to remove from the server
   */
  public static void removeUser(String username) {
    connectedUsers.remove(username);
  }

  /**
   * Add a game session to the server
   *
   * @param gameSession the gameSession to add
   */
  public static void addGameSession(GameSession gameSession) {
    gameSessions.put(gameSession.ID, gameSession);
  }

  /**
   * Check if a game session exists
   *
   * @param creator the creator of the game session
   * @return true if the game session exists
   */
  public static boolean hasGameSession(String creator) {
    return gameSessions.containsKey(creator);
  }

  /**
   * Check if a game session is available to join
   *
   * @param creator the creator of the game session
   * @return true if the game session is available
   */
  public static boolean gameSessionAvailable(String creator) {
    return gameSessions.get(creator).isAvailable();
  }

  /**
   * Gets the gameSessions
   *
   * @return the gameSessions as a string
   */
  public static String getGameSessions() {
    if (gameSessions.isEmpty()) {
      return "";
    }

    String gamesListAsString = "";
    for (GameSession gameSession : gameSessions.values()) {
      if (!gameSession.isAvailable()) {
        continue;
      }
      gamesListAsString = gamesListAsString.concat(gameSession.ID + Constants.MSG_DELIMITER);
    }
    return gamesListAsString;
  }

  /**
   * Get a game session with the given creator
   *
   * @param creator the creator of the game
   * @return the game session
   */
  public static GameSession getGameSession(String creator) {
    return gameSessions.get(creator);
  }

  /**
   * Remove a game session with the given creator as ID
   *
   * @param creator the creator of the game session
   */
  public static void removeGameSession(String creator) {
    gameSessions.remove(creator);
  }

  /**
   * Send a message to a user
   *
   * @param sessionID the sessionID of the
   * @param message   the message to send to the user
   * @throws IOException I/O related errors
   */
  public static void broadcastToSessionMembers(String sessionID, Message message)
      throws IOException {
    GameSession session = gameSessions.get(sessionID);
    if (session == null) {
      Logger.console("SERVER ERR", "Session not found");
      return;
    }

    String host = session.ID;
    if (connectedUsers.containsKey(host)) {
      ClientThread hostThread = connectedUsers.get(host);
      if (hostThread != null) {
        hostThread.sendMessage(message);
      }
    }

    String participant = session.getParticipant();
    if (connectedUsers.containsKey(participant)) {
      ClientThread participantThread = connectedUsers.get(participant);
      if (participantThread != null) {
        participantThread.sendMessage(message);
      }
    }
  }

  public static void main(String[] args) {
    new Server();
  }
}
