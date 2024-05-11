package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Socio;
import beans.Torneo;
import beans.Circolo;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private Circolo circolo;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		
		circolo = (Circolo)this.getServletContext().getAttribute("circolo");
		if(circolo == null) {
			circolo = new Circolo();
			this.getServletContext().setAttribute("circolo", circolo);
		}
		
		Torneo torneo = (Torneo)this.getServletContext().getAttribute("torneo");
		if(torneo == null) {
			torneo = new Torneo();
			this.getServletContext().setAttribute("torneo", torneo);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		Socio u = new Socio(username, pwd);
		
		circolo = (Circolo)this.getServletContext().getAttribute("circolo");
		
		//verifica dell'utente
		if(circolo.getUsernames().contains(username)) { 
			
			if(circolo.isAutenticato(username, pwd)) {
				
				if(username.equals("admin") && pwd.equals("admin")) {
					circolo.findSocio(username, pwd).setAdmin(true);
					circolo.findSocio(username, pwd).setLogged(true);
					request.getSession().setAttribute("username", username);
					
					response.sendRedirect(request.getContextPath()+"/admin.jsp");
					return;
				}
				
				if(!circolo.findSocio(username, pwd).isLogged()) {
					circolo.findSocio(username, pwd).setLogged(true);
					request.getSession().setAttribute("username", u); 
					
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
