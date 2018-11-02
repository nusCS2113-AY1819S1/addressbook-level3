package seedu.addressbook.commands;

import seedu.addressbook.data.CommandStack;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

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
            UndoAbleCommand toRedo = commandStack.redoLast();
            toRedo.executeRedo();
            commandHistory.addHistory(COMMAND_WORD);
            List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
            return new CommandResult(MESSAGE_SUCCESS, allPersons);
        } catch (CommandStack.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_NO_COMMAND_TO_REDO);
        } catch (Exception e){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
