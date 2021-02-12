package tesing.ballsTest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Balls extends Application{
	
	private double x, y;
	
	@Override
	public void start(Stage stage)
	{
		AnchorPane root = new AnchorPane();
		
		Text weight = new Text("asd");
		weight.relocate(100, 100);
		
		weight.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				x = weight.getX() - mouseEvent.getX();
				y = weight.getY() - mouseEvent.getY();
				
				System.out.println(weight.getX()+"\n"+weight.getY());
			}
		});
		
		weight.setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event)
			{				
				weight.setX(event.getX() - x);
                weight.setY(event.getY() - y);
			}
		});
		
        root.getChildren().add(weight);
		
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
