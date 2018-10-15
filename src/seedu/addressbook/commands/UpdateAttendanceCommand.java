package seedu.addressbook.commands;

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
            + "Parameters: indexOfPerson d/dd-mm-yyyy att/attendance \n"
            + "Example: " + COMMAND_WORD + " " + "1 d/29-09-2018 att/1 \n"
            + "To input today's date, input d/0";

    public static final String MESSAGE_SUCCESS = "Attendance updated for: ";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance has already been taken. New feature to" +
            " include overwriting will be done in v1.3";

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
            boolean duplicateDate = person.updateAttendanceMethod(date, isPresent);
            if(duplicateDate){
                return new CommandResult(String.format((MESSAGE_DUPLICATE_ATTENDANCE)));
            }else {
                return new CommandResult(String.format(MESSAGE_SUCCESS) + person.getName());
            }
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
