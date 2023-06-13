package networking.client;

import gui.GUI;
import gui.panels.online.CreateGameMenu;
import gui.panels.online.OnlineBoard;
import gui.panels.online.OnlineOptionMenu;
import gui.panels.online.ServerBrowserMenu;
import java.io.IOException;
import networking.utils.AbstractThread;
import networking.utils.Message;
import networking.utils.Message.MessageType;
import networking.utils.MoveMsg;
import utils.Constants;
import utils.Logger;

/**
 * Client class
 *
 * @author sageTendo
 */
public class Client extends AbstractThread {

  /**
   * Constructor
   *
   * @param host the host to connect to
   * @param port the port to connect to
   * @throws IOException I/O related errors
   */
  public Client(String host, int port) throws IOException {
    super(host, port);
  }

  /**
   * Handles registration to the server
   *
   * @param username the username to register
   * @return true if registration was successful false otherwise
   * @throws IOException            I/O related errors
   * @throws ClassNotFoundException class not found
   */
  public boolean register(String username) throws IOException, ClassNotFoundException {
    Message registrationMsg = new Message().setType(MessageType.REGISTER).setFrom(username);
    sendMessage(registrationMsg);

    Message response = getMessage();
    if (response.getType() == MessageType.ERR) {
      GUI.showErrorMessage(response.getContent());
      Logger.console("ERR", response.getContent());
      return false;
    }

    // register
    setUsername(username);
    setConnected(true);
    pingServer();
    return true;
  }

  /**
   * Pings the server to check if it is alive or not
   */
  public void pingServer() {
    new Thread(() -> {
      Message pingMsg = new Message().setType(MessageType.PING);
      try {
        while (isConnected()) {
          sendMessage(pingMsg);
          //noinspection BusyWait
          Thread.sleep(5000);
        }
      } catch (IOException e) {
        disconnect();
        Logger.console("ERR", e.getMessage());
      } catch (InterruptedException ignored) {
      }
    }).start();
  }

  @Override
  public void run() {
    while (isConnected()) {
      try {
        Message response = getMessage();
        switch (response.getType()) {
          case PONG -> Logger.console("PONG", "Server is alive...");
          case ERR -> {
            GUI.showErrorMessage(response.getContent());
            Logger.console("ERR", response.getContent());
          }
          case CREATE_GAME -> GUI.replaceContentPane(new CreateGameMenu());
          case GET_GAMES -> {
            String[] games = response.getContent().split(Constants.MSG_DELIMITER);
            ServerBrowserMenu.updateGamesList(games);
            Logger.console("GET_GAMES", response.getContent());
          }
          case GAME_START -> GUI.replaceContentPane(new OnlineBoard());
          case MOVE -> {
            MoveMsg moveMsg = (MoveMsg) response;
            OnlineBoard.updateGraphics(moveMsg.getX(), moveMsg.getY(), moveMsg.getState());
          }
          case GAME_END -> handleGameEnd(response);
          default -> Logger.console("ERR", "Unknown message type...");
        }
      } catch (IOException e) {
        disconnect();
        Logger.console("ERR", e.getMessage());
      } catch (ClassNotFoundException e) {
        Logger.console("ERR", e.getMessage());
      }
    }
  }

  /**
   * Creates a new game session
   */
  public void createGame() {
    try {
      sendMessage(new Message().setType(MessageType.CREATE_GAME));
    } catch (IOException e) {
      GUI.showErrorMessage(e.getMessage());
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Get the list of games from the server
   */
  public void fetchGames() {
    try {
      sendMessage(new Message().setType(MessageType.GET_GAMES));
    } catch (IOException e) {
      GUI.showErrorMessage(e.getMessage());
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Joins the game with ID given
   */
  public void joinGame(String ID) {
    try {
      sendMessage(new Message()
          .setType(MessageType.JOIN_GAME)
          .setFrom(getUsername())
          .setContent(ID));
    } catch (IOException e) {
      GUI.showErrorMessage(e.getMessage());
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Makes a move on the board
   *
   * @param x: the x coordinate of the move
   * @param y: the y coordinate of the move
   */
  public void makeMove(int x, int y) {
    try {
      sendMessage(new MoveMsg(x, y));
    } catch (IOException e) {
      GUI.showErrorMessage(e.getMessage());
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Ends the game session
   */
  public void endGame() {
    try {
      sendMessage(new Message().setType(MessageType.GAME_END).setFrom(getUsername()));
    } catch (IOException e) {
      GUI.showErrorMessage(e.getMessage());
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Disconnects the client
   */
  @Override
  public void disconnect() {
    try {
      super.disconnect();
    } catch (IOException e) {
      Logger.console("ERR", e.getMessage());
    }
  }

  /**
   * Handles game end
   *
   * @param response the response from the server
   */
  private void handleGameEnd(Message response) {
    String msg;
    if (response.getContent() != null) {
      // Handle game end
      if (response.getContent().equals(getUsername())) {
        msg = "You win!";
      } else if (response.getContent().equals("Draw")) {
        msg = "It's a draw!";
      } else {
        msg = "You lose!";
      }
      GUI.showMessage("Game End", msg);
    } else {
      // Handle player leaving
      msg = response.getFrom() + " has left the game...";
      if (!response.getFrom().equals(getUsername())) {
        GUI.showMessage("Game End", msg);
      }
    }
    GUI.replaceContentPane(new OnlineOptionMenu());
  }
}
