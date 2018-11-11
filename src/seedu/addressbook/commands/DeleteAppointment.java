package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;
import seedu.addressbook.data.person.UniquePersonList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

public class DeleteAppointment extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Delete appointment for the selected person.\n"
            + "Note that multiple appointment dates with timing are accepted\n\t"
            + "Parameters: DD-MM-YYYY-HH:MM...\n\t"
            + "Example 1: " + COMMAND_WORD + " 01-01-2019-13:00 \n\t"
            + "Example 2: " + COMMAND_WORD + " 01-01-2019-13:00" + " 01-02-2019-14:00" + " 01-03-2019-15:00";

    private static final String MESSAGE_NO_CHANGE_MADE = "No changes made to the %1$s set of appointment "
            + "as no appointment were made on %2$s";

    private static final String MESSAGE_DELETE_PERSON_APPOINTMENT = "%1$s has deleted appointment!\n";

    private static final String MESSAGE_FOR_DELETED_APPOINTMENTS = "\nDeleted appointment for: %1$s\n";

    private static final String MESSAGE_FOR_MISSING_APPOINTMENTS = "Appointment that does not exist: %1$s";


    private final Set<Schedule> scheduleSetToDelete;

    private final String inputForHistory;


    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public DeleteAppointment(Set<String> schedule) throws IllegalValueException{

        final Set<Schedule> scheduleSet = new HashSet<>();
        for (String scheduleDate : schedule) {
            scheduleSet.add(new Schedule(scheduleDate));
        }
        this.scheduleSetToDelete = scheduleSet;

        inputForHistory = String.join(" ", schedule);
    }

    @Override
    public CommandResult execute() {
        try {
            saveHistory("(edit-appointment " + checkEditingPersonIndex() + ") " + COMMAND_WORD + " " + inputForHistory);
            this.setTargetIndex(checkEditingPersonIndex());
            final ReadOnlyPerson target = getTargetPerson();
            Set<Schedule> scheduleSet = target.getSchedules();

            String detailsMessage = getDetailedMessage(scheduleSet, target.getName().toString());
            boolean hasChanges = scheduleSet.removeAll(scheduleSetToDelete);

            if (!hasChanges) {
                return new CommandResult(String.format(MESSAGE_NO_CHANGE_MADE, target.getName(), inputForHistory));
            }

            Person updatedPerson = new Person(target);
            updatedPerson.setSchedule(scheduleSet);
            addressBook.editPerson(target, updatedPerson);

            List<ReadOnlyPerson> editablePersonList = this.getEditableLastShownList();
            editablePersonList.set(checkEditingPersonIndex() - DISPLAYED_INDEX_OFFSET, updatedPerson);
            return new CommandResult(String.format(detailsMessage, target.getName()), editablePersonList, editablePersonList, false);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }


    /**
     * Constructs a feedback message that details the effects of the input appointment dates.
     *
     * @param initialScheduleSet the original schedule of the user
     * @param name the name tobe printed in the message
     * @return a message that shows which appointments are deleted
     * and which appointments are not (as they are missing) for the person
     */
    private String getDetailedMessage(Set<Schedule> initialScheduleSet, String name) {
        StringBuilder deletedAppointments = new StringBuilder();
        StringBuilder missingAppointments = new StringBuilder();

        for(Schedule scheduleDelete : scheduleSetToDelete) {
            if(initialScheduleSet.contains(scheduleDelete)){
                deletedAppointments.append(scheduleDelete.toString());
                deletedAppointments.append(" ");
            }else{
                missingAppointments.append(scheduleDelete.toString());
                missingAppointments.append(" ");
            }
        }
        String detailsMessage = String.format(MESSAGE_DELETE_PERSON_APPOINTMENT, name);
        detailsMessage +=  String.format(MESSAGE_FOR_DELETED_APPOINTMENTS, deletedAppointments);
        if (missingAppointments.length() > 0) detailsMessage += String.format(MESSAGE_FOR_MISSING_APPOINTMENTS, missingAppointments);

        return detailsMessage;
    }

}
