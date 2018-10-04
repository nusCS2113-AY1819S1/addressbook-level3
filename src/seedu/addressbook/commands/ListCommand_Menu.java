package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand_Menu extends Command {

    public static final String COMMAND_WORD = "listmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays all menu items in the RMS system as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult_Menu execute() {
        List<ReadOnlyMenus> allMenus = addressBook.getAllMenus().immutableListView();
        return new CommandResult_Menu(getMessageForMenuListShownSummary(allMenus), allMenus);
    }
}
