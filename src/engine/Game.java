package engine;

import engine.board.Board;
import engine.board.Square.SquareState;
import engine.player.APlayer;
import engine.player.CPU;
import engine.player.Player;

/**
 * Represents a game of tic-tac-toe
 */
public class Game {

  private final Board board;
  private APlayer playerX, playerO, currentPlayer;

  /**
   * Constructor
   *
   * @param pXName the name of the player X
   * @param pOName the name of the player O
   */
  public Game(String pXName, String pOName) {
    playerX = new Player(pXName, SquareState.X, false);
    playerO = new Player(pOName, SquareState.O, false);
    playerX = evaluatePlayerType(playerX);
    playerO = evaluatePlayerType(playerO);

    currentPlayer = playerX;
    board = new Board();
  }

  /**
   * Evaluates the player type based on the name of the player
   *
   * @param player the player to evaluate
   * @return the player
   */
  private APlayer evaluatePlayerType(APlayer player) {
    if (player.getName().equalsIgnoreCase("cpu")) {
      return new CPU(player.getSquareState());
    }
    return player;
  }

  /**
   * Makes a move on the board
   *
   * @param row the row of the square
   * @param col the column of the square
   * @return the state of the square
   * @throws IllegalAccessException if the square is already played or the game is over
   */
  public SquareState makeMove(int row, int col) throws IllegalAccessException {
    if (winner() != null) {
      return SquareState.EMPTY;
    }
    board.setSquare(row, col, currentPlayer.getSquareState());
    return currentPlayer.getSquareState();
  }

  /**
   * Swaps the current player of the game
   *
   * @return the new current player of the game
   */
  public APlayer swapPlayer() {
    currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    return currentPlayer;
  }

  /**
   * @return the current player
   */
  public APlayer getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Checks if the game is over
   *
   * @return the winner
   */
  public String winner() {
    board.checkForWin();
    return switch (board.getBoardState()) {
      case X_WINS -> playerX.getName();
      case O_WINS -> playerO.getName();
      case DRAW -> "Draw";
      default -> null;
    };
  }

  /**
   * @return the board
   */
  public Board getBoard() {
    return board;
  }

  public void display() {
    board.display();
    System.out.println(board.getBoardState());
    System.out.println();
  }
}
