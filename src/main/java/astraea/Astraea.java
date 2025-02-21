package astraea;

import java.io.IOException;

import astraea.command.Command;
import astraea.command.ExitCommand;
import astraea.exception.AstraeaInputException;
import astraea.parser.Parser;
import astraea.storage.Storage;
import astraea.task.TaskList;
import astraea.ui.GuiController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class of Astraea.
 */
public class Astraea extends Application {
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExit = false;
    private GuiController controller;
    private boolean hasExited = false;

    /**
     * Constructs a new instance of Astraea.
     */
    public Astraea() {
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
            controller = fxmlLoader.getController();
            controller.setAstraea(this); // inject the Astraea instance
            stage.setTitle("Astraea");
            stage.show();
            controller.printAsAstraea(intro());
            controller.printAsAstraea(storage.load(taskList));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String[] intro() {
        return new String[] {"Astraea here. What do you want?"};
    }

    public String[] getResponse(String input) {
        String[] message;
        try {
            Command command = Parser.parseInput(input);
            if (command instanceof ExitCommand) {
                isExit = true;
            }
            message = command.execute(taskList, storage);
        } catch (AstraeaInputException e) {
            message = e.getErrorMessage();
        }

        if (isExit) {
            // stop user inputs in the text box and button
            controller.stopInputs();
            // Wait for 5 seconds before exiting
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> {
                stop(); // Ensure stop() is called only after the delay
            });
            delay.play();
        }

        return message;
    }

    @Override
    public void stop() {
        if (hasExited) {
            return;
        }
        hasExited = true;

        new Thread(() -> Platform.runLater(Platform::exit)).start();
    }



}
