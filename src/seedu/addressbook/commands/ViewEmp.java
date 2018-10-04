package seedu.addressbook.commands;

import java.util.List;

import seedu.addressbook.data.person.ReadOnlyPerson;


// this class is a copy of List Command with allPersons changed to allEmployees
public class ViewEmp extends Command{

    public static final String COMMAND_WORD = "viewemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows details of all employees. \n\t"
            + "Parameters: NIL\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allEmployees = addressBook.getAllEmployees().immutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(allEmployees), allEmployees);
    }
}
