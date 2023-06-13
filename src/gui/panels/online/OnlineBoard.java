package gui.panels.online;

import gui.GUI;
import gui.panels.ABoardPanel;
import java.awt.event.ActionEvent;

/**
 * Representation of a board panel in the GUI
 */
public class OnlineBoard extends ABoardPanel {

  /**
   * Constructor
   */
  public OnlineBoard() {
    super();
  }

  @Override
  protected void makeMove(int row, int col) {
    GUI.getClient().makeMove(row, col);
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
      case "back" -> GUI.getClient().endGame();
    }
  }
}

