package servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570539459993871887L;

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username.equals("admin") && password.equals("admin")) {
			resp.sendRedirect("./Admin.jsp");
		}
		else
			resp.sendRedirect("./home.jsp");
	}
}
