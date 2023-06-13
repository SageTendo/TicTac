package gui.panels.online;

import gui.panels.APanel;
import gui.GUI;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import utils.Constants;

public class ServerBrowserMenu extends APanel {

  static JList<String> clientList;
  static DefaultListModel<String> clientModel;
  static JScrollPane scrollPane;
  //  JTextField searchBox;
//  JButton searchButton;
  JButton refresh;

  public ServerBrowserMenu() {
    super("Client Browser");
    clientList = new JList<>();
    clientModel = new DefaultListModel<>();
    scrollPane = new JScrollPane();
//    searchBox = new JTextField();
//    searchButton = new JButton("search");
    refresh = new JButton("refresh");
    ui();
  }

  public void ui() {
    font = new Font(fontName, Font.BOLD, 50);
    title.setFont(font);

    font = new Font(fontName, Font.BOLD, 32);
//    JPanel searchBarLayout = new JPanel(null);
//    setElementSize(searchBarLayout, 700, 50);
//    searchBarLayout.add(searchBox);
//    searchBarLayout.add(searchButton);

//    searchBox.setFont(font);
//    searchBox.addActionListener(this);
//    setElementSize(searchBox, 500, 50);
//    setAlignments(searchBox);

//    font = new Font(fontName, Font.BOLD, 24);
//    searchButton.setFont(font);
//    searchButton.addActionListener(this);
//    styleButton(searchButton);
//    setElementSize(searchButton, 200, 50);
//    searchButton.setLocation(500, searchBarLayout.getY());

    JPanel buttonLayout = new JPanel();
    refresh.setFont(font);
    refresh.setLayout(new GridLayout(0, 4));
    refresh.setLocation(800, 10);
    refresh.setActionCommand("refresh");
    refresh.addActionListener(this);
    styleButton(refresh);

    submit.setText("join");
    submit.setLocation(300, 10);
    setElementSize(refresh, 100, 50);
    setElementSize(buttonLayout, 800, 50);

    buttonLayout.setBackground(getBackground());
    buttonLayout.add(back);
    buttonLayout.add(refresh);
    buttonLayout.add(submit);

    font = new Font(fontName, Font.BOLD, 20);
    setElementSize(scrollPane, 700, 300);
    setAlignments(scrollPane);
    scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Constants.GRAY;
      }
    });

    clientList.setFont(font);
    clientList.setSelectionBackground(Constants.GRAY);
    clientList.setBackground(Constants.WHITE);
    clientList.setForeground(Color.BLACK);

    add(title);
    add(Box.createVerticalStrut(50));
//    add(searchBarLayout);
    add(Box.createVerticalStrut(50));
    add(scrollPane);
    add(buttonLayout);

    // Fetch games
    GUI.getClient().fetchGames();
    setVisible(true);
  }

  public static void updateGamesList(String[] games) {
    clientModel.clear();

    for (String game : games) {
      clientModel.addElement(game);
    }
    clientList.setModel(clientModel);
    scrollPane.setViewportView(clientList);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "back":
        GUI.replaceContentPane(new OnlineOptionMenu());
        break;
      case "refresh":
        GUI.getClient().fetchGames();
        break;
      case "search":
        //TODO
        break;
      case "submit":
        String value = clientList.getSelectedValue();
        if (value != null) {
          GUI.getClient().joinGame(clientList.getSelectedValue());
        }
        break;
    }
  }
}
