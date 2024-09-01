package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Beans.Response;
import Beans.Tennista;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//eventuali bean


public class Cerca extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Gson gson;
    List<Tennista> tennisti = new ArrayList<Tennista>();
    
	@Override
	public void init(ServletConfig config) throws ServletException  { 
		this.gson = new Gson();
		
		//						  cognome	ranking win	   lose	titoli tab
		tennisti.add(new Tennista("Musetti",	12, 100, 	87, 	2, 1));
		tennisti.add(new Tennista("Musiani",	12, 150, 	100, 	2, 2));
		tennisti.add(new Tennista("Marco",		12, 100, 	87, 	2, 2));
		tennisti.add(new Tennista("Antonio", 	12, 10, 	7, 		2, 1));
		tennisti.add(new Tennista("Novak",		12, 130,	87, 	2, 4));
		tennisti.add(new Tennista("Nadal",		12, 100,	86, 	2, 3));
		tennisti.add(new Tennista("Salti",		12, 100,	87, 	2, 3));
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
    
    //gestisco richiesta AJAX
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String cognome = req.getParameter("cognome");
		
		Tennista currentTennista = null;
		
		for(Tennista t : tennisti) {
			if(t.getCognome().equals(cognome)) {
				currentTennista = t;
				
				//genero risposta da mandare
				Response risp = new Response(t, tennisti);
				res.getWriter().write(gson.toJson(risp));
			}			
		}
		
		//tennista non iscritto
		if(currentTennista == null) {
			//genero risposta da mandare
			res.getWriter().write(gson.toJson(new Response()));
		}
    }
}   