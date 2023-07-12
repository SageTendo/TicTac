package networking.server;

import engine.board.SquareState;
import java.io.IOException;
import java.net.Socket;
import networking.utils.AbstractThread;
import networking.utils.Message;
import networking.utils.Message.MessageType;
import networking.utils.MoveMsg;
import utils.Logger;

/**
 * Client thread class
 *
 * @author sageTendo
 */
public class ClientThread extends AbstractThread {

  private GameSession session;

  /**
   * Constructor
   *
   * @param clientSocket the socket to the client
   * @throws IOException I/O related errors
   */
  public ClientThread(Socket clientSocket) throws IOException {
    super(clientSocket);
  }

  /**
   * Handles registration of a new user
   */
  void handleRegistration() {
    try {
      Message registrationMsg = getMessage();
      if (registrationMsg.getType()
          != MessageType.REGISTER) { // check if message is for registration
        sendMessage(new Message().setType(MessageType.ERR)
            .setContent("You must register first..."));
        return;
      }

      // check if username is valid
      if (!validUsername(registrationMsg.getFrom())) {
        return;
      }

      // check if username is unique
      if (Server.hasUser(registrationMsg.getFrom())) {
        sendMessage(new Message().setType(MessageType.ERR)
            .setContent("Username already taken..."));
        return;
      }

      // register user
      register(registrationMsg.getFrom());
    } catch (IOException e) {
      disconnect();
      Logger.console("REGISTRATION ERR", e.getMessage());
    } catch (ClassNotFoundException e) {
      Logger.console("REGISTRATION ERR", e.getMessage());
    }
  }

  /**
   * Registers a new user
   *
   * @param username the username to register
   * @throws IOException I/O related errors
   */
  public void register(String username) throws IOException {
    Server.addUser(username, this);
    setUsername(username);
    setIp(getClientSocket().getInetAddress());
    setConnected(true);
    sendMessage(
        new Message().setType(MessageType.REGISTER).setContent("Registration successful..."));
  }

  /**
   * Validates a username
   *
   * @param username the username to validate
   * @return true if username is valid and false otherwise
   * @throws IOException I/O related errors
   */
  private boolean validUsername(String username) throws IOException {
    if (username.length() < 3) {
      sendMessage(new Message().setType(MessageType.ERR).setContent("Username too short..."));
      return false;
    }

    if (!username.matches("[a-zA-Z0-9]+")) {
      sendMessage(new Message().setType(MessageType.ERR)
          .setContent("Username contains invalid characters..."));
      return false;
    }
    return true;
  }

  @Override
  public void run() {
    handleRegistration();
    while (isConnected()) {
      try {
        Message message = getMessage();
        switch (message.getType()) {
          case PING -> sendMessage(new Message().setType(MessageType.PONG));
          case MOVE -> makeMove((MoveMsg) message);
          case GAME_START -> startGame();
          case GAME_END -> endGame(message);
          case CREATE_GAME -> createGame();
          case GET_GAMES -> sendMessage(
              new Message().setType(MessageType.GET_GAMES).setContent(Server.getGameSessions())
          );
          case JOIN_GAME -> joinGame(message.getContent());
          default -> sendMessage(
              new Message().setType(MessageType.ERR).setContent("Unknown message type...")
          );
        }
      } catch (IOException e) {
        try { // Try to end the game if the player was in a game
          endGame(new Message().setType(MessageType.GAME_END).setFrom(getUsername()));
        } catch (IOException ignored) {
        }
        disconnect();
        Logger.console("CLIENT ERR", e.getMessage());
      } catch (ClassNotFoundException e) {
        Logger.console("CLIENT ERR", e.getMessage());
      }
    }
  }

  /**
   * Creates a new game session
   *
   * @throws IOException I/O related errors
   */
  private void createGame() throws IOException {
    if (Server.hasGameSession(getUsername())) {
      return;
    }

    session = new GameSession(getUsername());
    Server.addGameSession(session);
    sendMessage(new Message().setType(MessageType.CREATE_GAME));
  }

  /**
   * Joins a game session and starts the game
   *
   * @param gameID the game ID to join
   * @throws IOException I/O related errors
   */
  private void joinGame(String gameID) throws IOException {
    if (!Server.hasGameSession(gameID)) {
      sendMessage(new Message().setType(MessageType.ERR).setContent("Game not found..."));
      return;
    }

    if (!Server.gameSessionAvailable(gameID)) {
      sendMessage(
          new Message().setType(MessageType.ERR).setContent("Game is no longer available..."));
      return;
    }

    session = Server.getGameSession(gameID);
    session.setParticipant(getUsername());
    startGame();
  }

  /**
   * Starts the game session and sends the game start message to the clients
   */
  private void startGame() throws IOException {
    session.startGame();
    Server.broadcastToSessionMembers(session.ID, new Message()
        .setType(MessageType.GAME_START)
        .setFrom(session.ID)
        .setTo(session.getParticipant()));
  }

  /**
   * Ends the game session and sends the game end message to the clients
   *
   * @throws IOException I/O related errors
   */
  private void endGame(Message msg) throws IOException {
    if (session == null) {
      return;
    }

    Server.broadcastToSessionMembers(session.ID, msg.setContent(session.getWinner()));
    Server.removeGameSession(session.ID);
    session = null;
  }

  /**
   * Processes a move message
   *
   * @param moveMsg the move message to process
   * @throws IOException I/O related errors
   */
  private void makeMove(MoveMsg moveMsg) throws IOException {
    if (session != null) {
      if (!session.getCurrentPlayerName().equals(getUsername())) {
        sendMessage(new Message().setType(MessageType.ERR).setContent("Not your turn..."));
        return;
      }

      // Make the move on the board and broadcast if it's valid
      SquareState state = session.makeMove(moveMsg.getX(), moveMsg.getY());
      if (state == SquareState.EMPTY) {
        sendMessage(new Message().setType(MessageType.ERR).setContent("Invalid move..."));
        return;
      }

      //Broadcast move
      Server.broadcastToSessionMembers(session.ID, moveMsg.setState(state));

      // Check for game end
      session.checkForWin();

      // Swap player if game has not ended
      if (session.getWinner() == null) {
        session.swapPlayer();
      } else {
        if (session.getWinner().equals("Draw")) {
          endGame(new Message().setType(MessageType.GAME_END).setContent("It's a draw!"));
        } else {
          endGame(new Message().setType(MessageType.GAME_END)
              .setContent(session.getWinner()));
        }
      }
    }
  }

  /**
   * Disconnects the client from the server
   */
  @Override
  public void disconnect() {
    try {
      super.disconnect();
      Server.removeUser(getUsername());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
