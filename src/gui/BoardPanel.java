package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import engine.Game;
import engine.Move;
import engine.SquareState;

public class BoardPanel extends APanel {
    ImageIcon xIcon;
    ImageIcon oIcon;
    Game g;
    JButton[][] Buttons;
    JPanel boardPanel;
    JPanel infoPanel;

    BoardPanel(String playerX, String playerO) {
        super("Game");
        g = new Game(playerX, playerO);
        boardPanel = new JPanel(new GridLayout(3, 3));
        setElementSize(boardPanel, 600, 600);
        Buttons = new JButton[3][3];

        font = new Font(fontName, Font.BOLD, 14);
        infoPanel = new JPanel(new GridLayout(0, 3));
        setElementSize(infoPanel, 600, 50);
        setAlignments(infoPanel, CENTER_ALIGNMENT);
        JLabel playerXInfo = new JLabel(g.getPlayerXName() + ": " + g.getScore().getPlayerX());
        playerXInfo.setFont(font);
        playerXInfo.setForeground(GREY);
        setAlignments(playerXInfo, CENTER_ALIGNMENT);
        JLabel playerOInfo = new JLabel(g.getPlayerOName() + ": " + g.getScore().getPlayerO());
        playerOInfo.setFont(font);
        playerOInfo.setForeground(GREY);
        setAlignments(playerOInfo, CENTER_ALIGNMENT);
        JLabel drawInfo = new JLabel(" Draw: " + g.getScore().getDraw());
        drawInfo.setFont(font);
        drawInfo.setForeground(GREY);
        infoPanel.add(playerXInfo);
        infoPanel.add(playerOInfo);
        infoPanel.add(drawInfo);
        infoPanel.setBackground(Color.BLACK);

        xIcon = new ImageIcon(
                new ImageIcon("Assets/X.png")
                        .getImage()
                        .getScaledInstance(60, 60, Image.SCALE_DEFAULT)
        );
        oIcon = new ImageIcon(
                new ImageIcon("Assets/O.png")
                        .getImage()
                        .getScaledInstance(80, 80, Image.SCALE_DEFAULT)
        );
        ui();
    }

    public void ui() {
        int buttonID = 0;
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons[0].length; j++) {
                Buttons[i][j] = new JButton("");
                Buttons[i][j].setActionCommand(String.valueOf(buttonID));
                Buttons[i][j].addActionListener(this);
                Buttons[i][j].setBackground(new Color(0x1A1A1A));
                Buttons[i][j].setFocusPainted(false);
                Buttons[i][j].setBorder(
                        new LineBorder(GREY, 1)
                );
                boardPanel.add(Buttons[i][j]);
                buttonID += 1;
            }
        }
        removeAll();
        add(Box.createVerticalStrut(50));
        add(infoPanel);
        add(boardPanel);
    }

    void actionCommandHandler(String actionCommand) throws IllegalAccessException {
        switch (actionCommand) {
            case "0":
                makeMove(0, 0);
                break;
            case "1":
                makeMove(0, 1);
                break;
            case "2":
                makeMove(0, 2);
                break;
            case "3":
                makeMove(1, 0);
                break;
            case "4":
                makeMove(1, 1);
                break;
            case "5":
                makeMove(1, 2);
                break;
            case "6":
                makeMove(2, 0);
                break;
            case "7":
                makeMove(2, 1);
                break;
            case "8":
                makeMove(2, 2);
                break;
        }
    }

    private void checkWinner() {
        if (g.winner() != null) {
            buttonHandler(false);
            System.out.println(g.winner().getName());
        }
    }

    void swapPlayer() {
        g.swapPlayer();
        if (g.getCurrentPlayer().isCPU()) {
            buttonHandler(false);
        }
    }

    void makeMove(int row, int col) throws IllegalAccessException {
        SquareState returnedSquareState = g.makeMove(row, col);
        if (!returnedSquareState.equals(SquareState.EMPTY)) {
            updateGraphics(row, col, returnedSquareState);
        }
    }

    public void playAI() {
        if (g.getCurrentPlayer().isCPU()) {
            try {
                Move bestMove = g.getCurrentPlayer().playMove(g.getBoard());
                SquareState returnedSquareState = g.makeMove(bestMove.getRow(), bestMove.getCol());
                if (returnedSquareState != SquareState.EMPTY) {
                    updateGraphics(bestMove.getRow(), bestMove.getCol(), returnedSquareState);
                    g.swapPlayer();
                } //else {playAI();}
            } catch (IllegalAccessException e) {
                JOptionPane.showInternalMessageDialog(this, e);
                e.printStackTrace();
            }
            //re-enable player input
            if (!g.getCurrentPlayer().isCPU()) {
                buttonHandler(true);
            }
        }
    }

    void updateGraphics(int row, int col, SquareState returnedSquareState) {
        switch (returnedSquareState) {
            case X:
                Buttons[row][col].setIcon(xIcon);
                Buttons[row][col].setEnabled(false);
                break;
            case O:
                Buttons[row][col].setIcon(oIcon);
                Buttons[row][col].setEnabled(false);
                break;
        }
    }

    void buttonHandler(boolean b) {
        for(JButton[] buttonRow : Buttons){
            for(JButton button : buttonRow) {
                button.setEnabled(b);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            actionCommandHandler(e.getActionCommand());
            swapPlayer();
            checkWinner();
            playAI();
            checkWinner();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            JOptionPane error = new JOptionPane();
            setElementSize(error, 400, 100);
            error.setMessageType(JOptionPane.ERROR_MESSAGE);
            error.setMessage(illegalAccessException.getMessage());
            font = new Font(fontName, Font.BOLD, 14);
            error.setFont(font);
            error.grabFocus();
            error.setVisible(true);
            error.createDialog(this, "Input Error").setVisible(true);
        }
    }
}
