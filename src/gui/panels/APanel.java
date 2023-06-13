package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.Constants;

/**
 * Representation of a panel in the GUI
 *
 * @author sageTendo
 */
public abstract class APanel extends JPanel implements ActionListener {

  public JLabel title;
  public Font font;
  public String fontName = "ARCADE ROUNDED";
  public BoxLayout layout;
  public JButton back;
  public JButton submit;

  /**
   * Constructor
   *
   * @param title the title of the panel
   */
  public APanel(String title) {
    layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    setLayout(layout);
    setBackground(Color.BLACK);

    font = new Font(fontName, Font.BOLD, 92);
    this.title = new JLabel(title);
    this.title.setFont(font);
    this.title.setForeground(new Color(0x5E5E5E));
    setAlignments(this.title);

    font = new Font(fontName, Font.BOLD, 24);
    back = new JButton("back");
    back.setFont(font);
    setElementSize(back, 300, 50);
    back.addActionListener(this);
    back.setActionCommand("back");
    styleButton(back);
    setAlignments(back);

    submit = new JButton("submit");
    submit.setFont(font);
    setElementSize(submit, 300, 50);
    submit.addActionListener(this);
    submit.setActionCommand("submit");
    styleButton(submit);
    setAlignments(submit);
    add(Box.createVerticalStrut(150));
  }

  /**
   * init the components for the panel
   */
  public abstract void ui();

  /**
   * set the size of the component
   *
   * @param element the component to set the size of
   * @param w       the width of the component
   * @param h       the height of the component
   */
  public void setElementSize(JComponent element, int w, int h) {
    element.setSize(w, h);
    element.setMaximumSize(element.getSize());
  }

  /**
   * set the alignment of the component
   *
   * @param element the component to set the alignment
   */
  public void setAlignments(JComponent element) {
    element.setAlignmentX(Component.CENTER_ALIGNMENT);
    element.setAlignmentY(Component.CENTER_ALIGNMENT);
  }

  /**
   * style the button
   *
   * @param button the button to style
   */
  public void styleButton(JButton button) {
    button.setFocusPainted(false);
    button.setBackground(Constants.GRAY);
    button.setForeground(Color.BLACK);
  }

  /**
   * verify the name of the client
   *
   * @param textField the text field
   * @return true if the name is valid
   */
  public boolean verifyName(JTextField textField) {
    return textField.getText().matches("[a-zA-Z0-9]+") && textField.getText().length() >= 3;
  }

  /**
   * enable the button
   *
   * @param button the button to enable
   */
  public void enableButton(JButton button) {
    button.setEnabled(true);
    button.setBackground(Constants.GREEN);
  }

  /**
   * disable the button
   *
   * @param button the button to disable
   */
  public void disableButton(JButton button) {
    button.setEnabled(false);
    button.setBackground(Constants.RED);
  }

  /**
   * process the action event
   *
   * @param e the event to be processed
   */
  @Override
  public abstract void actionPerformed(ActionEvent e);
}

