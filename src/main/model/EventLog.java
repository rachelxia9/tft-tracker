package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/  EventLog class
/**
 * Represents a log of tft app events
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    /**
     * the only EventLog in the system (Singleton Design Pattern)
     */
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }


    // EFFECTS: gets instance or creates a new one
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }


    // EFFECTS: adds event to event log
    public void logEvent(Event e) {
        events.add(e);
    }


    // EFFECTS: clears log and logs event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}