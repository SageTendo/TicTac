package networking.server;

import engine.Game;
import engine.board.Square.SquareState;
import utils.Logger;

/**
 * Represents a session of a game of tic-tac-toe
 */
public class GameSession {

  public final String ID;
  private String participant;
  private boolean available;
  private Game game;

  /**
   * Constructor
   *
   * @param creator The creator of the game
   */
  public GameSession(String creator) {
    this.ID = creator;
    this.participant = "";
    this.available = true;
  }

  /**
   * @param participant the participant of the game
   */
  public void setParticipant(String participant) {
    this.participant = participant;
  }

  /**
   * @return the participant of the game
   */
  public String getParticipant() {
    return participant;
  }

  public void startGame() {
    this.game = new Game(ID, participant);
    this.available = false;
  }

  /**
   * Makes a move on the board of the game
   *
   * @param x The x coordinate of the move
   * @param y The y coordinate of the move
   * @return the state of the square
   */
  public SquareState makeMove(int x, int y) {
    try {
      return this.game.makeMove(x, y);
    } catch (IllegalAccessException e) {
      Logger.console("GAME ERR", e.getMessage());
    }
    return null;
  }

  /**
   * @return the name of the current player
   */
  public String getCurrentPlayerName() {
    return this.game.getCurrentPlayer().getName();
  }

  /**
   * Checks if the game is over
   */
  public void checkForWin() {
    this.game.getBoard().checkForWin();
  }

  /**
   * @return the winner of the game
   */
  public String getWinner() {
    return this.game.winner();
  }

  /**
   * Swaps the current player of the game
   */
  public void swapPlayer() {
    this.game.swapPlayer();
  }

  /**
   * @return true if the game is available
   */
  public boolean isAvailable() {
    return available;
  }

  @Override
  public String toString() {
    return "GameSession{" +
        "ID='" + ID + '\'' +
        ", participant='" + participant + '\'' +
        '}';
  }
}
