package it.unibo.tw.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unibo.tw.web.beans.Gruppo;
import it.unibo.tw.web.beans.Utente;

public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		
		Gruppo g1 = new Gruppo();
		g1.setNomeGruppo("Gruppo1");
		Gruppo g2 = new Gruppo();
		g2.setNomeGruppo("Gruppo2");
		Gruppo g3 = new Gruppo();
		g3.setNomeGruppo("Gruppo3");
		
		this.getServletContext().setAttribute(g1.getNomeGruppo(), g1);	
		this.getServletContext().setAttribute(g2.getNomeGruppo(), g2);	
		this.getServletContext().setAttribute(g3.getNomeGruppo(), g3);	
	}
	

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String richiesta = req.getParameter("richiesta");
		if( richiesta!=null && richiesta.equals("registrazione") ) {
			
			Utente utente = new Utente();

			String nomeUtente = req.getParameter("nomeutente");
			String password = req.getParameter("pw");
			String gruppo = req.getParameter("gruppoutente");
			utente.setGruppo(gruppo);
			utente.setNomeUtente(nomeUtente);
			utente.setPassword(password);
			System.out.println(utente);
			
			Gruppo gr = (Gruppo)this.getServletContext().getAttribute(gruppo);
			
			PrintWriter out = resp.getWriter(); 

	        out.println("<html>");
		        out.println("<head>");
			    out.println("<title>Registrazione Servlet</title>");
			    out.println("<link rel=\"stylesheet\" href=\"styles/default.css\" type=\"text/css\"></link>");
			    out.println("</head>");
		
			    out.println("<body>");
			    if(gr.containsUser(utente) && !gr.getUtenteByName(utente.getNomeUtente()).isValid()) { //registrato ma password non più valida
			    	Utente ut = gr.getUtenteByName(utente.getNomeUtente());
				 	ut.setPassword(password);
				 	ut.setZeroTime(new Date());
				 	ut.setValid(true);
				 	gr.addUtente(ut);  //viene sostituito
					out.println("Nuova password settata! La prossima volta entra con il Login.");
			    }
			    else if(gr.containsUser(utente)) {
			    	out.println("Risulti già registrato con un account valido! La prossima volta entra con il Login.");
			    }
			    else {
			    	gr.addUtente(utente);
			    	out.println("Registrazione effettuata!");
			    	System.out.println("Registrazione: " + gr.getUtenteByName(nomeUtente)+ " inserito nel "+ gr.getNomeGruppo() );
			    }

		        out.println("<br/>");
		        out.println("<hr/>");  //riga orizzontale
		        out.println("Benvenuto nella nostra applicazione Web!");
		        out.println("<br/>");
		        out.println("<a href=\"./././index.jsp\">Torna alla pagina iniziale</a><br />");
		        out.println("</body>");
	        out.println("</html>");
			
	        this.getServletContext().setAttribute(gr.getNomeGruppo(), gr);	
		}
		return;
	}

}
