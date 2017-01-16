package com.tiy.javafx;/**
 * Created by Paul Dennis on 1/16/2017.
 */

import com.tiy.starsys.Point;
import com.tiy.starsys.SpaceTunnel;
import com.tiy.starsys.StarSystem;
import com.tiy.starsys.StarSystemGraph;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MapTestCanvas extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    public static final int SCALE = 30;

    @Override
    public void start(Stage primaryStage) {
        Group rootGroup = new Group();

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setFocusTraversable(true);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
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

        Label label = new Label("Simplicity Itself");
        label.setLayoutX(10);
        label.setLayoutY(10);

        ObservableList<Node> children = rootGroup.getChildren();
        children.add(canvas);
        children.add(label);
        for (Label sysLabel : starSystemLabels) {
            children.add(sysLabel);
        }

        Scene scene = new Scene(rootGroup, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
