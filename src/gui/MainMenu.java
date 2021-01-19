package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainMenu extends APanel {
    JButton singlePlayer;
    JButton multiPlayer;
    Font font;
    String fontName = "ARCADE ROUNDED";

    MainMenu() {
        super("TIC-TAC");
        //GridLayout layout = new GridLayout(0, 3);
        singlePlayer = new JButton("Single Player");
        multiPlayer = new JButton("Multiplayer");
        ui();
    }

    @Override
    public void ui() {
        font = new Font(fontName, Font.BOLD, 96);
        title.setFont(font);
        title.setForeground(new Color(134, 134, 134));

        font = new Font(fontName, Font.BOLD, 40);
        singlePlayer.setFont(font);
        singlePlayer.setSize(580,100);
        singlePlayer.setMaximumSize(singlePlayer.getSize());
        singlePlayer.setLayout(null);
        styleButton(singlePlayer);

        font = new Font(fontName, Font.BOLD, 46);
        multiPlayer.setFont(font);
        multiPlayer.setSize(580,100);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "single":
                setContentPane(new SinglePlayerMenu());
                break;
            case "multi":
                setContentPane(new MultiplayerMenu());
                break;
        }
    }
}
