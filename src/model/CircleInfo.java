package model;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CircleInfo extends Circle
{
	private String text;
	private double dragBaseX;
    private double dragBaseY;
    Main app;
    
    public void setThisColor(Color color)
    {
    	setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
    }
	
    /* 
     * this constructor is used for graphfx thread.
     * any changes in its parameter will be determined later
     */
	public CircleInfo(Color color, String text, Main app,
			double x, double y, double radius)
	{
		super(x, y, radius);
		this.text = text;
		this.app = app;
		
		setCursor(Cursor.HAND);
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(4);
		setStrokeType(StrokeType.OUTSIDE);
		
		initMovable();
	}
	
	public CircleInfo(Color color, String text, double x, double y, double radius)
	{
		super(x, y, radius);
		this.text = text;
		this.app = null;
		
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(4);
		setStrokeType(StrokeType.OUTSIDE);
	}
	
	public CircleInfo(Color color, double radius)
	{
		super(radius);
		this.text = null;
		this.app = null;
		
		setFill(color.deriveColor(1, 1, 1, 0.5));
		setStroke(color);
		setStrokeWidth(4);
		setStrokeType(StrokeType.OUTSIDE);
	}
	
	public void initMovable()
	{		
		setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                dragBaseX = event.getSceneX() - getCenterX();
                dragBaseY = event.getSceneY() - getCenterY();
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                setCenterX(event.getSceneX() - dragBaseX);
                setCenterY(event.getSceneY() - dragBaseY);
                
                // update arrow positions when circles are dragged
                for(Arrow arrow : app.getArrows())
                	arrow.update();
                
                for(Weight weight : app.getWeight())
                	weight.updatePos();
                
                /*
                 * this method call updates the midpoint position of a
                 * text in MidPointTest application running. comment
                 * this call when working on GraphFX thread.
                 */
                //midPointTest.updatePos();
            }
        });
	}
	
	public void update()
	{
		for(Arrow arrow : app.getArrows())
        	arrow.update();
        
        for(Weight weight : app.getWeight())
        	weight.updatePos();
	}
	
	public String getText(){
		return text;
	}
	
	public String toString(){
		//return text+" "+getCenterX()+" "+getCenterY();
		return text;
	}
}