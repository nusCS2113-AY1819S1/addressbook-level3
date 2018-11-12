package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

public class SortTitleCommand extends Command{
    public static final String COMMAND_WORD = "sorttitle";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Sorts according to title and displays all persons in MediBook as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        saveHistory(COMMAND_WORD);
        List<ReadOnlyPerson> sortedPersons = addressBook.getAllPersons().sortTitleView();
        List<ReadOnlyPerson> allMutablePersons = addressBook.getAllPersons().mutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(sortedPersons), sortedPersons, allMutablePersons);
    }
}

