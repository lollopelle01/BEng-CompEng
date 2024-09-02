package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserInfo;

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		//mappa per raccogliere le informazioni di ogni utente che sta eseguendo le operazioni
		Map<String, UserInfo> users = new HashMap<>();
		
		//se la mappa non è nulla la salvo nella mia variabile
		if(getServletContext().getAttribute("database") != null) {
			users = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		} else {
			//altrimenti va inizializzata
			getServletContext().setAttribute("database", users);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//dai parametri della request ottengo username e password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//questi parametri vanno salvati nella sessione corrente
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("logged", true);
		
		//dal contesto della servlet poi ottengo il database
		Map<String, UserInfo> users = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		
		if(username.equals("admin") && password.equals("admin")) {
			//se il login corrisponde a quello dell'admin va reindizzato alla sua personale pagina
			response.sendRedirect("./Admin.jsp");
		} else {
			//altrimenti viene indirizzato alla normale pagina home, dopo aver salvato i sui dati nel database
			UserInfo user = new UserInfo();
			user.setUsername(username);
			user.setPassword(password);
			user.setCounter(0);
			
			users.put(username, user);
			
			response.sendRedirect("./home.jsp");
		}
	}
}
