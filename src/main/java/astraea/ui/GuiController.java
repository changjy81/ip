package astraea.ui;

import astraea.Astraea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class GuiController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Astraea astraea;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image astraeaImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Astraea instance */
    public void setAstraea(Astraea a) {
        astraea = a;
    }

    /**
     * Prints the given messages as Astraea to the GUI.
     */
    public void printAsAstraea(String[] startMessage) {
        dialogContainer.getChildren().addAll(
                DialogBox.getAstraeaDialog(startMessage, astraeaImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Astraea's reply and then appends them
     * to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String[] response = astraea.getResponse(input);
        assert response != null;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(new String[]{input}, userImage),
                DialogBox.getAstraeaDialog(response, astraeaImage)
        );
        userInput.clear();
    }

    /**
     * Prevent the user from continuing to send input during application shutdown.
     */
    public void stopInputs() {
        userInput.setEditable(false);
        userInput.setDisable(true);
        sendButton.setDisable(true);
    }
}
