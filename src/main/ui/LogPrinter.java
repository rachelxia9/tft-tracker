package ui;

import model.EventLog;
// SOURCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/  LogPrinter interface

// this interface defines the behaviour that log printers have to implement

public interface LogPrinter {
    // EFFECTS: Prints event log
    void printLog(EventLog el);
}
