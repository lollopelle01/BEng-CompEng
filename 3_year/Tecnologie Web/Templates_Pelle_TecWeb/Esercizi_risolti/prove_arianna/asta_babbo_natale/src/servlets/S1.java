package servlets;


import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Asta;
import beans.Regalo;

public class S1 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {		
		super.init(conf);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		//questa servlet si occupa di estrarre il regalo
		
		ServletContext app = this.getServletContext();
		Asta asta = (Asta)app.getAttribute("asta");
		
		String fine = "";
		if(asta.getRegaliDavendere().size() == 0) {
			fine = "Asta terminata, tutti i regali sono stati venduti!";
			app.setAttribute("msg", fine);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/welcome.jsp");	
			rd.forward(request, response);
			return;
		}
		
		Random random = new Random();
		Regalo r = asta.getRegaliDavendere().get(random.nextInt(asta.getRegaliDavendere().size()));
		
		asta.setInvendita(r);
		asta.getRegaliDavendere().remove(r);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/welcome.jsp");	
		rd.forward(request, response);
		return;

	}
	
}
