package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String canzone = (String)request.getParameter("blocca");
		this.getServletContext().setAttribute("bloccaDownload", canzone);
		
		//metto l'attributo in servletContext così lo possono vedere 
		//tutte le servlet che stanno eseguendo i download
		
		return;
	}

}
