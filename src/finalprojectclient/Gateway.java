/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import physics.Point;
import physics.Ray;
import simulation.Ball;
/**
 *
 * @author Owen
 */
public class Gateway implements net.NetConstants{
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private GamePane gamePane;
    private ObjectInputStream ObjInput;
    public Gateway(GamePane gp) {
        this.gamePane = gp;
        try {
            Socket socket = new Socket("143.44.68.64", 8000);

            outputToServer = new PrintWriter(socket.getOutputStream());
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void startSim(){
        outputToServer.println(START_SIM);
        outputToServer.flush();
    }
    public synchronized Point[] getPaddles(){
        outputToServer.println(GET_PADDLES);
        outputToServer.flush();
        Point p1=null;
        Point p2=null;
        try{
        p1 =(Point) ObjInput.readObject();
        p2 =(Point) ObjInput.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        //Triangle tr = new Triangle((int) pt.x,(int) pt.y,60,40,true);
        //Shape paddle = pt.getShape();
        Point[] points = new Point[2];
        points[0] = p1;
        points[1] = p2;
        return points;
    }
    public synchronized Point getBalls(){
        outputToServer.println(GET_BALLS);
        outputToServer.flush();
        Point pb=null;
        try{
        pb =(Point) ObjInput.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return pb;
    }
    public synchronized void sendMoves(int x, int y){
        outputToServer.println(SEND_MOVES);
        outputToServer.println(String.valueOf(x));
        outputToServer.println(String.valueOf(y));
        outputToServer.flush();
        
    }
    public int sendReady(boolean ready){
        outputToServer.println(SEND_READY);
        if(ready){
            outputToServer.println("ready");
        }else{
            outputToServer.println("unready");
        }
        int i=-1;
        outputToServer.flush();
        try{
             i = Integer.valueOf(inputFromServer.readLine());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return i;
    }
    public synchronized int[] getScore(){
        outputToServer.println(GET_SCORE);
        outputToServer.flush();
        int[] scores=new int[2];
        try{
            scores[0] = Integer.parseInt(inputFromServer.readLine());
            scores[1] = Integer.parseInt(inputFromServer.readLine());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return scores;
    }
}
