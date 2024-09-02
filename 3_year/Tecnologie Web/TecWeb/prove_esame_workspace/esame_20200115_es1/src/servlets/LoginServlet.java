package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Map<String, UserInfo> database;
	Map<String, List<UserInfo>> group_users;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		//mappa per raccogliere le informazioni di ogni utente che sta eseguendo le operazioni
		Map<String, UserInfo> database = new HashMap<>();
		
		//se la mappa non è nulla la salvo nella mia variabile
		if(getServletContext().getAttribute("database") != null) {
			database = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		} else {
			getServletContext().setAttribute("database", database);
		}
		
		// -----------------------------------------------------------------------------------------
		
		//mappa per raccogliere le informazioni di ogni utente e gruppo
		Map<String, List<UserInfo>> group_users = new HashMap<>();
		
		//se la mappa non è nulla la salvo nella mia variabile
		if(getServletContext().getAttribute("group_users") != null) {
			group_users = (Map<String, List<UserInfo>>) getServletContext().getAttribute("group_users");
		} else {
			// inseriamo dei gruppi
			group_users.put("group0", new ArrayList<UserInfo>());
			group_users.put("group1", new ArrayList<UserInfo>());
			group_users.put("group2", new ArrayList<UserInfo>());
			
			// salviamo la mappa
			getServletContext().setAttribute("group_users", group_users);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//dai parametri della request ottengo username e password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String group = request.getParameter("group");
		
		//questi parametri vanno salvati nella sessione corrente
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("group", group);
		
		//dal contesto della servlet poi ottengo il database
		Map<String, UserInfo> database = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		
		// Strutture utili nell'if
		UserInfo u = database.get(username);
		List<UserInfo> utenti = group_users.get(group);
		
		if(username.compareTo("admin")==0 && password.compareTo("admin")==0) {
			//se il login corrisponde a quello dell'admin va reindizzato alla sua personale pagina
			session.setAttribute("logged", true);
			session.setAttribute("registrato", true);
			response.sendRedirect("./admin.jsp");
		} 
		if(database.containsKey(username) && u.getPassword().compareTo(password)==0 && group_users.keySet().contains(group)) {
			// Come scrivo un Date di 60 gg?
			if(u.getDataPasswd().before(new Date())) {
				response.getWriter().print("Password vecchia, riscriverla");
				break;
			}
			
			//se il login corrisponde a quello di un utente e il gruppo esiste va reindizzato alla home degli utenti
			utenti.add(u);
			group_users.put(group, utenti);
			
			// salviamo la mappa
			getServletContext().setAttribute("group_users", group_users);
			
			session.setAttribute("logged", true);
			session.setAttribute("registrato", true);
			response.sendRedirect("./home.jsp");
		}
		else {
			int tentativi = database.get(username).getErrori();
			if(tentativi == 3) {
				database.remove(username, u);
				utenti.remove(u);
				group_users.put(group, utenti);
			}
			
			//altrimenti non è registrato
			session.setAttribute("registrato", false);
			response.sendRedirect("./login.jsp");
		}
	}
}
