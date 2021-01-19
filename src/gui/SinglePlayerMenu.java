package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SinglePlayerMenu extends APanel implements KeyListener {
    JTextField displayNameInput;

    SinglePlayerMenu() {
        super("Display Name");
        displayNameInput = new JTextField("enter name here...");
        ui();
    }

    @Override
    public void ui() {
        font = new Font(fontName, Font.BOLD, 36);
        title.setFont(font);
        title.setForeground(new Color(134, 134, 134));

        font = new Font(fontName, Font.BOLD, 20);
        displayNameInput.setFont(font);
        displayNameInput.setSize(500, 60);
        displayNameInput.setMaximumSize(displayNameInput.getSize());
        displayNameInput.setSelectionColor(GREY);
        displayNameInput.addKeyListener(this);
        displayNameInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                displayNameInput.setText("");
            }
        });

        submit.setText("Go");
        disableButton(submit);

        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(25));
        add(displayNameInput);
        add(Box.createVerticalStrut(10));
        add(submit);
        add(Box.createVerticalStrut(10));
        add(back);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("submit".equals(e.getActionCommand())) {
            System.out.printf("Welcome %s", displayNameInput.getText().toUpperCase());
            System.out.println();
            //TODO
            setContentPane(new BoardPanel(displayNameInput.getText(), "cpu"));
        }

        if ("back".equals(e.getActionCommand())) {
            setContentPane(new MainMenu());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyReleased(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (verifyName(displayNameInput)) {
            submit.setEnabled(true);
            submit.setBackground(GREEN);
        } else {
            submit.setEnabled(false);
            submit.setBackground(RED);
        }
    }
}
