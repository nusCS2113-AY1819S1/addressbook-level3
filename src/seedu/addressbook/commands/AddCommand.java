package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.login.Credentials;
import seedu.addressbook.login.login;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoAbleCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a person to the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Person must be either a 'Patient' or a 'Doctor' by specifying 'Patient' or 'Doctor' in the TITLE parameter. \n\t"
            + "Parameters: NAME [p]p/NRIC [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS s/TITLE [d/SCHEDULE]... [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " John Doe n/S1239875U p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 s/Patient d/01-01-2019-13:00 t/hasDiabetesType2 t/onInsulinTherapy";

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
                      String title,
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
                new Title(title),
                scheduleSet,
                tagSet,
                associatedSet
        );

        if(login.getAccesslevelF()==0 && title.equals("Doctor ")){
            AddDoctorLogin(nric);
        }
    }

    public AddCommand(Person toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyPerson getPerson() {
        return toAdd;
    }

    private void AddDoctorLogin(String nric){
        Credentials credentials = new Credentials(nric, "StandardPass123!", 2);
        credentials.newLogin();
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addPerson(toAdd);
            saveUndoableToHistory(COMMAND_WORD + " " + toAdd.toString());
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
