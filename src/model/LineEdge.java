package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class LineEdge extends CubicCurve
{
	private CircleInfo start;
	private CircleInfo end;
	private Arrow targetArrow;
	private Weight edgeWeight;
	
	public LineEdge(CircleInfo start, CircleInfo end, Weight edgeWeight)
	{
		this.start = start;
		this.end = end;
		this.edgeWeight = edgeWeight;
		
		startXProperty().bind(start.centerXProperty());
		startYProperty().bind(start.centerYProperty());
		endXProperty().bind(end.centerXProperty());
		endYProperty().bind(end.centerYProperty());
		
		controlX1Property().bind(edgeWeight.xProperty());
		controlY1Property().bind(edgeWeight.yProperty());
		controlX2Property().bind(edgeWeight.xProperty());
		controlY2Property().bind(edgeWeight.yProperty());
		
		setStrokeWidth(3.8);
		setStroke(Color.AQUA);
		setFill(null);
	}
	
	public LineEdge(CircleInfo start, CircleInfo end)
	{
		this.start = start;
		this.end = end;
		this.targetArrow = null;
		this.edgeWeight = null;
		
		startXProperty().bind(start.centerXProperty());
		startYProperty().bind(start.centerYProperty());
		endXProperty().bind(end.centerXProperty());
		endYProperty().bind(end.centerYProperty());
		
		setStrokeWidth(3.8);
		setStroke(Color.AQUA);
		setFill(null);
	}
	
	public LineEdge(CircleInfo start)
	{
		this.start = start;
		this.end = null;
		this.targetArrow = null;
		this.edgeWeight = null;
		
		startXProperty().bind(start.centerXProperty());
		startYProperty().bind(start.centerYProperty());
		setEndX(450);
		setEndY(450);
		
		setStrokeWidth(3.8);
		setStroke(Color.AQUA);
		setFill(null);
	}
	
	public LineEdge(int startX, int startY, int endX, int endY)
	{
		setStartX(startX);
		setStartY(startY);
		setEndX(endX);
		setEndY(endY);
	}

	// set color of this line edge
	public void setStroke(Color Value){
		super.setStroke(Value);
	}

	// mutators
	public void setEdgeWeight(Weight edgeWeight){
		this.edgeWeight = edgeWeight;
	}

	public void setStart(CircleInfo start){
		this.start = start;
	}

	public void setEnd(CircleInfo end){
		this.end = end;
	}

	public void setTargetArrow(Arrow targetArrow){
		this.targetArrow = targetArrow;
	}

	// accessors
	public Weight getWeight(){
		return edgeWeight;
	}

	public CircleInfo getStart(){
		return start;
	}

	public CircleInfo getEnd(){
		return end;
	}

	public Arrow getTargetArrow(){
		return targetArrow;
	}

	@Override
	public String toString(){
		return start.getText()+" "+end.getText();
	}
}