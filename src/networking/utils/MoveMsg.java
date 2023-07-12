package networking.utils;

import engine.board.Square.SquareState;

/**
 * Represents a move message
 */
public class MoveMsg extends Message {

  private final int x;
  private final int y;
  private SquareState state;

  /**
   * Constructs a new move message
   *
   * @param x the x coordinate of the move
   * @param y the y coordinate of the move
   */
  public MoveMsg(int x, int y) {
    super();
    this.x = x;
    this.y = y;
    setType(MessageType.MOVE);
  }

  @Override
  public MoveMsg setType(MessageType type) {
    return (MoveMsg) super.setType(type);
  }

  /**
   * Sets the state of the square
   *
   * @param state the state of the square
   * @return this
   */
  public MoveMsg setState(SquareState state) {
    this.state = state;
    return this;
  }

  /**
   * @return the state of the square
   */
  public SquareState getState() {
    return state;
  }

  /**
   * @return the x coordinate of the move
   */
  public int getX() {
    return x;
  }

  /**
   * @return the y coordinate of the move
   */
  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "MoveMsg{" +
        "x=" + x +
        ", y=" + y +
        ", state=" + state +
        '}';
  }
}
