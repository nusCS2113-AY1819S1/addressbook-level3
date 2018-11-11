package seedu.addressbook.commands;

/**
 * Represents an executable command.
 */
public abstract class UndoAbleCommand extends Command{

    /**
     * @param targetVisibleIndex last visible listing index of the target person
     * @param targetVisibleIndex2 last visible listing index of second target person
     */
    UndoAbleCommand(int targetVisibleIndex, int targetVisibleIndex2){
        super(targetVisibleIndex, targetVisibleIndex2);
    }

    /**
     * @param targetVisibleIndex last visible listing index of the target person
     */
    UndoAbleCommand(int targetVisibleIndex){
        super(targetVisibleIndex);
    }

    protected UndoAbleCommand(){}

    /**
     * Execute undo logic which should be implemented in each UndoAbleCommand sub class
     */
    public abstract void executeUndo() throws Exception;

    /**
     * Execute redo logic which should be implemented in each UndoAbleCommand sub class
     */
    public abstract void executeRedo() throws Exception;

    /**
     * Save the UndoAbleCommand object into the commandStack as well as call saveHistory
     */
    public void saveUndoableToHistory(String fullCommand){
        saveHistory(fullCommand);
        commandStack.checkForAction(this);
    }
}
