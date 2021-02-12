package tesing.arrows.arrow;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Anchor extends Circle
{
	private double x, y;
	CubicCurveManipulatorWithArrows app;
	
	public Anchor(Color color, DoubleProperty xx, DoubleProperty yy, CubicCurveManipulatorWithArrows app)
	{
		super(xx.get(), yy.get(), 10);
		
		this.app = app;
		
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(2);
		setStrokeType(StrokeType.OUTSIDE);
		
		xx.bind(centerXProperty());
		yy.bind(centerYProperty());
		
		setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				x = getCenterX() - mouseEvent.getX();
				y = getCenterY() - mouseEvent.getY();
				
				System.out.println(getCenterX()+"\n"+getCenterY());
			}
		});
		
		setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event)
			{				
				setCenterX(event.getSceneX() - x);
                setCenterY(event.getSceneY() - y);
                
                //	update arrow positions when circles are dragged
                for(Arrow arrow : app.getArrows())
                	arrow.update();
			}
		});
	}
}