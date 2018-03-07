package finalprojectclient;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class GamePane extends Pane {
    private Text scoreText;
    public GamePane(Text scoreText) {
        this.scoreText=scoreText;
        
    }
    
    public void setShapes(List<Shape> newShapes) {
        this.getChildren().clear();
        this.getChildren().addAll(newShapes);
        this.getChildren().add(this.scoreText);
    }
}
