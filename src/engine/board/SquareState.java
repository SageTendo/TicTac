package engine.board;

/**
 * The states that a square can be in, EMPTY : The state a square is in when no player has occupied
 * it. X : The state that PlayerX can set an EMPTY square O : The state that PlayerO can set an
 * EMPTY square
 */
public enum SquareState {
  EMPTY, O, X
}
