package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowStatic extends Polygon
{
	private LineEdge line;
	public Line staticLine;
	public Weight startPoint;
	
	public ArrowStatic(LineEdge line, Weight startPoint)
	{
		//super(new double[]{0.0, 10.0, -8.0, -8.0, 8.0, -8.0});
		
		super(new double[]{0.0, 10.0, -8.0, -8.0, 8.0, -8.0});
		
		this.line = line;
		this.startPoint = startPoint;
		initializeStraightEdge();
	}
	
	public ArrowStatic(Color color, LineEdge line)
	{
		super(new double[]{0.0, 6.0, -4.0, -4.0, 4.0, -4.0});
		this.line = line;
		initializeStraightEdge();
	}
	
	public ArrowStatic(Color color, Line line)
	{
		super(new double[]{0.0, 6.0, -4.0, -4.0, 4.0, -4.0});
		staticLine = line;
		initializeCurveEdge();
	}
	
	public void initializeCurveEdge()
	{
		double angle = Math.atan2(staticLine.getEndY() - staticLine.getStartY(),
				staticLine.getEndX() - staticLine.getStartX()) * 180 / 3.14;
		
		setFill(Color.BLACK);
		setRotate(angle - 90);
		setTranslateX(staticLine.getStartX());
		setTranslateY(staticLine.getStartY());
		setTranslateX(staticLine.getEndX());
		setTranslateY(staticLine.getEndY());
	}
	
	public void initializeStraightEdge()
	{
		// ty lord!
		double angle = Math.atan2(line.getEndY() - startPoint.getY(), line.getEndX() - startPoint.getX()) * 180 / 3.14;
		
		setFill(Color.AQUA);
		setRotate(angle - 90);
		setTranslateX(startPoint.getX());
		setTranslateY(startPoint.getY());
		setTranslateX(line.getEndX());
		setTranslateY(line.getEndY());
	}
	
	public void updateStraightEdges(){
		initializeStraightEdge();
	}
}