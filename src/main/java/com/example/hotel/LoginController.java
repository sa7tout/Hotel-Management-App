package com.example.hotel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController implements Initializable {

    private Connection con;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DbConnection dbc = new DbConnection();
        con = dbc.getConnection();
    }

    @FXML
    private void login() {
        if (isValidated()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        openMainPanel(username.getText());
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Invalid username and password.");
                        username.requestFocus();
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private boolean isValidated() {
        window = loginButton.getScene().getWindow();
        if (isInvalid(username, "Username", 5, 25) || isInvalid(password, "Password", 5, 25)) {
            return false;
        }
        return true;
    }

    private boolean isInvalid(TextField textField, String fieldName, int minLength, int maxLength) {
        String text = textField.getText();
        if (text.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "VAGO Agent Login Error", fieldName + " cannot be blank.");
            textField.requestFocus();
            return true;
        } else if (text.length() < minLength || text.length() > maxLength) {
            showAlert(Alert.AlertType.ERROR, "VAGO Agent Login Error", fieldName + " must be between " + minLength + " and " + maxLength + " characters.");
            textField.requestFocus();
            return true;
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        AlertHelper.showAlert(alertType, window, title, content);
    }

    private void openMainPanel(String username) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanelView.fxml"));
            Parent root = loader.load();

            MainPanelController mainPanelController = loader.getController();
            mainPanelController.initUser(username);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Panel");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
