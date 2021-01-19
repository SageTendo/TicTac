package gui;

import resources.FontCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    public GUI() {
        super("TIC TAC TOE");
        requestFocus();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        setLayout(new GridLayout(3, 3));
        new FontCreator("Assets\\ARCADE_R.ttf", Font.TRUETYPE_FONT).createFont();
        setContentPane(new MainMenu());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
