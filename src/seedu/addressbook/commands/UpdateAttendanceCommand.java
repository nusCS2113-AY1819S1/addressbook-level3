package seedu.addressbook.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;


/**
 *  Clocks in the date and time where the student is present.
 */
public class UpdateAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Updates the attendance of a student. \n"
            + "Parameters: indexOfPerson d/dd-mm-yyy att/attendance \n"
            + "Example: " + COMMAND_WORD + " " + "1 d/29-09-2018 att/1 \n"
            + "To input today's date, input d/0";

    public static final String MESSAGE_SUCCESS = "Attendance updated : %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "Attendance has already been taken";

    private boolean isPresent;
    private String date;

    // Constructor
    public UpdateAttendanceCommand(int targetIndex, String date, boolean isPresent) {
        super(targetIndex); // super is calling the constructor of the parent function
        this.date = date;
        this.isPresent = isPresent;
    }

    @Override
    public CommandResult execute() {
        try {
            Person person = addressBook.findPerson(getTargetPerson());
            person.updateAttendanceMethod(date, isPresent);
            return new CommandResult(String.format(MESSAGE_SUCCESS, getTargetIndex()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
