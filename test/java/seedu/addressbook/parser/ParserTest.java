package seedu.addressbook.parser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.member.MemberAddCommand;
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
import seedu.addressbook.commands.order.OrderAddCommand;
import seedu.addressbook.commands.order.OrderClearCommand;
import seedu.addressbook.commands.order.OrderDeleteCommand;
import seedu.addressbook.commands.order.OrderListCommand;
import seedu.addressbook.commands.statistics.StatsEmployeeCommand;
import seedu.addressbook.commands.statistics.StatsHelpCommand;
import seedu.addressbook.commands.statistics.StatsMemberCommand;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.commands.statistics.StatsOrderCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberEmail;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.tag.Tag;

public class ParserTest {

    private Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void emptyInput_returnsIncorrect() {
        final String[] emptyInputs = { "", "  ", "\n  \n" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    @Test
    public void unknownCommandWord_returnsHelp() {
        final String input = "unknowncommandword arguments arguments";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    /**
     * Test 0-argument commands
     */

    @Test
    public void helpCommand_parsedCorrectly() {
        final String input = "help";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    @Test
    public void menuClearCommand_parsedCorrectly() {
        final String input = "clearmenu";
        parseAndAssertCommandType(input, MenuClearCommand.class);
    }

    @Test
    public void menuListCommand_parsedCorrectly() {
        final String input = "listmenu";
        parseAndAssertCommandType(input, MenuListCommand.class);
    }

    @Test
    public void menuRecommendationCommand_parsedCorrectly() {
        final String input = "recommendations";
        parseAndAssertCommandType(input, MenuRecommendationCommand.class);
    }

    @Test
    public void menuShowMainMenuCommand_parsedCorrectly() {
        final String input = "showmainmenu";
        parseAndAssertCommandType(input, MenuShowMainMenuCommand.class);
    }

    @Test
    public void menuListByTypeCommand_noArgs() {
        // no keywords
        final String inputs = "listmenutype";
        final String resultMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MenuListByTypeCommand.MESSAGE_USAGE);

        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuListByTypeCommand_validArgs_parsedCorrectly() {
        final String type = "main";
        //final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));
        final String input = "listmenutype " + type;
        final MenuListByTypeCommand result =
                parseAndAssertCommandType(input, MenuListByTypeCommand.class);
        assertEquals(type, result.getItemword());
    }


    @Test
    public void statsEmployeeCommand_parsedCorrectly() {
        final String input = "statsemp";
        parseAndAssertCommandType(input, StatsEmployeeCommand.class);
    }

    @Test
    public void statsMemberCommand_parsedCorrectly() {
        final String input = "statsmember";
        parseAndAssertCommandType(input, StatsMemberCommand.class);
    }

    @Test
    public void statsMenuCommand_parsedCorrectly() {
        final String input = "statsmenu";
        parseAndAssertCommandType(input, StatsMenuCommand.class);
    }

    @Test
    public void statsOrderCommand_parsedCorrectly() {
        final String input = "statsorder";
        parseAndAssertCommandType(input, StatsOrderCommand.class);
    }

    @Test
    public void statsHelpCommand_parsedCorrectly() {
        final String input = "statistics";
        parseAndAssertCommandType(input, StatsHelpCommand.class);
    }

    @Test
    public void memberListCommand_parsedCorrectly() {
        final String input = "listmember";
        parseAndAssertCommandType(input, MemberListCommand.class);
    }

    @Test
    public void draftOrderClearCommand_parsedCorrectly() {
        final String input = "cleardraft";
        parseAndAssertCommandType(input, DraftOrderClearCommand.class);
    }

    @Test
    public void draftOrderConfirmCommand_parsedCorrectly() {
        final String input = "confirmdraft";
        parseAndAssertCommandType(input, DraftOrderConfirmCommand.class);
    }

    @Test
    public void orderAddCommand_parsedCorrectly() {
        final String input = "addorder";
        parseAndAssertCommandType(input, OrderAddCommand.class);
    }

    @Test
    public void orderClearCommand_parsedCorrectly() {
        final String input = "clearorder";
        parseAndAssertCommandType(input, OrderClearCommand.class);
    }

    @Test
    public void orderListCommand_parsedCorrectly() {
        final String input = "listorder";
        parseAndAssertCommandType(input, OrderListCommand.class);
    }

    @Test
    public void exitCommand_parsedCorrectly() {
        final String input = "exit";
        parseAndAssertCommandType(input, ExitCommand.class);
    }

    /**
     * Test single index argument commands
     */

    @Test
    public void menuDeleteCommand_noArgs() {
        final String[] inputs = { "delmenu", "delmenu " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuDeleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "delmenu notAnumber ", "delmenu 8*wh12", "delmenu 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuDeleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delmenu " + testIndex;
        final MenuDeleteCommand result = parseAndAssertCommandType(input, MenuDeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void draftOrderEditCustomerCommand_noArgs() {
        final String[] inputs = { "draftcustomer", "draftcustomer " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DraftOrderEditCustomerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void draftOrderEditCustomerCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "draftcustomer notAnumber ", "draftcustomer 8*wh12", "draftcustomer 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DraftOrderEditCustomerCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void draftOrderEditCustomerCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "draftcustomer " + testIndex;
        final DraftOrderEditCustomerCommand result = parseAndAssertCommandType(input,
                DraftOrderEditCustomerCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void orderDeleteCommand_noArgs() {
        final String[] inputs = { "deleteorder", "deleteorder " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                OrderDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void orderDeleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "deleteorder notAnumber ", "deleteorder 8*wh12", "deleteorder 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                OrderDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void orderDeleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "deleteorder " + testIndex;
        final OrderDeleteCommand result = parseAndAssertCommandType(input, OrderDeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test find persons by keyword in name command
     */

    @Test
    public void menuFindCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
            "findmenu",
            "findmenu "
        };
        final String resultMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MenuFindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuFindCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "findmenu " + String.join(" ", keySet);
        final MenuFindCommand result =
                parseAndAssertCommandType(input, MenuFindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void menuFindCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "findmenu " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final MenuFindCommand result =
                parseAndAssertCommandType(input, MenuFindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add menu item command
     */

    //Testing for invalid argument cases in add menu command (invalid if the price prefix is not present)

    @Test
    public void menuAddCommand_invalidArgs() {
        final String[] inputs = {
            "addmenu",
            "addmenu ",
            "addmenu wrong args format",
                // no price prefix
                String.format("addmenu $s $s type/$s", MenuName.EXAMPLE, Price.EXAMPLE, Type.EXAMPLE),
                // no type prefix
                String.format("addmenu $s p/$s $s", MenuName.EXAMPLE, Price.EXAMPLE, Type.EXAMPLE)
        };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuAddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuAddCommand_invalidMenuDataInArgs() {
        final String invalidMenuName = "[]\\[;]";
        final String validMenuName = MenuName.EXAMPLE;
        final String invalidPriceArg = "p/not__numbers";
        final String validPriceArg = "p/" + Price.EXAMPLE;
        final String invalidTypeArg = "type/notType";
        final String validTypeArg = "type/" + Type.EXAMPLE;
        final String invalidTagArg = "t/invalid_-[.tag";

        final String addMenuCommandFormatString = "addmenu $s $s $s";

        // test each incorrect person data field argument individually
        final String[] inputs = {
             // invalid menu name
             String.format(addMenuCommandFormatString, invalidMenuName, validPriceArg, validTypeArg),
             // invalid pricee
             String.format(addMenuCommandFormatString, validMenuName, invalidPriceArg, validTypeArg),
             // invalid typa
             String.format(addMenuCommandFormatString, validMenuName, validPriceArg, invalidTypeArg),
             // invalid tag
             String.format(addMenuCommandFormatString, validMenuName, validPriceArg, validTypeArg) + " " + invalidTagArg
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    //Testing for invalid Food Item Data Argument


    //Testing for valid Food Item Data parsed correctly

    @Test
    public void menuAddCommand_validFoodItemData_parsedCorrectly() {
        final Menu testMenu = generateTestMenu();
        final String input = convertMenuToAddCommandString(testMenu);
        final MenuAddCommand result = parseAndAssertCommandType(input, MenuAddCommand.class);
        assertEquals(result.getMenu(), testMenu);
    }

    @Test
    public void menuAddCommand_duplicateTags_merged() throws IllegalValueException {
        final Menu testMenu = generateTestMenu();
        String input = convertMenuToAddCommandString(testMenu);
        for (Tag tag : testMenu.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final MenuAddCommand result = parseAndAssertCommandType(input, MenuAddCommand.class);
        assertEquals(result.getMenu(), testMenu);
    }

    /**
     * Generate a menu item for testing
     */
    private static Menu generateTestMenu() {
        try {
            return new Menu(
                    new MenuName(MenuName.EXAMPLE),
                    new Price(Price.EXAMPLE),
                    new Type(Type.EXAMPLE),
                    new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test menu data should be valid by definition");
        }
    }

    /**
     * Return the command line used to add the given Menu item to the menu list
     */
    private static String convertMenuToAddCommandString(ReadOnlyMenus menu) {
        String addmenuCommand = "addmenu "
                + menu.getName().fullName
                + " p/" + menu.getPrice().value
                + " type/" + menu.getType().value;
        for (Tag tag : menu.getTags()) {
            addmenuCommand += " t/" + tag.tagName;
        }
        return addmenuCommand;
    }

    /**
     * Test add member command
     */

    @Test
    public void memberAddCommand_invalidMemberDataInArgs() {
        final String invalidName = "[]\\[;]";
        final String validName = MemberName.EXAMPLE;

        // address can be any string, so no invalid address
        final String memberAddCommandFormatString = "addmember p/";

        // test each incorrect person data field argument individually
        // add subsequent tests below when more fields are added...
        final String[] inputs = {
                // invalid name
                String.format(memberAddCommandFormatString, invalidName)
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void memberAddCommand_validEmptyMemberData_parsedCorrectly() {
        final Member testMember = generateTestEmptyMember();
        final String input = convertMemberToAddCommandString(testMember);
        final MemberAddCommand result = parseAndAssertCommandType(input, MemberAddCommand.class);
        assertEquals(result.getMember(), testMember);

    }

    @Test
    public void memberAddCommand_validMemberData_parsedCorrectly() {
        final Member testMember = generateTestMember();
        final String input = convertMemberToAddCommandString(testMember);
        final MemberAddCommand result = parseAndAssertCommandType(input, MemberAddCommand.class);
        assertEquals(result.getMember(), testMember);
    }


    /**
     * Generate an empty Member object for testing
     */
    private static Member generateTestEmptyMember() {
        try {
            return new Member();
        } catch (Exception ive) {
            throw new RuntimeException("test empty member data should be valid by definition");
        }
    }

    /**
     * Generate a Member for testing
     */
    private static Member generateTestMember() {
        try {
            return new Member(new MemberName(MemberName.EXAMPLE), new MemberEmail(MemberEmail.EXAMPLE));
        } catch (IllegalValueException ie) {
            throw new RuntimeException("test member data should be valid by definition");
        }
    }

    /**
     * Return the command line used to add the given Member to the member list
     */
    private static String convertMemberToAddCommandString(ReadOnlyMember member) {
        String addCommand = "addmember "
                + member.getName().fullName
                + " e/" + member.getEmail().value;

        return addCommand;
    }

    /**
     * Test statsmenu with arg command
     */

    @Test
    public void statsMenuCommand_validArgs_parsedCorrectly() {

        final String[] inputs = {
            "statsmenu f/01022018",
            "statsmenu t/04112018",
            "statsmenu f/01102017 t/04112018"
        };
        for (String input: inputs) {
            parseAndAssertCommandType(input, StatsMenuCommand.class);
        }
    }

    @Test
    public void statsMenuCommand_invalidArgs() {
        final String[] inputs = {
            // No from prefix
            "statsmenu 0102018",
            // Invalid date
            "statsmenu t/00012018",
            // No to prefix
            "statsmenu f/01102017 /04112018",
            // Duplicate prefix
            "statsmenu f/01102017 f/04112018"
        };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                StatsMenuCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    /**
     * Test draft dish command
     */

    @Test
    public void draftOrderEditDishCommand_invalidArgs() {
        final String[] inputs = {
            "draftdish",
            "draftdish ",
            "draftdish wrong args format",
            // no index
            "draftdish q/15",
            // index is not a single number
            "draftdish a q/15",
            "draftdish * q/15",
            "draftdish 1 2 3 4 q/15",
            // no quantity
            "draftdish 1 q/",
            "draftdish 1 q/ ",
            // quantity is not a single number
            "draftdish 1 q/a",
            "draftdish 1 q/*",
            "draftdish 1 q/1 2 3 4",
            // no quantity prefix
            "draftdish 1",
            "draftdish 1 2"
        };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DraftOrderEditDishCommand.MESSAGE_INVALID_FORMAT);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void draftOrderEditDishCommand_validArgs_parsedCorrectly() {
        final int testIndex = 1;
        final int testQuantity = 15;
        final String input = "draftdish " + testIndex + " q/" + testQuantity;
        final DraftOrderEditDishCommand result = parseAndAssertCommandType(input, DraftOrderEditDishCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
        assertEquals(result.getQuantity(), testQuantity);
    }


    /**
     * Utility methods
     */

    /**
     * Asserts that parsing the given inputs will return IncorrectCommand with the given feedback message.
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) {
        for (String input : inputs) {
            final IncorrectCommand result = parseAndAssertCommandType(input, IncorrectCommand.class);
            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }

    /**
     * Utility method for parsing input and asserting the class/type of the returned command object.
     *
     * @param input to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */
    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }
}
