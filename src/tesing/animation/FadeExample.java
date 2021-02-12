package tesing.animation;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FadeExample extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	AnchorPane root = new AnchorPane();
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		playIt();
		
		primaryStage.setScene(new Scene(root, 500, 500));
		primaryStage.show();
	}
	
	public void playIt()
	{
		Rectangle rect = new Rectangle (100, 40, 100, 100);
	     rect.setArcHeight(50);
	     rect.setArcWidth(50);
	     rect.setFill(Color.VIOLET);
	     
	     root.getChildren().add(rect);
	 
	     FadeTransition ft = 
	    		 new FadeTransition(Duration.millis(3000), rect);
	     ft.setFromValue(1.0);
	     ft.setToValue(0);	 
	     ft.play();
	     
	     ft.statusProperty().addListener(new ChangeListener<Status>(){
			@Override
			public void changed(ObservableValue<? extends Status> observable,
					Status oldValue, Status newValue)
			{
				if(newValue == Status.RUNNING)
					System.out.println("Running");
				else {
					System.out.println("not running");
				}
			}
		});
	}
}
