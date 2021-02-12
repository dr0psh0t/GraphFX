package window;
import java.util.ArrayList;
import java.util.LinkedList;
import adt.Vertex;
import application.Main;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Arrow;
import model.CircleInfo;
import model.LineEdge;
import model.Weight;

// http://stackoverflow.com/questions/24483686/how-to-force-javafx-application-close-request-programmatically

public class TopologicalWindow// extends Application
{
	/* 
	 * never instantiate instance fields to avoid IllegalArgumentException.
	 * let the start() method instatiate to renew fields for every attempt that opens this window
	 */
	private AnchorPane root;
	private Main app;
	private Scene scene;
	private Stage primaryStage;
	
	private ArrayList<CircleInfo> circles;
	private ArrayList<Text> vertexNames;
	private ArrayList<Weight> weight;
	
	// LinkedList collections
	private LinkedList<Arrow> arrows;
	private LinkedList<LineEdge> lines;
	
	public TopologicalWindow(Main app){
		this.app = app;
	}
	
	public void startTPO(LinkedList<Vertex> tpList)
	{
		root = new AnchorPane();
		primaryStage = new Stage();
		scene = new Scene(root, 1270, 670, Color.BLACK);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event)
			{
				System.out.println("Closing!");
				app.closeApp();
			}
		});
		
		root.getChildren().addAll(arrows);
		root.getChildren().addAll(lines);
		root.getChildren().addAll(circles);
		root.getChildren().addAll(vertexNames);
		root.getChildren().addAll(weight);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Topological Order");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		topologicalOrder(tpList);
		//change();
	}
	
	public void change()
	{
		for(Text text : vertexNames)
			text.setFill(Color.BLACK);
	}
	
	public void topologicalOrder(LinkedList<Vertex> tpList)
	{
		System.out.println("topological order");
	}
	
	//@SuppressWarnings("unchecked")
	public void setChildren(LinkedList<Arrow> arrows, LinkedList<LineEdge> lines,
			ArrayList<CircleInfo> circles, ArrayList<Text> vertexNames,
			 ArrayList<Weight> weight){
		this.arrows = new LinkedList<>(arrows);
		this.lines = new LinkedList<>(lines);
		this.circles = new ArrayList<>(circles);
		this.vertexNames = new ArrayList<>(vertexNames);
		this.weight = new ArrayList<>(weight);
	}
}