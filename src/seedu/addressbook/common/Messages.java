package seedu.addressbook.common;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESSBOOK = "Person could not be found in MediBook";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_APPOINTMENT_LISTED_OVERVIEW = "%1$s's %2$d appointment(s) listed!";
    public static final String MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE = "Launch command format: "
            + "java seedu.addressbook.Main [STORAGE_FILE_PATH]";
    public static final String MESSAGE_WELCOME = "Welcome to your MediBook!";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";
}
