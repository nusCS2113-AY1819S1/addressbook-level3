package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "statsemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for employees.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getEmployeeStats());
    }

    private String getEmployeeStats() {
        /*
        StringBuilder res = new StringBuilder();
        List<ReadOnlyEmployee> allEmployees = rms.getAllEmployees().immutableListView();
        if (allEmployees.isEmpty())
        return "There are no employees in the system.";
        String[] headings = new String[]{"1","2","333"};
        AsciiTable table = new AsciiTable(headings);
        for (ReadOnlyEmployee emp : allEmployees) {
            String[] data = new String[]{"1", emp.getName().value, emp.getPosition().value};
            table.addRow(data);
        }
        return table.toString();
        */
        return "Work In Progress\n";
    }
}
