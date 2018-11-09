package seedu.addressbook.commands;

import seedu.addressbook.data.CommandStack;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

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
            UndoAbleCommand toUndo = commandStack.undoLast();
            toUndo.executeUndo();
            commandHistory.addHistory(COMMAND_WORD);
            List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
            List<ReadOnlyPerson> editableAllPersons = addressBook.getAllPersons().mutableListView();
            return new CommandResult(MESSAGE_SUCCESS, allPersons, editableAllPersons);
        } catch (CommandStack.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_NO_COMMAND);
        } catch (Exception e){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
