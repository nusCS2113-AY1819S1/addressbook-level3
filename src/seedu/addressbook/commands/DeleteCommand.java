package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoAbleCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Deletes the person identified by the index number used in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private Person backup;


    public DeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getAndRemoveROP();
            saveUndoableToHistory(COMMAND_WORD + " " + getTargetIndex());
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    public ReadOnlyPerson getAndRemoveROP() throws PersonNotFoundException {
        final ReadOnlyPerson target = getTargetPerson();
        this.backup = target.getPerson();
        addressBook.removePerson(target);
        return target;
    }

    @Override
    public void executeUndo() throws Exception{
        addressBook.addPerson(this.backup);
    }

    @Override
    public void executeRedo() throws Exception{
        addressBook.removePerson(this.backup);
    }

    public void setBackup(Person forTest){
        backup = forTest;
    }
}
