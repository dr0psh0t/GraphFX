package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class TargetVertex
{
	Main app;
	
	@FXML
	TextField targetTF;
	
	@FXML
	Button okButton;
	
	@FXML
	public void initialize(){}
	
	public void setApp(Main app){
		this.app = app;
	}
	
	@FXML
	public void okAction()
	{
		String target = targetTF.getText();
		if(!target.isEmpty())
		{
			if(app.getVertex(target) != null)
			{
				// get a handle to the stage
				Stage stage = (Stage) okButton.getScene().getWindow();
				stage.close();
				
				app.shortestPathColor(target);
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Vertex "+target+" did not exist!");
				alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Empty fields!");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void clearAction(){
		targetTF.clear();
	}
}