package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

public class RegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6843401790722980795L;
	Map<String, UserInfo> database;
	Map<String, List<UserInfo>> group_users;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		//mappa per raccogliere le informazioni di ogni utente che sta eseguendo le operazioni
		Map<String, UserInfo> users = new HashMap<>();
		
		//se la mappa non è nulla la salvo nella mia variabile
		if(getServletContext().getAttribute("database") != null) {
			users = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		} else {
			getServletContext().setAttribute("database", users);
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
		// Prendiamo il database condiviso tra le servlet
		database = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String group = request.getParameter("group");
		
		HttpSession session = request.getSession();
		
		if(database.get(username) != null) {
			session.setAttribute("logged", "ERRORE: Utente già esistente!");
			response.sendRedirect("./Registration.jsp");
		} else {
			UserInfo user = new UserInfo();
			user.setUsername(username);
			user.setPassword(password);
			user.setDataPasswd(new Date());
			user.setErrori(0);
			database.put(username, user);
			group_users.putIfAbsent(key, value)
			//updateDatabaseFile();
			session.setAttribute("registrato", true);
			response.sendRedirect("./home.jsp");
		}
	}

	
/** VERRA' VISTO IN PAW **/
//	private synchronized void updateDatabaseFile() {
//		String path = getServletContext().getRealPath("./resources/database.txt");
//		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
//			System.out.println("Aggiornamento database...");
//			for(String user : database.keySet()) {
//				System.out.println(user);
//				writer.write(database.get(user).getUsername() + "#####" + database.get(user).getPassword() + "\n");
//			}
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
