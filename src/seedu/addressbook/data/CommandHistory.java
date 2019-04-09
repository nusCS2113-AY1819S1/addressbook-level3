package seedu.addressbook.data;

import java.util.ArrayList;

/**
 * Represents the history of executed commands
 */
public class CommandHistory {
    private ArrayList<String> history;

    /**
     * Create a new empty command history
     */
    public CommandHistory(){
        history = new ArrayList<>();
    }

    public static class EmptyHistoryException extends Exception {}

    /**
     * Adds Arg into history
     * @param Arg is supposed to represent the command word and arguments to be saved into history
     */
    public void addHistory(String Arg) {
        history.add(Arg);
    }

    /**
     * Gets the history to be printed
     * @return the printable history
     * @throws EmptyHistoryException if history is empty
     */
    public String getHistory() throws EmptyHistoryException {
        if(history.size() == 0) throw new EmptyHistoryException();
        return historyToString();
    }

    /**
     * Converts the history to a String
     * @return the String of the converted history
     */
    public String historyToString() {
        final StringBuilder builder = new StringBuilder();
        for (String command:history) {
            builder.append(command);
            builder.append("\n");
        }
        return builder.toString();
    }
}
