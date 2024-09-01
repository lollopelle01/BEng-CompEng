package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class autoCompleta extends HttpServlet{

private static final long serialVersionUID = 1L;

private Gson gson;

	@Override
	public void init() {
		this.gson = new Gson();
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Film> titoli = (Map<String, Film>) this.getServletContext().getAttribute("titoli");

		Map<String, List<Film>> attori = (Map<String, List<Film>>) this.getServletContext().getAttribute("attore");
		
		String ricerca = request.getParameter("stringa");
		System.out.println(ricerca);
		
		OperationResp risultato = new OperationResp();
		
		for(String t : titoli.keySet()) {
			if(t.startsWith(ricerca)) {
				System.out.println(t);
				risultato.getCorrispondenze_film().add(t);
			}
		}
		
		for(String a : attori.keySet()) {
			if(a.contains(ricerca)) {
				risultato.getCorrispondenze_attori().add(a);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(risultato));
		
		HttpSession session= request.getSession();
		
		if(session.getAttribute("counter") != null) {
			System.out.println(session.getAttribute("counter"));
		}

	}
}
