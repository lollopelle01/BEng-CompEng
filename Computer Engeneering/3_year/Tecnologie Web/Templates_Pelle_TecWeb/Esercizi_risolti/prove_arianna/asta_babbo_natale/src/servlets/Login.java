package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Asta;
import beans.Utente;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		
		Asta asta = (Asta)this.getServletContext().getAttribute("asta");
		if(asta == null) {
			asta = new Asta();
			this.getServletContext().setAttribute("asta", asta);
		}	
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		Utente u = new Utente(user, pwd);
		
		if(user.compareTo("admin")==0 && pwd.compareTo("admin")==0) {
			u.setAdmin(true);
			session.setAttribute("user", u);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");	
			rd.forward(request, response);
			return; 
		}
		
		//se non e' admin lo aggiungo all'asta
		Asta asta = (Asta)app.getAttribute("asta");
		asta.addUtente(u);
		session.setAttribute("user", u);
		
		//se il numero di utenti autenticati e' >=2 l'asta puo' cominciare
		if(asta.getUtenti().size() >=2) {
			asta.init();
			
			//inoltro la richiesta alla S1 che si occupa dell'estrazione del regalo
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/S1");	
			rd.forward(request, response);
			return;
		}
		
		
		//se è il primo utente autenticato intanto lo mando in attesa alla pagina delle offerte
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/welcome.jsp");	
		rd.forward(request, response);
		return; 
	}

}
