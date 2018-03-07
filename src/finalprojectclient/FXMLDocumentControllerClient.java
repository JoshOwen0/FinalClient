/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import physics.Point;
import simulation.Triangle;

/**
 *
 * @author Owen
 */
public class FXMLDocumentControllerClient implements Initializable {
    
    private Point pb=null;
    private Triangle paddle;
    private Triangle paddle2;
    private Gateway gateWay;
    private GamePane gamePane;
    private Rectangle outer;
    private double mousex;
    private double mousey;
    private int player=-1;
    private Text scoreText;
    private int score1,score2;
    private boolean done;
    
    @FXML
    private Button button;
    @FXML
    private CheckBox readyCheck;
    @FXML
    private ListView playerList;
    private int tick;
    @FXML
    private void startGame(){
        done = false;
        Stage stage = (Stage) readyCheck.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Scene scene = new Scene(gamePane, 1000, 800);
        score1=0;
        score2=0;
        //primaryStage.setMaximized(true);
        
        primaryStage.setTitle("Final Game");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();
        List<Shape> shapes = new ArrayList<Shape>();
        outer = new Rectangle(0,0,scene.getWidth(),scene.getHeight());
        outer.setStroke(Color.BLACK);
        outer.setFill(Color.MISTYROSE);
        shapes.add(outer);
        gamePane.setShapes(shapes);
        
        
        
        gateWay.startSim();
        
        
//        new Thread( () -> {
//            while(true){
//                int[] scores = gateWay.getScore();
//                Platform.runLater(() -> {
//                    score1=scores[1];
//                    score2=scores[0];
//                    scoreText.setText("Player 1: " + Integer.toString(scores[1]) + " \nPlayer 2: " + Integer.toString(scores[0]));
//                    
//                        });                
//                try{
//                    Thread.sleep(500);
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                }
//                
//            }
//        }).start();
        
        new Thread( () -> {while(true){
            physics.Point[] pt = gateWay.getPaddles();
            pb = gateWay.getBalls();
            scene.setOnMouseMoved((e)->{
                mousex = e.getSceneX();
                mousey = e.getSceneY();
            });
            if(player==0){
                paddle = new Triangle((int) pt[0].x,(int) pt[0].y,60,40,true);
                paddle2 = new Triangle((int) pt[1].x,(int) pt[1].y,60,-40,true);
                done=true;
            }else{
                paddle = new Triangle((int) pt[0].x,(int) pt[0].y,60,-40,true);
                paddle2 = new Triangle((int) pt[1].x,(int) pt[1].y,60,40,true);
                done=true;
            }
            
            double px = pt[0].x + paddle.width/2;
            double py = pt[0].y;
            //java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
            double x = mousex;
            double y = mousey + paddle.height/2;
            int dx=0;
            int dy=0;
            //sim.moveInner((int)x - pos.x, (int)y - pos.y);
            if(x<px && Math.abs(x- px) >2)
                //sim.moveInner(-3, 0);
                dx=-2;
            if(x>px && Math.abs(x-px) >2)
                //sim.moveInner(3, 0);
                dx=2;
            if(y>py && Math.abs(y- py) >2)
                //sim.moveInner(0, 3);
                dy=2;
            if(y<py && Math.abs(y- py) >2)
                //sim.moveInner(0, -3);
                dy=-2;
            gateWay.sendMoves(dx, dy);
            Platform.runLater(()->updateShapes());
            try{
                Thread.sleep(10);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        }}).start();
    }
    
    @FXML
    private void ready(){
            player = gateWay.sendReady(readyCheck.isSelected());
            
    }
    private void updateShapes(){
        
        if(score1>4){
            scoreText.setX(250);
            scoreText.setY(400);
            scoreText.setFont(Font.font("Broadway",70));
            scoreText.setText("PLAYER 1 WINS");
        }else{
            if(score2>4){
                scoreText.setX(250);
                scoreText.setY(400);
                scoreText.setFont(Font.font("Broadway",70));
                scoreText.setText("PLAYER 2 WINS");
                
            }else{
                List<Shape> shapes=new ArrayList<Shape>();
            shapes.add(outer);
        
            Circle c;
            Polygon p = (Polygon) paddle.getShape();
            Polygon p2 = (Polygon) paddle2.getShape();

            p.setStroke(Color.BLACK);
            p.setFill(Color.CYAN);
            p2.setStroke(Color.BLACK);
            p2.setFill(Color.CYAN);


            c = new Circle(pb.x,pb.y,4);
            c.setFill(Color.DARKGRAY);
            c.setStroke(Color.BLACK);

            shapes.add(p);
            shapes.add(p2);
            shapes.add(c);
            gamePane.setShapes(shapes);
            }
        }
        
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scoreText = new Text(20,20,"");
        scoreText.setFont(Font.font ("Helvetica",15));
        scoreText.setFill(Color.BLACK);
        gamePane = new GamePane(scoreText);
        gateWay = new Gateway(this.gamePane); 
    }    
    
}
