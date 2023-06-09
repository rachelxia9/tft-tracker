package ui;

import model.Event;
import model.EventLog;
import model.Game;
import model.MatchHistory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

// GUI class represents GUI of TFT tracker application

public class GUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/matchHistory.json";
    private static JDesktopPane desktop;
    private static JInternalFrame controlPanel;

    private MatchHistory mh;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JInternalFrame menu;
    private JButton openButton;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton printButton;
    private JButton getStatsButton;

    private static final String OPEN_COMMAND = "open";
    private static final String ADD_COMMAND = "add";
    private static final String STATS_COMMAND = "stats";
    private static final String QUIT_COMMAND = "quit";
    private static final String EDIT_COMMAND = "edit";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String PRINT_LOG = "print events log";
    private static final String RETURN_MAIN = "return to the main menu";
    private static final String ADD_GAME = "add game to history";
    private static final String EDIT_GAME = "edit game in history";


    private JPanel matchHistoryPanel;
    private JLabel games;
    private boolean visible; // idk

    private JPanel addGamePanel;
    private JButton addGame;
    private JLabel rank;
    private JLabel comp;
    private JTextField rankText;
    private JTextField compText;
    private JButton backToMain;
    private ArrayList<String> info;
    private JPanel editGamePanel;
    private JButton editGame;
    private JLabel id2;
    private JTextField idText2;
    private JTextField rankText2;
    private JTextField compText2;
    private JLabel rank2;
    private JLabel comp2;
    private JButton backToMain2;
    private JLabel warning;
    private JTextArea mhTxt;
    private JPanel logPanel;
    private JTextArea logArea;
    private JComboBox<String> printCombo;
    private JPanel buttonPanel;


    // SOURCE: SPACE INVADERS BASE, ButtonDemo: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // GUI constructor constructs a new JFrame with components of app - menu, input panels, add, and edit panels
    public GUI() {
        super("TFT APP");
        mh = new MatchHistory();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 500));
        //this.add(new JLabel(new ImageIcon("data/tft.jpeg")));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        startMainMenu();
        makeMatchHistoryPanel();
        makeAddGamePanel();
        makeEditGamePanel();
        //printLogPanel();

        initializeMenuButtons();
        addButtons(openButton, addButton, editButton, saveButton, loadButton);
        makeButtonsDoStuff();

        menu.setVisible(true);

    }


    // SOURCES: Background Image: https://stackoverflow.com/questions/1466240/how-to-set-an-image-as-a-background-for-frame-in-swing-gui-of-java
    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ addButtonPanel()
    // EFFECTS: Make panel for main menu and set bg colour
    public void startMainMenu() {
        menu = new JInternalFrame("tft tracker!!!", true, false, false, false);
        menu.setBackground(new Color(213, 184, 222));
        this.add(menu);
        games = new JLabel();
        games.setText("No games in match history yet..."); // starts off empty until you load in data
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ addButtonPanel()
    // EFFECTS: Initializes buttons for the main menu with labels
    public void initializeMenuButtons() {
        openButton = new JButton(OPEN_COMMAND);
        openButton.setIconTextGap(6);
        addButton = new JButton(ADD_COMMAND);
        addButton.setIconTextGap(6);
        editButton = new JButton(EDIT_COMMAND);
        editButton.setIconTextGap(6);
        saveButton = new JButton(SAVE_COMMAND);
        saveButton.setIconTextGap(6);
        loadButton = new JButton(LOAD_COMMAND);
        loadButton.setIconTextGap(6);
//        quitButton = new JButton(QUIT_COMMAND);
//        quitButton.setIconTextGap(6);

    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase/blob/a29bdc4920f7d5d1f3844454a1bc3c5a257cc56f/src/main/ca/ubc/cpsc210/spaceinvaders/ui/SpaceInvaders.java
    // EFFECTS: helper for addButtons, adds a button to menu panel
    public void addButton(JButton button, JPanel panel) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBackground(Color.white);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase/blob/a29bdc4920f7d5d1f3844454a1bc3c5a257cc56f/src/main/ca/ubc/cpsc210/spaceinvaders/ui/SpaceInvaders.java
    // EFFECTS: helper for addButtons, adds a button to menu panel
    public void addMenuButton(JButton button, JPanel panel) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.white);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: adds given buttons to menu panel
    public void addButtons(JButton openButton, JButton addButton, JButton editButton, JButton saveButton,
                           JButton loadButton) {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        addButton(openButton, buttonPanel);
        addButton(addButton, buttonPanel);
        addButton(editButton, buttonPanel);
        addButton(saveButton, buttonPanel);
        addButton(loadButton, buttonPanel);
        //addButton(quitButton, buttonPanel);
        buttonPanel.add(new JButton(new PrintLogAction()));
        //buttonPanel.add(createPrintCombo());
        menu.add(buttonPanel, BorderLayout.WEST);


    }

    // SOURCES: ButtonDemo.java https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
    // MODIFIES: this
    // EFFECTS: Assign actions to buttons on menu panel
    public void makeButtonsDoStuff() {

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

//        quitButton.addActionListener(this);
//        quitButton.setActionCommand(QUIT_COMMAND);
    }

    // SOURCES: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
    // https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabSolution/blob/776c8678071abed56af8e793b57ab2b39046a9eb/src/main/gui/IntersectionGUI.java
    // runs methods on match history upon click
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(OPEN_COMMAND)) {
            displayMatchHistory();
        } else if (ae.getActionCommand().equals(ADD_COMMAND)) {
            displayAddGamePanel();
        } else if (ae.getActionCommand().equals(EDIT_COMMAND)) {
            displayEditGamePanel();
        } else if (ae.getActionCommand().equals(EDIT_GAME)) {
            editGame();
        } else if (ae.getActionCommand().equals(ADD_GAME)) {
            addGame();
        } else if (ae.getActionCommand().equals(RETURN_MAIN)) {
            returnMain();

        } else if (ae.getActionCommand().equals(SAVE_COMMAND)) {
            saveMatchHistory();
        } else if (ae.getActionCommand().equals(LOAD_COMMAND)) {
            loadMatchHistory();
            //printLogConsole(EventLog.getInstance());
        }
//        } else if (ae.getActionCommand().equals(QUIT_COMMAND)) {
//            System.exit(0);
//        }
    }

//    public void printLogPanel() {
//        logPanel = new JPanel();
//        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));
//
//        logArea = new JTextArea("");
//        EventLog.getInstance().events;
//        logArea.setEditable(false);
//        for (Event next : EventLog.getInstance()) {
//            logArea.append(next.toString() + "\n");
//        }
//
//        // printLog(EventLog.getInstance());
//        JScrollPane scrollPane = new JScrollPane(logArea);
//        ;
//        logPanel.add(scrollPane);
//        logPanel.setPreferredSize(new Dimension(600, 600));
//
//    }

//    // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/047c12f321ec713fae1f1a5dfdb01688ea1df596/src/main/ca/ubc/cpsc210/alarm/ui/ScreenPrinter.java
//    public void printLog(EventLog el) {
//        for (Event next : el) {
//            logArea.append(next.toString() + "\n");
//        }
//        repaint();
//    }


    // SOURCE: https://stackoverflow.com/questions/5600051/java-swing-how-to-toggle-panels-visibility
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // EFFECTS: Adds the panel for add game action to the screen and hides the other ones
    public void displayLogPanel() {
        add(logPanel);
        matchHistoryPanel.setVisible(false);
        menu.setVisible(false);
        addGamePanel.setVisible(false);
        //logPanel.setVisible(true);
        editGamePanel.setVisible(false);
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ addButtonPanel()
    // EFFECTS: Makes panel for user input related to add game function
    public void makeAddGamePanel() {
        addGamePanel = new JPanel(new GridLayout(3, 2));
        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
        addGamePanel.setSize(new Dimension(400, 400));
        //addMenuButton(backToMain, matchHistoryPanel);
        //addGamePanel.setPreferredSize(new Dimension(1000, 1000));
        makeInputPage();
        addGameLabels();
    }

    // SOURCE: https://stackoverflow.com/questions/5600051/java-swing-how-to-toggle-panels-visibility
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // EFFECTS: Adds the panel for add game action to the screen and hides the other ones
    public void displayAddGamePanel() {
        add(addGamePanel);
        matchHistoryPanel.setVisible(false);
        menu.setVisible(false);
        addGamePanel.setVisible(true);
        editGamePanel.setVisible(false);
        //logPanel.setVisible(false);
    }

    // EFFECTS: constructs the fields for the add game panel
    public void makeInputPage() {
        backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);

        addGame = new JButton(ADD_GAME);
        addGame.setActionCommand(ADD_GAME);
        addGame.addActionListener(this)
        ;

        rank = new JLabel("Enter your rank (1-8):");
        rankText = new JTextField(1);

        comp = new JLabel("Enter the name of your comp:");
        compText = new JTextField(15);

        // visible = false;
    }

    // EFFECTS: adds labels to add game panel
    public void addGameLabels() {
        addGamePanel.add(rank);
        addGamePanel.add(rankText);
        addGamePanel.add(comp);
        addGamePanel.add(compText);
        addGamePanel.add(addGame); // puts add game button on menu with
        addGamePanel.add(backToMain);


    }

    // SOURCES: https://docs.oracle.com/javase/tutorial/uiswing/components/html.html,https://www.w3schools.com/html/html_paragraphs.asp
    // https://stackoverflow.com/questions/53110312/how-to-set-a-range-e-g-1-20-for-numeric-values-in-jtextfield
    // https://coderanch.com/t/483677/java/JTextArea-realtime-update
    // MODIFIES: this
    // EFFECTS: Adds game inputted by user to match history
    public void addGame() {
        try {
            String rankString = rankText.getText().trim();

            if (Integer.parseInt(rankString) < 1 || Integer.parseInt(rankString) > 8) {
                //String message = "Your number is out of range";
                JOptionPane.showMessageDialog(null, "Your number is out of range", "Output", JOptionPane.PLAIN_MESSAGE);
                rankString = "";
            }
            //String compString = compText.getText().trim();
            int rank = Integer.parseInt(rankString);
            String comp = compText.getText().trim();
            Game game = new Game(rank, comp);
            mh.addGame(game);
            mhTxt.append("ID: " + mh.getID(game) + " | Rank: " + rank + " | Comp: " + comp + "\n");
            games.setText("");
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string :(");
        } catch (IndexOutOfBoundsException e) {
            games.setText("Initialize match history file first");
        }
    }

    // EFFECTS: Makes panel for user input related to edit game function
    public void makeEditGamePanel() {
        editGamePanel = new JPanel(new GridLayout(4, 2));
        editGamePanel.setSize(new Dimension(400, 400));
        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
        //addMenuButton(backToMain2, matchHistoryPanel);

        makeInputPageEdit();
        editGameLabels();
    }

    // SOURCE: https://stackoverflow.com/questions/5600051/java-swing-how-to-toggle-panels-visibility
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // EFFECTS: Adds the panel for edit game action to the screen and hides the other ones
    public void displayEditGamePanel() {
        add(editGamePanel);
        matchHistoryPanel.setVisible(false);
        menu.setVisible(false);
        addGamePanel.setVisible(false);
        editGamePanel.setVisible(true);
        //logPanel.setVisible(false);
    }

    // EFFECTS: constructs the fields for the add game panel
    public void makeInputPageEdit() {
        backToMain2 = new JButton(RETURN_MAIN);
        backToMain2.setActionCommand(RETURN_MAIN);
        backToMain2.addActionListener(this);

        editGame = new JButton(EDIT_GAME);
        editGame.setActionCommand(EDIT_GAME);
        editGame.addActionListener(this);

        id2 = new JLabel("Enter the id of the game you want to replace");
        idText2 = new JTextField(1);

        rank2 = new JLabel("Enter your rank (1-8):");
        rankText2 = new JTextField(1);

        comp2 = new JLabel("Enter the name of your comp:");
        compText2 = new JTextField(15);

        // visible = false;
    }

    // EFFECTS: adds labels to add game panel
    public void editGameLabels() {
        editGamePanel.add(id2);
        editGamePanel.add(idText2);
        editGamePanel.add(rank2);
        editGamePanel.add(rankText2);
        editGamePanel.add(comp2);
        editGamePanel.add(compText2);
        editGamePanel.add(editGame); // puts add game button on menu with
        editGamePanel.add(backToMain2);

    }

    // SOURCE: https://stackoverflow.com/questions/53110312/how-to-set-a-range-e-g-1-20-for-numeric-values-in-jtextfield
    // https://stackoverflow.com/questions/17163699/clear-text-from-a-jtextarea
    // MODIFIES: this
    // EFFECTS: Edits game in history based on info inputted by user
    public void editGame() {
        try {
            if (!mh.getGames().containsKey(Integer.parseInt(idText2.getText().trim()))) {
                JOptionPane.showMessageDialog(null, "Game not found", "Output", JOptionPane.PLAIN_MESSAGE);
            } else {
                Game g = mh.accessGame(Integer.parseInt(idText2.getText().trim()));
                String rankString = rankText2.getText().trim();
                if (Integer.parseInt(rankString) < 1 || Integer.parseInt(rankString) > 8) {
                    JOptionPane.showMessageDialog(null, "Out of range", "Output", JOptionPane.PLAIN_MESSAGE);
                    rankString = "";
                }
                g.updateRank(Integer.parseInt(rankString));
                g.updateComp(compText2.getText().trim());
                mhTxt.setText("");

                for (Game game : mh.getGames().values()) {
                    //info.add("ID: " + mh.getID(g) + " | Rank: " + g.getRank() + " | Comp: " + g.getComp());
                    mhTxt.append("ID:" + mh.getID(game) + " | Rank: " + game.getRank() + " | Comp: "
                            + game.getComp() + "\n");
                }
                games.setText("");
            }
        } catch (NumberFormatException nfe) {
            //System.out.println("NumberFormat Exception: invalid input string :(");
        } catch (IndexOutOfBoundsException e) {
            games.setText("Initialize match history file first");
        }
    }

    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
    // https://docs.oracle.com/javase/7/docs/api/javax/swing/JTextArea.html#:~:text=A%20JTextArea%20is%20a%20multi,it%20can%20reasonably%20do%20so.
    // MODIFIES: this
    // EFFECTS: Constructs match history panel
    public void makeMatchHistoryPanel() {
        matchHistoryPanel = new JPanel();
        matchHistoryPanel.setLayout(new BoxLayout(matchHistoryPanel, BoxLayout.PAGE_AXIS));
        matchHistoryPanel.add(new JLabel(new ImageIcon("data/tft.jpeg")));
        //games = new JLabel("");
        matchHistoryPanel.add(games);
        mhTxt = new JTextArea("");
        //matchHistoryPanel.add(textArea, BorderLayout.CENTER);
//        JScrollPane scrollItem = new JScrollPane(games, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane scrollItem = new JScrollPane(mhTxt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
        addButton(backToMain, matchHistoryPanel);
        matchHistoryPanel.setPreferredSize(new Dimension(300, 500));
        matchHistoryPanel.add(scrollItem);

    }

    // SOURCE: https://stackoverflow.com/questions/5600051/java-swing-how-to-toggle-panels-visibility
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // EFFECTS: Adds the match history panel to the screen and hides the other ones
    public void displayMatchHistory() {
        add(matchHistoryPanel);
        matchHistoryPanel.setVisible(true);
        menu.setVisible(false);
        addGamePanel.setVisible(false);
        editGamePanel.setVisible(false);
        //logPanel.setVisible(false);

    }

    // EFFECTS: save match history to file
    public void saveMatchHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(mh);
            jsonWriter.close();
            System.out.println("Saved match history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (NullPointerException e) {
            System.out.println("Load file first");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads match history from file and clear previous unsaved games
    public void loadMatchHistory() {
        try {
            mh = jsonReader.read();

            HashMap<Integer, Game> allGames = mh.getGames();
            mhTxt.setText("");
            for (Game g : allGames.values()) {
                int id = mh.getID(g);
                int rank = g.getRank();
                String comp = g.getComp();
                mhTxt.append("ID: " + id + " | Rank: " + rank + " | Comp: " + comp + "\n");
            }
            games.setText("");

            System.out.println("Loaded match history from " + JSON_STORE);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            games.setText("Empty match history file");
        } catch (IndexOutOfBoundsException e) {
            games.setText("Initialize history first");

        }
    }

    // SOURCE: https://stackoverflow.com/questions/5600051/java-swing-how-to-toggle-panels-visibility
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    public void returnMain() {
        matchHistoryPanel.setVisible(false);
        menu.setVisible(true);
        addGamePanel.setVisible(false);
        editGamePanel.setVisible(false);
    }

//    public void printLogConsole(EventLog el) {
//        for (Event next : el) {
//            System.out.println(next.toString() + "\n");
//        }
//    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/  PrintLogAction
    //EFFECTS: runs action to be taken when the user wants to print log
    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Quit and Show Log");
        }

//        public void printLogConsole(EventLog el) {
//            for (Event next : el) {
//                System.out.println(next.toString() + "\n");
//            }
//        }
//
//        printLogConsole()
        @Override
        public void actionPerformed(ActionEvent evt) {
            //String selected = (String) printCombo.getSelectedItem();
            LogPrinter lp = new ScreenPrinter(GUI.this);
            menu.add((ScreenPrinter) lp);

            lp.printLog(EventLog.getInstance());
            buttonPanel.setVisible(false);

        }
    }


    /**
     * Represents the action to be taken when the user wants to
     * clear the event log.
     */
//    private class ClearLogAction extends AbstractAction {
//        ClearLogAction() {
//            super("Clear log");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            EventLog.getInstance().clear();
//        }
//    }


// SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/  DesktopFocusAction
    private class DesktopFocusAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            GUI.this.requestFocusInWindow();
        }
    }

}
