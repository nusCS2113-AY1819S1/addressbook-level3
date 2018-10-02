package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.curriculum.EnrolledClass;


public class AddEnrolledClassCommand extends Command {
    public static final String COMMAND_WORD = "addClass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds an enrolled class "
            + "to a person in the address book.\n\t"
            + "Parameters: INDEX ec/ENROLLED_CLASS\n\t"
            + "Example: " + COMMAND_WORD
            + " 1 ec/CS2113T";

    public static final String MESSAGE_SUCCESS = "New enrolled class added: %1$s";

    private final EnrolledClass toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddEnrolledClassCommand(int targetVisibleIndex,
                      String enrolledClassName) throws IllegalValueException {
        super(targetVisibleIndex);
        this.toAdd = new EnrolledClass(enrolledClassName);

    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            target.getCurriculum().addEnrolledClass(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }  /*catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } */
    }

}