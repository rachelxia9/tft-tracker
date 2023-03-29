package ui;

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

// SOURCES: Robust Traffic Light, Alarm System

public class GUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/matchHistory.json";
    private static JDesktopPane desktop;
    private static JInternalFrame controlPanel;

    private MatchHistory mh;
    private Game game;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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


    // SOURCE: SPACE INVADERS BASE, ButtonDemo: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
    // SOURCE: https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
    // GUI constructor constructs a new JFrame with components of app - menu, input panels, add, and edit panels
    public GUI() {
        super("TFT APP");
        mh = new MatchHistory();
        centreOnScreen();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        startMainMenu();
        makeMatchHistoryPanel();
        makeAddGamePanel();
        makeEditGamePanel();


        JLabel startLabel = new JLabel("Welcome to the TFT tracker App!");
//        JLabel startImg = new JLabel();
//        startImg.setIcon(new ImageIcon("data/tft.jpeg"));
//        startImg.setMinimumSize(new Dimension(700, 700));
        menu.add(startLabel);
//        menu.add(startImg);

        initializeMenuButtons();
        addButtons(openButton, addButton, editButton, saveButton, loadButton, quitButton);
        makeButtonsDoStuff();

        menu.setVisible(true);

    }

    // SOURCE: SPACE INVADERS BASE
    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ centreOnScreen() in AlarmControllerUI
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // SOURCES: Background Image: https://stackoverflow.com/questions/1466240/how-to-set-an-image-as-a-background-for-frame-in-swing-gui-of-java
    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ addButtonPanel()
    // EFFECTS: Make panel for main menu and set bg colour
    public void startMainMenu() {
        menu = new JPanel();

        final Image startImg;
        try {
            startImg = ImageIO.read(new File("data/tft.jpeg"));
            setContentPane(new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    g.drawImage((startImg), 0, 0, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menu.setLocation(1000, 200);
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
        quitButton = new JButton(QUIT_COMMAND);
        quitButton.setIconTextGap(6);
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
                           JButton loadButton, JButton quitButton) {
        addButton(openButton, menu);
        addButton(addButton, menu);
        addButton(editButton, menu);
        addButton(saveButton, menu);
        addButton(loadButton, menu);
        addButton(quitButton, menu);


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

        quitButton.addActionListener(this);
        quitButton.setActionCommand(QUIT_COMMAND);
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
        } else if (ae.getActionCommand().equals(QUIT_COMMAND)) {
            System.exit(0);

        }
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/ addButtonPanel()
    // EFFECTS: Makes panel for user input related to add game function
    public void makeAddGamePanel() {
        addGamePanel = new JPanel(new GridLayout(4, 2));
        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
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
            game = new Game(Integer.parseInt(rankString), compText.getText().trim());
            mh.addGame(game);

            HashMap<Integer, Game> allGames = mh.getGames();
            info = new ArrayList<>();

            for (Game g : allGames.values()) {
                int id = mh.getID(g);
                int rank = g.getRank();
                String comp = g.getComp();
                info.add("ID: " + id + " | Rank: " + rank + " | Comp: " + comp);
            }
            games.setText("<html><pre> Games: \n" + " " + info + "\n</pre></html>");
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string :(");
        } catch (IndexOutOfBoundsException e) {
            games.setText("Initialize match history file first");
        }
    }

    // EFFECTS: Makes panel for user input related to edit game function
    public void makeEditGamePanel() {
        editGamePanel = new JPanel(new GridLayout(5, 3));
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
    // MODIFIES: this
    // EFFECTS: Edits game in history based on info inputted by user
    public void editGame() {
        try {
            //HashMap<Integer, Game> allGames = mh.getGames();
            //String idString2 = idText2.getText().trim();
            if (!mh.getGames().containsKey(Integer.parseInt(idText2.getText().trim()))) {
                JOptionPane.showMessageDialog(null, "Game not found", "Output", JOptionPane.PLAIN_MESSAGE);
                //System.out.println("Game not found");
            } else {
                Game g = mh.accessGame(Integer.parseInt(idText2.getText().trim()));
                String rankString = rankText2.getText().trim();
                if (Integer.parseInt(rankString) < 1 || Integer.parseInt(rankString) > 8) {
                    //String message = "Your number is out of range";
                    JOptionPane.showMessageDialog(null, "Out of range", "Output", JOptionPane.PLAIN_MESSAGE);
                    rankString = "";
                }
                g.updateRank(Integer.parseInt(rankString));
                g.updateComp(compText2.getText().trim());
            }
            //HashMap<Integer, Game> updated = mh.getGames();
            info = new ArrayList<>();
            for (Game g : mh.getGames().values()) {
                info.add("ID: " + mh.getID(g) + " | Rank: " + g.getRank() + " | Comp: " + g.getComp());
            }
            games.setText("<html><pre> Games: \n" + " " + info + "\n</pre></html>");
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string :(");
        } catch (IndexOutOfBoundsException e) {
            games.setText("Initialize match history file first");
        }
    }

    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
    // MODIFIES: this
    // EFFECTS: Constructs match history panel
    public void makeMatchHistoryPanel() {
        matchHistoryPanel = new JPanel();
        matchHistoryPanel.setLayout(new BoxLayout(matchHistoryPanel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollItem = new JScrollPane(games, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        JList list = new JList(info.toArray());
//        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        list.setVisibleRowCount(-1);
//        JScrollPane scrollItem  = new JScrollPane(list);
        JButton backToMain = new JButton(RETURN_MAIN);
        backToMain.setActionCommand(RETURN_MAIN);
        backToMain.addActionListener(this);
        addButton(backToMain, matchHistoryPanel);
        matchHistoryPanel.setPreferredSize(new Dimension(700, 700));

//        BufferedImage tft = null;
//        try {
//            tft = ImageIO.read(new File("./data/tft.jpg"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        JLabel label = new JLabel(new ImageIcon(tft));
//        matchHistoryPanel.add(label);
//        img.setIcon(new ImageIcon("./data/tft.png"));
//        img.setMinimumSize(new Dimension(5, 5));
//        menu.add(img);
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
    // EFFECTS: loads match history from file
    public void loadMatchHistory() {
        try {
            mh = jsonReader.read();

            HashMap<Integer, Game> allGames = mh.getGames();
            ArrayList<String> info = new ArrayList<>();
            for (Game g : allGames.values()) {
                int id = mh.getID(g);
                int rank = g.getRank();
                String comp = g.getComp();
                info.add("ID: " + id + " | Rank: " + rank + " | Comp: " + comp + "\n");
            }
            games.setText("Current games" + info);
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

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/  DesktopFocusAction
    private class DesktopFocusAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            GUI.this.requestFocusInWindow();
        }
    }

}
