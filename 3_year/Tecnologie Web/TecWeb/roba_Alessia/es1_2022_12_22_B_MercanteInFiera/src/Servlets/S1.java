package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.AbstractDocument.Content;

import com.google.gson.Gson;

import Beans.*;
import java.io.*;
import java.util.Date;

//eventuali bean

public class S1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

	private LocalTime timer ;
	private Gson g;

	private static final String adminUsr = "username";
    private static final String adminPsw = "password";
	
	@Override
	public void init(ServletConfig config) throws ServletException  { 
		super.init(config);
		g = new Gson();
	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		/* SE HO UN TIMER PER L'APPLICAZIONE:
		 *	es. se non ci sono offerte per i regali entro 120 sec dall'ultima */
		if(timer==null) {
			synchronized(this) {
				timer = LocalTime.now();
			}
		} else {
			if(timer.isBefore(LocalTime.now().minusSeconds(120))) {
				synchronized(this) {
					
					/*	es. se non ci sono offerte entro 120 sec dall'ultima,
					 *		l'offerta dell'utente x (ultima) riceve il regalo */
				}
				
				this.getServletContext().getRequestDispatcher("/J1.jsp").forward(req, res);;
		        
			}
		}

		HttpSession session = req.getSession();

		

		/* CONVERSIONE DA JSON:
		 *  |	Creati una classe con variabili che ti servono	|
		 *	|	in modo da rendere più facile la conversione	|
		 *	|	JSON -> String in JS o String -> JS in Java		| */
		 
		String cotinente = (String) req.getParameter("continente");
		double denari = Double.parseDouble(req.getParameter("denari"));


		/* CONVERSIONE IN JSON: voglio inviare dati in formato JSON
		 * 	-> se usi AJAX:		crei il tuo bean Risp con tutte le variabili da inviare
		 * 						e lo converti in formato JSON per inviarlo
		 * 	-> se non usi AJAX: lo puoi settare a livello di sessione utente,
		 * 						sempre con la struttura/bean contenente le info nec.*/

		/*********** AJAX *************/
		String dato1 = "bologna";
		double dato2 = 15.00;
		


		/********** NO AJAX ***********/
		/* 	se devo inviare i dati con JSON ma non passo da un JS (quindi no AJAX),
			lo setto semplicemente come attributo di sessione */
		
		this.getServletContext().getRequestDispatcher("/J1.jsp").forward(req, res);
		return;
    }
} 