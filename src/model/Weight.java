package model;

import application.Main;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Weight extends Text
{
	private double xPoint, yPoint;
	private LineEdge lineEdge;
	private Main app;	
	
	public Weight(String label, Main app)
	{
		super(label);
		lineEdge = null;
		this.app = app;
		initialize();
	}
	
	public Weight(String label, LineEdge lineEdge, Main app)
	{
		super(label);
		this.lineEdge = lineEdge;
		this.app = app;
		initialize();
	}
	
	public void setPointProperty(DoubleProperty x1, DoubleProperty y1,
			DoubleProperty x2, DoubleProperty y2, LineEdge LineEdge){
		
		this.lineEdge = LineEdge;
		
		x1.bind(xProperty());
		y1.bind(yProperty());
		x2.bind(xProperty());
		y2.bind(yProperty());
		
		updatePos();
	}
	
	public void initialize()
	{
		setCursor(Cursor.CLOSED_HAND);
		setFill(Color.WHITE);
		setBoundsType(TextBoundsType.VISUAL);
		setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		
		setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				xPoint = getX() - mouseEvent.getX();
				yPoint = getY() - mouseEvent.getY();
			}
		});
		
		setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event)
			{
				setX(event.getX() - xPoint);
				setY(event.getY() - yPoint);
				
				// update arrow positions when circles are dragged
                for(Arrow arrow : app.getArrows())
                	arrow.update();
			}
		});
	}
	
	public void setLine(LineEdge lineEdge){
		this.lineEdge = lineEdge;
	}
	
	public LineEdge getLine(){
		return lineEdge;
	}
	
	public void updatePos()
	{
		setX(getMidpointX(lineEdge));
		setY(getMidpointY(lineEdge));
	}
	
	private double getMidpointX(LineEdge lineEdge){
		return (lineEdge.getStartX() + lineEdge.getEndX()) / 2;
	}
	
	private double getMidpointY(LineEdge lineEdge){
		return (lineEdge.getStartY() + lineEdge.getEndY()) / 2;
	}
}