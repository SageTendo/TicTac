package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ServerBrowserMenu extends APanel {
    JList<String> clientList;
    DefaultListModel<String> clientModel;
    JScrollPane scrollPane;
    JTextField searchBox;
    JButton searchButton;
    JButton refresh;

    public ServerBrowserMenu() {
        super("Client Browser");
        clientList = new JList<>();
        clientModel = new DefaultListModel<>();
        scrollPane = new JScrollPane();
        searchBox = new JTextField();
        searchButton = new JButton("search");
        refresh = new JButton("refresh");
        ui();
    }

    public void ui(){
        font = new Font(fontName, Font.BOLD, 50);
        title.setFont(font);

        font = new Font(fontName, Font.BOLD, 32);
        JPanel searchBarLayout = new JPanel(null);
        setElementSize(searchBarLayout, 700, 50);
        searchBarLayout.add(searchBox);
        searchBarLayout.add(searchButton);

        searchBox.setFont(font);
        setElementSize(searchBox, 500, 50);
        setAlignments(searchBox, CENTER_ALIGNMENT);

        font = new Font(fontName, Font.BOLD, 24);
        searchButton.setFont(font);
        styleButton(searchButton);
        setElementSize(searchButton, 200, 50);
        searchButton.setLocation(500,searchBarLayout.getY());

        JPanel buttonLayout = new JPanel();
        refresh.setFont(font);
        refresh.setLayout(new GridLayout(0,4));
        refresh.setLocation(800, 10);
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
        setAlignments(scrollPane, CENTER_ALIGNMENT);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GREY;
            }
        });

        clientList.setFont(font);
        clientList.setSelectionBackground(GREY);
        clientList.setBackground(WHITE);
        clientList.setForeground(Color.BLACK);
        //Dummy data
        for (int i = 0; i < 30; i++) {
            clientModel.addElement("user "+i);
        }
        clientList.setModel(clientModel);
        scrollPane.setViewportView(clientList);

        add(title);
        add(Box.createVerticalStrut(50));
        add(searchBarLayout);
        add(Box.createVerticalStrut(50));
        add(scrollPane);
        add(buttonLayout);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                setContentPane(new MultiplayerMenu());
                break;
            case "refresh":
                //TODO
                break;
            case "search":
                //TODO
                break;
            case "join":
                //TODO
                break;
        }
    }
}
