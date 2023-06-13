package gui.panels.local;

import gui.panels.APanel;
import gui.GUI;
import gui.panels.MainMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import utils.Constants;

public class SinglePlayerMenu extends APanel implements KeyListener {

  JTextField displayNameInput;

  public SinglePlayerMenu() {
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
    displayNameInput.setSelectionColor(Constants.GRAY);
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
    switch (e.getActionCommand()) {
      case "submit" -> GUI.replaceContentPane(new LocalBoard(displayNameInput.getText(), "cpu"));
      case "back" -> GUI.replaceContentPane(new MainMenu());
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
      submit.setBackground(Constants.GREEN);
    } else {
      submit.setEnabled(false);
      submit.setBackground(Constants.RED);
    }
  }
}
