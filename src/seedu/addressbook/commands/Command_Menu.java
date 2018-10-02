package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.RMS_menu;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public abstract class Command_Menu {
    protected RMS_menu menuBook;
    protected List<? extends ReadOnlyMenus> relevantMenus;
    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command_Menu(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command_Menu() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param menusDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForMenuListShownSummary(List<? extends ReadOnlyMenus> menusDisplayed) {
        return String.format(Messages.MESSAGE_MENUS_LISTED_OVERVIEW, menusDisplayed.size());
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
    public void setData(RMS_menu menuBook, List<? extends ReadOnlyMenus> relevantMenus) {
        this.menuBook = menuBook;
        this.relevantMenus = relevantMenus;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyMenus getTargetMenu() throws IndexOutOfBoundsException {
        return relevantMenus.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
