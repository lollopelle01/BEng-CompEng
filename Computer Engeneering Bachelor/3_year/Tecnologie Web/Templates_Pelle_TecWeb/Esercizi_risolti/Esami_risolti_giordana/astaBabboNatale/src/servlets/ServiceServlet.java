package servlets;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Acquirente;
import beans.Regalo;

public class ServiceServlet extends HttpServlet{
	
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
	    	
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
	    	if(regali.isEmpty()) {
	    		resp.sendRedirect("./fine.jsp");
	    	}else {
	    		//biosgna selezionare un nuovo regalo da mettere all'asta
		    	int i = random.nextInt(regali.size());
		    	Regalo selezionato = regali.get(i);
		    	regali.remove(i);
		    	
		    	getServletContext().setAttribute("asta", selezionato);
		    	
		    	resp.sendRedirect("./Admin.jsp");
	    	}
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			HttpSession session = req.getSession();

			Map<String, Acquirente> acquirenti = (Map<String, Acquirente>) getServletContext().getAttribute("acquirenti");
	    	
	    	List<Regalo> regali = (List<Regalo>) getServletContext().getAttribute("regali");
	    	
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
	    	if(regali.isEmpty()) {
	    		resp.sendRedirect("./fine.jsp");
	    	}else {
	    		//biosgna selezionare un nuovo regalo da mettere all'asta
		    	int i = random.nextInt(regali.size());
		    	Regalo selezionato = regali.get(i);
		    	regali.remove(i);
		    	
		    	getServletContext().setAttribute("asta", selezionato);
		    	
		    	resp.sendRedirect("./home.jsp");
	    	}
			
	}
}
