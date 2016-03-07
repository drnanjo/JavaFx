/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myseaapp;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Nancy
 */
public class MySeaApp extends Application {
    
    private ImageView seahorse;
    private Rectangle seaClip;
    private double sX = 0;
    private DoubleProperty coordXReal= new SimpleDoubleProperty(0);
    private ImageView coralfishB;
    private Rectangle coralClip;
    private ImageView shark;
    private FadeTransition fadeTransition;
    private DropShadow sharkShadow;
    
    private SimpleDoubleProperty xOff = new SimpleDoubleProperty(0.0);
    private SimpleDoubleProperty yOff = new SimpleDoubleProperty(0.0);        
    private SimpleDoubleProperty sOff = new SimpleDoubleProperty(5.0);
    private SimpleDoubleProperty scale = new SimpleDoubleProperty(.3);
    private Timeline sharkSwimAway;
    
    private boolean exited = true;
    
    private ImageView quit;

    
/**
      @param args the command line arguments

*/
         
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
            
          
        seahorse = new ImageView(new Image(MySeaApp.class.getResourceAsStream("images/seahorse.jpg")));
        seaClip = new Rectangle(800, 800);
        seaClip.setArcWidth(30);
        seaClip.setArcHeight(30);
        
        coralfishB = new ImageView(new Image(MySeaApp.class.getResourceAsStream("images/coralfishB.jpg")));
        coralClip = new Rectangle(800, 800);
        coralClip.setArcWidth(30);
        coralClip.setArcHeight(30);
        coralfishB.setOpacity(0.0);
        
        
        
        fadeTransition = new FadeTransition(Duration.seconds(1), coralfishB);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        
        setDrag();
        setShark();
        setQuit();
        
        seahorse.setClip(seaClip);
        coralfishB.setClip(coralClip);
        Pane root = new Pane();
        root.getChildren().addAll(seahorse, coralfishB, shark, quit);
        Scene myScene = new Scene(root, 1000, 800);
        myScene.setFill(null);
        primaryStage.setScene(myScene);
        primaryStage.show();
        
    }
    private void setQuit(){
      quit = new ImageView(new Image(MySeaApp.class.getResourceAsStream("images/close.png")));
        quit.setFitHeight(25);
        quit.setFitWidth(25);
        quit.setX(10);
        quit.setY(5);
        
        
        quit.setOnMouseClicked((MouseEvent t) -> {
            System.exit(0);
        
        });
    } 
        private void setDrag() {
        seahorse.setOnMousePressed(new EventHandler<MouseEvent>() {
        
        
        public void handle(MouseEvent t) {
            sX = t.getSceneX() - coordXReal.getValue();
    }
    });

         seahorse.setOnMouseDragged(new EventHandler<MouseEvent>() {
        
        
        public void handle(MouseEvent t) {
            coordXReal.set(t.getSceneX() - sX); 
    }
    });

         
        seahorse.xProperty().bind(coordXReal);
        
         
        coralfishB.setOnMouseEntered(new EventHandler<MouseEvent>() {
            
           public void handle(MouseEvent t) {
            //coralfishB.setOpacity(1.0);
               if (exited){
               fadeTransition.setRate(1.0);
               fadeTransition.play();
               exited = false;
        }
           }
            
        });
         
        coralfishB.setOnMouseExited(new EventHandler<MouseEvent>() {
            
           public void handle(MouseEvent t) {
            //coralfishB.setOpacity(0.0);
               if (!coralfishB.contains(new Point2D(t.getSceneX(), t.getSceneY())))
               fadeTransition.setRate(-1.0);
               fadeTransition.play();
               exited = true; 
        }
            
            
        }); 
        
        
        
        coralfishB.setOnMousePressed(new EventHandler<MouseEvent>() {
        
        
        public void handle(MouseEvent t) {
            sX = t.getSceneX() - coordXReal.getValue();
    }
    });

         coralfishB.setOnMouseDragged(new EventHandler<MouseEvent>() {
        
        
        public void handle(MouseEvent t) {
            coordXReal.set(t.getSceneX() - sX); 
    }
    });

        coralfishB.xProperty().bind(coordXReal);
        }
    
        private void setShark(){
            
        shark = new ImageView(new Image(MySeaApp.class.getResourceAsStream("images/shark.png")));
        shark.setScaleX(0.3);
        shark.setScaleY(0.3);
        shark.opacityProperty().bind(coralfishB.opacityProperty());
        
        shark.scaleXProperty().bind(scale);
        shark.scaleYProperty().bind(scale);
        shark.xProperty().bind(xOff);
        shark.yProperty().bind(yOff);
        
        
        sharkSwimAway = new Timeline(
        new KeyFrame(Duration.ZERO,
            new KeyValue(sOff, 0),
            new KeyValue(scale, 0.3),
            new KeyValue(xOff, 0),
            new KeyValue(yOff, 0)),
        new KeyFrame(new Duration(5000),
            new KeyValue(sOff, -100),
            new KeyValue(scale, 3.0),
            new KeyValue(xOff, 2500),
            new KeyValue(yOff, 800))
            
          
            );
        shark.setOnMouseClicked(new EventHandler<MouseEvent>() {
        
        
        public void handle(MouseEvent t) {
            sharkSwimAway.play(); 
    }
    });
        
        
        
        
            
        sharkShadow = new DropShadow(5.0, 5.0, 0.0, Color.BLACK);
        sharkShadow.offsetXProperty().bind(sOff);
        sharkShadow.offsetYProperty().bind(sOff);
        shark.setEffect(sharkShadow);
        
        }
}

    
