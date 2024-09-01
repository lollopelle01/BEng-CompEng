package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Asta;
import beans.AstaInfoAjax;
import beans.AstaManager;
import beans.OffertaAjax;
import beans.Utente;


public class JsonastaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AstaManager astaManager = null;
	Gson gson = new Gson();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		synchronized (application) {
			astaManager = (AstaManager) application.getAttribute("astamanager");
			if ( astaManager == null) {
				astaManager = new AstaManager();
				application.setAttribute("astamanager", astaManager);
			}
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			getServletContext().getRequestDispatcher("/accessoServlet").forward(request, response);
		}
		if (!astaManager.hasUtente(utente)) {
			request.setAttribute("loginerror", "Non sei autorizzato!");
			getServletContext().getRequestDispatcher("/accessoServlet").forward(request, response);
		}
		
		String serializedObj = request.getParameter("offerta");
		if (serializedObj != null) {
			OffertaAjax offerta = gson.fromJson(serializedObj, OffertaAjax.class);
			astaManager.getCurrentAsta().makeOffer(offerta.getOfferta(), utente);
		}
		
		Asta asta = astaManager.getCurrentAsta();
		String nome;
		String cognome;
		if (asta.getBestBidder() != null) {
			nome = asta.getBestBidder().getNome();
			cognome = asta.getBestBidder().getCognome();
		}
		else {
			nome = "ancora";
			cognome = "nessuno";
		}
		AstaInfoAjax astaAjax = new AstaInfoAjax(nome, cognome, asta.getNome(), asta.getBestBid());
		String gsonResponse = gson.toJson(astaAjax);
		
		response.getWriter().println(gsonResponse);
	}

}
