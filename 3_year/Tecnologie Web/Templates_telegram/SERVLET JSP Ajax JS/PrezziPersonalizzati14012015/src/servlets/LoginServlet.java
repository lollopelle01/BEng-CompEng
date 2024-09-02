package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Catalogo;
import beans.InvalidatorThread;
import beans.Utente;
import beans.UtentiManager;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UtentiManager utentiManager = null;
	private Catalogo catalogo = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		synchronized (application) {
			utentiManager = (UtentiManager) application.getAttribute("utentiManager");
			if ( utentiManager == null) {
				utentiManager = new UtentiManager();
				application.setAttribute("utentiManager", utentiManager);
			}
			catalogo = (Catalogo) application.getAttribute("catalogo");
			if ( catalogo == null) {
				catalogo = new Catalogo();
				application.setAttribute("catalogo", catalogo);
			}
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		if (checkCredenziali(nome, cognome)) {
			Utente utente = utentiManager.getUtente(nome, cognome, catalogo);
			InvalidatorThread thread = new InvalidatorThread(request.getSession());
			thread.start();
			request.getSession().setAttribute("utente", utente);
		}
		else {
			request.setAttribute("loginerror", "Utente non valido");
		}
		getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
		
	}
	
	//Metodo mock per la gestione delle credenziali di accesso.
	private boolean checkCredenziali (String utente, String password) {
		if (utente != null && password != null)
			return true;
		else
			return false;
	}

}
