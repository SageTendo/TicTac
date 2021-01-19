package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LocalMenu extends APanel implements KeyListener {
    String defaultPlayerXText = "Player X name...";
    String defaultPlayerOText = "Player O name...";
    JTextField playerX;
    JTextField playerO;

    public LocalMenu() {
        super("Display Names:");
        font = new Font(fontName, Font.BOLD, 20);
        playerX = new JTextField("Player X name...");
        playerX.setFont(font);
        playerX.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!verifyName(playerX)) {
                    playerX.setText("");
                }
            }
        });

        playerX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!verifyName(playerX)) {
                    playerX.setText(defaultPlayerXText);
                }
            }
        });
        playerX.addKeyListener(this);
        playerX.setFocusTraversalKeysEnabled(false);


        playerO = new JTextField("Player O name...");
        playerO.setFont(font);
        playerO.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!verifyName(playerO)) {
                    playerO.setText("");
                }
            }
        });
        playerO.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!verifyName(playerO)) {
                    playerO.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!verifyName(playerO)) {
                    playerO.setText(defaultPlayerOText);
                }
            }
        });
        playerO.addKeyListener(this);
        playerO.setFocusTraversalKeysEnabled(false);

        submit.setText("Play");
        submit.setActionCommand("play");
        disableButton(submit);
        font = new Font(fontName, Font.BOLD, 20);
        ui();

    }

    @Override
    public void ui() {
        JPanel playerXPanel = new JPanel(null);
        setElementSize(playerXPanel, 600, 50);
        setElementSize(playerX, playerXPanel.getWidth(), playerXPanel.getHeight());
        playerXPanel.add(playerX);

        JPanel playerOPanel = new JPanel(null);
        setElementSize(playerOPanel, 600, 50);
        setElementSize(playerO, playerOPanel.getWidth(), playerOPanel.getHeight());
        playerOPanel.add(playerO);

        JPanel buttonLayout = new JPanel(null);
        setElementSize(buttonLayout, 600, 50);
        setElementSize(back, 250, buttonLayout.getHeight());
        setElementSize(submit, 250, buttonLayout.getHeight());
        back.setLocation(buttonLayout.getX(), buttonLayout.getY());
        submit.setLocation(350, buttonLayout.getY());
        buttonLayout.setBackground(Color.BLACK);
        buttonLayout.add(back);
        buttonLayout.add(submit);

        add(Box.createVerticalStrut(50));
        font = new Font(fontName, Font.BOLD, 32);
        title.setFont(font);
        title.setForeground(WHITE);
        add(title);
        add(playerXPanel);
        add(Box.createVerticalStrut(50));
        add(playerOPanel);
        add(Box.createVerticalStrut(50));
        add(buttonLayout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "play":
                System.out.printf("Welcome %s and %s", playerX.getText(), playerO.getText());
                System.out.println();
                setContentPane(new BoardPanel(playerX.getText(), playerO.getText()));
                break;
            case "back":
                setContentPane(new MultiplayerMenu());
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (verifyName(playerX) && verifyName(playerO) &&
                !playerX.getText().equalsIgnoreCase(playerO.getText())
        ) {
            enableButton(submit);
        } else {
            disableButton(submit);
        }
    }
}
