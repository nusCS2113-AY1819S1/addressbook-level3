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
            List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
            List<ReadOnlyPerson> editableAllPersons = addressBook.getAllPersons().mutableListView();
            return new CommandResult(MESSAGE_SUCCESS, allPersons, editableAllPersons);
        } catch (CommandStack.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_NO_COMMAND_TO_REDO);
        } catch (Exception e){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Gets the UndoAbleCommand object to be redone and execute its redo
     * @throws Exception if something goes wrong with redo-ing
     */
    public void prepRedo() throws Exception {
        UndoAbleCommand toRedo = commandStack.redoLast();
        toRedo.executeRedo();
    }
}
