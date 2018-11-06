package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoAbleCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a person to the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: NAME [p]p/NRIC [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS [p]s/TITLE [d/SCHEDULE]... [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " John Doe n/S1239875U p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 s/Doctor d/31-10-2018 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name,
                      String nric, boolean isNricPrivate,
                      String phone, boolean isPhonePrivate,
                      String email, boolean isEmailPrivate,
                      String address, boolean isAddressPrivate,
                      String title, boolean isTitlePrivate,
                      Set<String> schedule,
                      Set<String> tags) throws IllegalValueException {

        final Set<Schedule> scheduleSet = new HashSet<>();
        for (String scheduleDate : schedule) {
            scheduleSet.add(new Schedule(scheduleDate));
        }
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        final Set<Associated> associatedSet = new HashSet<>();
        this.toAdd = new Person(
                new Name(name),
                new Nric(nric, isNricPrivate),
                new Phone(phone, isPhonePrivate),
                new Email(email, isEmailPrivate),
                new Address(address, isAddressPrivate),
                new Title(title, isTitlePrivate),
                scheduleSet,
                tagSet,
                associatedSet
        );
    }

    public AddCommand(Person toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyPerson getPerson() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addPerson(toAdd);
            commandStack.checkForAction(this);
            commandHistory.addHistory(COMMAND_WORD + " " + toAdd.toString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    public void executeUndo() throws Exception{
        addressBook.removePerson(toAdd);
    }

    @Override
    public void executeRedo() throws Exception{
        addressBook.addPerson(toAdd);
    }
}
