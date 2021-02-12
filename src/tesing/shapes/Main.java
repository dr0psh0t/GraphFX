package tesing.shapes;

import java.text.NumberFormat;
import java.text.ParseException;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox root = new HBox();

        DoubleProperty value = new SimpleDoubleProperty(12.345);

        TextField valueTextField = new TextField();

        Bindings.bindBidirectional(valueTextField.textProperty(), value, new IntegerStringConverter());

        root.getChildren().addAll(valueTextField);

        primaryStage.setScene(new Scene(root, 310, 200));
        primaryStage.show();

    }

    public class IntegerStringConverter extends StringConverter<Number> {

        NumberFormat formatter = NumberFormat.getIntegerInstance();

        @Override
        public String toString(Number value) {

            return formatter.format(value);

        }

        @Override
        public Number fromString(String text) {

            try {

                return formatter.parse(text);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}