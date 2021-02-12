package tesing.animation;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Sample extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		AnchorPane root = new AnchorPane();
		
		Button btn3 = new Button("Rotate Transition");
		btn3.setOnAction(new EventHandler<ActionEvent>(){			
			@Override
			public void handle(ActionEvent event)
			{
				
			}
		});
		
		Scene scene = new Scene(root, 200, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args){
		launch(args);
	}
}