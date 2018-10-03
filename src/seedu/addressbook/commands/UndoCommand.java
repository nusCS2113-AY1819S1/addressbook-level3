package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.CommandHistory;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo previous undo-able action.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful!\n";
    public static final String MESSAGE_FAILURE = "No command to undo";

    @Override
    public CommandResult execute() {
        try {
            commandHistory.undoLast();
            commandHistory.addHistory(COMMAND_WORD);
            List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
            return new CommandResult(MESSAGE_SUCCESS + getMessageForPersonListShownSummary(allPersons), allPersons);
        } catch (CommandHistory.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
