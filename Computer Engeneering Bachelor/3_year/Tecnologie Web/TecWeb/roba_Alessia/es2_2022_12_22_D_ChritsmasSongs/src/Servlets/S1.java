package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	@Override
	public void init(ServletConfig config) throws ServletException  { 
		super.init(config);
		g = new Gson();
		
		List<Canzone> ChristmasSongs = new ArrayList<>();
		
		//String nome, double min, String genere
		ChristmasSongs.add(new Canzone("Last Christmas", 					3.44, "mp3", "001010101010"));
		ChristmasSongs.add(new Canzone("I give you my heart", 				2.55, "mp3", "001001101010"));
		ChristmasSongs.add(new Canzone("I hate Christmas", 					1.32, "mp4", "0000010001010"));
		ChristmasSongs.add(new Canzone("We want the snow", 					5.35, "mp4", "00100101010"));
		ChristmasSongs.add(new Canzone("Panettone vs Pandoro", 				7.11, "mp3", "0011010101010"));
		ChristmasSongs.add(new Canzone("Christmas",		 					3.44, "mp4", "001110000000"));
		ChristmasSongs.add(new Canzone("So much gifts", 					2.55, "mp4", "111010010110"));
		ChristmasSongs.add(new Canzone("Ma quante canzoni devo mettere",	1.32, "mp4", "11110101010101"));
		ChristmasSongs.add(new Canzone("Giorno del ringraziamento",			5.35, "mp3", "1111111111110"));
		ChristmasSongs.add(new Canzone("Tacchino vs Lasagna della nonna", 	7.11, "mp3", "11110000001111"));	
		ChristmasSongs.add(new Canzone("I can see in your eyes", 			3.44, "mp3", "110101010100000"));
		ChristmasSongs.add(new Canzone("Mi amerai a Natale", 				2.55, "mp3", "10100111110"));
		ChristmasSongs.add(new Canzone("It is snows on Los Angeles", 		1.32, "mp4", "11110100101010"));
		ChristmasSongs.add(new Canzone("London Winter", 					5.35, "mp4", "1111111111110"));
		ChristmasSongs.add(new Canzone("More more more snow", 				7.11, "mp4", "0000000000001"));
		ChristmasSongs.add(new Canzone("Last song of christmas", 			1.12, "mp2", "00011010000001"));
		
		this.getServletContext().setAttribute("ChristmasSongs", ChristmasSongs);
	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println(req.getParameter("numero"));
		int numero = Integer.parseInt(req.getParameter("numero"));
		
		List<Canzone> ChristmasSongs = (ArrayList<Canzone>) this.getServletContext().getAttribute("ChristmasSongs");
		List<Canzone> download = new ArrayList<Canzone>();
		
		for(int i = 1; i <= ChristmasSongs.size()-1; i++) {
			if(i % numero == 0) {
				download.add(ChristmasSongs.get(i));
			}
		}
		
		/* CONVERSIONE IN JSON: voglio inviare dati in formato JSON
		 * 	-> se usi AJAX:		crei il tuo bean Risp con tutte le variabili da inviare
		 * 						e lo converti in formato JSON per inviarlo
		 * 	-> se non usi AJAX: lo puoi settare a livello di sessione utente,
		 * 						sempre con la struttura/bean contenente le info nec.*/

		/*********** AJAX *************/
		Risposta risp = new Risposta(download, numero);
		res.getWriter().write(g.toJson(risp));
    }
} 