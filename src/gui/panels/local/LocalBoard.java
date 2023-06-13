package gui.panels.local;

import engine.Game;
import engine.player.Move;
import engine.board.SquareState;
import gui.GUI;
import gui.panels.ABoardPanel;
import gui.panels.MainMenu;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class LocalBoard extends ABoardPanel {

  Game game;

  /**
   * Constructor
   *
   * @param playerX The name of player X
   * @param playerO The name of player O
   */
  public LocalBoard(String playerX, String playerO) {
    super();
    game = new Game(playerX, playerO);
  }

  @Override
  protected void makeMove(int row, int col) {
    try {
      SquareState returnedSquareState = game.makeMove(row, col);
      if (!returnedSquareState.equals(SquareState.EMPTY)) {
        updateGraphics(row, col, returnedSquareState);
      }
    } catch (IllegalAccessException ignored) {
    }
  }

  /**
   * @return true if the game is over
   */
  private boolean gameEnded() {
    return game.winner() != null;
  }

  /**
   * Play the AI move
   */
  private void playAI() {
    try {
      Move bestMove = game.getCurrentPlayer().playMove(game.getBoard());
      SquareState returnedSquareState = game.makeMove(bestMove.getRow(), bestMove.getCol());
      if (returnedSquareState != SquareState.EMPTY) {
        updateGraphics(bestMove.getRow(), bestMove.getCol(), returnedSquareState);
      }
    } catch (IllegalAccessException e) {
      JOptionPane.showInternalMessageDialog(this, e);
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "0" -> makeMove(0, 0);
      case "1" -> makeMove(0, 1);
      case "2" -> makeMove(0, 2);
      case "3" -> makeMove(1, 0);
      case "4" -> makeMove(1, 1);
      case "5" -> makeMove(1, 2);
      case "6" -> makeMove(2, 0);
      case "7" -> makeMove(2, 1);
      case "8" -> makeMove(2, 2);
      case "back" -> {
        GUI.replaceContentPane(new MainMenu());
        return;
      }
    }

    // Check if the game is over
    if (gameEnded()) {
      buttonHandler(false);
      GUI.showMessage("GAME END", game.winner());
      return;
    }

    // Swap the player and play the AI
    buttonHandler(game.swapPlayer().isNotCPU());
    playAI();
    buttonHandler(game.swapPlayer().isNotCPU());
  }
}
