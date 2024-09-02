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

public class ServiceServlet2 extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4868321428757122655L;
	
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
		
		List<String> anagrammi = new ArrayList<>();
		String vocals = "aeiou";
		
		for(int i = 0; i < 10; i++) {
			String anagramma = "";
			int attempts = 0;
			do {
				attempts++;
				boolean[] position = new boolean[text.length()]; //Mi dice se ho già preso il carattere in posizione i
				for(int j = 0; j < text.length(); j++) {
					position[j] = false;
				}
				
				while(anagramma.length() != text.length()) {
					int randomPos = random.nextInt(text.length());
					if(position[randomPos] == false) {
						String randomChar = text.charAt(randomPos) + "";
						if(!(anagramma.length() == 0 && vocals.contains(randomChar))) { //se il primo carattere non è una vocale => consonante
							anagramma += randomChar;
							position[randomPos] = true;
						}
					}
				}
			} while(anagrammi.contains(anagramma) && attempts < 100);
			//Se al cinquantesimo tentativo non trova un anagramma diverso, sono finiti (o si è bloccato)
			if(attempts >= 50) {
				break;
			}
			anagrammi.add(anagramma);
			System.out.println("Anagramma " + i + ": " + anagramma);
		}
		
		Result result = new Result();
		result.setAnagrammi(anagrammi);
		resp.getWriter().write(gson.toJson(result));
	}
}
