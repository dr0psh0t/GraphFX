package tesing.arrows.arrow2nd;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class AnchorSecond extends Circle
{
	private double x, y;
	TestArrow app;
	
	public AnchorSecond(Color color, int x, int y)
	{
		super(x, y, 19);
		
		setRotate(360);
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(4);
		setStrokeType(StrokeType.OUTSIDE);
	}
	
	public AnchorSecond(Color color, DoubleProperty xx, DoubleProperty yy, TestArrow app)
	{
		//		x point		y point		radius
		super(xx.get(), yy.get(), 19);
		
		this.app = app;
		
		setRotate(360);
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(4);
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
                for(ArrowSecond arrow : app.getArrows())
                	arrow.update();
			}
		});
	}
}
