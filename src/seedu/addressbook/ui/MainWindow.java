package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seedu.addressbook.commands.CommandResult_Menu;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;
import java.util.Optional;

import static seedu.addressbook.common.Messages.*;

/**
 * Main Window of the GUI.
 */
public class MainWindow {

    private Logic logic;
    private Stoppable mainApp;

    public MainWindow(){
    }

    public void setLogic(Logic logic){
        this.logic = logic;
    }

    public void setMainApp(Stoppable mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private TextArea outputConsole;

    @FXML
    private TextField commandInput;


    @FXML
    void onCommand(ActionEvent event) {
        try {
            String userCommandText = commandInput.getText();
            CommandResult result = logic.execute(userCommandText);
            if(isExitCommand(result)){
                exitApp();
                return;
            }
            displayResult(result);
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
    public void clearOutputConsole(){
        outputConsole.clear();
    }

    /** Displays the result of a command execution to the user. */
    public void displayResult(CommandResult result) {
        clearOutputConsole();
        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();
        final Optional<List<? extends ReadOnlyMenus>> resultMenus = result.getRelevantMenus();
        final Optional<List<? extends ReadOnlyOrder>> resultOrders = result.getRelevantOrders();
        if(resultPersons.isPresent()) {
            display(resultPersons.get());
        } else if (resultOrders.isPresent()) {
            displayOrderResult(resultOrders.get());
        } else if(resultMenus.isPresent()) {
            displayMenuResult(resultMenus.get());
        }
        display(result.feedbackToUser);
    }

    public void displayWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        display(MESSAGE_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo);
    }

    public void displayRMSWelcomeMessage(String version, String orderListStorageFilePath) {
        String orderListStorageFileInfo = String.format(MESSAGE_USING_ORDER_LIST_STORAGE_FILE,
                orderListStorageFilePath);
        display(MESSAGE_RMS_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, orderListStorageFileInfo);
    }

    /**
     * Displays the list of persons in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayMenuResult(List<? extends ReadOnlyMenus> menus) {
        display(new Formatter().formatMenu(menus));
    }

    private void display(List<? extends ReadOnlyPerson> persons) {

        display(new Formatter().format(persons));
    }

    /**
     * Displays the list of persons in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    private void displayOrderResult(List<? extends ReadOnlyOrder> orders) {
        display(new Formatter().formatOrderResult(orders));
    }

    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    private void display(String... messages) {
        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
    }

}
