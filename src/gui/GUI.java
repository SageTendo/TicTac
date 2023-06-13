package gui;

import gui.panels.APanel;
import gui.panels.MainMenu;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import networking.client.Client;
import utils.Constants;
import utils.FontCreator;

/**
 * GUI class
 */
public class GUI {

  private static final JFrame frame = new JFrame("TIC TAC TOE");
  private static Client client;

  /**
   * Constructor
   */
  public GUI() {
    frame.requestFocus();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(800, 800);
    frame.setResizable(false);
    frame.setLayout(new GridLayout(3, 3));
    new FontCreator(Paths.get(Constants.ASSETS_DIR, "ARCADE_R.ttf").toString(),
        Font.TRUETYPE_FONT).createFont();
    frame.setContentPane(new MainMenu());
    frame.setVisible(true);
  }

  /**
   * Replaces the content pane of the frame with the given content pane
   *
   * @param contentPane the content pane to set
   */
  public static void replaceContentPane(APanel contentPane) {
    frame.getContentPane().removeAll();
    frame.setContentPane(contentPane);
    frame.getContentPane().revalidate();
  }

  /**
   * Shows a message
   *
   * @param msg the message to show
   */
  public static void showMessage(String type, String msg) {
    JOptionPane.showMessageDialog(frame, msg, type, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Shows an error message
   *
   * @param message the message to show
   */
  public static void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Gets a client instance with the given ip and port
   *
   * @param ip   the ip address of the server
   * @param port the port of the server
   * @return the client instance
   * @throws IOException if the connection cannot be established
   */
  public static Client getClientInstance(String ip, int port) throws IOException {
    if (client == null) {
      client = new Client(ip, port);
    }
    return client;
  }

  /**
   * Closes the client
   */
  public static void closeClient() {
    client.disconnect();
    client = null;
  }

  /**
   * @return the client
   */
  public static Client getClient() {
    return client;
  }

  /**
   * Starts the client handler
   */
  public static void startClient() {
    if (!client.isAlive()) {
      client.start();
    }
  }
}
