package gui.panels;

import gui.GUI;
import gui.panels.local.LocalMenu;
import gui.panels.online.OnlineLoginMenu;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.JButton;

public class MultiplayerMenu extends APanel {

  JButton localButton;
  JButton onlineButton;

  public MultiplayerMenu() {
    super("MODE");
    localButton = new JButton("LOCAL");
    localButton.addActionListener(this);
    localButton.setActionCommand("local");
    styleButton(localButton);
    setAlignments(localButton);

    onlineButton = new JButton("ONLINE");
    onlineButton.addActionListener(this);
    onlineButton.setActionCommand("online");
    styleButton(onlineButton);
    setAlignments(onlineButton);
    setElementSize(onlineButton, 200, 50);
    ui();
  }

  @Override
  public void ui() {
    font = new Font(fontName, Font.BOLD, 32);
    localButton.setFont(font);
    font = new Font(fontName, Font.BOLD, 26);
    onlineButton.setFont(font);

    add(title);
    add(Box.createVerticalStrut(50));
    add(localButton);
    add(Box.createVerticalStrut(20));
    add(onlineButton);
    add(Box.createVerticalStrut(100));
    add(back);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "local" -> GUI.replaceContentPane(new LocalMenu());
      case "online" -> GUI.replaceContentPane(new OnlineLoginMenu());
      case "back" -> GUI.replaceContentPane(new MainMenu());
    }
  }
}
