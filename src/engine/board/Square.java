package engine.board;

/**
 * Representation of a square on the board
 */
public class Square {

  /**
   * The states that a square can be in, EMPTY : The state a square is in when no player has
   * occupied it. X : The state that PlayerX can set an EMPTY square O : The state that PlayerO can
   * set an EMPTY square
   */
  public enum SquareState {
    EMPTY, O, X
  }

  private SquareState state = SquareState.EMPTY;

  /**
   * Sets the state of the square
   *
   * @param state : The provided engine.board.Square State
   */
  public void setState(SquareState state) {
    this.state = state;
  }

  /**
   * @return engine.board.Square.SquareState : The state of the square
   */
  public SquareState getState() {
    return this.state;
  }
}
