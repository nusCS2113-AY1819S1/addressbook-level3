package seedu.addressbook.commands;

import seedu.addressbook.data.exception.DuplicateDataException;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;
import seedu.addressbook.data.person.UniquePersonList;

import java.util.*;

public class AddAppointment extends Command{

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "Add in an appointment date for .\n\t"
            + "Parameters: DD-MM-YYYY\n\t"
            + "Example: " + COMMAND_WORD + " 01-01-2019";

    public static final String MESSAGE_EDIT_PERSON_APPOINTMENT = "%1$s has new appointment date(s)";

    //public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This person already exists in the address book";

   // private final Person toAdd;
    private final Set<Schedule> scheduleSetToAdd;
        //private final Set<Schedule> scheduleSetThatExist;
        //private Set<Schedule> finalScheduleSet;
    //private Set<Schedule> addedScheduleSet;
    //private Set<Schedule> duplicateScheduleSet;

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    /*public static class OnlyDuplicateScheduleException extends DuplicateDataException {
        protected OnlyDuplicateScheduleException() {
            super("Operation does not make changes to the set of appointments");
        }
    }*/

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddAppointment(Set<String> schedule) throws IllegalValueException{

        final Set<Schedule> scheduleSet = new HashSet<>();
        for (String scheduleDate : schedule) {
            scheduleSet.add(new Schedule(scheduleDate));
        }
        this.scheduleSetToAdd = scheduleSet;

    }

    //public ReadOnlyPerson getPerson() {
    //    return toAdd;
    //}

    @Override
    public CommandResult execute() {
        //return new CommandResult("command under construct, tbc ");
        try {
            commandHistory.addHistory("(edit-appointment " + checkEditingPersonIndex() + ") " + COMMAND_WORD + " "); //CHECK EFFECT
            this.setTargetIndex(checkEditingPersonIndex());
            final ReadOnlyPerson target = getTargetPerson();
            //Set<Schedule> scheduleSetThatExist = target.getSchedules();

            Set<Schedule> finalScheduleSet = target.getSchedules();//scheduleSetThatExist;
            boolean hasChanges = finalScheduleSet.addAll(scheduleSetToAdd);

            //if(hasChanges == false){ throw new OnlyDuplicateScheduleException();}
            if (!hasChanges) {
                return new CommandResult("Operation does not make changes to the set of appointments");
            }

            /*Set<Schedule> addedScheduleSet = new Set<Schedule>;
            Set<Schedule> duplicateScheduleSet = new Set<Schedule>;
            for(Schedule scheduleAdd : scheduleSetToAdd) {
                if(scheduleSetThatExist.contains(scheduleAdd)){
                    duplicateScheduleSet.add(scheduleAdd);
                }else{
                    addedScheduleSet.add(scheduleAdd);
                }
            }
            */
            Person updatedSchedulePerson = new Person(target);
            updatedSchedulePerson.setSchedule(finalScheduleSet);

            //finalScheduleSet make changes
            addressBook.editPerson(target, updatedSchedulePerson);

            List<ReadOnlyPerson> editablePersonList = this.getEditableLastShownList();
            /*
            int index = editablePersonList.indexOf(updatedSchedulePerson);
            if (index == -1) {
                throw new UniquePersonList.PersonNotFoundException();
            }
            editablePersonList.set(index, updatedSchedulePerson);
            */
            //editablePersonList.set(checkEditingPersonIndex(), updatedSchedulePerson);



            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_APPOINTMENT, target.getName()) );//, scheduleSetToAdd); //editablePersonList);//, true);

            //return new CommandResult(String.format("Successful schedule size of %1$d parser edit!", scheduleSetToAdd.size()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }// catch (UniquePersonList.DuplicatePersonException dpe) {
        //    return new CommandResult("Operation also does not make changes to the set of appointments");
        //}
        //catch (UniquePersonList.PersonNotFoundException pnfe) {
        // return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        //}

    }

}

