package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Node {
	
	private final StringProperty name;
	
	public Node(String name){
		this.name = new SimpleStringProperty(name);
	}
	
	public StringProperty nameProperty(){
		return name;
	}
}
