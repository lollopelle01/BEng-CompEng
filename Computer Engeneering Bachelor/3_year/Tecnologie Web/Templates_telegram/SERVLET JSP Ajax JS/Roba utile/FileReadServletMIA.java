package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WordProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filename = "/file.txt";
	
    
    @Override
	public void init() throws ServletException {
    	super.init();
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream stream = getServletContext().getResourceAsStream(filename);
		int i, x;
		try {
			i = Integer.parseInt(request.getParameter("i"));
			x = Integer.parseInt(request.getParameter("x"));
		} catch (Exception e) {
			throw new ServletException("Errore nel parsing o parametri non impostati",e);
		}
		BufferedReader reader = new BufferedReader (new InputStreamReader(stream));
		int j=0;
		String line;
		while ((line=reader.readLine()) != null && j<i*x) {
			j++;
		}
		j=0;
		while ((line=reader.readLine()) != null && j<x) {
			response.getWriter().println(line.toLowerCase());
			j++;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
