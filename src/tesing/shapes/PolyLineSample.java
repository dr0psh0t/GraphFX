package tesing.shapes;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PolyLineSample extends Application {

  @Override
  public void start(Stage stage)
  {
    Group root = new Group();
    Scene scene = new Scene(root, 600, 600);
    stage.setScene(scene);

    Polyline polyline = new Polyline();
    polyline.getPoints().addAll(new Double[]{
    		50.0, 50.0,
    		20.0, 10.0,
    		10.0, 20.0
    		});
    
    Circle circle = new Circle(18, Color.AQUA);
    
    PathTransition pathTransition = new PathTransition(Duration.millis(1000), polyline, circle);
    pathTransition.play();
    
    root.getChildren().addAll(polyline, circle);
    
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}