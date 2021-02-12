package controller;

import application.Main;
import javafx.fxml.FXML;

public class AboutController
{
	Main app;
	
	@FXML
	public void initialize(){}
	
	public void setApp(Main app){
    	this.app = app;
    }
}