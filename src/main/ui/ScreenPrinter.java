package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a screen printer for printing event log to screen.
 */
public class ScreenPrinter extends JInternalFrame implements LogPrinter {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private JTextArea logArea;

    /**
     * Constructor sets up window in which log will be printed on screen
     *
     * @param parent the parent component
     */
    public ScreenPrinter(Component parent) {
        super("Event log", true, false, true, true);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(400, 400);
        setPosition(parent);
        setVisible(true);
    }

    @Override
    public void printLog(EventLog el) {
        for (Event next : el) {
            logArea.setText(logArea.getText() + next.toString() + "\n\n");
        }

        repaint();
    }

    /**
     * Sets the position of window in which log will be printed relative to
     * parent
     *
     * @param parent the parent component
     */
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
