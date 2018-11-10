package seedu.addressbook.commands;

import seedu.addressbook.data.person.UniquePersonList;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoAbleCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears address book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "MediBook has been cleared!";

    private UniquePersonList copied;

    @Override
    public CommandResult execute() {
        copied = addressBook.getAllPersons();
        addressBook.clear();
        saveUndoableToHistory(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void executeUndo(){
        addressBook.switchAddressBook(copied);
    }

    @Override
    public void executeRedo(){
        addressBook.clear();
    }

    public void setCopied(UniquePersonList forTest){
        copied = forTest;
    }

}
