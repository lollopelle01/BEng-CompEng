package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;
import beans.Whiteboard;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Whiteboard whiteboard;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = this.getServletContext();
		synchronized (application) {
			//Inizializzazione stato di applicazione
			whiteboard = (Whiteboard) application.getAttribute("whiteboard");
			if (whiteboard == null) {
				whiteboard = new Whiteboard();
				application.setAttribute("whiteboard", whiteboard);
			}
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username != null && password != null && username.length() > 0 && password.length() > 0) {
			Utente utente = new Utente(username, password, whiteboard.getVersion());
			request.getSession().setAttribute("utente", utente);
			if (isAdmin(username, password))
				request.getSession().setAttribute("authorization", "yes");
			else
				request.getSession().setAttribute("authorization", "no");
			getServletContext().getRequestDispatcher("/whiteboard.jsp").forward(request, response);
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
