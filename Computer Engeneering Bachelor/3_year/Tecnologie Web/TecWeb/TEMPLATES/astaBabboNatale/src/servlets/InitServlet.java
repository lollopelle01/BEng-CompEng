package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Acquirente;
import beans.Offerta;
import beans.Regalo;
import beans.User;

public class InitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gson gson;
	Map<String, User> database;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		String path = getServletContext().getRealPath("/resources/database.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			this.database = new HashMap<>();
			String line;
			while((line = reader.readLine()) != null) {
				String[] info = line.split("#####");
				User user = new User();
				user.setusername(info[0]);
				user.setPassword(info[1]);
				System.out.println(user);
				database.put(info[0], user);
			}
			reader.close();
			getServletContext().setAttribute("database", database);
			getServletContext().setAttribute("gson", gson);
			System.out.println("Server inizializzato correttamente.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//mappa di acquirenti, string = username, Acquirente= username, denari, list regali
		Map<String, Acquirente> acquirenti = new HashMap<>();
		getServletContext().setAttribute("acquirenti", acquirenti);
		
		//List dei 10 regali da mettere all'asta
		List<Regalo> regali = new ArrayList<>();
		
		regali.add(new Regalo("cappello", 20, "un utile cappello invernale"));
		regali.add(new Regalo("guanti", 5, "un utile paio di guanti invernale"));
		regali.add(new Regalo("moto", 70, "la famosissima moto 56 del dottore"));
		regali.add(new Regalo("ipad", 50, "l'ultimo ipad apple"));
		regali.add(new Regalo("telefono", 40, "un classico smartphone"));
		regali.add(new Regalo("pc", 60, "l'avanguardia della tecnologia"));
		regali.add(new Regalo("monopoli", 15, "bestemmia a egioca anche tu a monopoli"));
		regali.add(new Regalo("esame", 99, "prendi questo meritato 30 al modico prezzo di 99 denari"));
		regali.add(new Regalo("mouse", 18, "mouse da gamin scontato"));
		regali.add(new Regalo("tastiera", 22, "tastiera meccanica rgb"));
		
		getServletContext().setAttribute("regali", regali);
		
		//attributo per la durata dell'asta
		getServletContext().setAttribute("start", null);
		
		//attributo per la verifica di un oggetto all'asta
		getServletContext().setAttribute("asta", null);
		
		getServletContext().setAttribute("offerta", new Offerta(0, ""));
		
		
		//attributo per la durata dell'asta di ogni regalo
		getServletContext().setAttribute("timer_asta", null);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./login.jsp");
	}
}
