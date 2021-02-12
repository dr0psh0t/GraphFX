package tesing.arrows.arrow;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/*
 * For a number line with the coordinates a and b as endpoints:
 * midpoint = (a + b) / 2
 * 
 * For the coordinate plane:
 * xFinal = (x1 + x2) / 2
 * yFinal = (y1 + y2) / 2
 * 
 * midpoint = (xFinal, yFinal)
 */

public class CubicCurveManipulatorWithArrows extends Application
{
	ArrayList<Arrow> arrows = new ArrayList<>();
	
	@Override
	public void start(Stage stage)
	{
		QuadCurve curve = createStartingCurve();

	    Anchor start = new Anchor(Color.PALEGREEN, curve.startXProperty(), curve.startYProperty(), this);
	    Anchor end = new Anchor(Color.TOMATO, curve.endXProperty(), curve.endYProperty(), this);	    
	    Anchor control1 = new Anchor(Color.GOLD, curve.controlXProperty(), curve.controlYProperty(), this);

	    Group group = new Group();
	    group.getChildren().addAll(curve, start, end, control1);
	    
	    double[] arrowShape = new double[]{0.0, 6.0, -4.0, -4.0, 4.0, -4.0};
	    arrows.add(new Arrow(curve, 0f, arrowShape));
	    arrows.add(new Arrow(curve, 1f, arrowShape));
	    
	    group.getChildren().addAll(arrows);
	    
	    stage.setTitle("Cubic Curve Manipulation Sample");
	    stage.setScene(new Scene(group, 400, 400, Color.ALICEBLUE));
	    stage.show();
	}
	
	public ArrayList<Arrow> getArrows(){
		return arrows;
	}
	
	private QuadCurve createStartingCurve()
	{
	    QuadCurve curve = new QuadCurve();
	    curve.setStartX(100);
	    curve.setStartY(100);
	    
	    curve.setControlX(250);
	    curve.setControlY(150);
	    
	    curve.setEndX(300);
	    curve.setEndY(100);
	    
	    curve.setStroke(Color.FORESTGREEN);
	    curve.setStrokeWidth(4);
	    curve.setStrokeLineCap(StrokeLineCap.ROUND);
	    //curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
	    curve.setFill(Color.ALICEBLUE);
	    return curve;
	}
	
	public static void main(String[] args){
		launch(args);
	}
}