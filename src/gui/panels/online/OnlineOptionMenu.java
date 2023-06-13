package gui.panels.online;

import gui.panels.APanel;
import gui.GUI;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.JButton;

public class OnlineOptionMenu extends APanel {

  JButton createButton;
  JButton joinButton;

  public OnlineOptionMenu() {
    super("MODE");

    // Start client handler
    GUI.startClient();

    createButton = new JButton("CREATE GAME");
    createButton.addActionListener(this);
    createButton.setActionCommand("create");
    styleButton(createButton);
    setAlignments(createButton);

    joinButton = new JButton("JOIN GAME");
    joinButton.addActionListener(this);
    joinButton.setActionCommand("join");
    styleButton(joinButton);
    setAlignments(joinButton);
    ui();
  }

  @Override
  public void ui() {
    font = new Font(fontName, Font.BOLD, 32);
    createButton.setFont(font);
    font = new Font(fontName, Font.BOLD, 26);
    joinButton.setFont(font);

    add(title);
    add(Box.createVerticalStrut(50));
    add(createButton);
    add(Box.createVerticalStrut(20));
    add(joinButton);
    add(Box.createVerticalStrut(100));
    add(back);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "create" -> GUI.getClient().createGame();
      case "join" -> GUI.replaceContentPane(new ServerBrowserMenu());
      case "back" -> {
        GUI.closeClient();
        GUI.replaceContentPane(new OnlineLoginMenu());
      }
    }
  }
}
