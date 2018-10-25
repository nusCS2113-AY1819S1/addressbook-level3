package seedu.addressbook.parser;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.addressbook.commands.employee.*;
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberDeleteCommand;
import seedu.addressbook.commands.member.MemberListCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListCommand;
import seedu.addressbook.commands.menu.MenuViewAllCommand;
import seedu.addressbook.commands.order.DraftOrderClearCommand;
import seedu.addressbook.commands.order.DraftOrderConfirmCommand;
import seedu.addressbook.commands.order.DraftOrderEditCustomerCommand;
import seedu.addressbook.commands.order.DraftOrderEditDishCommand;
import seedu.addressbook.commands.order.OrderAddCommand;
import seedu.addressbook.commands.order.OrderClearCommand;
import seedu.addressbook.commands.order.OrderDeleteCommand;
import seedu.addressbook.commands.order.OrderListCommand;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.ListCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.commands.statistics.StatsEmployeeCommand;
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

    public static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final Pattern EMPLOYEE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "p/(?<phone>[^/]+)"
                    + "e/(?<email>[^/]+)"
                    + "a/(?<address>[^/]+)"
                    + "pos/(?<position>[^/]+)");

    public static final Pattern EMPLOYEE_EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>.+ )"
                    + "((p/(?<phone>[^/]+))?)"
                    + "((e/(?<email>[^/]+))?)"
                    + "((a/(?<address>[^/]+))?)"
                    + "((pos/(?<position>[^/]+))?)");

                  /* working when all fields are in
                  Pattern.compile("(?<targetIndex>.+)"
                           + "p/(?<phone>[^/]+)"
                           + "e/(?<email>[^/]+)"
                           + "a/(?<address>[^/]+)"
                           + "pos/(?<position>[^/]+)");*/

    public static final Pattern MEMBER_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)"); // variable number of tags

    public static final Pattern MENU_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " p/(?<price>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final Pattern ORDER_DISH_ARGS_FORMAT = Pattern.compile("i/(?<targetIndex>.+)\\s+q/(?<quantity>.+)");

    public static final Pattern STATSMENU_DATE_ARGS_FORMAT =
            Pattern.compile("(f\\/(?<dateFrom>(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])[12]\\d{3}))? ?(t\\/(?<dateTo>(0[1-9]|[12]\\d|3[01])(0[1-9]|1[0-2])[12]\\d{3}))?"); // variable number of tags

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
        switch (commandWord) {
            case EmployeeAddCommand.COMMAND_WORD:
                return prepareEmployeeAdd(arguments);

            case EmployeeDeleteCommand.COMMAND_WORD:
                return prepareEmployeeDelete(arguments);

            case EmployeeEditCommand.COMMAND_WORD:
                return prepareEmployeeEdit(arguments);

            case EmployeeListCommand.COMMAND_WORD:
                return new EmployeeListCommand();

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

            case MenuViewAllCommand.COMMAND_WORD:
                return prepareViewAllMenu(arguments);

            case MenuDeleteCommand.COMMAND_WORD:
                return prepareMenuDelete(arguments);

            case MenuFindCommand.COMMAND_WORD:
                return prepareMenuFind(arguments);

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

            case AddCommand.COMMAND_WORD:
                return prepareAdd(arguments);

            case DeleteCommand.COMMAND_WORD:
                return prepareDelete(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return prepareFind(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case ViewCommand.COMMAND_WORD:
                return prepareView(arguments);

            case ViewAllCommand.COMMAND_WORD:
                return prepareViewAll(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD: // Fallthrough
            default:
                return new HelpCommand();
        }
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

                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the add member command.
     * @param args full command args string
     * @return the prepared command
     */

    private Command prepareAddMember(String args){
        final Matcher matcher = MEMBER_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE));
        }
        try {
            return new MemberAddCommand(
                    matcher.group("name")
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
    private Command prepareAddMenu(String args){
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
    private Command prepareEmployeeAdd(String args){
        final Matcher matcher = EMPLOYEE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeAddCommand.MESSAGE_USAGE));
        }
        try {
            return new EmployeeAddCommand(
                    matcher.group("name"),

                    matcher.group("phone"),

                    matcher.group("email"),

                    matcher.group("address"),

                    matcher.group("position")
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
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
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeDeleteCommand.MESSAGE_USAGE));
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
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the edit employee command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEmployeeEdit(String args) {
        final Matcher matcher = EMPLOYEE_EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeEditCommand.MESSAGE_USAGE));
        }
        try{
            final int targetIndex = parseArgsAsDisplayedIndex(matcher.group("targetIndex"));
            return new EmployeeEditCommand(
                    targetIndex,
                    prepareEditArg(matcher.group("phone"), "phone"),
                    prepareEditArg(matcher.group("email"), "email"),
                    prepareEditArg(matcher.group("address"), "address"),
                    prepareEditArg(matcher.group("position"), "position")
            );
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeEditCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
        return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Returns new information to be edited if it is not empty,
     * else returns a placeholder string indicating that there is no new information
     */
    private static String prepareEditArg(String toCheck, String argumentType){
        if (toCheck == null || toCheck.isEmpty()) {
            switch(argumentType) {
                case "phone":
                    toCheck = "00000000";
                    break;
                case "email":
                    toCheck = "noargs@noargs.com";
                    break;
                default:
                    toCheck = "noargs";
                    break;

            }
            return toCheck;
        }
        return toCheck;
    }

    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }

    /**
     *      * Extracts the new person's tags from the add command's tag arguments string.
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

    private Command prepareViewAllMenu(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new MenuViewAllCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MenuViewAllCommand.MESSAGE_USAGE));
        }
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
        final String[] keywords = matcher.group("keywords").split("\\s+");
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
                    DraftOrderEditDishCommand.MESSAGE_USAGE));
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


}