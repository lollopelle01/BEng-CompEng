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

import beans.CounterRichieste;
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
		
		Map<String, Integer> richieste = new HashMap<>();
		getServletContext().setAttribute("richieste", richieste);
		getServletContext().setAttribute("counter", new CounterRichieste());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./login.jsp");
	}
}
