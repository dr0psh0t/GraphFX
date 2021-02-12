package tesing.animation;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RandomColorTest extends Application
{
    int radius = 20;
    int sceneWidth = 300;
    int sceneHeight = 300;
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));

        final ObservableList<Node> ol = root.getChildren();

        //http://stackoverflow.com/questions/13784333/platform-runlater-and-task-javafx
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
            	int g = 0;
                while(g < 5)	// creation of more circles is stop
                {
                    try
                    {
                        // Wait for 2 seconds.
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run()
                        {
                        	System.out.println("ol size:" + ol.size());
                            if (ol.size() == 5)
                            	ol.remove(0);
                            ol.add(createCircle());
                        }
                    });
                    ++g;
                }	// end while loop
            }	// end run()
        }).start();
        
        primaryStage.show();
    }
    
    // Create randomly positioned and colored circle
    private Circle createCircle()
    {
        final Circle circle = new Circle();
        circle.setRadius(radius);

        Random r = new Random();
        int rCol1 = r.nextInt(256);
        int rCol2 = r.nextInt(256);
        int rCol3 = r.nextInt(256);
        int rX = radius+r.nextInt(sceneWidth);
        if (rX>sceneWidth-radius) {
            rX=rX-2*radius;
        }
        int rY = radius+r.nextInt(sceneHeight);
        if (rY>sceneHeight-radius) {
            rY=rY-2*radius;
        }
        circle.setLayoutX(rX);
        circle.setLayoutY(rY);

        circle.setStroke(Color.BLACK);
        circle.setFill(Color.rgb(rCol1,rCol2,rCol3));
        //System.out.println(rCol1+"-"+rCol2+"-"+rCol3+"-"+rX+"-"+rY);
        return circle;
    }
    
    public static void main(String[] args) { launch(args); }
}