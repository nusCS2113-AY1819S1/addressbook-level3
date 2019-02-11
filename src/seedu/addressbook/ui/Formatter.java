package seedu.addressbook.ui;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;


/**
 * Used for formatting text for display. e.g. for adding text decorations.
 */
public class Formatter {

    /** A decorative prefix added to the beginning of lines printed by Rms */
    private static final String LINE_PREFIX = " ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();


    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    private static final int DISPLAYED_INDEX_OFFSET = 1;


    /** Formats the given strings for displaying to the user. */
    public String format(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String m : messages) {
            sb.append(LINE_PREFIX).append(m.replace("\n", LS + LINE_PREFIX)).append(LS);
        }
        return sb.toString();
    }

    //@@author SalsabilTasnia
    /** Formats the given list of menus for displaying to the user. */
    public String formatMenuResult(List<? extends ReadOnlyMenus> menus) {
        final List<String> formattedMenus = new ArrayList<>();
        for (ReadOnlyMenus menu : menus) {
            formattedMenus.add(menu.getAsText());
        }
        return format(asIndexedList(formattedMenus));
    }

    //@@author px1099
    /** Formats the given list of orders for displaying to the user. */
    public String formatOrderResult(List<? extends ReadOnlyOrder> orders) {
        final List<String> formattedOrders = new ArrayList<>();
        for (ReadOnlyOrder order : orders) {
            formattedOrders.add(order.getAsText());
        }
        return format(asIndexedList(formattedOrders));
    }

    //@@author kangmingtay
    /** Formats the given list of members for displaying to the user. */
    public String formatMemberResult(List<? extends ReadOnlyMember> members) {
        final List<String> formattedMembers = new ArrayList<>();
        for (ReadOnlyMember member : members) {
            formattedMembers.add(member.getAsText());
        }
        return format(asIndexedList(formattedMembers));
    }

    //@@author kianhong95
    /** Formats the given list of employees for displaying to the user. */
    public String formatEmployeeResult(List<? extends ReadOnlyEmployee> employees) {
        final List<String> formattedOrders = new ArrayList<>();
        for (ReadOnlyEmployee employee : employees) {
            formattedOrders.add(employee.getAsTextShowDetails());
        }
        return format(asIndexedList(formattedOrders));
    }

    //@@author
    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

}
