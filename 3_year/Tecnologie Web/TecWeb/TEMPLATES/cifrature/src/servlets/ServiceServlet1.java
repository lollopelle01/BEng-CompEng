package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Result;

public class ServiceServlet1 extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3403693893991634090L;
	Random random;
	Gson gson;
	@Override
	public void init() throws ServletException {
		this.random = new Random(System.currentTimeMillis());
		this.gson = new Gson();
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		String text = req.getParameter("text");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> anagrammi = new ArrayList<>();
		String vocals = "aeiou";
		
		//per 5 permutazioni diverse
		for(int i = 0; i < 5; i++) {
			String anagramma = "";
			
			//esegui fintanto che l'anagramma formato non è contenuto nel risultato e non si sono superati i 50 tentativi
			do {
				boolean[] position = new boolean[text.length()]; //Mi dice se ho già preso il carattere in posizione i (true= preso)
				//inizializzo l'array
				for(int j = 0; j < text.length(); j++) {
					position[j] = false;
				}
				
				//finche il mio anagramma non ha la sessa dimensione della parola arrivata
				while(anagramma.length() != text.length()) {
					int randomPos = random.nextInt(text.length());
					
					if(position[randomPos] == false) { //se il carattere selezionato non è gia stato usato allora
						String randomChar = text.charAt(randomPos) + ""; 
						
						if(!(anagramma.length() == 0 && !vocals.contains(randomChar))) {//se il primo carattere è una vocale allora
							anagramma += randomChar;//aggiungo il carattere alla mia parola
							position[randomPos] = true; //e aggiorno l'array di booleean per la posizione
						}
					}
				}
			} while(anagrammi.contains(anagramma));
			
			//a questo punto posso inserire l'anagramma nel risultato
			anagrammi.add(anagramma);
			System.out.println("Servlet1: Anagramma " + i + ": " + anagramma);
		}
		
		Result result = new Result();
		result.setAnagrammi(anagrammi);
		resp.getWriter().write(gson.toJson(result));
	}
}
