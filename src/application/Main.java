package application;
	
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import adt.Edge;
import adt.GraphAlgo;
import adt.Vertex;
import controller.AboutController;
import controller.CreateController;
import controller.LoginController;
import controller.SourceAndDeleteController;
import controller.Register;
import controller.TargetVertex;
import controller.Window;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.Arrow;
import model.CircleInfo;
import model.LineEdge;
import model.MinimumEdge;
import model.Weight;
import utility.UtilityClass;
import window.AdjacencyWindow;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/*
 * main application
 * class: Main
 * description: The entry point of the application
 * author: Daryll David E. Dagondon
 * group: Log Horizon
 * 
 */
public class Main extends Application
{
	// javafx classes
	private Stage thisStage;
	private AnchorPane root;
	private Scene scene;
	private Window myWindow;
	//private TopologicalWindow topologicalWindow = new TopologicalWindow(this);
	
	// classes
	private GraphAlgo algo = new GraphAlgo();
	private Random rand = new Random();
	private AdjacencyWindow adjacencyWindow = new AdjacencyWindow(this);
	
	// ArrayList collections
	private ArrayList<CircleInfo> circles = new ArrayList<>(30);
	private ArrayList<Text> vertexNames = new ArrayList<>(30);
	private ArrayList<Weight> weights = new ArrayList<>(30);
	
	// LinkedList collections
	private LinkedList<Arrow> arrows = new LinkedList<>();
	private LinkedList<LineEdge> lines = new LinkedList<>();
	private LinkedList<Vertex> vList = new LinkedList<>();
	
	// the counter for updating the input of vertices
	private int counter = 0;
	
	/* 
	 * the index of the vertex which is specified as the starting point for
	 * any traversal. default colored index is 0 which is the first vertex created in a graph.
	 */
	private int indexColored = 0;
	
	// determines whether the graph created is directed graph or undirected graph. 
	private boolean digraph;
	
	// determines wheter the source vertex is explicitly specified by the user 
	private boolean sourceSelected;
	
	// determines whether the degree of all vertices are displayed such as indegree, outdegree or all degree
	private boolean degreeDisplayed;
	
	/* 
	 * Provides support for standard platform file dialogs. 
	 * These dialogs have look and feel of the platform UI components which is independent of JavaFX.
	 */
	public FileChooser fileChooser = new FileChooser();
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			thisStage = primaryStage;
			sourceSelected = false;
			degreeDisplayed = false;
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/controller/Window.fxml"));
			root = (AnchorPane) loader.load();
			
			// Gets the extension filters used in the displayed file dialog.
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GraphFX files", "*.gpx"));

			myWindow = (Window) loader.getController();
			
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			// open key object
			final KeyCombination openKey = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
			
			// save key object
			final KeyCombination saveKey = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
			
			// add ctrl+o and ctrl+s KeyEvent to scene
			scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event)
				{
					if(openKey.match(event))
						loadGraph(fileChooser);
					else if(saveKey.match(event))
						saveGraph(fileChooser);
				}
			});
			
			myWindow.setApp(this, scene);
			
			AnchorPane.setTopAnchor(myWindow.getGraphTypeLabel(), 10.0);
			AnchorPane.setRightAnchor(myWindow.getGraphTypeLabel(), 20.0);
			AnchorPane.setBottomAnchor(myWindow.getStatusLabel(), 10.0);
			AnchorPane.setLeftAnchor(myWindow.getStatusLabel(), 20.0);
			AnchorPane.setBottomAnchor(myWindow.getRestoreButton(), 10.0);
			AnchorPane.setRightAnchor(myWindow.getRestoreButton(), 10.0);
			AnchorPane.setRightAnchor(myWindow.getTableView(), 10.0);
			
			thisStage.setTitle("GraphFX");
			thisStage.setScene(scene);
			
			thisStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent e)
				{
					e.consume();
					closeApp();
				}
			});
			
			thisStage.show();
			
			AnchorPane.setBottomAnchor(myWindow.getRepositionButton(), 10.0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * gives the created circle a path for its animation
	 * 
	 * @param xCoord - end x point of a path
	 * @param yCoord - end y point of a path
	 * @param from - start x point of a path
	 * @param to - start y point of a path
	 * 
	 * @return Path object
	 */
	private Path createPath(int xCoord, int yCoord, int from, int to)
	{
		Path path = new Path();
		
		path.getElements().addAll(new MoveTo(from, to), new LineTo(xCoord, yCoord));
		path.setFill(null);
		path.setStroke(Color.gray(1));
		path.setStrokeWidth(0);
		
		return path;
	}
	
	/*
	 * @param
	 */
	public void createVertex(String vertexName, String sourceName, int weight, int xPoint, int yPoint)
	{		
		CircleInfo vertex;
		PathTransition pt;
		
		if(xPoint > 0 && yPoint > 0)
		{
			/*
			 * this if block is always executed when loading any saved graph to graphfx application
			 * using the parameters xPoint and yPoint coordinated generated from .gpx graphfx files 
			 */
			
			vertex = createVertex(vertexName, xPoint, yPoint, Color.AQUA, true);
			pt = new PathTransition(Duration.millis(500), createPath(xPoint, yPoint, 600, 50), vertex);
		}
		else
		{			
			// this else block will be executed when creating a new graph using CreateController window
			
			int xCoord = rand.nextInt((1279 - 50) + 1) + 50;
			int	yCoord = (int) (Math.random() * (631 - 78) + 78);
			
			vertex = createVertex(vertexName, xCoord, yCoord, Color.AQUA, true);
			pt = new PathTransition(Duration.millis(500), createPath(xCoord, yCoord, 600, 50), vertex);
		}
		
		pt.play();
		
		Vertex newVertex = new Vertex(vertexName);	// the new vertex to be inserted to the graph
		
		newVertex.setIndexKey(counter);
		newVertex.setCircleInfo(vertex);
		
		circles.add(vertex);

		if(!sourceName.isEmpty())
		{
			// if the source vertex name is not an empty string
			
			boolean found = false;			
			Edge edge = null;
			
			if(!vList.isEmpty())
			{
				for(Vertex v : vList)
				{
					if(v.getName().equals(sourceName))
					{
						newVertex.incrementInDegree();
						v.incrementOutDegree();
						
						if(!digraph)	// if it is undirected graph
							newVertex.addAdjacency(new Edge(v, weight));
						
						edge = new Edge(newVertex, weight);
						v.addAdjacency(edge);
						
						vList.add(newVertex);
						break;
					}
				}
			}
			else
			{
				// graph is empty. add the first vertex. first-time happens. 				
				vList.add(newVertex);
			}
			
			for(CircleInfo circleInfo : circles)
			{
				if(circleInfo.getText().equals(sourceName))
				{
					// source vertex is found
					
					Weight weightLabel = new Weight(weight+"", this);					
					LineEdge lineEdge = new LineEdge(circleInfo, vertex);
					
					weightLabel.setPointProperty(lineEdge.controlX1Property(), lineEdge.controlY1Property(),
							lineEdge.controlX2Property(), lineEdge.controlY2Property(), lineEdge);
					
					if(digraph)
					{
						// if the user selects directed graph, add an arrow
						
						Arrow thisArrow = new Arrow(lineEdge, 0.96f, weightLabel);
						
						arrows.add(thisArrow);
						
						lineEdge.setTargetArrow(thisArrow);
						lineEdge.setEdgeWeight(weightLabel);
						
						root.getChildren().addAll(circles.get(counter), lineEdge, thisArrow, weightLabel);
					}
					else
					{
						// if the user selects undirected graph
						
						lineEdge.setEdgeWeight(weightLabel);
						root.getChildren().addAll(circles.get(counter), lineEdge, weightLabel);
					}
					
					lines.add(lineEdge);
					weights.add(weightLabel);
					found = true;
					break;
				}
			}
			
			if(!found)
			{
				/*
				 * source vertex does not exist even when the source name is input by the user.
				 * add the vertex without any connections
				 */
				
				root.getChildren().addAll(circles.get(counter));
				vList.add(newVertex);
			}
		}
		else
		{
			// source vertex is not specified. add the vertex without any connections
			
			root.getChildren().addAll(circles.get(counter));
			vList.add(newVertex);
		}
		
		removeAndGetBack();
		root.getChildren().remove(path);
		counter++;
	}
	
	private CircleInfo createVertex(String thisText, double x, double y,
			Color color, boolean isCreated){		
		
		//	create Text object
		
		Text text = new Text(thisText);
		text.setFill(Color.WHITE);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px;"
        );
		
		//	create Circle object
		CircleInfo circle = (isCreated ? new CircleInfo(color, thisText, this, x, y, 17) :
			new CircleInfo(color, thisText, x, y, 17));
		
		text.xProperty().bind(circle.centerXProperty());
		text.yProperty().bind(circle.centerYProperty());
		
		if(isCreated)
			vertexNames.add(text);
		return circle;
	}
	
	/*
	 * connect two vertices in a graph, if both exists.
	 * 
	 * @param source the source vertex specfied by user by vertex name
	 * @param target the target vertex specfied by user by vertex name
	 * @param weight the weight or distance from source to target vertex
	 */
	public void connectVertices(String target, String source, int weight)
	{
		CircleInfo vTarget = getCircleInfo(target),
				vSource = getCircleInfo(source);
		
		Vertex vTargetV = getVertex(target);
		Vertex vSourceV = getVertex(source);
		
		if(!suchAdjacencyExists(source, target))
		{
			if((vTargetV != null) && (vSourceV != null))
			{
				vSourceV.addAdjacency(new Edge(vTargetV, weight));
				vSourceV.incrementOutDegree();
				vTargetV.incrementInDegree();
				
				if(digraph)
				{				
					Weight weight2 = new Weight(weight+"", this);					
					LineEdge lineEdge = new LineEdge(vSource, vTarget, weight2);
					
					//Arrow thisArrow = new Arrow(lineEdge, 0.9f, weight2);
					//Arrow thisArrow = new Arrow(lineEdge, 0.98f, weight2);
					Arrow thisArrow = new Arrow(lineEdge, 0.96f, weight2);
					
					weight2.setPointProperty(lineEdge.controlX1Property(), lineEdge.controlY1Property(),
							lineEdge.controlX2Property(), lineEdge.controlY2Property(), lineEdge);
					
					lineEdge.setTargetArrow(thisArrow);
					
					weights.add(weight2);
					lines.add(lineEdge);
					arrows.add(thisArrow);
					
					thisArrow.update();
					
					root.getChildren().addAll(lineEdge, thisArrow, weight2);
				}
				else	// if it is unidirected graph
				{
					Weight weightLabel = new Weight(weight+"", this);
					LineEdge line = new LineEdge(vSource, vTarget, weightLabel);
					
					weightLabel.setPointProperty(line.controlX1Property(), line.controlY1Property(),
							line.controlX2Property(), line.controlY2Property(), line);
					
					vTargetV.addAdjacency(new Edge(vSourceV, weight));
					line.setEdgeWeight(weightLabel);
					
					lines.add(line);
					weights.add(weightLabel);
					
					root.getChildren().addAll(line, weightLabel);
				}
			}
			removeAndGetBack();
		}
		else
		{
			vSource = vTarget = null;
			vSourceV = vTargetV = null;
		}
	}
	
	// return true if adjacencies exist between source and target specified
	public boolean suchAdjacencyExists(String source, String target)
	{
		Vertex vTargetV = getVertex(target);
		for(Vertex v : vList)
		{
			if(v.getName().equals(source))
			{
				for(Edge edge : v.getAdjacencies())
				{
					if(edge.getTarget().equal(vTargetV))
						return true;
				}
			}
		}
		return false;
	}
	
	private CircleInfo getCircleInfo(String name)
	{
		Iterator<CircleInfo> iterator = circles.iterator();		
		while(iterator.hasNext())
		{
			CircleInfo returnCircle = iterator.next();
			if(returnCircle.getText().equals(name))
				return returnCircle;
		}
		return null;
	}
	
	public Vertex getVertex(String name)
	{
		for(Vertex vertex : vList)
		{
			if(vertex.getName().equals(name))
				return vertex;
		}
		return null;
	}
	
	public Text geText(String name)
	{
		for(Iterator<Text> lIterator = vertexNames.iterator(); lIterator.hasNext();)
		{
			Text text = lIterator.next();
			if(text.getText().equals(name))
				return text;
		}
		return null;
	}
	
	public void printVertices()
	{
		for(Vertex vertex : vList)
		{
			System.out.print(vertex+": ");
			
			for(Edge edge : vertex.getAdjacencies())
			{
				System.out.print(edge.getTarget()+" ");
			}
			
			System.out.println();
		}
		
		/*System.out.print("CircleInfo: ");
		for(CircleInfo circle : circles)
			System.out.print(circle+" ");
		System.out.println();
		
		System.out.print("Vertex ADT: ");
		for(Iterator<Vertex> lIterator = vList.iterator(); lIterator.hasNext();)
			System.out.print(lIterator.next()+" ");
		System.out.println();
		
		System.out.print("Text: ");
		Iterator<Text> lIterator = vertexNames.iterator();
		while(lIterator.hasNext())
			System.out.print(lIterator.next().getText()+" ");
		System.out.println();
		
		retrieveNodes();*/
	}
	
	public boolean suchNameExist(String name)
	{
		for(CircleInfo circle : circles)
		{
			if(circle.getText().equals(name))
				return true;
		}
		return false;
	}
	
	public void destroyGraph()
	{
		clearColoredPath();
		
		root.getChildren().removeAll(circles);
		root.getChildren().removeAll(lines);
		root.getChildren().removeAll(arrows);
		root.getChildren().removeAll(vertexNames);
		root.getChildren().removeAll(weights);
		root.getChildren().removeAll(vList);

		weights.clear();
		circles.clear();
		lines.clear();
		arrows.clear();
		vertexNames.clear();
		vList.clear();
		
		counter = 0;
		digraph = false;
	}
	
	/*
	 * sets the color of the vertex selected.
	 * @param vertex the name of the vertex
	 */
	public void setColor(String vertex)
	{
		clear();
		int g = 0;
		sourceSelected = false;
		
		for(Iterator<CircleInfo> iterator = circles.iterator(); iterator.hasNext();)
		{
			CircleInfo circleInfo = iterator.next();
			String text = circleInfo.getText();
			
			if(text.equals(vertex))
			{
				circleInfo.setThisColor(Color.YELLOWGREEN);
				indexColored = g;
				sourceSelected = true;
				break;
			}
			++g;
		}
	}	
	
	public void shortestPathColor(String target)
	{
		clear();
		clearColoredPath();
		
		Vertex from = vList.get(indexColored);
		Vertex to = getVertex(target);
		
		if(sourceSelected)
			algo.shortestPath(from);
		else
			algo.shortestPath(vList.getFirst());		
		
		List<Vertex> sp = algo.getShortestPathTo(to);
		LineEdge lastEdge = getLastEdge(sp, target);
		
		/*if(lastEdge == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Trying to get the last edge was null\nor target vertex is the first vertex.\nDebug the algorithm.");
    		alert.showAndWait();
		}
		else
			System.out.println("Last edge:\nStart: "+lastEdge.getStart()+"\nEnd: "+lastEdge.getEnd());*/
		
		double distance = 0;
		for(Vertex vertex : sp)
			distance += vertex.getDistance();
		
		if(distance == Double.POSITIVE_INFINITY)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText("There is no path from "+from+" to "+to);
    		alert.showAndWait();
		}
		else
		{
			myWindow.spRunning();
			
			Thread spThread = new Thread(new SPThread(sp, lastEdge));
			spThread.start();
		}
	}
	
	public LineEdge getLastEdge(List<Vertex> sp, String target)
	{
		if(sp.get(0).getName().equals(target))
			return null;
		else
		{
			CircleInfo lastCircle = getCircleInfo(sp.get(sp.size() - 1).getName());
			CircleInfo lastSecondCircle = getCircleInfo(sp.get(sp.size() - 2).getName());
			
			for(LineEdge lineEdge : lines)
			{
				if(lineEdge.getStart().equals(lastSecondCircle) && lineEdge.getEnd().equals(lastCircle))
					return lineEdge;
			}
			return null;
		}
	}
	
	ArrayList<CircleInfo> path = new ArrayList<>(30);
	LinkedList<LineEdge> coloredEdges = new LinkedList<>();
	
	private void clearColoredPath()
	{
		path.clear();
		coloredEdges.clear();
	}

	private void clear()
	{
		for(Vertex vertex2 : vList)
		{
			vertex2.setDistance(Double.POSITIVE_INFINITY);
			vertex2.setPrevious(null);
		}
		setAllDefaultColors();
	}
	
	private void setAllDefaultColors()
	{
		for(CircleInfo cInfo : circles)
			cInfo.setThisColor(Color.AQUA);
		
		for(Iterator<LineEdge> lIterator = lines.iterator(); lIterator.hasNext();)
			lIterator.next().setStroke(Color.AQUA);
	}
	
	//	this method makes sure that vertex names must be always on top of the AnchorPane
	private void removeAndGetBack()
	{
		root.getChildren().removeAll(vertexNames);
		root.getChildren().removeAll(lines);
		
		root.getChildren().addAll(lines);
		root.getChildren().addAll(vertexNames);
	}
	
	public void dft()
	{
		setAllDefaultColors();
		LinkedList<Vertex> dftList;
		
		if(!sourceSelected)
			dftList =  algo.depthFirstTraversal(vList);
		else
		{
			LinkedList<Vertex> order = new LinkedList<>(vList);
			Vertex sourceV = order.get(indexColored);
			
			order.remove(sourceV);
			order.addFirst(sourceV);
			
			dftList = algo.depthFirstTraversal(order);
		}
		
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				for(Iterator<Vertex> lIterator = dftList.iterator(); lIterator.hasNext();)
				{
					Vertex vertex = lIterator.next();
					sleep(500);
					
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							myWindow.addNode(vertex.getName());
							vertex.getCircleInfo().setThisColor(Color.YELLOWGREEN);
						}
					});
				}
				
				Platform.runLater(new Runnable(){
					@Override
					public void run(){
						myWindow.idle();
					}
				});
			}
		}).start();
	}
	
	public void depthFirstTraversal()
	{
		setAllDefaultColors();
		
		Thread dftThread = new Thread(new DFTThread());
		dftThread.start();
	}
	
	public void depthFirstRec(Vertex vertex, LinkedList<Vertex> visited)
	{
		visited.add(vertex);
		runLaterSearch(vertex, Color.YELLOWGREEN, true);
		sleep(500);
		
		for(Edge edge : vertex.getAdjacencies())
		{
			Vertex node = edge.getTarget();
			
			if(!visited.contains(node))
				depthFirstRec(node, visited);
			else
			{
				sleep(500);
				runLaterSearch(node, Color.RED, false);
				
				sleep(500);
				runLaterSearch(node, Color.YELLOWGREEN, false);
			}
		}
	}
	
	public void breadthFirstTraversal()
	{
		setAllDefaultColors();
		
		Thread bftThread = new Thread(new BFTThread());
		bftThread.start();
	}
	
	private void sleep(int val)
	{
		try{
			Thread.sleep(val);
		}
		catch(InterruptedException exc){
			exc.printStackTrace();
		}
	}
	
	private void runLaterSearch(Vertex vertex, Color color, boolean visited)
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run()
			{
				vertex.getCircleInfo().setThisColor(color);
				
				if(visited)
					myWindow.addNode(vertex.getName());
			}
		});
	}
	
	public void bft()
	{
		LinkedList<Vertex> bftList;
		
		if(!sourceSelected)
			bftList =  algo.breadthFirstTraversal(vList);
		else
		{
			System.out.println("Source is selected");
			
			LinkedList<Vertex> order = new LinkedList<>(vList);
			Vertex sourceV = order.get(indexColored);
			
			order.remove(sourceV);
			order.addFirst(sourceV);
			
			System.out.println("order: "+order);
			
			bftList = algo.breadthFirstTraversal(order);
		}
		
		double[] points = new double[bftList.size() * 2];
		
		int x = -1;
		for(Vertex vertex : bftList)
		{
			CircleInfo cInfo = getCircleInfo(vertex.getName());
			points[++x] = cInfo.getCenterX();
			points[++x] = cInfo.getCenterY();
		}
		
		CircleInfo cInfo = new CircleInfo(Color.TOMATO, 17);
		Polyline polyline = new Polyline(points);
		
		polyline.setFill(null);
		polyline.setStrokeWidth(0);
		
		root.getChildren().addAll(polyline, cInfo);
		
		PathTransition pathTransition =	new PathTransition(Duration.millis(1000 * bftList.size()), polyline, cInfo);
	    pathTransition.play();
	    pathTransition.statusProperty().addListener(new ChangeListener<Status>(){
			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue)
			{
				if(newValue != Status.RUNNING)
				{
					root.getChildren().removeAll(polyline, cInfo);
					
					new Thread(new Runnable(){						
						@Override
						public void run()
						{
							for(Vertex vertex : bftList)
							{
								sleep(500);								
								myWindow.addNode(vertex.getName());
							}
						}
					}).start();
				}
			}
		});
	}
	
	public int getNumber(){
		return circles.size();
	}
	
	public boolean isEmptyGraph(){
		return circles.isEmpty();
	}
	
	public boolean isDigraph(){
		return digraph;
	}
	
	// set this graph to directed or undirected
	public void setDigraph(boolean digraph){
		this.digraph = digraph;
	}
	
	// display the adjacency list of this graph
	public void adjList(){
		adjacencyWindow.adjacencyList(vList);
	}
	
	// display the matrix of this graph
	public void adjMatrix(){
		adjacencyWindow.adjacencyMatrix(vList);
	}
	
	public void printDegrees()
	{
		System.out.println("In degree:");
		for(Iterator<Vertex> lIterator = vList.iterator(); lIterator.hasNext();)
		{
			Vertex vertex = lIterator.next();
			System.out.println(vertex+": "+vertex.getInDegree());
		}
		
		System.out.println("Out degree:");
		for(Iterator<Vertex> lIterator = vList.iterator(); lIterator.hasNext();)
		{
			Vertex vertex = lIterator.next();
			System.out.println(vertex+": "+vertex.getOutDegree());
		}
		
		System.out.println("Total Degree");
		for(Iterator<Vertex> lIterator = vList.iterator(); lIterator.hasNext();)
		{
			Vertex vertex = lIterator.next();
			System.out.println(vertex+": "+vertex.getTotalDegree());
		}
	}
	
	// display the indegrees of a vertex
	public void inDegree()
	{
		restoreNames();
		
		for(int x = 0, limit = vertexNames.size(); x < limit; ++x)
		{
			Text name = vertexNames.get(x);
			name.setText(vList.get(x).getInDegree()+"");
		}
		
		degreeDisplayed = true;
	}
	
	// display the outdegrees of a vertex
	public void outDegree()
	{
		restoreNames();
		
		for(int x = 0, limit = vertexNames.size(); x < limit; ++x)
		{
			Text name = vertexNames.get(x);
			name.setText(vList.get(x).getOutDegree()+"");
		}
		
		degreeDisplayed = true;
	}
	
	// display the total degrees of a vertex
	public void totalDegree()
	{
		restoreNames();
		
		for(int x = 0, limit = vertexNames.size(); x < limit; ++x)
		{
			Text name = vertexNames.get(x);
			name.setText(vList.get(x).getTotalDegree()+"");
		}
		
		degreeDisplayed = true;
	}
	
	// restore vertex names in a scene after displaying their degrees
	public void restoreNames()
	{
		clear();
		clearColoredPath();
		
		if(degreeDisplayed)
		{
			for(int x = 0, limit = vList.size(); x < limit; ++x)
			{
				Text name = vertexNames.get(x);
				name.setText(vList.get(x).getName());
			}
			
			degreeDisplayed = false;
		}
	}
	
	// get the weight matrix of a graph
	private int[][] getWeightMatrix()
	{
		updateIndexKeys();
		int size = vList.size();
		int[][] weightMatrix = new int[size][size];
		
		for(int g = 0; g < size; ++g)
		{
			for(int h = 0; h < size; ++h)
				weightMatrix[g][h] = getWeight(vList.get(g), vList.get(h));
		}
		return weightMatrix;
	}
	
	// get weight between 2 vertices
	private int getWeight(Vertex from, Vertex to)
	{
		for(Vertex vertex : vList)
		{
			if(vertex.equal(from))
			{
				for(Edge edge : vertex.getAdjacencies())
				{
					if(edge.getTarget().equal(to))
						return edge.getWeight();
				}
			}
		}
		return 0;
	}
	
	// display the minimum spanning tree of an undirected graph
	public void minimumSpanningTree()
	{		
		setAllDefaultColors();
		
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				for(MinimumEdge minimumEdge : algo.minimumSpanningTree(getWeightMatrix()))
				{
					Vertex from = getVertexByKey(minimumEdge.getFrom());
					Vertex to = getVertexByKey(minimumEdge.getTo());
					
					sleep(1000);
					
					myWindow.addNode(from+" --- "+to);
					
					Platform.runLater(new Runnable(){	
						@Override
						public void run()
						{							
							for(Iterator<LineEdge> lIterator = lines.iterator(); lIterator.hasNext();)
							{
								LineEdge lEdge = lIterator.next();
								CircleInfo start = lEdge.getStart();
								CircleInfo end = lEdge.getEnd();
								
								if(from.getName().equals(start.getText()) && to.getName().equals(end.getText()) ||
									from.getName().equals(end.getText()) && to.getName().equals(start.getText()))
								{
									
									lEdge.setStroke(Color.YELLOWGREEN);
									start.setThisColor(Color.YELLOWGREEN);
									end.setThisColor(Color.YELLOWGREEN);
									
									break;
								}
							}
						}
					});
				}
				
				Platform.runLater(new Runnable(){	
					@Override
					public void run(){
						myWindow.idle();
					}
				});
			}
		}).start();
	}
	
	// displays the topological order of vertices
	public void topologicalOrder(int status)
	{
		setAllDefaultColors();
		LinkedList<Vertex> tpList;
		
		/* 
		 * check to see if the graph has cycle by checking if the size returned
		 * is equal to the size of the vlist list.
		 */
		if(algo.getBreadthFirstTopologicalSort(vList).size() == vList.size())
		{			
			if(status == 1)
				tpList = new LinkedList<>(algo.getDepthFirstTopologicalSort(vList));
			else
				tpList = algo.getBreadthFirstTopologicalSort(vList);
			
			System.out.println(tpList);
			
			new Thread(new Runnable(){
				@Override
				public void run()
				{			
					for(Vertex vertex : tpList)
					{
						Platform.runLater(new Runnable(){	
							@Override
							public void run()
							{
								myWindow.addNode(vertex.getName());
								tpSortHelper(vertex);
							}
						});
						
						sleep(2000);
					}
					
					Platform.runLater(new Runnable(){	
						@Override
						public void run()
						{
							retrieveNodes();
							myWindow.idle();
						}
					});
				}
			}).start();
		}
		else	// graph has cycle/s. therefore, topological order is not possible.
		{
			myWindow.idle();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Topological Order is not possible\nfor Cyclic Graphs");
			alert.showAndWait();
		}
	}
	
	public void restoreNodes()
	{
		root.getChildren().addAll(arrows);
		root.getChildren().addAll(lines);
		root.getChildren().addAll(circles);
		root.getChildren().addAll(vertexNames);
		root.getChildren().addAll(weights);
	}
	
	/*
	 *  set the source vertex status
	 *  
	 *  @param status boolean value to set.
	 */
	public void setSourceStatus(boolean status){
		sourceSelected = status;
	}
	
	// decrement degrees when invoking deleteVertex
	private boolean removeDegrees(Vertex vertex)
	{
		if(vertex != null)
		{
			// remove indegrees first
			for(Edge edge : vertex.getAdjacencies())
				edge.getTarget().decrementInDegree();
			
			// remove outdegrees pointed to vertex
			for(Iterator<Vertex> vIterator = vList.iterator(); vIterator.hasNext();)
			{
				Vertex v = vIterator.next();			
				for(Edge edge : v.getAdjacencies())
				{
					Vertex v2 = edge.getTarget();
					if(v2.equal(vertex))
						v.decrementOutDegree();
				}
			}
			return true;
		}
		else
			return false;
	}
	
	// method to delete a vertex
	public void tpSortHelper(Vertex vertex)
	{
		CircleInfo cInfo = getCircleInfo(vertex.getName());
		Text text = geText(vertex.getName());

		if(cInfo != null && vertex != null && text != null)
		{
			List<LineEdge> list = new LinkedList<>();
			List<Arrow> list2 = new LinkedList<>();
			List<Weight> list3 = new ArrayList<>();

			for(LineEdge lineEdge : lines)
			{
				if(lineEdge.getStart().equals(cInfo) || lineEdge.getEnd().equals(cInfo))
				{
					list.add(lineEdge);
					list2.add(lineEdge.getTargetArrow());
					list3.add(lineEdge.getWeight());
				}
			}
			
			deleteTransition(cInfo, text, list, list2, list3, false);
			clearColoredPath();
		}
	}
	
	// get the vertex using the parameter keyIndex
	private Vertex getVertexByKey(int keyIndex)
	{
		for(Vertex vertex : vList)
		{
			if(vertex.getIndexKey() == keyIndex)
				return vertex;
		}
		return null;
	}
	
	// this method is invoked after deleting a vertex to update its indexKeys
	private void updateIndexKeys()
	{
		for(int f = 0, max = vList.size(); f < max; ++f)
			vList.get(f).setIndexKey(f);
	}
	
	// method to delete a vertex
	public boolean deleteVertex(String name)
	{
		CircleInfo cInfo = getCircleInfo(name);
		Vertex vertex = getVertex(name);
		Text text = geText(name);
		
		if(cInfo != null && vertex != null && text != null && removeDegrees(vertex))
		{
			List<LineEdge> list = new LinkedList<>();	// LineEdges to remove
			List<Arrow> list2 = new LinkedList<>();	// arrow temp list to remove
			List<Weight> list3 = new ArrayList<>();
			
			vList.remove(vertex);
			vertex.clearAdjacencies();	// remove all adjacencies before removing this vertex
			
			for(LineEdge lineEdge : lines)
			{
				if(lineEdge.getStart().equals(cInfo) || lineEdge.getEnd().equals(cInfo))
				{
					list.add(lineEdge);
					list2.add(lineEdge.getTargetArrow());
					list3.add(lineEdge.getWeight());
				}
			}
			
			// delete adjacencies
			for(Vertex vertex2 : vList)
			{
				for(Iterator<Edge> lIterator = vertex2.getAdjacencies().iterator(); lIterator.hasNext();)
				{
					Edge edge = lIterator.next();
					Vertex target = edge.getTarget();
					
					if(target.equal(vertex))
					{
						vertex2.getAdjacencies().remove(edge);
						break;	// break the inner for loop to avoid ConcurrentModificationException
					}
				}
			}
			
			vertex = null;
			updateIndexKeys();	// updates indexKeys of all vertices.
			indexColored = 0;	// set the source to the first vertex
			deleteTransition(cInfo, text, list, list2, list3, true);
			clearColoredPath();
			clear();
			--counter;
			
			return true;
		}
		else
			return false;
	}
	
	private void deleteTransition(CircleInfo cInfo, Text text, List<LineEdge> list,
			List<Arrow> list2, List<Weight> list3, boolean bool){
		
		for(LineEdge node : list)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.play();
		}
		
		for(Arrow node : list2)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.play();
		}
		
		for(Weight node : list3)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(1);
			fadeTransition.setToValue(0);
			fadeTransition.play();
		}
		
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), text);
		fadeTransition2.setFromValue(1);
		fadeTransition2.setToValue(0);
		fadeTransition2.play();
		
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), cInfo);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.play();
		fadeTransition.statusProperty().addListener(new ChangeListener<Status>(){
			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue)
			{
				if(newValue != Status.RUNNING)
				{
					if(bool)	// the method is used to delete vertex
					{
						circles.remove(cInfo);
						vertexNames.remove(text);
						lines.removeAll(list);
						arrows.removeAll(list2);
						weights.removeAll(list3);
					}
					
					for(LineEdge node : list)
						node.setVisible(false);
					
					if(digraph)
					{
						for(Arrow node : list2)
							node.setVisible(false);
					}
					
					for(Weight node : list3)
						node.setVisible(false);
				}
			}
		});
	}
	
	public void retrieveNodes()
	{
		for(LineEdge node : lines)
			node.setVisible(true);
		
		for(Arrow node : arrows)
			node.setVisible(true);
		
		for(Weight node : weights)
			node.setVisible(true);
		
		for(LineEdge node : lines)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
		
		for(Arrow node : arrows)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
		
		for(Weight node : weights)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
		
		for(Text node : vertexNames)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
		
		for(CircleInfo node : circles)
		{
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
			fadeTransition.setNode(node);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			fadeTransition.play();
		}
	}
	
	public void updatePos(Weight weight)
	{
		LineEdge lEdge = weight.getLine();
		weight.setX(getMidpointX(lEdge));
		weight.setY(getMidpointY(lEdge));
	}
	
	private double getMidpointX(LineEdge lineEdge){
		return (lineEdge.getStartX() + lineEdge.getEndX()) / 2;
	}
	
	private double getMidpointY(LineEdge lineEdge){
		return (lineEdge.getStartY() + lineEdge.getEndY()) / 2;
	}
	
	public boolean sourceStatus(){
		return sourceSelected;
	}
	
	public void closeApp()
	{
		Optional<ButtonType> result = UtilityClass.consume();
		if(result.get() == ButtonType.OK)
			thisStage.close();
	}
	
	public double getHalfWidth(){
		return scene.getWidth() / 2;
	}
	
	public double getHalfHeight(){
		return scene.getHeight() / 2;
	}
	
	
	
	// sets position of all circles to mid
	public void setPosToMid()
	{
		PathTransition pTransition;
		int xPos = 0;
		
		for(CircleInfo cInfo : circles)
		{
			pTransition = new PathTransition(Duration.millis(700), createPath(xPos,
					(int) getHalfHeight(), (int) cInfo.getCenterX(), (int) cInfo.getCenterY()), cInfo);
			
			pTransition.play();
			
			xPos += 30;
			cInfo.setCenterX(xPos);
			cInfo.setCenterY(getHalfHeight());
			
			cInfo.update();
		}
	}
	
	public ArrayList<Weight> getWeight(){return weights;}
	
	public LinkedList<Arrow> getArrows(){return arrows;}
	
	public LinkedList<LineEdge> getEdges(){return lines;}
	
	public ArrayList<CircleInfo> getCircles(){return circles;}
	
	public void hide()
	{
		for(Text text : vertexNames)
			text.setVisible(false);
		
		for(Weight weight : weights)
			weight.setVisible(false);
	}
	
	public void show()
	{
		for(Text text : vertexNames)
			text.setVisible(true);
		
		for(Weight weight : weights)
			weight.setVisible(true);
	}
	
	// save graphfx file
	public void saveGraph(FileChooser fileChooser)
	{
		File file = fileChooser.showSaveDialog(thisStage);
		if(file != null)
		{
			try
			{
				PrintWriter outFile = new PrintWriter(file);
				outFile.println((digraph) ? "digraph" : "undirectedgraph");
				
				for(CircleInfo cInfo : circles)
					outFile.println(cInfo+" "+cInfo.getCenterX()+" "+cInfo.getCenterY());
				
				outFile.print("done");
				
				for(Vertex vertex : vList)
				{
					for(Edge edge : vertex.getAdjacencies())
					{
						double weight = edge.getWeight();
						Vertex target = edge.getTarget();
						
						outFile.println();
						outFile.print(vertex+" "+target+" "+weight);
					}
				}			
				outFile.close();
			}
			catch(IOException e1){
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
			}
		}
	}
	
	public void loadGraph(FileChooser fileChooser)
	{
		try
		{
			File file = fileChooser.showOpenDialog(thisStage);
			Scanner inFile = new Scanner(file);
			
			String graphType = inFile.nextLine();
			String adjacentVertex;
			String vertex;
			
			double weight;
			double xPoint;
			double yPoint;
			
			destroyGraph();
			digraph = (graphType.equals("digraph")) ? true : false;
			
			if(digraph)
				myWindow.digraph();
			else
				myWindow.undirectedGraph();
			
			while(inFile.hasNextLine())
			{
				vertex = inFile.next();
				
				if(vertex.equals("done"))
					break;
				
				xPoint = inFile.nextDouble();
				yPoint = inFile.nextDouble();
				
				createVertex(vertex, "", 0, (int) xPoint, (int) yPoint);
			}
			
			while(inFile.hasNextLine())
			{
				vertex = inFile.next();	// vertex scanned
				adjacentVertex = inFile.next();	// adjacent vertex to add
				weight = inFile.nextDouble();	// weight of the adjacency
				
				// an extra empty line can throw a NoSuchElementException
				connectVertices(adjacentVertex, vertex, (int)weight);
			}
			
			for(Arrow arrow : arrows)
            	arrow.update();
			
			inFile.close();
			myWindow.visible();
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
	    	
			alert.setTitle("GraphFX");
	    	alert.setHeaderText("Load GraphFX File");
	    	alert.setContentText("Unable to correctly load graphfx file.");
	    	alert.showAndWait();
	    	
	    	destroyGraph();
		}
	}
	
	public void openWindow(int choice)
	{
		String url;
		
		switch(choice)
		{
		case 1: url = "/controller/Create.fxml";
			break;
		case 2: url = "/controller/TargetVertex.fxml";
			break;
		case 3: url = "/controller/About.fxml";
			break;
		case 4: url = "/controller/SourceAndDelete.fxml";
			break;
		case 5: url = "/controller/Login.fxml";
			break;
		case 6: url = "/controller/Form.fxml";
			break;
		default: url = "";
			break;
		}
		
		try
		{
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(false);
			stage.initOwner(thisStage);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(url));
			AnchorPane page = (AnchorPane) loader.load();
			
			Scene scene = new Scene(page);
			stage.setScene(scene);
			
			if(choice == 1)
			{
				CreateController components = loader.getController();
				components.setApp(stage, this);
			}
			else if(choice == 2)
			{
				TargetVertex svController = loader.getController();
				svController.setApp(this);
			}
			else if(choice == 3)
			{
				AboutController aController = loader.getController();
				aController.setApp(this);
			}
			else if(choice == 4)
			{
				SourceAndDeleteController aController = loader.getController();
				aController.setApp(this);
			}
			else if(choice == 5)
			{
				LoginController loginController = loader.getController();
				loginController.setApp(this);
				stage.setTitle("GraphFX Login");
			}
			else if(choice == 6)
			{
				Register sRegister = loader.getController();
				sRegister.setApp(this);
				stage.setTitle("Register Student");
			}
			
			stage.show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public class BFTThread implements Runnable
	{
		LinkedList<Vertex> queue;
		LinkedList<Vertex> visited;
		LinkedList<Vertex> bftList;
		
		public BFTThread()
		{
			queue = new LinkedList<>();
			visited = new LinkedList<>();
			bftList = new LinkedList<>(vList);
			
			if(sourceSelected)
			{			
				Vertex sourceV = bftList.get(indexColored);
				
				bftList.remove(sourceV);
				bftList.addFirst(sourceV);
			}
		}
		
		@Override
		public void run()
		{
			Vertex u;
			
			for(Vertex vertex : bftList)
			{
				sleep(500);
				
				if(!visited.contains(vertex))
				{
					queue.add(vertex);
					visited.add(vertex);
					
					runLaterSearch(vertex, Color.YELLOWGREEN, true);						
					sleep(500);
					
					while (!queue.isEmpty())
					{
						u = queue.pollFirst();
						
						for(Edge edge : u.getAdjacencies())
						{
							Vertex node = edge.getTarget();
							
							if(!visited.contains(node))
							{
								queue.add(node);
								visited.add(node);
								
								runLaterSearch(node, Color.YELLOWGREEN, true);
								sleep(500);
							}
							else
							{
								sleep(500);
								runLaterSearch(node, Color.RED, false);
								
								sleep(500);
								runLaterSearch(node, Color.YELLOWGREEN, false);
							}
						}
					}
				}
				else
				{
					runLaterSearch(vertex, Color.RED, false);						
					sleep(500);
					runLaterSearch(vertex, Color.YELLOWGREEN, false);
				}
			}
			
			System.out.println(visited);
			
			Platform.runLater(new Runnable(){
				@Override
				public void run(){
					myWindow.idle();
				}
			});
		}
	}
	
	public class DFTThread implements Runnable
	{
		LinkedList<Vertex> visited;
		LinkedList<Vertex> dftList;;
		
		public DFTThread()
		{
			visited = new LinkedList<>();
			dftList = new LinkedList<>(vList);
			
			if(sourceSelected)
			{			
				Vertex sourceV = dftList.get(indexColored);
				
				dftList.remove(sourceV);
				dftList.addFirst(sourceV);
			}	
		}
		
		@Override
		public void run()
		{
			for(Vertex vertex : dftList)
			{
				sleep(500);
				
				if(!visited.contains(vertex))
					depthFirstRec(vertex, visited);
				else
				{
					runLaterSearch(vertex, Color.RED, false);
					
					sleep(500);
					runLaterSearch(vertex, Color.YELLOWGREEN, false);
				}
			}
			
			System.out.println(visited);
			
			Platform.runLater(new Runnable(){
				@Override
				public void run(){
					myWindow.idle();
				}
			});
		}
	}
	
	public class SPThread implements Runnable
	{
		List<Vertex> sp;
		LineEdge lastEdge;
		
		public SPThread(List<Vertex> sp, LineEdge lastEdge)
		{
			this.sp = sp;
			this.lastEdge = lastEdge;
		}
		
		@Override
		public void run()
		{
			for(Iterator<Vertex> sPList = sp.iterator(); sPList.hasNext();)
			{
				Vertex v = sPList.next();
				sleep(400);
				
				Platform.runLater(new Runnable(){							
					@Override
					public void run()
					{
						CircleInfo c = v.getCircleInfo();
						c.setThisColor(Color.YELLOWGREEN);
						path.add(c);
					}
				});
			}// end for loop
			
			int adv = 1;
			for(int j = 0, h = path.size(); ((j < h) && (adv < h)); ++j)
			{
				for(Iterator<LineEdge> lIterator = lines.iterator(); lIterator.hasNext();)
				{
					LineEdge lEdge = lIterator.next();
					CircleInfo start = lEdge.getStart();
					CircleInfo end = lEdge.getEnd();
					
					if(start.equals(path.get(j)) && end.equals(path.get(adv)))
					{
						coloredEdges.add(lEdge);
						break;
					}
				}
				
				++adv;
				if(adv >= h)
					break;
			}	// end for loop
			
			for(LineEdge lineEdge : coloredEdges)
			{
				sleep(350);
				
				Platform.runLater(new Runnable(){
					@Override
					public void run()
					{
						root.getChildren().remove(lineEdge);
						lineEdge.setStroke(Color.YELLOWGREEN);
						root.getChildren().add(lineEdge);
					}
				});
			}	// end for loop
			
			if(lastEdge != null)
			{
				sleep(350);
				
				Platform.runLater(new Runnable(){
					@Override
					public void run()
					{
						root.getChildren().remove(lastEdge);
						lastEdge.setStroke(Color.YELLOWGREEN);
						root.getChildren().add(lastEdge);
						myWindow.idle();
					}
				});
			}
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}
}