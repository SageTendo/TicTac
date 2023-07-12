package gui.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import engine.board.Square.SquareState;
import utils.Constants;

public abstract class ABoardPanel extends APanel {

  protected static ImageIcon xIcon;
  protected static ImageIcon oIcon;
  protected static JButton[][] Buttons;
  JPanel boardPanel;
  public JPanel infoPanel;

  /**
   * Constructor
   */
  public ABoardPanel() {
    super("Game");
    boardPanel = new JPanel(new GridLayout(3, 3));
    setElementSize(boardPanel, 600, 600);
    Buttons = new JButton[3][3];

    font = new Font(fontName, Font.BOLD, 14);
    infoPanel = new JPanel(new GridLayout(0, 3));
    infoPanel.setBackground(Color.BLACK);
    infoPanel.add(back);

    setElementSize(infoPanel, 600, 50);
    setAlignments(infoPanel);
    ui();
  }

  public void ui() {
    xIcon = new ImageIcon(new ImageIcon("Assets/X.png").getImage()
        .getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    oIcon = new ImageIcon(new ImageIcon("Assets/O.png").getImage()
        .getScaledInstance(80, 80, Image.SCALE_DEFAULT));

    int buttonID = 0;
    for (int i = 0; i < Buttons.length; i++) {
      for (int j = 0; j < Buttons[0].length; j++) {
        Buttons[i][j] = new JButton("");
        Buttons[i][j].setActionCommand(String.valueOf(buttonID));
        Buttons[i][j].addActionListener(this);
        Buttons[i][j].setBackground(new Color(0x1A1A1A));
        Buttons[i][j].setFocusPainted(false);
        Buttons[i][j].setBorder(
            new LineBorder(Constants.GRAY, 1)
        );
        boardPanel.add(Buttons[i][j]);
        buttonID += 1;
      }
    }
    removeAll();
    add(Box.createVerticalStrut(50));
    add(boardPanel);
    add(infoPanel);
  }

  /**
   * Make a move on the board
   *
   * @param row The row to be played
   * @param col The column to be played
   */
  protected abstract void makeMove(int row, int col);

  /**
   * Update the graphics of the board
   *
   * @param row                 The row of the square to be updated
   * @param col                 The column of the square to be updated
   * @param returnedSquareState The state of the square
   */
  public static void updateGraphics(int row, int col, SquareState returnedSquareState) {
    switch (returnedSquareState) {
      case X -> {
        Buttons[row][col].setIcon(xIcon);
        Buttons[row][col].setEnabled(false);
      }
      case O -> {
        Buttons[row][col].setIcon(oIcon);
        Buttons[row][col].setEnabled(false);
      }
    }
  }

  protected void buttonHandler(boolean b) {
    for (JButton[] buttonRow : Buttons) {
      for (JButton button : buttonRow) {
        button.setEnabled(b);
      }
    }
  }

  @Override
  public abstract void actionPerformed(ActionEvent e);
}
