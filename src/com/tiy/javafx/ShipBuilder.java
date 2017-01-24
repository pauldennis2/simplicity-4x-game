package com.tiy.javafx;/**
 * Created by Paul Dennis on 1/17/2017.
 */

import com.tiy.cli.Player;
import com.tiy.starsys.StarSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ShipBuilder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    FXMLLoader fxmlLoader;
    Player player;

    public ShipBuilder () {
        player = new Player (new StarSystem("Beetlejuice"), "Tester");
    }

    public ShipBuilder (Player player) {
        this.player = player;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("ship-builder.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(location.openStream());

        primaryStage.setTitle("Ship Builder");
        primaryStage.setScene(new Scene(root, 900, 450));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
