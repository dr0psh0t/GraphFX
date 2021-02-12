package tesing.shapes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class RectTest extends Application
{
	@Override
	public void start(Stage stage)
	{
		AnchorPane root = new AnchorPane();
		
		for(int x = 50; x < 300; x = x + 100)
			root.getChildren().add(rectangle(x, 50));
		
		stage.setScene(new Scene(root, 500, 200));
		stage.setResizable(false);
		stage.show();
		
	}
	
	public StackPane rectangle(int xPos, int yPos)
	{
		Text text = new Text("fgh"+xPos);
		text.setFill(Color.BLACK);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		
		Rectangle rectangle = new Rectangle(50, 50);
		rectangle.relocate(100, 100);
		rectangle.setFill(Color.AQUA);
		rectangle.setStroke(Color.BLACK);
		
		StackPane layer = new StackPane(rectangle, text);
		layer.relocate(xPos, yPos);
		return layer;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}