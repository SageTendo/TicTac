package gui.panels;

import gui.GUI;
import gui.panels.local.SinglePlayerMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Representation of a main menu panel
 */
public class MainMenu extends APanel {

  JButton singlePlayer;
  JButton multiPlayer;
  Font font;
  String fontName = "ARCADE ROUNDED";

  /**
   * Constructor
   */
  public MainMenu() {
    super("TIC-TAC");
    singlePlayer = new JButton("Single Player");
    multiPlayer = new JButton("Multiplayer");
    ui();
  }

  /**
   * Initialize the components of the panel
   */
  @Override
  public void ui() {
    font = new Font(fontName, Font.BOLD, 96);
    title.setFont(font);
    title.setForeground(new Color(134, 134, 134));

    font = new Font(fontName, Font.BOLD, 40);
    singlePlayer.setFont(font);
    singlePlayer.setSize(580, 100);
    singlePlayer.setMaximumSize(singlePlayer.getSize());
    singlePlayer.setLayout(null);
    styleButton(singlePlayer);

    font = new Font(fontName, Font.BOLD, 46);
    multiPlayer.setFont(font);
    multiPlayer.setSize(580, 100);
    multiPlayer.setMaximumSize(singlePlayer.getSize());
    styleButton(multiPlayer);

    singlePlayer.addActionListener(this);
    singlePlayer.setActionCommand("single");
    multiPlayer.addActionListener(this);
    multiPlayer.setActionCommand("multi");

    title.setAlignmentX(CENTER_ALIGNMENT);
    title.setAlignmentY(CENTER_ALIGNMENT);
    singlePlayer.setAlignmentX(CENTER_ALIGNMENT);
    singlePlayer.setAlignmentY(CENTER_ALIGNMENT);
    multiPlayer.setAlignmentX(CENTER_ALIGNMENT);
    multiPlayer.setAlignmentY(CENTER_ALIGNMENT);

    add(title);
    add(Box.createVerticalStrut(50));
    add(singlePlayer);
    add(Box.createVerticalStrut(50));
    add(multiPlayer);
    setVisible(true);
  }

  /**
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "single" -> GUI.replaceContentPane(new SinglePlayerMenu());
      case "multi" -> GUI.replaceContentPane(new MultiplayerMenu());
    }
  }
}
