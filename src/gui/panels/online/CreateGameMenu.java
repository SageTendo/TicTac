package gui.panels.online;

import gui.panels.APanel;
import gui.GUI;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import networking.client.Client;

public class CreateGameMenu extends APanel {

  private final Client client;

  public CreateGameMenu() {
    super("Waiting for a player to join...");
    this.client = GUI.getClient();
    ui();
  }

  @Override
  public void ui() {
    font = new Font(fontName, Font.BOLD, 20);
    title.setFont(font);
    add(title);
    add(Box.createVerticalStrut(50));
    add(back);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("back")) {
      client.endGame();
    }
  }
}
