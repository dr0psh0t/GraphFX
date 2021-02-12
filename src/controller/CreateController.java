package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateController
{
	Main app;
	Stage thisStage;
	
	@FXML
	TextField textFieldVertexCV,
				textFieldSourceCV,
				textFieldEdgeWeightCV;
	
	@FXML
	TextField textFieldVertexNE,
				textFieldSourceNE,
				textFieldEdgeWeightNE;
	
	@FXML
	public void initialize(){}
	
	public void setApp(Stage thisStage, Main app)
	{
		this.thisStage = thisStage;
		this.app = app;
	}
	
	@FXML
	public void clearFieldsCV()
	{
		textFieldVertexCV.clear();
		textFieldSourceCV.clear();
		textFieldEdgeWeightCV.clear();
	}
	
	@FXML
	public void okayActionCV()
	{
		String vName = textFieldVertexCV.getText(),
    			source = textFieldSourceCV.getText();
		
		int weight;
		
		try
		{
			if(textFieldEdgeWeightCV.getText().isEmpty())
				weight = 0;
			else
				weight = Integer.parseInt(textFieldEdgeWeightCV.getText());
			
			if(!app.suchNameExist(vName))
	    	{
	    		if(vName.isEmpty() && source.isEmpty())
	        	{
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setHeaderText("Empty fields");
	        		alert.showAndWait();
	        	}
	        	else if(vName.isEmpty() && !(source.isEmpty()))
	        	{
	        		Alert alert = new Alert(AlertType.WARNING);
	        		alert.setHeaderText("Warning!");
	        		alert.setContentText("Provide a label for the new vertex");
	        		alert.showAndWait();
	        	}
	        	else if(!(vName.isEmpty()) && source.isEmpty()) 
	        	{
	        		// set weight to 0 if the source name is empty but the new vertex isn't
	        		
	        		app.createVertex(textFieldVertexCV.getText(), "", 0, 0, 0);
	        		
	        		textFieldVertexCV.clear();
	        		textFieldSourceCV.clear();
	        	}
	        	else
	        	{
	        		// if both vertex and source are specified
	        		
	        		app.createVertex(vName, source, weight, 0, 0);
	        		
	        		textFieldVertexCV.clear();
	        		textFieldSourceCV.clear();
	    		}
	    	}
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setHeaderText("Existing!");
	    		alert.setHeaderText("Vertex: "+vName+" already exist!");
	    		alert.showAndWait();
	    		textFieldVertexCV.clear();
	    	}
		}
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Edge weight must be a number!");
    		alert.showAndWait();
		}
	}
	
	@FXML
	public void connectActionNE()
	{
		String vName = textFieldVertexNE.getText(),
				source = textFieldSourceNE.getText();		
		int weight;
		
		try
		{
			if(textFieldEdgeWeightNE.getText().isEmpty())
				weight = 0;
			else
				weight = Integer.parseInt(textFieldEdgeWeightNE.getText());
			
			if(!vName.isEmpty() && !source.isEmpty())
			{
				if(app.suchNameExist(vName) && app.suchNameExist(source))	// if two vertices exist
				{
					if(!app.suchAdjacencyExists(source, vName))
						app.connectVertices(vName, source, weight);
					else
					{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setHeaderText(source+" is already adjacent to "+vName);
						alert.showAndWait();
					}
				}
				else
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("No vertices");
					alert.showAndWait();
				}
				clearFieldsNE();
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Empty fields");
				alert.showAndWait();
			}
		}
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Edge weight must be a number!");
    		alert.showAndWait();
		}
	}
	
	@FXML
	public void clearFieldsNE()
	{
		textFieldVertexNE.clear();
		textFieldSourceNE.clear();
		textFieldEdgeWeightNE.clear();
	}
}