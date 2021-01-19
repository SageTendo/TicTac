package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class APanel extends JPanel implements ActionListener {
    JLabel title;
    Font font;
    String fontName = "ARCADE ROUNDED";
    BoxLayout layout;
    JButton back;
    JButton submit;
    Color RED = new Color(0xFFE32A2A);
    Color GREEN = new Color(0x4DFF34);
    Color GREY = Color.GRAY;

    Color WHITE = Color.WHITE;

    public APanel(String t) {
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        setBackground(Color.BLACK);

        font = new Font(fontName, Font.BOLD, 92);
        title = new JLabel(t);
        title.setFont(font);
        title.setForeground(new Color(0x5E5E5E));
        setAlignments(title, CENTER_ALIGNMENT);

        font = new Font(fontName, Font.BOLD, 24);
        back = new JButton("back");
        back.setFont(font);
        setElementSize(back, 300, 50);
        back.addActionListener(this);
        back.setActionCommand("back");
        styleButton(back);
        setAlignments(back, CENTER_ALIGNMENT);

        submit = new JButton("submit");
        submit.setFont(font);
        setElementSize(submit, 300, 50);
        submit.addActionListener(this);
        submit.setActionCommand("submit");
        styleButton(submit);
        setAlignments(submit, CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(150));
    }

    public abstract void ui();

    public void setElementSize(JComponent element, int w, int h) {
        element.setSize(w, h);
        element.setMaximumSize(element.getSize());
    }

    void setAlignments(JComponent element, float alignment) {
        element.setAlignmentX(alignment);
        element.setAlignmentY(alignment);
    }

    void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(GREY);
        button.setForeground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e){}

    void setContentPane(Container pane) {
        setVisible(false);
        getRootPane().setContentPane(pane);
    }

    boolean verifyName(JTextField textField) {
        if (textField.getText().matches("\\s+"))
            return false;
        switch (textField.getText()) {
            case "":
            case "enter name here...":
            case "DISPLAY NAME REQUIRED!!":
            case "Player O name...":
            case "Player X name...":
                return false;
            default:
                return true;
        }
    }

    void enableButton(JButton button) {
        button.setEnabled(true);
        button.setBackground(GREEN);
    }

    void disableButton(JButton button) {
        button.setEnabled(false);
        button.setBackground(RED);
    }
}

