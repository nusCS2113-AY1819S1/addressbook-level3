package seedu.addressbook.parser;

import seedu.addressbook.commands.*;
import seedu.addressbook.data.exception.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses user input.
 */
public class Parser {

    public static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    public static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isNricPrivate>p?)n/(?<nric>[^/]+)"
                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + " s/(?<title>[^/]+)"
                    + "(?<scheduleArguments>(?: d/[^/]+)*)" //variable number of schedule (including 0)
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags (including 0)

    public static final Pattern APPOINTMENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<scheduleArguments>.+)"); // variable number of schedule

    public static final Pattern PERSON_INDEX_ARGS_FORMAT2 = Pattern.compile("(?<targetIndex>[^ ]+)" + " (?<targetIndex2>.+)");

    public static final Pattern USER_PASSWORD_ARGS_FORMAT = Pattern.compile("pw/(?<currentpassword>[^/]+)" + "npw/(?<password>[^/]+)" + "cpw/(?<confirmpassword>[^/]+)");


    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        ParseException(String message) {
            super(message);
        }
    }

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if(!Command.checkEditingAppointmentState()) {
            switch (commandWord.toLowerCase()) {

                case AddCommand.COMMAND_WORD:
                    return prepareAdd(arguments);

                case DeleteCommand.COMMAND_WORD:
                    return prepareDelete(arguments);

                case ClearCommand.COMMAND_WORD:
                    return new ClearCommand();

                case FindCommand.COMMAND_WORD:
                    return prepareFind(arguments.toLowerCase());

                case ListCommand.COMMAND_WORD:
                    return new ListCommand();

                case ViewCommand.COMMAND_WORD:
                    return prepareView(arguments);

                case ViewAllCommand.COMMAND_WORD:
                    return prepareViewAll(arguments);

                case ExitCommand.COMMAND_WORD:
                    return new ExitCommand();

                case UndoCommand.COMMAND_WORD:
                    return new UndoCommand();

                case RedoCommand.COMMAND_WORD:
                    return new RedoCommand();

                case HistoryCommand.COMMAND_WORD:
                    return new HistoryCommand();

                case EditAppointmentCommand.COMMAND_WORD:
                    return prepareEditAppointment(arguments);

                case LinkCommand.COMMAND_WORD:
                    return prepareLink(arguments);

                case UnLinkCommand.COMMAND_WORD:
                    return prepareUnLink(arguments);

                case AssociateListCommand.COMMAND_WORD:
                    return prepareAssociateList(arguments);

                case ChangePasswordCommand.COMMAND_WORD:
                  return prepareChangePassword(arguments);

                case SortNameCommand.COMMAND_WORD:
                    return new SortNameCommand();

                case SortTitleCommand.COMMAND_WORD:
                    return new SortTitleCommand();

                case HelpCommand.COMMAND_WORD: // Fallthrough
                default:
                    return new HelpCommand();
            }
        }else{
            switch (commandWord.toLowerCase()) {
                case ExitEditAppointment.COMMAND_WORD:
                    return new ExitEditAppointment();

                case ListAppointment.COMMAND_WORD:
                    return new ListAppointment();

                case AddAppointment.COMMAND_WORD:
                    return prepareAddAppointment(arguments);

                case DeleteAppointment.COMMAND_WORD:
                    return prepareDeleteAppointment(arguments);

                case HelpEditAppointment.COMMAND_WORD:
                default:
                    return new HelpEditAppointment();
            }
        }
    }

    private Command prepareChangePassword(String args){
        final Matcher matcher = USER_PASSWORD_ARGS_FORMAT.matcher(args.trim());
        if(!matcher.matches()){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
        }
        return new ChangePasswordCommand(
                matcher.group("currentpassword"), matcher.group("password"), matcher.group("confirmpassword")
        );
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),

                    matcher.group("nric"),
                    isPrivatePrefixPresent(matcher.group("isNricPrivate")),

                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    matcher.group("title"),

                    getScheduleFromArgs(matcher.group("scheduleArguments")),

                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }

    /**
     * Extracts the new person's schedule from the add command's schedule arguments string.
     * Merges duplicate schedule strings.
     */
    private static Set<String> getScheduleFromArgs(String scheduleArguments) {
        // no schedule
        if (scheduleArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> scheduleStrings = Arrays.asList(scheduleArguments.replaceFirst(" d/", "").split(" d/"));
        return new HashSet<>(scheduleStrings);
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view all command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareViewAll(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewAllCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAllCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    private Command prepareLink(String args) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT2.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkCommand.MESSAGE_USAGE));
        }
        try {
            final int targetIndex = Integer.parseInt(matcher.group("targetIndex"));
            final int targetIndex2 = Integer.parseInt(matcher.group("targetIndex2"));
            return new LinkCommand(targetIndex, targetIndex2);
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkCommand.MESSAGE_USAGE));
        }
    }
    
    private Command prepareUnLink(String args) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT2.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnLinkCommand.MESSAGE_USAGE));
        }
        try {
            final int targetIndex = Integer.parseInt(matcher.group("targetIndex"));
            final int targetIndex2 = Integer.parseInt(matcher.group("targetIndex2"));
            return new UnLinkCommand(targetIndex, targetIndex2);
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the edit appointment command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditAppointment(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new EditAppointmentCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the add appointment command for the selected person in edit-appointment mode.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddAppointment(String args){
        final Matcher matcher = APPOINTMENT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointment.MESSAGE_USAGE));
        }
        try {
            return new AddAppointment(getScheduleArgs(matcher.group("scheduleArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the delete appointment command for the selected person in edit-appointment mode.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeleteAppointment(String args) {
        final Matcher matcher = APPOINTMENT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointment.MESSAGE_USAGE));
        }
        try {
            return new DeleteAppointment(getScheduleArgs(matcher.group("scheduleArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Extracts the new set of schedule input from the add and delete command in edit-appointment mode.
     * Merges duplicate schedule strings.
     */
    private static Set<String> getScheduleArgs(String scheduleArguments){
        final Collection<String> scheduleStrings = Arrays.asList(scheduleArguments.split("\\s+"));
        return new HashSet<>(scheduleStrings);
    }

    /**
     * Parses arguments in the context of the associatelist command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAssociateList(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new AssociateListCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssociateListCommand.MESSAGE_USAGE));
        }
    }

}
