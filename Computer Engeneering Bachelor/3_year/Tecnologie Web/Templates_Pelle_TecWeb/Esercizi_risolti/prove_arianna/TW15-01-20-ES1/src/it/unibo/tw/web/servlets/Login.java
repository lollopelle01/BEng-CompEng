package it.unibo.tw.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unibo.tw.web.beans.Gruppo;
import it.unibo.tw.web.beans.Utente;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String richiesta = req.getParameter("richiesta");
		if( richiesta!=null && richiesta.equals("login") ) {
			
			Utente utente = new Utente();

			String nomeUtente = req.getParameter("nomeutente");
			String password = req.getParameter("pw");
			String gruppo = req.getParameter("gruppoutente");
			utente.setGruppo(gruppo);
			utente.setNomeUtente(nomeUtente);
			utente.setPassword(password);
			Gruppo gr = (Gruppo)this.getServletContext().getAttribute(gruppo);
			System.out.println("tentativo login da: "+ utente);
			
			if(nomeUtente.compareTo("admin")==0 && password.compareTo("admin")==0)
			 {
				 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/admin.jsp");
				 rd.forward(req, resp);
				 return;
			 }
			if(gr.checkValidity()>=3)
			 {
				 req.getSession().setAttribute("err", "1");
				 //  pwd scaduta a 3 o piu del gruppo.. reset
				 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
				 rd.forward(req, resp);
				 return;
			 }
			
			PrintWriter out = resp.getWriter(); 

	        out.println("<html>");
		        out.println("<head>");
			    out.println("<title>Login Servlet</title>");
			    out.println("<link rel=\"stylesheet\" href=\"styles/default.css\" type=\"text/css\"></link>");
			    out.println("</head>");
		
			    out.println("<body>");
		        
			    if(!gr.containsUser(utente)) { 
			    	//non trova l'utente nel gruppo corrispondente, 
			    	//MA se il nome corrisponde, allora ha sbagliato password
			    	if((gr.getUtenteByName(utente.getNomeUtente())) != null){
			    		String cookieVal = null;
			        	Cookie[] cookies = req.getCookies();
			        	if ( cookies != null && cookies.length > 0 ) {
			        		for ( Cookie cookie : cookies ) {
			            		if ( cookie.getName().equals("tentativi") ) {
			            			cookieVal = cookie.getValue();
			            			break;
			            		}
			        		}
			        	}
			        	
			        	if(cookieVal == null) {  //se è la prima volta che entra, costruisco il cookie
			        		cookieVal = "0";
			        		Cookie cookie = new Cookie("tentativi", cookieVal);
				            resp.addCookie(cookie); 
			        	}
			        	
			        	out.println("Password errata...");
			        	Cookie cookie = new Cookie("tentativi", String.valueOf(Integer.parseInt(cookieVal)+1));
			            resp.addCookie(cookie);
			            out.println("<br/>");
			            
			        	if (Integer.parseInt(cookie.getValue()) >= 3 )
				    		out.println("...per la terza volta, tentativi esauriti, UTENTE BLOCCATO");
				    	else
				    		out.println("<a href=\"./././index.jsp\">Riprova</a><br />");
				        
			    	}
			    	else {  //se il nome non corrisponde allora l'utente non è nel gruppo
			    		out.println("Attenzione, non risulta registrato in questo gruppo...");
			    		out.println("<a href=\"./././index.jsp\">Torna alla pagina iniziale</a><br />");
			    	}
			    }
			    
			    //se invece trova l'utente, verifico la validità della password
			    else if(!utente.isStillValid()) {
			    	
			    	out.println("La sua password è scaduta, si prega di rieffettuare la registrazione sostituendola");
			    	out.println("<br/>");
			    	out.println("<a href=\"./././index.jsp\">Torna alla pagina iniziale</a><br />");
			    }
			    else {
			    	out.println("Login effettuato con successo!");
			    	out.println("<br/>");
			        out.println("<hr/>");  //riga orizzontale
			        out.println("Benvenuto nella nostra applicazione Web!");
			        out.println("<br/>");
			        out.println("<a href=\"./././index.jsp\">Torna alla pagina iniziale</a><br />");
			    }
		        out.println("<br/>");
		        out.println("</body>");
	        out.println("</html>");
	        this.getServletContext().setAttribute(gr.getNomeGruppo(), gr);	
		}
		
	}

}
