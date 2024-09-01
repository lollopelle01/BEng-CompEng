package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Beans.Json;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Random;


public class S1 extends HttpServlet {

    private static final long serialVersionUID = 1L;
	
    Gson g = new Gson();
    Random r = new Random();
    
	@Override
	public void init(ServletConfig config) throws ServletException  { 
        super.init(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Json request = g.fromJson(req.getParameter("json"), Json.class);
		String testo = request.getTesto();
		
		String newTesto = "";
		
		int num = r.nextInt(9) + 1;
		System.out.println("numero: " + num);
		
		//eliminare dal testo ricevuto tutti i caratteri di posizione multipla di i
		for(int i = 1; i < testo.length(); i++) {
			if(i % num == 0) {
				newTesto += testo.charAt(i);
			}
		}
		request.setTesto(newTesto);
		req.setAttribute("json", request);
		this.getServletContext().getRequestDispatcher("/J2.jsp").forward(req, res);
    }
} 