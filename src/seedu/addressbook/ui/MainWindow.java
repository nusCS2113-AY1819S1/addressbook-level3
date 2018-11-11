package seedu.addressbook.ui;

import static seedu.addressbook.common.Messages.MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_ORDER_LIST_STORAGE_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_WELCOME;

import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.logic.Logic;


/**
 * Main Window of the GUI.
 */
public class MainWindow {

    private Logic logic;
    private Stoppable mainApp;

    @FXML
    private TextArea outputConsole;

    @FXML
    private TextField commandInput;

    public MainWindow(){
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setMainApp(Stoppable mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Handle the text interface command line
     * Exit the program if the exit command is given
     */
    @FXML
    void onCommand(ActionEvent event) {
        try {
            String userCommandText = commandInput.getText();
            CommandResult result = logic.execute(userCommandText);
            if (isExitCommand(result)) {
                exitApp();
                return;
            }
            displayResult(userCommandText, result);
            clearCommandInput();
        } catch (Exception e) {
            display(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void exitApp() throws Exception {
        mainApp.stop();
    }

    /** Returns true of the result given is the result of an exit command */
    private boolean isExitCommand(CommandResult result) {
        return result.feedbackToUser.equals(ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    /** Clears the command input box */
    private void clearCommandInput() {
        commandInput.setText("");
    }

    /** Clears the output display area */
    public void clearOutputConsole() {
        outputConsole.clear();
    }

    /** Displays the result of a command execution to the user. */
    public void displayResult(String input, CommandResult result) {
        clearOutputConsole();
        final Optional<List<? extends ReadOnlyMenus>> resultMenus = result.getRelevantMenus();
        final Optional<List<? extends ReadOnlyOrder>> resultOrders = result.getRelevantOrders();
        final Optional<List<? extends ReadOnlyMember>> resultMembers = result.getRelevantMember();
        final Optional<List<? extends ReadOnlyEmployee>> resultEmployees = result.getRelevantEmployee();
        if (resultOrders.isPresent()) {
            displayOrderResult(resultOrders.get());
        } else if (resultMenus.isPresent()) {
            displayMenuResult(resultMenus.get());
        } else if (resultMembers.isPresent()) {
            displayMemberResult(resultMembers.get());
        } else if (resultEmployees.isPresent()) {
            displayEmployeeResult(resultEmployees.get());
        }
        display(String.format(Messages.MESSAGE_ENTERED_COMMAND_FORMAT, input), result.feedbackToUser);
    }

    /**
     * Display the welcome message with the version information and the storage file path
     */
    public void displayRmsWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_ORDER_LIST_STORAGE_FILE, storageFilePath);
        display(MESSAGE_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo);
    }

    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    private void display(String... messages) {
        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
    }

    /**
     * Displays the list of members in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayMemberResult(List<? extends ReadOnlyMember> members) {
        display(new Formatter().formatMemberResult(members));
    }

    /**
     * Displays the list of orders in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayOrderResult(List<? extends ReadOnlyOrder> orders) {
        display(new Formatter().formatOrderResult(orders));
    }

    /**
     * Displays the menu list in the output display area, formatted as an indexed list.
     */
    private void displayMenuResult(List<? extends ReadOnlyMenus> menus) {
        display(new Formatter().formatMenuResult(menus));
    }

    /**
     * Displays the employee list in the output display area, formatted as an indexed list.
     */
    private void displayEmployeeResult(List<? extends ReadOnlyEmployee> employees) {
        display(new Formatter().formatEmployeeResult(employees));
    }


}
