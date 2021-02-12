package tesing.shapes;

import java.text.MessageFormat;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EllipseTransmator extends Application
{

  @Override
  public void start( final Stage primaryStage )
  {
    final Ellipse ellipse = createEllipse();

    final Group root = new Group( ellipse );
    final Property<Point2D> mousePressedPoint = new SimpleObjectProperty<>( Point2D.ZERO );
    final DoubleProperty initialRotation = new SimpleDoubleProperty( 0 );
    final DoubleProperty initialRadiusX = new SimpleDoubleProperty( 0 );
    final DoubleProperty initialRadiusY = new SimpleDoubleProperty( 0 );

    ellipse.setOnMousePressed( press ->
    {
      mousePressedPoint.setValue( new Point2D( press.getSceneX(), press.getSceneY() ) );
      initialRotation.set( ellipse.getRotate() );
      initialRadiusX.set( ellipse.getRadiusX() );
      initialRadiusY.set( ellipse.getRadiusY() );
    } );

    ellipse.setOnMouseDragged( drag ->
    {
      final Point2D ellipseCenter = new Point2D( ellipse.getCenterX(), ellipse.getCenterY() );
      final Point2D dragPoint = new Point2D( drag.getSceneX(), drag.getSceneY() );

      if ( drag.isShortcutDown() )
      {
        final Point2D dragDistance = dragPoint.subtract( mousePressedPoint.getValue() );

        if ( drag.isAltDown() )
        {
          final double radiusY = initialRadiusY.getValue() + dragDistance.getY();
          if ( radiusY > 10 )
          {
            ellipse.setRadiusY( radiusY );
            showText( MessageFormat.format( "{0}px", ellipse.getRadiusY() ),
                ellipseCenter.subtract( 15, 15 ), Color.GREY, 100, root );
          }
        }

        final double radiusX = initialRadiusX.getValue() + dragDistance.getX();
        if ( radiusX > 10 )
        {
          ellipse.setRadiusX( radiusX );
          showText( MessageFormat.format( "{0}px", ellipse.getRadiusX() ), ellipseCenter.add( 15, 15 ),
              Color.GREY, 100, root );
        }
      }
      else
      {

        final double angle = computeAngle( ellipseCenter, dragPoint, mousePressedPoint.getValue() );
        ellipse.setRotate( initialRotation.get() + angle );

        showLine( ellipseCenter, dragPoint, Color.DODGERBLUE, 200, root );
        showText( MessageFormat.format( "{0}°", ellipse.getRotate() ), dragPoint, Color.WHITE, 100, root );
      }
    } );

    final Scene scene = new Scene( root, 800, 600, Color.BLACK );
    scene.setCursor( Cursor.CROSSHAIR );
    primaryStage.setScene( scene );
    primaryStage.show();
  }

  private Ellipse createEllipse()
  {
    final Ellipse ellipse = new Ellipse( 400, 300, 20, 40 );
    ellipse.setFill( Color.AZURE );
    ellipse.setStroke( Color.ANTIQUEWHITE );
    ellipse.setStrokeWidth( 3 );
    ellipse.setEffect( new DropShadow( 12, Color.WHITE ) );
    return ellipse;
  }

  public static void main( final String[] args )
  {
    launch( args );
  }

  /**
   * Stolen and afterwards adapted from:
   * https://stackoverflow.com/questions/3365171/calculating-the-angle-between-two-lines-without-having-to-
   * calculate-the-slope
   */
  private double computeAngle( final Point2D v, final Point2D a, final Point2D b )
  {
    final double angle1 = Math.atan2( v.getY() - a.getY(), v.getX() - a.getX() );
    final double angle2 = Math.atan2( v.getY() - b.getY(), v.getX() - b.getX() );
    return (angle1 - angle2) / Math.PI * 180;
  }
  private static void showLine( final Point2D from, final Point2D to, final Color color, final int duration,
                                final Group inGroup )
  {
    final Line line = new Line( from.getX(), from.getY(), to.getX(), to.getY() );
    line.setStroke( color );
    line.setStrokeWidth( 3 );
    line.setOpacity( .75 );
    inGroup.getChildren().add( line );

    final FadeTransition trans = new FadeTransition();
    trans.setDuration( Duration.millis( duration ) );
    trans.setNode( line );
    trans.setToValue( 0 );

    trans.setInterpolator( Interpolator.EASE_OUT );
    trans.setOnFinished( finishHim -> inGroup.getChildren().remove( line ) );
    trans.play();
  }

  private static void showText( final String textString, final Point2D position, final Color color,
                                final int duration, final Group inGroup )
  {
    final Text text = new Text( textString );
    text.setX( 15 + position.getX() );
    text.setY( position.getY() );
    text.setFill( color );
    text.setFont( Font.font( "monospace", 32 ) );
    text.setOpacity( .75 );
    inGroup.getChildren().add( text );

    final FadeTransition trans = new FadeTransition();
    trans.setDuration( Duration.millis( duration ) );
    trans.setNode( text );
    trans.setToValue( 0 );

    trans.setInterpolator( Interpolator.EASE_OUT );
    trans.setOnFinished( finishHim -> inGroup.getChildren().remove( text ) );
    trans.play();
  }
}