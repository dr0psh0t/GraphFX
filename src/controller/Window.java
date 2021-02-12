package controller;

import application.Main;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;

public class Window
{
	private Main app;
	
	@FXML
	private TableView<Node> gridTableView;
	
	@FXML
	private TableColumn<Node, String> nodeColumn;
	
	private ObservableList<Node> nodeList = FXCollections.observableArrayList();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private MenuItem mnNewConnection,
		mnNewVertex,
		mnUndirectedGraph,
		mnDirectedGraph,
		mnSourceVertex,
		mnDeleteVertex;
    
    @FXML
    private TextField vertexTF, sourceTF;
    
    @FXML
    private Label graphTypeLabel, statusLabel;
    
    @FXML
    private Button restorebutton, repositionButton;
    
    private boolean graphSelected;
    
    @FXML
    public void initialize()
    {
    	mnNewVertex.setDisable(true);
    	mnNewConnection.setDisable(true);
    	mnSourceVertex.setDisable(true);
    	mnDeleteVertex.setDisable(true);
    	
    	nodeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Node,String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<Node, String> param){
				return param.getValue().nameProperty();
			}
		});
    	
    	
    }
    
    public void visible()
    {
    	mnNewConnection.setDisable(false);
		mnSourceVertex.setDisable(false);
		mnDeleteVertex.setDisable(false);
    }
    
    public void setApp(Main app, Scene scene)
    {
    	this.app = app;
    	nodeList.add(new Node("Venus"));
    	gridTableView.setItems(nodeList);
    }
    
    public TableView<Node> getTableView(){
    	return gridTableView;
    }
    
    public Label getGraphTypeLabel(){
    	return graphTypeLabel;
    }
    
    public Label getStatusLabel(){
    	return statusLabel;
    }
    
    public Button getRestoreButton(){
    	return restorebutton;
    }
    
    public Button getRepositionButton(){
    	return repositionButton;
    }
    
    @FXML
    public void repositionAction(){
    	//app.setPosToMid();
    }
    
    @FXML
    public void closeAction()
    {
    	nodeList.clear();
    	app.closeApp();
    }
    
    public void addNode(String name){
    	nodeList.add(new Node(name));
    }
    
    public void clearNodeList(){
    	nodeList.clear();
    }
    
    @FXML
    public void enterAction()
    {
    	if(graphSelected)
    	{
    		mnNewConnection.setDisable(false);
    		mnSourceVertex.setDisable(false);
    		mnDeleteVertex.setDisable(false);
        	app.openWindow(1);
    	}
    	else
    		mnNewVertex.setDisable(true);
    }
    
    public void printAction(ActionEvent e){
    	app.printVertices();
    }
	
	@FXML
	public void cancelButtonClicked()
	{
		app.destroyGraph();
		
		mnNewConnection.setDisable(true);
		mnNewVertex.setDisable(true);
		mnDirectedGraph.setDisable(false);
		mnUndirectedGraph.setDisable(false);
		
		graphTypeLabel.setText("Graph");		
		graphSelected = false;
	}
	
	@FXML
	public void undirectedGraph()
	{
		graphSelected = true;
		app.setDigraph(false);
		enterAction();
		
		mnNewVertex.setDisable(false);
		mnNewConnection.setDisable(false);
		mnUndirectedGraph.setDisable(true);
		mnDirectedGraph.setDisable(true);
		graphTypeLabel.setText("Undirected Graph");
	}
	
	@FXML
	public void directedGraph()
	{
		graphSelected = true;
		app.setDigraph(true);
		enterAction();
		
		mnNewVertex.setDisable(false);
		mnNewConnection.setDisable(false);
		mnUndirectedGraph.setDisable(true);
		mnDirectedGraph.setDisable(true);
		graphTypeLabel.setText("Directed Graph");
	}
	
	// this method is invoked when loading a graph
	public void digraph()
	{
		graphSelected = true;
		
		mnNewVertex.setDisable(false);
		mnNewConnection.setDisable(false);
		mnUndirectedGraph.setDisable(true);
		mnDirectedGraph.setDisable(true);
		graphTypeLabel.setText("Directed Graph");
	}
	
	@FXML
	public void aboutAction(){
		app.openWindow(3);
	}
	
	@FXML
	public void newConnectionAction()
	{
		if(app.getNumber() > 1)
		{
			mnNewConnection.setDisable(false);
			app.openWindow(1);
		}
		else
			mnNewConnection.setDisable(true);
	}
	
	@FXML
    public void deleteAction()
	{
		if(app.getNumber() > 1)
		{
			mnDeleteVertex.setDisable(false);
			app.openWindow(4);
		}
		else
			mnDeleteVertex.setDisable(true);
    }
	
	@FXML
	public void sourceVertexAction()
	{
		if(app.getNumber() > 1)
		{
			mnSourceVertex.setDisable(false);
			app.openWindow(4);
		}
		else
			mnSourceVertex.setDisable(true);
	}
	
	@FXML
	public void dftAction()
	{
		if(!app.isEmptyGraph())
		{
			nodeList.clear();
			nodeColumn.setText("DFS");
			//app.dft();
			app.depthFirstTraversal();
			statusLabel.setText("Running depth-first traversal");
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Cannot perform Depth-first\ntraversal on empty graph.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void bftAction()
	{
		if(!app.isEmptyGraph())
		{
			nodeList.clear();
			nodeColumn.setText("BFS");
			//app.bft();
			app.breadthFirstTraversal();
			statusLabel.setText("Running breadth-first traversal");
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Cannot perform Breadth-first\ntraversal on empty graph.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void shortestPathAction()
	{
		if(!app.isEmptyGraph())
			app.openWindow(2);
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Cannot perform Shortest-path\non empty graph.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void adjacencyMatrixAction()
	{
		if(app.isEmptyGraph())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("No vertices found to\ndisplay adjacency matrix");
			alert.showAndWait();
		}
		else
			app.adjMatrix();
	}
	
	@FXML
	public void adjacencyListAction()
	{
		if(app.isEmptyGraph())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("No vertices found to\ndisplay adjacency list");
			alert.showAndWait();
		}
		else
			app.adjList();
	}
	
	@FXML
	public void mstAction()
	{
		if(!app.isDigraph())
		{
			nodeColumn.setText("MST");
			nodeList.clear();
			app.minimumSpanningTree();
			statusLabel.setText("Running Minimum Spanning Tree");
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Cannot find the Minimum Spanning Tree\n"
					+ "of a Directed Graph. Destroy the current Digraph\n"
					+ "and create a new Undirected Graph");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void depthFirstTopologicalAction()
	{
		if(app.isDigraph())
		{
			nodeColumn.setText("TPO");
			nodeList.clear();
			statusLabel.setText("Running Depth-First Topological Order");
			app.topologicalOrder(1);
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Topological Sort is unavailable for Undirected Graphs");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void breadthFirstTopologicalAction()
	{
		if(app.isDigraph())
		{
			nodeColumn.setText("TPO");
			nodeList.clear();
			statusLabel.setText("Running Breadth-First Topological Order");
			app.topologicalOrder(2);
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Topological Sort is unavailable for Undirected Graphs");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void loadGraph(){
		app.loadGraph(app.fileChooser);
	}
	
	@FXML
	public void saveGraph(){
		app.saveGraph(app.fileChooser);
	}
	
	@FXML
	public void inDegreeAction(){
		app.inDegree();
	}
	
	@FXML
	public void outDegreeAction(){
		app.outDegree();
	}
	
	@FXML
	public void allDegreeAction(){
		app.totalDegree();
	}
	
	@FXML
	public void restoreAction(){
		app.restoreNames();
	}
	
	private boolean hidden = false;
	
	@FXML
	public void hideAction()
	{
		if(!hidden)
		{
			app.hide();
			hidden = true;
		}
	}
	
	@FXML
	public void showAction()
	{
		if(hidden)
		{
			app.show();
			hidden = false;
		}
	}
	
	public void idle(){statusLabel.setText("Idle");}
	
	public void spRunning(){statusLabel.setText("Running shortest-path");}
	
	@FXML
	public void loginStudentEvent(){
		app.openWindow(5);
	}
}