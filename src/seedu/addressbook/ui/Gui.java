package seedu.addressbook.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.addressbook.Main;
import seedu.addressbook.logic.Logic;
//import seedu.addressbook.logic.Logic_RMS;

import java.io.IOException;

/**
 * The GUI of the App
 */
public class Gui {

    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    public static final int INITIAL_WINDOW_WIDTH = 800;
    public static final int INITIAL_WINDOW_HEIGHT = 600;
    private final Logic logic_rms;

    private MainWindow mainWindow_rms;
    private String version;

    public Gui(Logic logic_rms, String version) {
        this.logic_rms = logic_rms;
        this.version = version;
    }

    public void start(Stage stage, Stoppable mainApp) throws IOException {
        mainWindow_rms = createMainWindow(stage, mainApp);
        mainWindow_rms.displayWelcomeMessage(version, logic_rms.getStorageFilePath());
    }

    private MainWindow createMainWindow(Stage stage, Stoppable mainApp) throws IOException{
        FXMLLoader loader = new FXMLLoader();

        /* Note: When calling getResource(), use '/', instead of File.separator or '\\'
         * More info: http://docs.oracle.com/javase/8/docs/technotes/guides/lang/resources.html#res_name_context
         */
        loader.setLocation(Main.class.getResource("ui/mainwindow.fxml"));

        stage.setTitle(version);
        stage.setScene(new Scene(loader.load(), INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT));
        stage.show();
        MainWindow mainWindow_rms = loader.getController();
        mainWindow_rms.setLogic(logic_rms);
        mainWindow_rms.setMainApp(mainApp);
        return mainWindow_rms;
    }

}
