package tesing.shapes;

import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestAppForCoords extends Application {

    //Scene Graph:
    //ScrollPane - scroll
    // - Group - root
    //   - VBox - vboxEXTRA - with random padding
    //     - HBox - hbox - with random padding
    //       - VBox
    //       - VBox - vbox - with random padding
    //         - Circle - circle
    //       - VBox
    //     - Group - lines
    //       - Line - line

    Line line = new Line();
    Circle circle = new Circle();

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Random rand = new Random();
        int randomNum1 = rand.nextInt((100 - 0) + 1) + 0;
        int randomNum2 = rand.nextInt((100 - 0) + 1) + 0;
        int randomNum3 = rand.nextInt((100 - 0) + 1) + 0;
        System.out.println(randomNum1);
        System.out.println(randomNum2);
        System.out.println(randomNum3);


        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(500, 300);
        Scene scene = new Scene(scroll);

        primaryStage.setScene(scene);

        Group root = new Group();

        HBox hbox = new HBox();

        hbox.setPadding(new Insets(randomNum1));

        VBox vbox = new VBox();
        hbox.getChildren().add(new VBox());

        vbox.setPadding(new Insets(randomNum2));
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(new VBox());

        circle.setRadius(10);
        vbox.getChildren().add(circle);

        Group lines = new Group();
        root.getChildren().add(hbox);
        root.getChildren().add(lines);

        root.autosize();
        root.requestLayout();



        lines.getChildren().add(line);


        VBox vboxEXTRA = new VBox();
        vboxEXTRA.setPadding(new Insets(randomNum3));
        vboxEXTRA.getChildren().add(root);


        scroll.setContent(vboxEXTRA);

        line.setStartX(0);
        line.setStartY(0);

        //This dosen't work prob because we aren't drawing yet
        Point2D p = circle.localToScene(circle.getCenterX(),circle.getCenterY());
        p = line.sceneToLocal(p);
        line.setEndX(p.getX());
        line.setEndY(p.getY());


        primaryStage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                Point2D p = circle.localToScene(circle.getCenterX(),circle.getCenterY());
                p = line.sceneToLocal(p);
                line.setEndX(p.getX());
                line.setEndY(p.getY());
            }
        });

        primaryStage.show();
    }



    public static void main(String[] args) {
        System.out.println("Start JavaFXTestApp");
        launch(args);
        System.out.println("End JavaFXTestApp");
    }   
}