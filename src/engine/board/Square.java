package engine.board;

/**
 * Representation of a square on the board
 */
public class Square {

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
   * @return engine.board.SquareState : The state of the square
   */
  public SquareState getState() {
    return this.state;
  }
}
