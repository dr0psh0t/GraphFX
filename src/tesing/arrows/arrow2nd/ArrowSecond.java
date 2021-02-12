package tesing.arrows.arrow2nd;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowSecond extends Polygon
{
	private Line line;
	
	public ArrowSecond(double[] points, Line line)
	{
		super(points);
		this.line = line;
		initialize();
	}
	
	private void initialize()
	{
		double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX()) * 180 / 3.14;
		
		setRotate(angle - 90);
		setTranslateX(line.getStartX());
		setTranslateY(line.getStartY());
		setTranslateX(line.getEndX());
		setTranslateY(line.getEndY());
	}
	
	public void update(){
		initialize();
	}
}