package seedu.addressbook.commands;

import seedu.addressbook.data.CommandStack;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

/**
 * Undo an UndoAbleCommand at the top of undoStack in commandStack
 */
public class RedoCommand extends Command{
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Redo previous undid action.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful!\n";
    public static final String MESSAGE_FAILURE = "No command to redo";
    public static final String MESSAGE_NO_COMMAND_TO_REDO = "No command to redo";

    @Override
    public CommandResult execute() {
        try {
            prepRedo();
            saveHistory(COMMAND_WORD);
            List<ReadOnlyPerson> allPersons = addressBook.getMutableListView();
            List<ReadOnlyPerson> editableAllPersons = addressBook.getMutableListView();
            return new CommandResult(MESSAGE_SUCCESS, allPersons, editableAllPersons);
        } catch (CommandStack.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_NO_COMMAND_TO_REDO);
        } catch (CommandStack.ImplementationErrorException iie){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Call the redo logic in command stack
     * @throws CommandStack.HistoryOutOfBoundException if there is no command to be redone
     * @throws CommandStack.ImplementationErrorException if something went wrong while executing redo logic
     */
    public void prepRedo() throws CommandStack.HistoryOutOfBoundException, CommandStack.ImplementationErrorException {
        commandStack.redoLast();
    }
}
