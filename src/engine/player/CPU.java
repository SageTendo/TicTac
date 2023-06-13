package engine.player;

import engine.board.Board;
import engine.board.SquareState;

/**
 * Represents an AI player of tic-tac-toe
 */
public class CPU extends APlayer {

  SquareState player;
  SquareState AI;

  /**
   * Constructor
   *
   * @param squareState the square state
   */
  public CPU(SquareState squareState) {
    super("cpu", squareState, true);
    AI = getSquareState();
    player = (AI == SquareState.X) ? SquareState.O : SquareState.X;
  }

  /**
   * Plays a move on the board
   *
   * @param board the board to play
   * @return the move that was played
   * @throws IllegalAccessException if the square is already played
   */
  public Move playMove(Board board) throws IllegalAccessException {
    return findBestMove(board);
  }

  /**
   * Evaluates the board
   *
   * @param board the board to evaluate
   * @return the score of the board
   */
  public int evaluateBoard(Board board) {
    // Checking for Rows for X or O victory.
    for (int row = 0; row < 3; row++) {
      if (board.getSquare(row, 0) == board.getSquare(row, 1) &&
          board.getSquare(row, 1) == board.getSquare(row, 2)) {
        if (board.getSquare(row, 0) == AI) {
          return 10;
        } else if (board.getSquare(row, 0) == player) {
          return -10;
        }
      }
    }

    // Checking for Columns for X or O victory.
    for (int col = 0; col < 3; col++) {
      if (board.getSquare(0, col) == board.getSquare(1, col) &&
          board.getSquare(1, col) == board.getSquare(2, col)) {
        if (board.getSquare(0, col) == AI) {
          return 10;
        } else if (board.getSquare(0, col) == player) {
          return -10;
        }
      }
    }

    // Checking for Diagonals for X or O victory.
    if (
        board.getSquare(0, 0) == board.getSquare(1, 1) &&
            board.getSquare(1, 1) == board.getSquare(2, 2)
    ) {
      if (board.getSquare(0, 0) == AI) {
        return 10;
      } else if (board.getSquare(0, 0) == player) {
        return -10;
      }
    }

    if (
        board.getSquare(0, 2) == board.getSquare(1, 1) &&
            board.getSquare(1, 1) == board.getSquare(2, 0)
    ) {
      if (board.getSquare(0, 2) == AI) {
        return 10;
      } else if (board.getSquare(0, 2) == player) {
        return -10;
      }
    }

    // Else if none of them have won then return 0
    return 0;
  }


  /**
   * Minimax algorithm to find the best move for the player
   *
   * @param board the board to evaluate
   * @param depth the depth of the search algorithm
   * @param isMax true if this is the maximizer's move
   * @return the best possible move
   * @throws IllegalAccessException if the square is already played
   */
  public int minimax(Board board, int depth, boolean isMax) throws IllegalAccessException {
    //evaluate the board
    int score = evaluateBoard(board);

    //If maximizer has won then return score
    if (score == 10) {
      return score - depth;
    }

    //if minimizer has won then return score
    if (score == -10) {
      return score + depth;
    }

    //no more moves and no one has won
    if (board.isBoardFull()) {
      return 0;
    }

    //if this is maximizer's move
    int best;
    if (isMax) {
      best = Integer.MIN_VALUE;

      //traverse all cells of the board
      for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board.length; col++) {
          //check if square is empty
          if (board.isSquareFree(row, col)) {
            //play move
            board.setSquare(row, col, AI);

            //call minimax recursively and choose the max value
            best = Math.max(best, minimax(board, depth + 1, false));

            //undo the move
            board.resetSquare(row, col, this);
          }
        }
      }
    } else {
      best = Integer.MAX_VALUE;

      //traverse all cells of the board
      for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board.length; col++) {
          //check if square is empty
          if (board.isSquareFree(row, col)) {
            //play move
            board.setSquare(row, col, player);

            //call minimax recursively and choose the max value
            best = Math.min(best, minimax(board, depth + 1, true));

            //undo the move
            board.resetSquare(row, col, this);
          }
        }
      }
    }
    return best;
  }

  /**
   * Finds the best move for the AI
   *
   * @param board the board to evaluate
   * @return the best possible move
   * @throws IllegalAccessException if the square is already played
   */
  Move findBestMove(Board board) throws IllegalAccessException {
    int bestVal = Integer.MIN_VALUE;
    Move bestMove = new Move();
    bestMove.row = -1;
    bestMove.col = -1;

    // Traverse all cells, evaluate minimax function for
    // all empty cells. And return the cell with optimal
    // value.
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        // Check if cell is empty
        if (board.isSquareFree(row, col)) {
          // Make the move
          board.setSquare(row, col, AI);

          // compute evaluation function for this
          // move.
          int moveVal = minimax(board, 0, false);

          // Undo the move
          board.resetSquare(row, col, this);

          // If the value of the current move is
          // more than the best value, then update
          // best/
          if (moveVal > bestVal) {
            bestMove.row = row;
            bestMove.col = col;
            bestVal = moveVal;
          }
        }
      }
    }

    System.out.printf("The value of the best Move is : %d\n\n",
        bestVal);
    System.out.print("The Optimal Move is :\n");
    System.out.printf("ROW: %d COL: %d\n\n", bestMove.row,
        bestMove.col);

    return bestMove;
  }
}
