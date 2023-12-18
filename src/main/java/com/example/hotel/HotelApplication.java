package com.example.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HotelApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HotelApplication.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 440);

        LoginController loginController = fxmlLoader.getController();

        stage.setTitle("VAGO PORTAL");
        stage.getIcons().add(new Image(HotelApplication.class.getResource("/assets/vagoicon.png").toExternalForm()));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
