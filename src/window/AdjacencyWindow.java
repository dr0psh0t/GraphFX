package window;
import java.util.Iterator;
import java.util.LinkedList;
import adt.Edge;
import adt.Vertex;
import application.Main;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import model.ArrowStatic;

public class AdjacencyWindow
{
	private Main app;
	
	public AdjacencyWindow(Main app){
		this.app = app;
	}
	
	public void adjacencyList(LinkedList<Vertex> vList)
	{
		Stage stage = new Stage();
		AnchorPane root = new AnchorPane();
		
		root.getChildren().addAll(createText("VERTEX", 55, 20), createText("ADJACENT", 300, 20));
		
		int x = 50, y = 50;
		int xx = 70, yy = 140;
		int xTemp, yTemp, zTemp;
		
		xTemp = x + xx;
		yTemp = yy;
		zTemp = 65;
		
		for(Vertex vertex : vList)
		{
			Line lEdge = new Line(xTemp, zTemp, yTemp, zTemp);			
			root.getChildren().addAll(rectangle(x, y, vertex.getName()),
					lEdge, new ArrowStatic(Color.BLACK, lEdge));
			
			for(Iterator<Edge> lIterator = vertex.getAdjacencies().iterator(); lIterator.hasNext();)
			{
				x += 90;
				xTemp += 90;
				yTemp += 90;
				
				Line line = new Line(xTemp, zTemp, yTemp, zTemp);
				root.getChildren().addAll(rectangle(x + 6, y, lIterator.next().getTarget().getName()),
						line, new ArrowStatic(Color.BLACK, line));
			}
			
			x = 50;
			y += 40;
			
			xTemp = x + xx;
			yTemp = yy;
			zTemp += 40;
		}
		
		Scene scene = new Scene(root, 1000, 650);
		
		stage.setScene(scene);
		stage.setTitle("Adjacency List");
		stage.setResizable(false);
		stage.show();
	}
	
	public void adjacencyMatrix(LinkedList<Vertex> vList)
	{
		Stage stage = new Stage();
		AnchorPane root = new AnchorPane();
		LinkedList<Vertex> copyList = vList;
		
		int x = 50, y = 50;
		int xv = 35, yv = 0;		
		int xh = xv + 50;
		
		for(Iterator<Vertex> lIterator = vList.iterator(); lIterator.hasNext();)
		{
			Vertex vertex = lIterator.next();			
			root.getChildren().add(createText(vertex.getName(), xh, 25));
			xh += 68;
		}
		
		for(Vertex vertex : vList)
		{
			yv = y + 15;
			root.getChildren().add(createText(vertex.getName(), xv, yv));
					
			for(Iterator<Vertex> lIterator = copyList.iterator(); lIterator.hasNext();)
			{
				Vertex vertex2 = lIterator.next();
				if(app.suchAdjacencyExists(vertex.getName(), vertex2.getName()) ||
						app.suchAdjacencyExists(vertex2.getName(), vertex.getName()))
				{
					System.out.print("1 ");
					root.getChildren().add(rectangle(x, y, "1"));
				}
				else
				{
					System.out.print("0 ");
					root.getChildren().add(rectangle(x, y, "0"));
				}
				x += 70;
			}
			
			System.out.println();
			y += 30;
			x = 50;
			yv = y + 15;
		}
		
		Scene scene = new Scene(root, 1000, 650);
		
		stage.setScene(scene);
		stage.setTitle("Adjacency Matrix");
		stage.setResizable(false);
		stage.show();
	}
	
	public static StackPane rectangle(int xPos, int yPos, String label)
	{
		Text text = createText(label, xPos, yPos);
		
		Rectangle rectangle = new Rectangle(70, 30);
		rectangle.setFill(Color.AQUA);
		rectangle.setStroke(Color.BLACK);
		
		StackPane layer = new StackPane(rectangle, text);
		layer.relocate(xPos, yPos);
		return layer;
	}
	
	public static Text createText(String label, int xPos, int yPos)
	{
		Text vertexText = new Text(label);
		vertexText.setFill(Color.BLACK);
		vertexText.relocate(xPos, yPos);
		vertexText.setBoundsType(TextBoundsType.VISUAL);
		vertexText.setStyle("-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 15px;"
        );		
		return vertexText;
	}
}