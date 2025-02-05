package astraea;

import java.io.IOException;

import astraea.command.Command;
import astraea.command.ExitCommand;
import astraea.exception.AstraeaInputException;
import astraea.parser.Parser;
import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.AstraeaUi;
import astraea.ui.GuiController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class of Astraea.
 */
public class Astraea extends Application {
    private final AstraeaUi ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExit = false;
    private GuiController controller;

    /**
     * Constructs a new instance of Astraea.
     */
    public Astraea() {
        this.ui = new AstraeaUi();
        this.storage = new Storage();
        this.taskList = new TaskList();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Astraea.class.getResource("/view/AstraeaGui.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            controller = fxmlLoader.<GuiController>getController();
            controller.setAstraea(this); // inject the Astraea instance
            stage.show();
            controller.printAsAstraea(ui.intro());
            controller.printAsAstraea(storage.load(ui, taskList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // obsolete class with GUI now
    private void run() {
        // ui.intro();
        // storage.load(ui, taskList);
        while (!isExit) {
            try {
                String input = ui.readInput();
                Command command = Parser.parseInput(input);
                if (command instanceof ExitCommand) {
                    isExit = true;
                }
                command.execute(taskList, storage, ui);
            } catch (AstraeaInputException ae) {
                ui.printBoundedMessage(ae.getErrorMessage());
            }
        }
    }

    public static void main(String[] args) {
        // new Astraea().run();
    }

    public String[] getResponse(String input) {
        String[] message;
        try {
            Command command = Parser.parseInput(input);
            if (command instanceof ExitCommand) {
                isExit = true;
            }
            message = command.execute(taskList, storage, ui);
        } catch (AstraeaInputException e) {
            message = e.getErrorMessage();
        }

        if (isExit) {
            this.stop();
        }

        return message;
    }

    @Override
    public void stop() {
        controller.stopInputs();
        // Run sleep in a new thread to avoid blocking JavaFX
        new Thread(() -> {
            try {
                Thread.sleep(5000); // Sleep without locking JavaFX thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
            Platform.runLater(Platform::exit); // Ensure it runs on JavaFX thread
        }).start();
    }

}
