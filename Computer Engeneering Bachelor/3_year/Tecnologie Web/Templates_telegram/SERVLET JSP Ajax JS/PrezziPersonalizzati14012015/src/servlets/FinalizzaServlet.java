package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.*;


public class FinalizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Catalogo catalogo = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		synchronized (application) {
			catalogo = (Catalogo) application.getAttribute("catalogo");
			if ( catalogo == null) {
				catalogo = new Catalogo();
				application.setAttribute("catalogo", catalogo);
			}
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			if (utente.getCarrello().size() > 0 && catalogo.checkAndBuy(utente.getCarrello())) {
				utente.finalizza();
				utente.popolaSessioneCorrente(catalogo);
				utente.svuotaCarello();
				request.setAttribute("message", "Acquisto completato!");
			} else if (utente.getCarrello().size() == 0){
				request.setAttribute("message", "Il carrello era vuoto!");
			} else {
				request.setAttribute("message", "Errore!");
				utente.svuotaCarello();
			}
			getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
