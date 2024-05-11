package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Beans.Risposta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class S1 extends HttpServlet {

    private static final long serialVersionUID = 1L;
	
    Gson g = new Gson();
    
	@Override
	public void init(ServletConfig config) throws ServletException  { 
        super.init(config);
        
    
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String parola = (String) req.getParameter("parola");
		String servlet = (String) req.getParameter("servlet");
		
		String[] anagrammi = createAnagramma(parola);
		/*for(int i = 0; i < anagrammi.length; i++) {
			System.out.println(anagrammi[i]);
		}*/
		res.getWriter().println(g.toJson(new Risposta(servlet, anagrammi)));
    }
	
	
	public String[] createAnagramma(String parola) {
		
		List<String> anagrammi = new ArrayList<>();
		
		Random random = new Random();
		String ambiente = "aeiou"; //serve per l'iniziale della parola
		
		for(int i = 0; i < 10; i++) {
			String anagramma = "";
			
			//esegui fintanto che l'anagramma formato non è contenuto nel risultato e non si sono superati i 50 tentativi
			do {
				boolean[] position = new boolean[parola.length()]; //Mi dice se ho già preso il carattere in posizione i (true= preso)
				//inizializzo l'array
				for(int j = 0; j < parola.length(); j++) {
					position[j] = false;
				}
				
				//finche il mio anagramma non ha la sessa dimensione della parola arrivata
				while(anagramma.length() != parola.length()) {
					int randomPos = random.nextInt(parola.length());
					
					if(position[randomPos] == false) { //se il carattere selezionato non è gia stato usato allora
						String randomChar = parola.charAt(randomPos) + ""; 
						
						if(!(anagramma.length() == 0 && !ambiente.contains(randomChar))) {//se il primo carattere è una vocale allora
							anagramma += randomChar;//aggiungo il carattere alla mia parola
							position[randomPos] = true; //e aggiorno l'array di booleean per la posizione
						}
					}
				}
			} while(anagrammi.contains(anagramma));
			
			//a questo punto posso inserire l'anagramma nel risultato
			anagrammi.add(anagramma);
		}
		
		String[] array = anagrammi.toArray(new String[0]);
		
		return array;
	}
} 