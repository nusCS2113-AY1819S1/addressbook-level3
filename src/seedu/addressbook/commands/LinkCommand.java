package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Associated;
import seedu.addressbook.data.person.ReadOnlyPerson;

public class LinkCommand extends UndoAbleCommand{
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "associates a person identified by the index number used in the last person listing with another person. One of them must a patient and the other a doctor\n\t"
            + "Parameters: INDEX1 INDEX2\n\t"
            + "Example: " + COMMAND_WORD + " 1" + " 2";
    public static final String MESSAGE_SUCCESS = "Associated %1$s and %2$s!\n";
    public static final String MESSAGE_DUPLICATE_ASSOCIATION = "Association already exists!\n";
    public static final String MESSAGE_SAME_TITLE_FAILURE = "Only able to associate 2 person with different title!\n";

    private ReadOnlyPerson target;
    private ReadOnlyPerson target2;

    public LinkCommand(int targetVisibleIndex, int targetVisibleIndex2) {
        super(targetVisibleIndex, targetVisibleIndex2);
    }

    @Override
    public CommandResult execute() {
        try {
            target = getTargetPerson();
            target2 = getTargetPerson2();
            addressBook.linkTwoPerson(target, target2);
            saveUndoableToHistory(COMMAND_WORD + " " + getTargetIndex() + " " + getTargetIndex2());
            return new CommandResult(String.format(MESSAGE_SUCCESS, target.getName() ,target2.getName()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (Associated.DuplicateAssociationException dae) {
            return new CommandResult(MESSAGE_DUPLICATE_ASSOCIATION);
        } catch (Associated.SameTitleException ste) {
            return new CommandResult(MESSAGE_SAME_TITLE_FAILURE);
        }
    }

    @Override
    public void executeUndo() throws Exception{
        addressBook.unlinkTwoPerson(target, target2);
    }

    @Override
    public void executeRedo() throws Exception{
        addressBook.linkTwoPerson(target, target2);
    }

    public void setTargets(ReadOnlyPerson target, ReadOnlyPerson target2) {
        this.target = target;
        this.target2 = target2;
    }
}
