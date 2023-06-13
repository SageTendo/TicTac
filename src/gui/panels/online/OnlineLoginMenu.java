package gui.panels.online;

import gui.panels.APanel;
import gui.GUI;
import gui.panels.MultiplayerMenu;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import utils.Constants;
import utils.Logger;

public class OnlineLoginMenu extends APanel implements KeyListener {

  JTextField username;

  public OnlineLoginMenu() {
    super("LOGIN");
    font = new Font(fontName, Font.BOLD, 20);
    username = new JTextField("Enter username...");
    username.setFont(font);
    username.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (!verifyName(username)) {
          username.setText("");
        }
      }
    });

    username.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        if (!verifyName(username)) {
          username.setText("Enter username...");
        }
      }
    });
    username.addKeyListener(this);
    username.setFocusTraversalKeysEnabled(false);

    submit.setText("Connect");
    submit.setActionCommand("connect");
    disableButton(submit);
    font = new Font(fontName, Font.BOLD, 20);
    ui();

  }

  @Override
  public void ui() {
    JPanel playerXPanel = new JPanel(null);
    setElementSize(playerXPanel, 600, 50);
    setElementSize(username, playerXPanel.getWidth(), playerXPanel.getHeight());
    playerXPanel.add(username);

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
    title.setForeground(Constants.WHITE);
    add(title);
    add(playerXPanel);
    add(Box.createVerticalStrut(50));
    add(buttonLayout);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "connect" -> {
        try {
          if (!GUI.getClientInstance(Constants.DEFAULT_HOST, Constants.DEFAULT_PORT)
              .register(username.getText())) {
            return;
          }
          GUI.replaceContentPane(new OnlineOptionMenu());
        } catch (IOException ex) {
          GUI.showErrorMessage("Failed to connect to server...");
        } catch (ClassNotFoundException ex) {
          Logger.console("ERR (OnlineMenu)", ex.getMessage());
        }
      }
      case "back" -> GUI.replaceContentPane(new MultiplayerMenu());
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
    if (verifyName(username)
    ) {
      enableButton(submit);
    } else {
      disableButton(submit);
    }
  }
}
