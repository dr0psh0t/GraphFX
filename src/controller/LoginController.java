package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController
{
	public Main app;
	
	@FXML
	TextField usernameTextField;
	
	@FXML
	PasswordField pwPasswordField;
	
	@FXML
	Button loginButton;
	
	@FXML
	Label registerLabel;
	
	@FXML
	public void initialize()
	{
		loginButton.setCursor(Cursor.HAND);
		registerLabel.setCursor(Cursor.HAND);
	}
	
	public void setApp(Main app){
		this.app = app;
	}
	
	@FXML
	public void registerEvent(){
		app.openWindow(6);
	}
}