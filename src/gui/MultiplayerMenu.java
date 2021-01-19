package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MultiplayerMenu extends APanel {

    JButton localButton;
    JButton onlineButton;
    public MultiplayerMenu() {
        super("MODE");
        localButton = new JButton("LOCAL");
        localButton.addActionListener(this);
        localButton.setActionCommand("local");
        styleButton(localButton);
        setAlignments(localButton, CENTER_ALIGNMENT);


        onlineButton = new JButton("ONLINE");
        onlineButton.addActionListener(this);
        onlineButton.setActionCommand("online");
        styleButton(onlineButton);
        setAlignments(onlineButton, CENTER_ALIGNMENT);
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
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "local":
                setContentPane(new LocalMenu());
                break;
            case "online":
                setContentPane(new ServerBrowserMenu());
                break;
            case "back":
                setContentPane(new MainMenu());
                break;
        }
    }
}
