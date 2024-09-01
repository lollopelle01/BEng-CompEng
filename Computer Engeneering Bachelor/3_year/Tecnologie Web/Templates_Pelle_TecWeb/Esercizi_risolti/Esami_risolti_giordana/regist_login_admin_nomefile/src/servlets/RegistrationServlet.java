package servlets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

public class RegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6843401790722980795L;
	Map<String, User> database;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		database = (Map<String, User>) getServletContext().getAttribute("database");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		if(database.get(username) != null) {
			session.setAttribute("logged", "ERRORE: Utente già esistente!");
			response.sendRedirect("./registration.jsp");
		} else {
			User user = new User();
			user.setusername(username);
			user.setPassword(password);
			database.put(username, user);
			updateDatabaseFile();
			session.setAttribute("logged", "Logged.");
			session.setAttribute("username", username);
			response.sendRedirect("./home.jsp");
		}
	}

	private synchronized void updateDatabaseFile() {
		String path = getServletContext().getRealPath("./resources/database.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			System.out.println("Aggiornamento database...");
			for(String user : database.keySet()) {
				System.out.println(user);
				writer.write(database.get(user).getusername() + "#####" + database.get(user).getPassword() + "\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
