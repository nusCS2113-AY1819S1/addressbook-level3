package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;

public class RedoCommand extends Command{
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Redo previous undid action.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "No command to redo";

    @Override
    public CommandResult execute() {
        try {
            addressBook.redoLast();

            return new CommandResult(MESSAGE_SUCCESS);
        } catch (AddressBook.HistoryOutOfBoundException hoobe){
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
