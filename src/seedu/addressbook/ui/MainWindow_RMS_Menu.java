/*package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.CommandResult_Menu;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.ExitCommand_Menu;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.logic.Logic_RMS;

import java.util.List;
import java.util.Optional;

import static seedu.addressbook.common.Messages.*;

/**
 * Main Window of the GUI.
 */
/*public class MainWindow_RMS_Menu {

    private Logic_RMS logic_rms;
    private Stoppable mainApp;

    public MainWindow_RMS_Menu(){
    }

    public void setLogic(Logic_RMS logic_rms){
        this.logic_rms = logic_rms;
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
            CommandResult_Menu result = logic_rms.execute(userCommandText);
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
   /* private boolean isExitCommand(CommandResult_Menu result) {
        return result.feedbackToUser.equals(ExitCommand_Menu.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    /** Clears the command input box */
    /*private void clearCommandInput() {
        commandInput.setText("");
    }

    /** Clears the output display area */
    /*public void clearOutputConsole(){
        outputConsole.clear();
    }

    /** Displays the result of a command execution to the user. */
    /*public void displayResult(CommandResult_Menu result) {
        clearOutputConsole();
        final Optional<List<? extends ReadOnlyMenus>> resultMenus = result.getRelevantMenus();
        if(resultMenus.isPresent()) {
            display(resultMenus.get());
        }
        display(result.feedbackToUser);
    }

    public void displayWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        display(MESSAGE_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo);
    }

    /**
     * Displays the list of persons in the output display area, formatted as an indexed list.
     * Private contact details are hidden.
     */
    /*private void display(List<? extends ReadOnlyMenus> menus) {
        display(new Formatter_Menu().format(menus));
    }

    /**
     * Displays the given messages on the output display area, after formatting appropriately.
     */
    /*private void display(String... messages) {
        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
    }

}
*/