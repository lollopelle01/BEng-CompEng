package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		Utente u = new Utente(user, pwd);
		u.setLogged(true);
		
		if(user.compareTo("admin")==0 && pwd.compareTo("admin")==0) {
			u.setAdmin(true);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");	
			rd.forward(request, response);
			return; 
		}
		
		request.getSession().setAttribute("user", u);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/utente.jsp");	
		rd.forward(request, response);
		return; 
	}

}
