package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UtentiAutenticati;

public class Signin extends HttpServlet {

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		response.setContentType("text/html");

		utenti=(UtentiAutenticati)this.getServletContext().getAttribute("UtentiAutenticati");

		String username= request.getParameter("username").trim();
		String password= request.getParameter("password").trim();

		if(password==null || password.equals("") || username==null || username.equals("")) {
			request.getSession().setAttribute("errSign", -1);  
			request.setAttribute("result","Please enter both username and password. <br/><br/>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
			requestDispatcher.include(request, response);
		} else {
			for(String u: utenti.getUsernames()) {
				if(!u.equals(username)) {
					utenti.addUtente(username, password);
					utenti.findUtente(username, password).setLogged(true);		
					request.getSession().setAttribute("username", username);
					response.sendRedirect("/welcome.jsp");
				}
				else {
					request.getSession().setAttribute("errSign", -2);  
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
					requestDispatcher.forward(request, response);
				}
			}
			
		}

	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		throw new ServletException("This servlet can only be reached via an HTTP POST REQUEST");
	}
}
