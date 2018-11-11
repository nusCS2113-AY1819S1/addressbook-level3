package seedu.addressbook.common;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_ENTERED_COMMAND_FORMAT = "Entered command: %1$s\n";

    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid.";
    public static final String MESSAGE_EMPLOYEE_NOT_IN_RMS = "Employee could not be found in Rms.";
    public static final String MESSAGE_EMPLOYEES_LISTED_OVERVIEW = "%1$d employees listed.";
    public static final String MESSAGE_NO_EMPLOYEES_IN_SYSTEM = "There are currently no employees added in the system.";

    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is invalid";
    public static final String MESSAGE_MEMBER_NOT_IN_RMS = "Member could not be found in Rms";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed!";
    public static final String MESSAGE_NEGATIVE_POINTS = "Update points cannot result in negative points.";

    public static final String MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX = "The menu item index provided is invalid";
    public static final String MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK = "Menu item could not be found in Rms";
    public static final String MESSAGE_MENUS_LISTED_OVERVIEW = "%1$d food items listed!";

    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid";
    public static final String MESSAGE_DRAFT_ORDER_DETAILS = "Current draft order: ";
    public static final String MESSAGE_ORDER_NOT_IN_ORDER_LIST = "Order could not be found in order list";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_USING_ORDER_LIST_STORAGE_FILE = "Using order list storage file : %1$s";
    public static final String MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE = "Launch command format: "
            + "java seedu.addressbook.Main [STORAGE_FILE_PATH]";
    public static final String MESSAGE_WELCOME = "Welcome to Restaurant Management System! \n"
            + "- To access EMPLOYEE DATABASE, key in 'listemp'\n"
            + "- To access MEMBER DATABASE, key in 'listmember'\n"
            + "- To access MENU, key in 'listmenu'\n"
            + "- To access ORDER, key in 'listorder'\n"
            + "- To access STATISITCS information, key in 'statistics'\n"
            + "- For further assistance, key in 'help'\n"
            + "----------------------------------------------------------";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";

}
