package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Giocatore;
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
		//mappa di giocatori, string = username, giocatore=denari, carte, username
		Map<String, Giocatore> giocatori = new HashMap<>();
		getServletContext().setAttribute("giocatori", giocatori);
		
		//attributo per la consegna delle carte
		boolean prese[] = new boolean[30];
		for(int i=0; i<30; i++) {
			prese[i] = false;
		}
		getServletContext().setAttribute("carte_prese", prese);
		
		//attributo per la vendita di una carta con valore in vendita e carta in vendita
		getServletContext().setAttribute("venditaInCorsoDi", "");
		getServletContext().setAttribute("cartaInVendita", 0);
		getServletContext().setAttribute("offertaAttuale", 0);
		getServletContext().setAttribute("offertaAttualeDi", "");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./login.jsp");
	}
}
