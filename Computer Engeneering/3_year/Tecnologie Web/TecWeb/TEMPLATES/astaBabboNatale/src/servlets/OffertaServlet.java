package servlets;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.*;

public class OffertaServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7554977297793320847L;
	Gson gson;
	Random random;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		this.random = new Random(System.currentTimeMillis());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();

			Map<String, Acquirente> acquirenti = (Map<String, Acquirente>) getServletContext().getAttribute("acquirenti");
	    	
	    	List<Regalo> regali = (List<Regalo>) getServletContext().getAttribute("regali");
	    	
	    	String username = (String) session.getAttribute("username");
	    	
	    	Regalo regalo = (Regalo) getServletContext().getAttribute("asta");
	    	
	    	Offerta attuale = (Offerta) getServletContext().getAttribute("offerta");
	    	
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
	    	if(getServletContext().getAttribute("timer_asta")!=null && 
	    			Duration.between((LocalDateTime) getServletContext().getAttribute("timer_asta"), LocalDateTime.now()).toSeconds()>60) {
	    		acquirenti.get(attuale.getUsername()).addRegalo(regalo);
	    		
	    		getServletContext().setAttribute("asta", null);
	    		getServletContext().setAttribute("offerta", new Offerta(0, ""));
	    		
	    	}else {
	    		//si ottiene l'offerta fatta dall'username e si elabora
		    	int offerta = Integer.parseInt((String) req.getParameter("offerta"));
		    	
		    	if(offerta > attuale.getOfferta() && offerta <= acquirenti.get(username).getDenari()) {
		    		getServletContext().setAttribute("offerta", new Offerta(offerta, username));
		    		session.setAttribute("risultato", "la tua offerta è stata accettata");
		    	}else {
					session.setAttribute("risultato", "la tua offerta non può essere accettata");
				}
		    	
		    	getServletContext().setAttribute("timer_asta", LocalDateTime.now());
	    	}
			
	    	resp.sendRedirect("./home.jsp");
	}
}
