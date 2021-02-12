package tesing.scrollbar;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.UtilityClass;

public class ScrollBarCircle extends Application
{
	final ScrollBar sc = new ScrollBar();
	final VBox vb = new VBox();
	
	@Override
	public void start(Stage primaryStage)
	{
		Group root = new Group();
		Scene scene = new Scene(root, 640, 360);
		scene.setFill(Color.BLACK);
		primaryStage.setScene(scene);
		
		vb.setLayoutX(10);
		vb.setLayoutY(10);
		vb.setSpacing(10);
		
		Button button = new Button("play");
		button.relocate(200, 10);
		button.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					add();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

		Button button2 = new Button("print");
		button2.relocate(250, 10);
		button2.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event){
				System.out.println(scene.getWidth()+" x "+scene.getHeight());
			}
		});
		
		root.getChildren().addAll(vb, sc, button, button2);
		
		System.out.println(scene.getWidth() - sc.getWidth());
		
		//sc.setLayoutX(scene.getWidth() - sc.getWidth());
		sc.setLayoutX(625);
		sc.setMin(0);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setPrefHeight(360);
		sc.setMax(600);
		
		sc.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val){
				vb.setLayoutY(-new_val.doubleValue());
			}
		});
		
		primaryStage.show();
	}
	
	public void add() throws InterruptedException
	{ 
		new Thread(new Runnable(){
			@Override
			public void run(){
				for(int g = 0; g < 6; ++g)
				{
					try{
						Thread.sleep(500);
					}
					catch(InterruptedException exc){
						exc.printStackTrace();
					}
					
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							Circle cInfo = new Circle(20, Color.WHITE);
							PathTransition pt = new PathTransition(Duration.millis(500),
									createPath(10, UtilityClass.getY(), 10, -10), cInfo);
							pt.play();
							vb.getChildren().add(cInfo);
						}
					});
					//UtilityClass.incrementY(20);
				}
			}
		}).start();
	}
	
	private Path createPath(int xCoord, int yCoord, int from, int to)
	{
		Path path = new Path();
		path.getElements().addAll(new MoveTo(from, to), new LineTo(xCoord, yCoord));
		path.setFill(null);
		path.setStroke(Color.gray(1));
		path.setStrokeWidth(0);		
		return path;
	}

	public static void main(String[] args){launch(args);}
}