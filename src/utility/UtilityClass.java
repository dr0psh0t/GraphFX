package utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/*
 * This is not a comment.
 */

public class UtilityClass
{
	private static int yCoord = 50;
	
	public static int getY(){
		return yCoord;
	}
	
	public static void incrementY(int val){
		yCoord = yCoord + val;
	}
	
	public static void setDefaultX(){
		yCoord = 20;
	}
	
	public static Optional<ButtonType> consume()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("GraphFX");
    	alert.setHeaderText("Exit Application");
    	alert.setContentText("Are you sure you want to close GraphFX?");

    	Optional<ButtonType> result = alert.showAndWait();
    	//if(result.get() == ButtonType.OK)
    	
    	return result;
	}
}