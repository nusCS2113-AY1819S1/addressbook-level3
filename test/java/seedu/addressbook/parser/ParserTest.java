package seedu.addressbook.parser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

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
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberListCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuClearCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
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
import seedu.addressbook.commands.statistics.StatsEmployeeCommand;
import seedu.addressbook.commands.statistics.StatsMemberCommand;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.commands.statistics.StatsOrderCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
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
    public void clearCommand_parsedCorrectly() {
        final String input = "clear";
        parseAndAssertCommandType(input, ClearCommand.class);
    }

    @Test
    public void menuClearCommand_parsedCorrectly() {
        final String input = "clearmenu";
        parseAndAssertCommandType(input, MenuClearCommand.class);
    }

    @Test
    public void listCommand_parsedCorrectly() {
        final String input = "list";
        parseAndAssertCommandType(input, ListCommand.class);
    }

    @Test
    public void menuListCommand_parsedCorrectly() {
        final String input = "listmenu";
        parseAndAssertCommandType(input, MenuListCommand.class);
    }

    @Test
    public void menuListByTypeCommand_invalidArgs() {
        // no keywords
        final String inputs = "listmenutype";
        final String resultMessage =
                String.format(MenuListByTypeCommand.MESSAGE_ERROR, MenuListByTypeCommand.MESSAGE_USAGE);
        //parseAndAssertIncorrectWithMessage(resultMessage, inputs);
        parseAndAssertCommandType(inputs, IncorrectCommand.class);
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
    public void memberListCommand_parsedCorrectly() {
        final String input = "listmembers";
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
    public void deleteCommand_noArgs() {
        final String[] inputs = { "delete", "delete " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuDeleteCommand_noArgs() {
        final String[] inputs = { "deletemenu", "deletemenu " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "delete notAnumber ", "delete 8*wh12", "delete 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuDeleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "deletemenu notAnumber ", "deletemenu 8*wh12", "deletemenu 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuDeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delete " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void menuDeleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "deletemenu " + testIndex;
        final MenuDeleteCommand result = parseAndAssertCommandType(input, MenuDeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewCommand_noArgs() {
        final String[] inputs = { "view", "view " };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "view notAnumber ", "view 8*wh12", "view 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 2;
        final String input = "view " + testIndex;
        final ViewCommand result = parseAndAssertCommandType(input, ViewCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewAllCommand_noArgs() {
        final String[] inputs = { "viewall", "viewall " };
        final String resultMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuViewAllCommand_noArgs() {
        final String[] inputs = { "viewallmenu", "viewallmenu " };
        final String resultMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MenuViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewall notAnumber ", "viewall 8*wh12", "viewall 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void menuViewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewallmenu notAnumber ", "viewallmenu 8*wh12", "viewallmenu 1 2 3 4 5" };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewall " + testIndex;
        final ViewAllCommand result = parseAndAssertCommandType(input, ViewAllCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void menuViewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewallmenu " + testIndex;
        final MenuViewAllCommand result = parseAndAssertCommandType(input, MenuViewAllCommand.class);
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
    public void findCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
            "find",
            "find "
        };
        final String resultMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

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
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "find " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
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
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "find " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
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
     * Test add person command
     */

    @Test
    public void addCommand_invalidArgs() {
        final String[] inputs = {
            "add",
            "add ",
            "add wrong args format",
                // no phone prefix
                String.format("add $s $s e/$s a/$s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
                // no email prefix
                String.format("add $s p/$s $s a/$s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
                // no address prefix
                String.format("add $s p/$s e/$s $s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE)
        };
        final String resultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }


    @Test
    public void addCommand_invalidPersonDataInArgs() {
        final String invalidName = "[]\\[;]";
        final String validName = Name.EXAMPLE;
        final String invalidPhoneArg = "p/not__numbers";
        final String validPhoneArg = "p/" + Phone.EXAMPLE;
        final String invalidEmailArg = "e/notAnEmail123";
        final String validEmailArg = "e/" + Email.EXAMPLE;
        final String invalidTagArg = "t/invalid_-[.tag";

        // address can be any string, so no invalid address
        final String addCommandFormatString = "add $s $s $s a/" + Address.EXAMPLE;

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validPhoneArg, validEmailArg),
                // invalid phone
                String.format(addCommandFormatString, validName, invalidPhoneArg, validEmailArg),
                // invalid email
                String.format(addCommandFormatString, validName, validPhoneArg, invalidEmailArg),
                // invalid tag
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg) + " " + invalidTagArg
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addCommand_validPersonData_parsedCorrectly() {
        final Person testPerson = generateTestPerson();
        final String input = convertPersonToAddCommandString(testPerson);
        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPerson(), testPerson);
    }


    @Test
    public void addCommand_duplicateTags_merged() throws IllegalValueException {
        final Person testPerson = generateTestPerson();
        String input = convertPersonToAddCommandString(testPerson);
        for (Tag tag : testPerson.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPerson(), testPerson);
    }

    /**
     * Generate a Person for testing
     */
    private static Person generateTestPerson() {
        try {
            return new Person(
                    new Name(Name.EXAMPLE),
                    new Phone(Phone.EXAMPLE, true),
                    new Email(Email.EXAMPLE, false),
                    new Address(Address.EXAMPLE, true),
                    new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test person data should be valid by definition");
        }
    }

    /**
     * Return the command line used to add the given Person to the address book
     */
    private static String convertPersonToAddCommandString(ReadOnlyPerson person) {
        String addCommand = "add "
                + person.getName().fullName
                + (person.getPhone().isPrivate() ? " pp/" : " p/") + person.getPhone().value
                + (person.getEmail().isPrivate() ? " pe/" : " e/") + person.getEmail().value
                + (person.getAddress().isPrivate() ? " pa/" : " a/") + person.getAddress().value;
        for (Tag tag : person.getTags()) {
            addCommand += " t/" + tag.tagName;
        }
        return addCommand;
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
        final String validPriceArg = "p/" + Phone.EXAMPLE;
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
            return new Member(new MemberName(MemberName.EXAMPLE));
        } catch (IllegalValueException ie) {
            throw new RuntimeException("test member data should be valid by definition");
        }
    }

    /**
     * Return the command line used to add the given Member to the member list
     */
    private static String convertMemberToAddCommandString(ReadOnlyMember member) {
        String addCommand = "addmember "
                + member.getName().fullName;

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
                DraftOrderEditDishCommand.MESSAGE_USAGE);
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
