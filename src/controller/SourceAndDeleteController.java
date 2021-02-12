package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/*
 * class: SourceAndDeleteController
 * description: The controller class to handle selection and deletion of vertex
 * author: Daryll David E. Dagondon
 * group: Log Horizon
 * 
 */

public class SourceAndDeleteController
{
	private Main app;
	
	@FXML
	Button selectButton, deleteButton;
	
	@FXML
	TextField textFieldSourceSS, textFieldVertex;
	
	@FXML
	public void initialize(){}
	
	public void setApp(Main app){
		this.app = app;
	}
	
	@FXML
	public void selectActionSS()
	{
		String name = textFieldSourceSS.getText();
		
		if(app.suchNameExist(name))
		{
			app.setColor(name);
			app.setSourceStatus(true);
			
			Stage stage = (Stage) selectButton.getScene().getWindow();
			stage.close();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Vertex "+name+" did not exist");
			alert.showAndWait();
		}
		
		clearFieldsSS();
	}
	
	@FXML
	public void clearFieldsSS(){
		textFieldSourceSS.clear();
	}
	
	@FXML
	public void deleteVertexAction()
	{
		String name = textFieldVertex.getText();
		
		if(!app.deleteVertex(name))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Unable to delete vertex "+name+"\nOr vertex "+name+" did not exist");
			alert.showAndWait();
		}
		
		textFieldVertex.clear();
	}
	
	@FXML
	public void clearFieldVertex(){
		System.out.println("cleared");
	}
}