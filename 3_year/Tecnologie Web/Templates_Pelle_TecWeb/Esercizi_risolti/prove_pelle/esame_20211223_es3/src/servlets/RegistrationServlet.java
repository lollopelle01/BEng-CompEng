package servlets;

import java.io.IOException;
import java.util.HashMap;
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prendiamo il database condiviso tra le servlet
		database = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		
		// Se utente va subito a registrarsi, il database non esiste
		if (database == null) { 
			database = new HashMap<String, UserInfo>(); 
			getServletContext().setAttribute("database", database);
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		if(database.get(username) != null) {
			session.setAttribute("reg_msg", "ERRORE: Utente già esistente!");
			response.sendRedirect("./login.jsp");
		} else {
			UserInfo user = new UserInfo();
			user.setUsername(username);
			user.setPassword(password);
			database.put(username, user);
			//updateDatabaseFile();
			session.setAttribute("reg_msg", "SUCCESSO: Utente registrato!");
			response.sendRedirect("./login.jsp");
		}
	}

}
