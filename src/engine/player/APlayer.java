package engine.player;

import engine.board.Board;
import engine.board.SquareState;

/**
 * Representation of a player in the game
 *
 * @author sageTendo
 */
public abstract class APlayer {

  private final String name;
  private final SquareState squareState;
  private final boolean isCPU;

  /**
   * Constructor
   *
   * @param name        the name of the player
   * @param squareState the state of the square the player can play
   * @param isCPU       true if the player is a CPU
   */
  public APlayer(String name, SquareState squareState, boolean isCPU) {
    this.name = name;
    this.squareState = squareState;
    this.isCPU = isCPU;
  }

  /**
   * @return String : the name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * @return SquareState : the state of the square the player can play
   */
  public SquareState getSquareState() {
    return squareState;
  }

  /**
   * Plays a move on the board
   *
   * @param board the board to be played
   * @return Move : the move to be played
   * @throws IllegalAccessException illegal access
   */
  public abstract Move playMove(Board board) throws IllegalAccessException;

  /**
   * @return boolean : true if the player is a CPU
   */
  public boolean isCPU() {
    return isCPU;
  }
}
