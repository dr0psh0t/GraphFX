package tesing.arrows.arrow2nd;

import java.util.Iterator;
import java.util.LinkedList;

//import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Sample extends Application
{
	LinkedList<Text> texts = new LinkedList<>();
	LinkedList<AnchorSecond> circles = new LinkedList<>();
	
	AnchorPane root = new AnchorPane();
	
	@Override
	public void start(Stage primaryStage)
	{
		AnchorSecond v0 = new AnchorSecond(Color.AQUA, 50, 200);
		AnchorSecond v1 = new AnchorSecond(Color.AQUA, 150, 200);
		AnchorSecond v2 = new AnchorSecond(Color.AQUA, 250, 200);
		
		circles.add(v0); circles.add(v1); circles.add(v2);
		
		texts.add(makeText("v0", 50, 200));
		texts.add(makeText("v1", 150, 200));
		texts.add(makeText("v2", 250, 200));
		
		Button btn= new Button("Rotate Transition");
		btn.relocate(20, 20);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				RotateTransition rTransition = new RotateTransition(Duration.seconds(1));
				rTransition.setByAngle(360);
				
				new Thread(new Runnable(){					
					@Override
					public void run()
					{
						for(Iterator<AnchorSecond> iterator = circles.iterator(); iterator.hasNext();)
						{
							Platform.runLater(new Runnable(){
								@Override
								public void run()
								{
									//System.out.printf("%s is rotated\n", text.getText());
									//new ParallelTransition(text, rTransition).play();
									
									AnchorSecond anchor = iterator.next();
									anchor.setFill(Color.YELLOW);
									anchor.setStroke(Color.YELLOW);
									
								}
							});
							
							try{
								Thread.sleep(2000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
		
		root.getChildren().addAll(btn);
		root.getChildren().addAll(circles);
		root.getChildren().addAll(texts);
		
		primaryStage.setScene(new Scene(root, 500, 500));
		primaryStage.show();
	}
	
	/*
	private void removeAndGet()
	{
		root.getChildren().removeAll(texts);
		root.getChildren().addAll(texts);
	}*/
	
	private Text makeText(String name, int xPos, int yPos)
	{
		Text text = new Text(name);
		
		text.relocate(xPos, yPos);
		text.setFill(Color.BLACK);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		
		return text;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}