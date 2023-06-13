package engine.board;

import engine.player.CPU;

/**
 * Representation of a board
 *
 * @author sageTendo
 */
public class Board {

  private final Square[][] grid;
  private BoardState boardState;
  public final int length;

  /**
   * Initializes the board and its boardState (to PLAYING), and setting every cell of to the EMPTY
   * engine.board.Square State.
   */
  public Board() {
    grid = new Square[3][3];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j] = new Square();
      }
    }
    length = grid.length;
    boardState = BoardState.PLAYING;
  }

  /**
   * Sets the boardState of the specified square in the grid to either X or O.
   *
   * @param row   : the provided row index
   * @param col   : the provided column index
   * @param state : the provided engine.board.Square State.
   */
  public void setSquare(int row, int col, SquareState state) throws IllegalAccessException {
    if (state.equals(SquareState.EMPTY)) {
      throw new IllegalAccessException("Can't set square to an EMPTY boardState.");
    }

    if (grid[row][col].getState() == SquareState.EMPTY) {
      grid[row][col].setState(state);
    } else {
      throw new IllegalAccessException("Square has already been played.");
    }
  }

  /**
   * Checks if a win has been achieved by a player either by; N player SquareStates in a row, N
   * player SquareStates in col or N player engine.board.SquareState in diagonal (upwards or downward)
   * (Where N is the size of the grid) and then sets the board boardState to: PLAYING : No win found
   * X_WINS : engine.player.Player with X engine.board.SquareState has won O_WINS : engine.player.Player with O
   * engine.board.SquareState has won
   */
  public void checkForWin() {
    for (int row = 0; row < length; row++) {
      //check rows
      if (grid[row][0].getState() == grid[row][1].getState() &&
          grid[row][1].getState() == grid[row][2].getState()
      ) {
        //check for X
        if (grid[row][0].getState() == SquareState.X) {
          boardState = BoardState.X_WINS;
          return;
        } else if (grid[row][0].getState() == SquareState.O) { //check for O
          boardState = BoardState.O_WINS;
          return;
        }
      }
    }

    //check cols
    for (int col = 0; col < grid[0].length; col++) {
      if (grid[0][col].getState() == grid[1][col].getState() &&
          grid[1][col].getState() == grid[2][col].getState()
      ) {
        //check for X
        if (grid[0][col].getState() == SquareState.X) {
          boardState = BoardState.X_WINS;
          return;
        } else if (grid[0][col].getState() == SquareState.O) { //check for O
          boardState = BoardState.O_WINS;
          return;
        }
      }
    }

    //check diagonals
    if (grid[0][0].getState() == grid[1][1].getState()
        && grid[1][1].getState() == grid[2][2].getState()) {
      if (grid[0][0].getState() == SquareState.X) {
        boardState = BoardState.X_WINS;
        return;
      } else if (grid[0][0].getState() == SquareState.O) {
        boardState = BoardState.O_WINS;
        return;
      }
    }

    if (grid[0][2].getState() == grid[1][1].getState()
        && grid[1][1].getState() == grid[2][0].getState()) {
      if (grid[0][2].getState() == SquareState.X) {
        boardState = BoardState.X_WINS;
        return;
      } else if (grid[0][2].getState() == SquareState.O) {
        boardState = BoardState.O_WINS;
        return;
      }
    }

    // Check if the board is full
    if (isBoardFull()) {
      boardState = BoardState.DRAW;
    }
  }


  /**
   * Checks for a cell with an EMPTY engine.board.SquareState cell.
   *
   * @return boolean : true if the board is full otherwise false.
   */
  public boolean isBoardFull() {
    for (Square[] row : grid) {
      for (Square col : row) {
        if (col.getState().equals(SquareState.EMPTY)) {
          return false;
        }
      }
    }
    return true;
  }

  //ai

  /**
   * Displays the board in a formatted manner to the console.
   */
  public void display() {
    for (Square[] row : grid) {
      for (Square col : row) {
        System.out.printf("%10s %10s", col.getState(), "|");
      }
      if (row[0] != grid[grid.length - 1][0]) {
        System.out.println();
        System.out.println(
            String.format("%63s", " ").replaceAll(" ", "-")
        );
      }
    }
  }

  /**
   * @return the boardState
   */
  public BoardState getBoardState() {
    return boardState;
  }

  /**
   * Checks if a square is empty
   *
   * @param row the provided row index
   * @param col the provided column index
   * @return true if the square is empty
   */
  public boolean isSquareFree(int row, int col) {
    return grid[row][col].getState() == SquareState.EMPTY;
  }

  /**
   * Gets the SquareState of the square at the specified row and column
   *
   * @param row the provided row index
   * @param col the provided column index
   * @return the SquareState
   */
  public SquareState getSquare(int row, int col) {
    return grid[row][col].getState();
  }

  /**
   * Resets the square at the specified row and column
   *
   * @param row the provided row index
   * @param col the provided column index
   * @param o   the provided Square State
   */
  public void resetSquare(int row, int col, Object o) {
    if (o instanceof CPU) {
      grid[row][col].setState(SquareState.EMPTY);
    }
  }
}
