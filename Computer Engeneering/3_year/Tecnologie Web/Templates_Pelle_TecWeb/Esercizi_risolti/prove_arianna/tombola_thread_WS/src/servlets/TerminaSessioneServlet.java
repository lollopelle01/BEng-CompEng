package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.DBUtenti;

public class TerminaSessioneServlet extends HttpServlet{
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		if(!username.equals("admin")) {
			DBUtenti dbUtenti = (DBUtenti) this.getServletContext().getAttribute("DBUtenti");
			dbUtenti.findUtente(username).setSessioneAttiva(false);
		}
		getServletContext().getRequestDispatcher("/mainAdmin.jsp").forward(request, response);
	}
	
}
