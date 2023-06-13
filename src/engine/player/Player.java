package engine.player;

import engine.board.Board;
import engine.board.SquareState;

/**
 * Representation of a player in the game
 */
public class Player extends APlayer {

  /**
   * Constructor
   *
   * @param name        the name of the player
   * @param squareState the state of the square the player can play
   * @param isCPU       true if the player is a CPU
   */
  public Player(String name, SquareState squareState, boolean isCPU) {
    super(name, squareState, isCPU);
  }

  /**
   * Plays a move on the board
   *
   * @param board the board to be played
   * @return Move : the move to be played
   */
  @Override
  public Move playMove(Board board) {
    return null;
  }
}
