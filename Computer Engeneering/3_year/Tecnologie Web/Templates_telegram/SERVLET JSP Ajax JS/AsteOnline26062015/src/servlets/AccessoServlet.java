package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Asta;
import beans.AstaManager;
import beans.Utente;


public class AccessoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AstaManager astaManager = null;

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
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"styles/default.css\" type=\"text/css\"></link>");
		out.println("</head>");
		out.println("<body>");
		if (utente == null) {
//			ServletContext servletContext = getServletContext();
//			RequestDispatcher disp = servletContext.getRequestDispatcher("/login.html");
//			disp.forward(request, response);
			out.println("<script src=\"scripts/forms.js\" type=\"text/javascript\"></script>"+
			"<fieldset><legend>Login</legend>"+
			"<form name=\"login\" action=\"loginServlet\""+
				"onsubmit=\"return isAllCompiled(this)\" method=\"post\">"+
				"<label for=\"nome\">Nome</label>"+
				"<input type=\"text\" name=\"nome\" placeholder=\"nome\" required>"+
				"<br />"+
				"<label for=\"cognome\">Cognome</label>"+
				"<input type=\"text\" name=\"cognome\" placeholder=\"cognome\" required>"+
				"<br />"+
				"<input type=\"submit\" value=\"Login\">"+
			"</form>"+
			"</fieldset>");
		}
		else {
			out.println("<h3> Benvenuto "+utente.getNome()+" "+utente.getCognome()+"</h3>");
		}
		
		String loginerror = (String) request.getAttribute("loginerror");
		if (loginerror != null) {
			out.println("<br/><h3> "+loginerror+"</h3>");
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
		out.println("<br/><br/><h1>Asta in corso:</h1>");
		out.println("<p>"+asta.getNome()+", miglior offerta: "+asta.getBestBid()+", miglior offerente: "+nome + " "+cognome+"</p>");
		
		if (utente != null && astaManager.hasUtente(utente)) {
			out.println("<br/><a href=\""+request.getContextPath()+"/offerta.jsp\"> Fai un'offerta </a>");
		}
		
		Asta oldAsta = astaManager.getLastAsta();
		if (oldAsta != null) {
			out.println("<br/><br/><h1>Ultima Asta conclusa:</h1>");
			out.println("<p>"+oldAsta.getNome()+", offerta vincitrice: "+oldAsta.getBestBid()+", utente vincitore: "+oldAsta.getBestBidder().getNome() + " "+oldAsta.getBestBidder().getCognome()+"</p>");
		}
		
		out.println("</body>");
		out.println("</html>");
	}

}
