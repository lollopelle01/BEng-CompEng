package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;
import beans.UtentiAutenticati;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private UtentiAutenticati utenti;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		
		utenti = (UtentiAutenticati)this.getServletContext().getAttribute("utentiAutenticati");
		if(utenti == null) {
			utenti = new UtentiAutenticati();
			this.getServletContext().setAttribute("utentiAutenticati", utenti);
		}
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		Utente u = new Utente(username, pwd);
		
		utenti = (UtentiAutenticati)this.getServletContext().getAttribute("utentiAutenticati");
		
		//verifica dell'utente
		if(utenti.getUsernames().contains(username)) { 
			
			if(utenti.isAutenticato(username, pwd)) {
				
				if(username.equals("admin") && pwd.equals("admin")) {
					utenti.findUtente(username, pwd).setAdmin(true);
					utenti.findUtente(username, pwd).setLogged(true);
					request.getSession().setAttribute("username", username);
					
					response.sendRedirect(request.getContextPath()+"/admin.jsp");
					return;
				}
				
				if(!utenti.findUtente(username, pwd).isLogged()) {
					utenti.findUtente(username, pwd).setLogged(true);
					request.getSession().setAttribute("username", username);
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getContextPath()+"/welcome.jsp");
					requestDispatcher.forward(request, response);
				}
			}
			else {
				u.setTentativi(u.getTentativi()+1);
				if(u.getTentativi() >= 3) {
					response.sendRedirect("pages/access_denied.html");
					return;
				} else {
					request.getSession().setAttribute("errLog", -2);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
			}
		}
		else {
			request.getSession().setAttribute("errLog", -1);  
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		throw new ServletException("This servlet can only be reached via an HTTP POST REQUEST");
	}
}
