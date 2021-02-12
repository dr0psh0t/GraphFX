package tesing.arrows.arrow2nd;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class TestArrow extends Application
{
	int startX = 300,
	startY = 300,
	endX = 200,
	endY = 200;
	
	ArrayList<ArrowSecond> arrows = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage)
	{
		AnchorPane root = new AnchorPane();
		
		Line line = new Line(startX, startY, endX, endY);
		line.setStrokeWidth(3);
		line.setStroke(Color.BLACK);
		
		AnchorSecond start = new AnchorSecond(Color.AQUA, line.startXProperty(), line.startYProperty(), this);
		AnchorSecond end = new AnchorSecond(Color.AQUA, line.endXProperty(), line.endYProperty(), this);
		
		Text text0 = new Text("v0");
		text0.setFill(Color.WHITE);
		text0.setBoundsType(TextBoundsType.VISUAL);
		text0.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		text0.xProperty().bind(end.centerXProperty());
		text0.yProperty().bind(end.centerYProperty());
		
		Text text1 = new Text("v1");
		text1.setFill(Color.WHITE);
		text1.setBoundsType(TextBoundsType.VISUAL);
		text1.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		
		text1.xProperty().bind(start.centerXProperty());
		text1.yProperty().bind(start.centerYProperty());
		
        root.getChildren().addAll(start, end, text0, text1);
        
        Scene scene = new Scene(root, 500, 500);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public ArrayList<ArrowSecond> getArrows(){
		return arrows;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}