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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import simulation.Triangle;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 *
 * @author Owen
 */
public class FXMLDocumentControllerClient implements Initializable {
    
    private Gateway gateWay;
    private GamePane gamePane;
    @FXML
    private Button button;
    @FXML
    private CheckBox readyCheck;
    @FXML
    private ListView playerList;
    
    @FXML
    private void startGame(){
        
        Stage primaryStage = new Stage();
        Scene scene = new Scene(gamePane, 600, 500);
        
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Final Game");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();
        List<Shape> list = new ArrayList<Shape>();
        
        
        Triangle t = new Triangle(100,100, 60,40,true);

        list.add(t.getShape());
        gamePane.setShapes(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gamePane = new GamePane();
        gateWay = new Gateway(this.gamePane);
        List<Shape> list = new ArrayList<Shape>();
        Rectangle r = new Rectangle(0,0,-1,1);
        r.setFill(Color.BLACK);
        Rectangle rd = new Rectangle(0,4,4,-1);
        list.add(r);
        list.add(rd);
        
        
    }    
    
}
