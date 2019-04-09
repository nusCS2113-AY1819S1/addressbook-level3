package seedu.addressbook.commands;

import seedu.addressbook.data.CommandStack;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

/**
 * Undo an UndoAbleCommand at the top of undoStack in commandStack
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo previous undo-able action.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful!\n";
    public static final String MESSAGE_NO_COMMAND = "No command to undo";
    public static final String MESSAGE_FAILURE = "An error has occurred with the undo command";

    @Override
    public CommandResult execute() {
        try {
            prepUndo();
            saveHistory(COMMAND_WORD);
            List<ReadOnlyPerson> allPersons = addressBook.getMutableListView();
            List<ReadOnlyPerson> editableAllPersons = addressBook.getMutableListView();
            return new CommandResult(MESSAGE_SUCCESS, allPersons, editableAllPersons);
        } catch (CommandStack.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_NO_COMMAND);
        } catch (CommandStack.ImplementationErrorException iee){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Call the undo logic in command stack
     * @throws CommandStack.HistoryOutOfBoundException if there is no command to be undone
     * @throws CommandStack.ImplementationErrorException if something went wrong while executing undo logic
     */
    public void prepUndo() throws CommandStack.HistoryOutOfBoundException, CommandStack.ImplementationErrorException {
        commandStack.undoLast();
    }
}
