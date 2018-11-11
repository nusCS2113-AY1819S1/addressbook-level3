package seedu.addressbook.parser;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.employee.EmployeeAddCommand;
import seedu.addressbook.commands.employee.EmployeeClockInCommand;
import seedu.addressbook.commands.employee.EmployeeClockOutCommand;
import seedu.addressbook.commands.employee.EmployeeDeleteCommand;
import seedu.addressbook.commands.employee.EmployeeEditCommand;
import seedu.addressbook.commands.employee.EmployeeListCommand;
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberDeleteCommand;
import seedu.addressbook.commands.member.MemberListCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuClearCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
import seedu.addressbook.commands.menu.MenuListCommand;
import seedu.addressbook.commands.menu.MenuRecommendationCommand;
import seedu.addressbook.commands.menu.MenuShowMainMenuCommand;
import seedu.addressbook.commands.order.DraftOrderClearCommand;
import seedu.addressbook.commands.order.DraftOrderConfirmCommand;
import seedu.addressbook.commands.order.DraftOrderEditCustomerCommand;
import seedu.addressbook.commands.order.DraftOrderEditDishCommand;
import seedu.addressbook.commands.order.DraftOrderEditPointsCommand;
import seedu.addressbook.commands.order.OrderAddCommand;
import seedu.addressbook.commands.order.OrderClearCommand;
import seedu.addressbook.commands.order.OrderDeleteCommand;
import seedu.addressbook.commands.order.OrderListCommand;
import seedu.addressbook.commands.statistics.StatsEmployeeCommand;
import seedu.addressbook.commands.statistics.StatsHelpCommand;
import seedu.addressbook.commands.statistics.StatsMemberCommand;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.commands.statistics.StatsOrderCommand;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Parses user input.
 */
public class Parser {

    public static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    public static final Pattern ITEMWORD_ARGS_FORMAT = Pattern.compile("(?<type>[^/]+)"); //one keyword only

    // '/' forward slashes are reserved for delimiter prefixes
    public static final Pattern EMPLOYEE_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)"
                    + "p/(?<phone>[^/]+)"
                    + "e/(?<email>[^/]+)"
                    + "a/(?<address>[^/]+)"
                    + "pos/(?<position>[^/]+)");

    // '/' forward slashes are reserved for delimiter prefixes
    public static final Pattern EMPLOYEE_EDIT_DATA_ARGS_FORMAT =
            Pattern.compile("(?<targetIndex>\\d+ )"
                    + "((p/(?<phone>[^/]+))?)"
                    + "((e/(?<email>[^/]+))?)"
                    + "((a/(?<address>[^/]+))?)"
                    + "((pos/(?<position>[^/]+))?)");

    // '/' forward slashes are reserved for delimiter prefixes
    public static final Pattern EMPLOYEE_EDIT_DATA_NOARGS_FORMAT =
            Pattern.compile("(?<targetIndex>\\d+)");

    // '/' forward slashes are reserved for delimiter prefixes
    public static final Pattern CLOCK_IN_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)");

    public static final Pattern MEMBER_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)"
                    + "e/(?<email>[^/]+)"
            ); // variable number of tags

    // '/' forward slashes are reserved for delimiter prefixes
    public static final Pattern MENU_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)"
                    + " p/(?<price>[^/]+)"
                    + "type/(?<type>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final Pattern ORDER_DISH_ARGS_FORMAT =
            Pattern.compile("(?<targetIndex>\\d+)\\s+q/(?<quantity>\\d{1,3})");

    public static final Pattern REDEEM_POINTS_ARGS_FORMAT = Pattern.compile("(?<points>[^/]+)");

    public static final String STATSMENU_DATE_ARGS_FORMAT_PATTERN_COMPILE_STRING =
            "(f\\/(?<dateFrom>(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])[12]\\d{3}))?"
            + " ?(t\\/(?<dateTo>(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])[12]\\d{3}))?"; // variable number of tags

    public static final Pattern STATSMENU_DATE_ARGS_FORMAT =
            Pattern.compile(STATSMENU_DATE_ARGS_FORMAT_PATTERN_COMPILE_STRING);

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case EmployeeAddCommand.COMMAND_WORD:
            return prepareEmployeeAdd(arguments);

        case EmployeeDeleteCommand.COMMAND_WORD:
            return prepareEmployeeDelete(arguments);

        case EmployeeEditCommand.COMMAND_WORD:
            return prepareEmployeeEdit(arguments);

        case EmployeeListCommand.COMMAND_WORD:
            return new EmployeeListCommand();

        case EmployeeClockInCommand.COMMAND_WORD:
            return prepareClockIn(arguments);

        case EmployeeClockOutCommand.COMMAND_WORD:
            return prepareClockOut(arguments);

        case MemberListCommand.COMMAND_WORD:
            return new MemberListCommand();

        case MemberAddCommand.COMMAND_WORD:
            return prepareAddMember(arguments);

        case MemberDeleteCommand.COMMAND_WORD:
            return prepareMemberDelete(arguments);

        case MenuAddCommand.COMMAND_WORD:
            return prepareAddMenu(arguments);

        case MenuListCommand.COMMAND_WORD:
            return new MenuListCommand();

        case MenuShowMainMenuCommand.COMMAND_WORD:
            return new MenuShowMainMenuCommand();

        case MenuListByTypeCommand.COMMAND_WORD:
            return prepareMenuListByType(arguments);

        case MenuRecommendationCommand.COMMAND_WORD:
            return new MenuRecommendationCommand();

        case MenuDeleteCommand.COMMAND_WORD:
            return prepareMenuDelete(arguments);

        case MenuFindCommand.COMMAND_WORD:
            return prepareMenuFind(arguments);

        case MenuClearCommand.COMMAND_WORD:
            return new MenuClearCommand();

        case OrderAddCommand.COMMAND_WORD:
            return new OrderAddCommand();

        case OrderDeleteCommand.COMMAND_WORD:
            return prepareOrderDelete(arguments);

        case OrderClearCommand.COMMAND_WORD:
            return new OrderClearCommand();

        case OrderListCommand.COMMAND_WORD:
            return new OrderListCommand();

        case DraftOrderEditCustomerCommand.COMMAND_WORD:
            return prepareDraftOrderEditCustomer(arguments);

        case DraftOrderEditDishCommand.COMMAND_WORD:
            return prepareDraftOrderEditDish(arguments);

        case DraftOrderEditPointsCommand.COMMAND_WORD:
            return prepareDraftOrderEditPoints(arguments);

        case DraftOrderClearCommand.COMMAND_WORD:
            return new DraftOrderClearCommand();

        case DraftOrderConfirmCommand.COMMAND_WORD:
            return new DraftOrderConfirmCommand();

        case StatsEmployeeCommand.COMMAND_WORD:
            return new StatsEmployeeCommand();

        case StatsMemberCommand.COMMAND_WORD:
            return new StatsMemberCommand();

        case StatsMenuCommand.COMMAND_WORD:
            return prepareStatsMenu(arguments);

        case StatsOrderCommand.COMMAND_WORD:
            return new StatsOrderCommand();

        case StatsHelpCommand.COMMAND_WORD:
            return new StatsHelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD: // Fallthrough

        default:
            return new HelpCommand();
        }
    }

    /**
     * Parses arguments in the context of the add member command.
     * @param args full command args string
     * @return the prepared command
     */

    private Command prepareAddMember(String args) {
        final Matcher matcher = MEMBER_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE));
        }
        try {
            return new MemberAddCommand(
                    matcher.group("name"),
                    matcher.group("email")
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the add menu command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddMenu(String args) {
        final Matcher matcher = MENU_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuAddCommand.MESSAGE_USAGE));
        }
        try {
            return new MenuAddCommand(
                    matcher.group("name"),

                    matcher.group("price"),
                    //isPrivatePrefixPresent(matcher.group("isPricePrivate")),
                    matcher.group("type"),

                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the add employee command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEmployeeAdd(String args) {
        final Matcher matcher = EMPLOYEE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeAddCommand.MESSAGE_USAGE));
        }
        try {
            return new EmployeeAddCommand(
                    matcher.group("name"),

                    matcher.group("phone"),
                    matcher.group("email"),

                    matcher.group("address"),
                    matcher.group("position"));

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the clock in command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareClockIn(String args) {
        final Matcher matcher = CLOCK_IN_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeClockInCommand.MESSAGE_USAGE));
        }
        return new EmployeeClockInCommand(matcher.group("name"));
    }

    /**
     * Parses arguments in the context of the clock out command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareClockOut(String args) {
        final Matcher matcher = CLOCK_IN_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeClockOutCommand.MESSAGE_USAGE));
        }
        return new EmployeeClockOutCommand(matcher.group("name"));
    }

    /**
     * Parses arguments in the context of the delete employee command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEmployeeDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new EmployeeDeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the delete member command.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMemberDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new MemberDeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    MemberDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the edit employee command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEmployeeEdit(String args) {
        final Matcher checkForArgs = EMPLOYEE_EDIT_DATA_NOARGS_FORMAT.matcher(args.trim());
        if (checkForArgs.matches()) {
            return new IncorrectCommand(String.format(
                    EmployeeEditCommand.MESSAGE_NOARGS,
                    EmployeeEditCommand.MESSAGE_USAGE));
        }
        final Matcher matcher = EMPLOYEE_EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeEditCommand.MESSAGE_USAGE));
        }
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(matcher.group("targetIndex"));
            return new EmployeeEditCommand(
                    targetIndex,
                    matcher.group("phone"),
                    matcher.group("email"),
                    matcher.group("address"),
                    matcher.group("position")
            );
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    EmployeeEditCommand.MESSAGE_USAGE));
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
     *      * Extracts the new menu's tags from the add command's tag arguments string.
     *      * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete menu item command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMenuDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new MenuDeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the delete order command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareOrderDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new OrderDeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    OrderDeleteCommand.MESSAGE_USAGE));
        }
    }


    /**
     * Parses arguments in the context of the find menu command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMenuFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MenuFindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").toLowerCase().split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new MenuFindCommand(keywordSet);
    }

    /**
     * Parses arguments in the context of the edit draft order customer command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDraftOrderEditCustomer(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DraftOrderEditCustomerCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DraftOrderEditCustomerCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the edit draft order dish command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDraftOrderEditDish(String args) {
        try {
            final Matcher matcher = ORDER_DISH_ARGS_FORMAT.matcher(args.trim());
            // Validate arg string format
            if (!matcher.matches()) {
                throw new ParseException("Could not find index number and quantity to parse");
            }
            final int targetIndex = Integer.parseInt(matcher.group("targetIndex"));
            final int quantity = Integer.parseInt(matcher.group("quantity"));
            return new DraftOrderEditDishCommand(targetIndex, quantity);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DraftOrderEditDishCommand.MESSAGE_INVALID_FORMAT));
        }
    }

    /**
     *  Parses arguments in the context of the edit draft points command.
     */
    private Command prepareDraftOrderEditPoints(String args) {
        try {
            final Matcher matcher = REDEEM_POINTS_ARGS_FORMAT.matcher(args.trim());
            // Validate arg string format
            if (!matcher.matches()) {
                throw new ParseException("Could not find the points to redeem");
            }
            final int points = Integer.parseInt(matcher.group("points"));
            return new DraftOrderEditPointsCommand(points);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DraftOrderEditPointsCommand.MESSAGE_USAGE));
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
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }

    /**
     * Parses arguments in the context of the stats menu command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareStatsMenu(String args) {
        final Matcher matcher = STATSMENU_DATE_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsMenuCommand.MESSAGE_USAGE));
        }
        return new StatsMenuCommand(
                matcher.group("dateFrom"),

                matcher.group("dateTo")
        );
    }

    /**
     * Parses arguments in the context of the list menu by type command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMenuListByType(String args) {
        final Matcher matcher = ITEMWORD_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MenuListByTypeCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        //final String[] keywords = matcher.group("keywords").split("\\s+");
        //final String itemword = matcher.group("itemword");
        return new MenuListByTypeCommand(matcher.group("type"));
    }

}
