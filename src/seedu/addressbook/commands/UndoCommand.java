package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo previous undo-able action.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "Undo successful!";

    @Override
    public CommandResult execute() {
        try {
            addressBook.undoLast();

            return new CommandResult(MESSAGE_SUCCESS);
        } catch (AddressBook.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
