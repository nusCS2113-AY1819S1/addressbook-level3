package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand_Menu extends Command_Menu {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand_Menu(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult_Menu execute() {
        final List<ReadOnlyMenus> menusFound = getMenusWithNameContainingAnyKeyword(keywords);
        return new CommandResult_Menu(getMessageForMenuListShownSummary(menusFound), menusFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyMenus> getMenusWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyMenus> matchedMenus = new ArrayList<>();
        for (ReadOnlyMenus menu : menuBook.getAllMenus()) {
            final Set<String> wordsInName = new HashSet<>(menu.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedMenus.add(menu);
            }
        }
        return matchedMenus;
    }

}
