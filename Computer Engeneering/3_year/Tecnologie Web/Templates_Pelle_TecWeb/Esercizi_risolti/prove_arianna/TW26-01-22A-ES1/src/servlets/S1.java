package servlets;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;
import beans.UtentiAutenticati;


public class S1 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		
		//contesto -> scope:application
		List<LocalDateTime> list=(List<LocalDateTime>)this.getServletContext().getAttribute("richiesteTime");
		if(list == null) {
			list=new ArrayList<LocalDateTime>();
			this.getServletContext().setAttribute("richiesteTime", list);
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		List<LocalDateTime> list=(List<LocalDateTime>)this.getServletContext().getAttribute("richiesteTime");
		this.getServletContext().setAttribute("richiesteTime", list.add(LocalDateTime.now()));
		
		String username = (String)request.getSession().getAttribute("username");
		UtentiAutenticati utenti = (UtentiAutenticati)this.getServletContext().getAttribute("utentiAutenticati");
		Utente u = utenti.getUtenteByName(username);
		u.incrementaRichieste();
		
		String filename = request.getParameter("filename").trim();
		String alphabet = "abcdefghijklmnopqrstuvwxtz";
		
		Random r = new Random();
		int i = r.nextInt(alphabet.length()+1);
		
		char c = alphabet.charAt(i), cc;
		
		File f = new File(filename+".txt");
		FileReader fr = new FileReader(f);
		String textChanged = "";
		
		try {
			while( (cc = (char)fr.read()) > 0) {
				if(cc != c)
					textChanged += cc;
			}
			fr.close();
		} catch (Exception e) {
			throw new ServletException();
		}
		
		request.setAttribute("text", textChanged);
        
	    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/J1.jsp");
		dispatcher.forward(request, response);
        return;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		throw new ServletException("This servlet can only be reached via an HTTP POST REQUEST");
	}
}
