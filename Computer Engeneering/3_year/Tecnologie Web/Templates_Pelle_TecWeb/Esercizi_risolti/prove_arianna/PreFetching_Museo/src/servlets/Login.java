package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utente;

import java.util.*;

public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		Utente u = new Utente(user, pwd);
		
		HttpSession session = request.getSession();
		u.setLogged(true);
		
		session.setAttribute("user", u);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/museo.jsp");	
		rd.forward(request, response);
		return; 
	}

}
