package ui;

import model.Game;
import model.MatchHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SOURCES: Robust Traffic Light, Alarm System

public class GUI extends JFrame implements ActionListener {
    private static final String MATCHHISTORY_FILE = "./data/matchHistory.json";
    private MatchHistory mh;
    private Game game;

    private JPanel menu;
    private JButton openButton;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton getStatsButton;

    private static final String OPEN_COMMAND = "open";
    private static final String ADD_COMMAND = "add";
    private static final String STATS_COMMAND = "stats";
    private static final String QUIT_COMMAND = "quit";
    private static final String EDIT_COMMAND = "edit";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String RETURN_MAIN = "return to the main menu";


    private JPanel matchHistoryPanel;
    private JLabel games;
    private boolean visible; // idk

    private JPanel addGamePanel;
    private JButton addGame;


    // GUI constructs a new JFrame with components of app
    public GUI() {
        super("TFT App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));
        startMainMenu();
        makeMatchHistoryPanel();
        makeAddGamePanel();

        initializeMenuButtons();
        addButtons();
        makeButtonsDoStuff();

        menu.setVisible(true);

    }

    // EFFECTS: Make panel for main menu and set bg colour
    private void startMainMenu() {
        menu = new JPanel();
        menu.setBackground(Color.pink);
        add(menu);
        games = new JLabel();
        games.setText("No games in match history yet..."); // starts off empty until you load in data
    }

    // EFFECTS: Initializes buttons for the main menu with labels
    private void initializeMenuButtons() {
        openButton = new JButton(OPEN_COMMAND);
        addButton = new JButton(ADD_COMMAND);
        editButton = new JButton(EDIT_COMMAND);
        saveButton = new JButton(SAVE_COMMAND);
        loadButton = new JButton(LOAD_COMMAND);
        quitButton = new JButton(QUIT_COMMAND);
    }

    // EFFECTS: helper for addButtons, adds a button to menu panel
    private void addButton(JButton button, JPanel panel) {

        button.setBackground(Color.white);
        panel.add(button);
        setVisible(true);


    }

    // EFFECTS: adds given buttons to menu panel
    private void addButtons() {
        addButton(openButton, menu);
        addButton(addButton, menu);
        addButton(editButton, menu);
        addButton(saveButton, menu);
        addButton(loadButton, menu);
        addButton(quitButton, menu);


    }

    // MODIFIES: this
    // EFFECTS: Assign actions to buttons on menu panel
    private void makeButtonsDoStuff() {

        openButton.addActionListener(this);
        openButton.setActionCommand(OPEN_COMMAND);

        addButton.addActionListener(this);
        addButton.setActionCommand(ADD_COMMAND);

        editButton.addActionListener(this);
        editButton.setActionCommand(EDIT_COMMAND);

        saveButton.addActionListener(this);
        saveButton.setActionCommand(SAVE_COMMAND);

        loadButton.addActionListener(this);
        loadButton.setActionCommand(LOAD_COMMAND);

        quitButton.addActionListener(this);
        quitButton.setActionCommand(QUIT_COMMAND);
    }

    // runs methods on match history upon click
    private void runButtons(ActionEvent ae) {
        if (ae.getActionCommand().equals(OPEN_COMMAND)) {
            displayMatchHistory();
        }
    }

    // EFFECTS: Adds the match history panel to the screen and hides the other ones
    private void displayMatchHistory() {
        add(matchHistoryPanel);
        matchHistoryPanel.setVisible(true);
        menu.setVisible(false);
        addGamePanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Constructs match history panel
    private void makeMatchHistoryPanel() {

        JScrollPane scrollItem = new JScrollPane(games, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
        addButton(backToMain, matchHistoryPanel);
        matchHistoryPanel.add(scrollItem);

    }

    // EFFECTS: Adds the panel for add game action to the screen and hides the other ones
    private void displayAddGamePanel() {
        add(addGamePanel);
        matchHistoryPanel.setVisible(false);
        menu.setVisible(false);
        addGamePanel.setVisible(true);
    }

    private void makeAddGamePanel() {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
