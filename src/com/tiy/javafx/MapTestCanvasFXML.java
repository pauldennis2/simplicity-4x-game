package com.tiy.javafx;/**
 * Created by Paul Dennis on 1/16/2017.
 */

import com.tiy.starsys.Point;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;
import com.tiy.starsys.StarSystemGraph;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapTestCanvasFXML extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    FXMLLoader fxmlLoader;

    public static final int SCALE = 30;


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("map-test-canvas.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = (Parent) fxmlLoader.load(location.openStream());
        System.out.println(((TestController)fxmlLoader.getController()).mainCanvas);



        GraphicsContext graphicsContext = null;// = mainCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2);

        StarSystemGraph sysGraph = new StarSystemGraph(new Point(0, 0));

        List<Label> starSystemLabels = new ArrayList<>();

        for (StarSystem starSystem : sysGraph.getStarSystems()) {
            graphicsContext.strokeOval(starSystem.getX() * SCALE, starSystem.getY() * SCALE, 3, 3);
            Label sysLabel = new Label(starSystem.getName());
            sysLabel.setLayoutX(starSystem.getX() * SCALE);
            sysLabel.setLayoutY(starSystem.getY() * SCALE);
            starSystemLabels.add(sysLabel);
            sysLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("You clicked " + sysLabel.getText());
                    System.out.println(sysGraph.getNameStarSystemMap().get(sysLabel.getText()));
                }
            });
        }

        for (SpaceTunnel tunnel : sysGraph.getTunnels()) {
            StarSystem firstSystem = tunnel.getFirstSystem();
            StarSystem secondSystem = tunnel.getSecondSystem();
            graphicsContext.strokeLine(firstSystem.getX() * SCALE, firstSystem.getY() * SCALE,
                    secondSystem.getX() * SCALE, secondSystem.getY() * SCALE);
        }

        Label gameLabel = new Label("Simplicity Itself");
        gameLabel.setLayoutX(10);
        gameLabel.setLayoutY(10);

        for (Label sysLabel : starSystemLabels) {
            System.out.println(sysLabel.getText());
        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }
}
