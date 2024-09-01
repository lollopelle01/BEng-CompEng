package servlets;


import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class IntegralCalculator extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		double result = 0;
		
		int x = Integer.parseInt(request.getParameter("x"));
		int y = Integer.parseInt(request.getParameter("y"));
		result = (y*y) - (x*x);

		response.getWriter().println(result); //in responseText
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
	}
}
