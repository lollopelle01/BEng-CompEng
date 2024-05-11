package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (isAdmin(username, password)) {
			Utente utente = new Utente();
			utente.setUsername(username);
			utente.setPassword(password);
			request.getSession().setAttribute("utente", utente);
			request.getSession().setAttribute("authorization", "yes");
			getServletContext().getRequestDispatcher("/nuovoLibro.jsp").forward(request, response);
		}
		else {
			request.getSession().setAttribute("authorization", "no");
			request.setAttribute("loginerror", "Utente non valido");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}
	
	//Metodo mock per la gestione delle credenziali di accesso.
	private boolean isAdmin (String username, String password) {
		if (username != null && password != null && username.equals("admin") && password.equals("admin"))
			return true;
		else
			return false;
	}

}
