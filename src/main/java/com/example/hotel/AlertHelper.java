package com.example.hotel;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AlertHelper {

    public static boolean result = false;

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        if (alertType.name().equals(Alert.AlertType.INFORMATION.name())) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message).hideAfter(Duration.seconds(10))
                    .showInformation();
        } else if (alertType.name().equals(Alert.AlertType.ERROR.name())) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message).hideAfter(Duration.seconds(10))
                    .showError();
        }
    }
    public static boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null) == yesButton;
    }

    public static <T> Optional<T> showCustomDialog(String title, Node content, Dialog<T> dialog) {
        DialogPane dialogPane = dialog.getDialogPane();

        // Determine the style of the window
        Window window = dialogPane.getScene().getWindow();
        if (window instanceof Stage) {
            StageStyle stageStyle = ((Stage) window).getStyle();
            dialogPane.getStyleClass().add(stageStyle.name().toLowerCase());
        }

        return dialog.showAndWait();
    }




}
