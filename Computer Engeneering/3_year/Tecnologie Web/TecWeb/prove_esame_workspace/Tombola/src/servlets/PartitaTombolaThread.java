package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.*;

import org.hibernate.ejb.criteria.predicate.IsEmptyPredicate;

import com.google.gson.Gson;

import beans.Request;
import beans.Response;
import beans.UpdateReq;

import java.time.Instant;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class PartitaTombolaThread extends Thread {

	private ArrayList<Session> giocatori;
    private Gson g = new Gson();

	public PartitaTombolaThread(ArrayList<Session> giocatori){
		this.giocatori = giocatori;
	}

	@Override
    public void run(){

        // Inviamo le cartelle
        for (Session giocatore : giocatori){
            try {
                giocatore.getBasicRemote().sendText(g.toJson(this.generaCartella()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Inviamo numero estratto
        while(true){
            for (Session giocatore : giocatori){
                Session tmp=null;
                try {
                    tmp=giocatore;
                    giocatore.getBasicRemote().sendText(g.toJson((int) (Math.random()*90 + 1)));
                } catch (IOException e) {
                    giocatori.remove(tmp);
                    giocatori.forEach(player -> {
                        try {player.getBasicRemote().sendText("Un giocatore è uscito");} catch (IOException e1) {e1.printStackTrace();}
                    });
                }
            }
        }
        
    }

    private int[] generaCartella(){
        int[] result = new int[15];
        for(int i=0; i<15; i++){
            result[i] = (int) (Math.random()*90 + 1);
        }
        return result;
    }

}
