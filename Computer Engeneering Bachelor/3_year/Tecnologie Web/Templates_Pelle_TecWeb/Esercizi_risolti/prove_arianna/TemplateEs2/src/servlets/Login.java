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
import java.util.*;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		
		//conta delle sessioni
		List<HttpSession> sessioniAttive = new ArrayList<HttpSession>();
		this.getServletContext().setAttribute("sessioni", sessioniAttive);
		
		// e tutti i settaggi di informazioni utili
		// che posso riprendere o qui o da altre jsp e servlet
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		
		List<HttpSession> sessioniAttive = (List<HttpSession>) this.getServletContext().getAttribute("sessioni");
		if(session.isNew())
			sessioniAttive.add(session);
		
		if(user.compareTo("admin")==0 && pwd.compareTo("admin")==0) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");	
			rd.forward(request, response);
			return; 
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/utente.jsp");	
		rd.forward(request, response);
		return; 
	}

}
