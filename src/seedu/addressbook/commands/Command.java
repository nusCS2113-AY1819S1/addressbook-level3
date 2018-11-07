package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.CommandHistory;
import seedu.addressbook.data.CommandStack;
import seedu.addressbook.data.person.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public abstract class Command {
    protected AddressBook addressBook;
    protected CommandHistory commandHistory;
    protected CommandStack commandStack;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    protected List<ReadOnlyPerson> editableLastShownList;
    private int targetIndex = -1;
    private int targetIndex2 = -1;
    private static boolean isEditingAppointment = false;
    private static int editingPersonIndex = 0;

    public static boolean checkEditingAppointmentState(){ return isEditingAppointment;}

    public static void setEditingAppointmentState(boolean state){ isEditingAppointment = state;}

    public static int checkEditingPersonIndex(){ return editingPersonIndex;}

    public static void setEditingPersonIndex(int index){ editingPersonIndex = index;}


    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    public Command(int targetIndex, int targetIndex2) {
        this.setTargetIndex(targetIndex);
        this.setTargetIndex2(targetIndex2);
    }

    protected Command() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param appointmentDisplayed used to generate summary
     * @return a list of appointments made for the chosen person
     */
    public static String getMessageForAppointmentMadeByPerson(Set<? extends Schedule> appointmentDisplayed, Name name) {
        return String.format(Messages.MESSAGE_APPOINTMENT_LISTED_OVERVIEW, name.toString(), appointmentDisplayed.size());
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute(){
        throw new UnsupportedOperationException("This method should be implement in child classes");
    }

    //Note: it is better to make the execute() method abstract, by replacing the above method with the line below:
    //public abstract CommandResult execute();

    /**
     * Supplies the data the command will operate on.
     */
    public void setData(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons, List<ReadOnlyPerson> editableLastShownList) {
        this.addressBook = addressBook;
        this.commandHistory = addressBook.getCommandHistory();
        this.commandStack = addressBook.getCommandStack();
        this.relevantPersons = relevantPersons;
        this.editableLastShownList = editableLastShownList;
    }


    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public List<ReadOnlyPerson> getEditableLastShownList() {
        return editableLastShownList;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson2() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex2() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex2() {
        return targetIndex2;
    }

    public void setTargetIndex2(int targetIndex2) {
        this.targetIndex2 = targetIndex2;
    }
}
