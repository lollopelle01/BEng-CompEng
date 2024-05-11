package servlets;


import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570539459993871887L;

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		Map<String, User> database = (Map<String, User>) getServletContext().getAttribute("database");
		if(database == null) {
			resp.sendRedirect("./init");
		}
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String result = "";
		
		HttpSession session = req.getSession();
		
		if(username.equals("admin") && password.equals("admin")) {
			result = "Admin.";
			session.setAttribute("logged", result);
			session.setAttribute("username", username);
			
			getServletContext().setAttribute(username, "loggato");
			resp.sendRedirect("./Admin.jsp");
		}
		else {
			if(database.get(username) == null) {
				result = "Accesso negato: Utente inesistente.";
				session.setAttribute("logged", result);
				resp.sendRedirect("./registration.jsp");
				
			} else if(!database.get(username).getPassword().equals(password)) {
				result = "Accesso negato: Password errata.";
				session.setAttribute("logged", result);
				resp.sendRedirect("./login.jsp");
			} else {
				result = "Logged.";
				session.setAttribute("logged", result);
				session.setAttribute("username", username);
				
				getServletContext().setAttribute(username, "loggato");
				resp.sendRedirect("./home.jsp");
			}
		}
		
	}
}
