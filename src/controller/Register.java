package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Register
{
	public Main app;
	
	@FXML
	ChoiceBox<String> occupChoiceBox,
						genderChoiceBox;
	
	@FXML
	public void initialize()
	{
		occupChoiceBox.getItems().add("Student");
		occupChoiceBox.getItems().add("Instructor");
		
		genderChoiceBox.getItems().add("Male");
		genderChoiceBox.getItems().add("Female");
	}
	
	public void setApp(Main app){
		this.app = app;
	}
}