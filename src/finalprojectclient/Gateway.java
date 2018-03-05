/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.ObjectInputStream;
import java.util.List;
import javafx.scene.shape.Shape;
/**
 *
 * @author Owen
 */
public class Gateway implements net.NetConstants{
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private ObjectInputStream inObjFromServer;
    private GamePane gamePane;
    private Gson gson;
    public Gateway(GamePane gp) {
        this.gamePane = gp;
        try {
            Socket socket = new Socket("localhost", 8000);

            outputToServer = new PrintWriter(socket.getOutputStream());

            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inObjFromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            
        }
    }
    public List<Shape> getShapes(){
        outputToServer.println(GET_SHAPES);
        outputToServer.flush();
        
        List<Shape> shapes = new ArrayList<Shape>();
        try{
            shapes = (List<Shape>) inObjFromServer.readObject();
//            int n = Integer.parseInt(inputFromServer.readLine());
//            for(int i=0;i<n;i++){
//                String json = inputFromServer.readLine();
//                shapes.add(gson.fromJson(json, Shape.class));
//
//            }
            System.out.println(shapes);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return shapes;
    }
    public void sendMoves(int x, int y){
        outputToServer.println(SEND_MOVES);
        outputToServer.println(String.valueOf(x) + " " + String.valueOf(y));
        outputToServer.flush();
    }
    public void sendReady(boolean ready){
        outputToServer.println(SEND_READY);
        if(ready){
            outputToServer.println("ready");
        }else{
            outputToServer.println("unready");
        }
        outputToServer.flush();
    }
}
