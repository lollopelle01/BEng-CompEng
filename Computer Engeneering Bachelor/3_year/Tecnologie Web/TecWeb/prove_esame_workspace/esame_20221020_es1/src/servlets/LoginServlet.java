package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//dai parametri della request ottengo username e password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int numTavolo = Integer.parseInt(request.getParameter("tavolo"));
		
		//questi parametri vanno salvati nella sessione corrente
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("numTavolo", numTavolo);
		
		if(username.compareTo("admin")==0 && password.compareTo("admin")==0) {
			//se il login corrisponde a quello dell'admin va reindizzato alla sua personale pagina
			session.setAttribute("logged", true);
			response.sendRedirect("./admin.jsp");
		} 
		else if(username.compareTo("cameriere")==0 && password.compareTo("cameriere")==0) {
			//se il login corrisponde a quello dell'admin va reindizzato alla sua personale pagina
			session.setAttribute("logged", true);
			response.sendRedirect("./cameriere.jsp");
		} 
		else {
			//altrimenti non è registrato
			response.sendRedirect("./home.jsp");
		}
	}
}
