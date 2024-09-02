package servlets;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Museo;
import beans.Risultato;
import beans.Stanza;

public class S1 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
		
		Museo m = (Museo)this.getServletContext().getAttribute("museo");
		if(m == null) {
			m = new Museo();
			this.getServletContext().setAttribute("museo", m);
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		int n = Integer.parseInt(request.getParameter("n"));   
		int n1 = Integer.parseInt(request.getParameter("n1"));
		int n2 = Integer.parseInt(request.getParameter("n2"));
		
		// i valori n, n1, n2 possono essere o -1 o il numero della stanza da richiedere
		// quindi se n = -1, non viene scaricata la stanza
		
		Museo m = (Museo)this.getServletContext().getAttribute("museo");
		Risultato r = new Risultato();
		
		for(Stanza s : m.getMuseo()) {
			if(n == s.getNumber())
				r.setStanza(0, s);
			if (n1 == s.getNumber())
				r.setStanza(1, s);
			if(n2 == s.getNumber())
				r.setStanza(2, s);
		}
		
		response.getWriter().println(gson.toJson(r)); //in responseText
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
	}
}
