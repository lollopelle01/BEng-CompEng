package servlets;


import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.hsqldb.Session;

import beans.GruppoUtenti;
import beans.Utente;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;

public class Login extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		//servletConfig serve per accedere ai propri parametri di configurazione
		
		
	}
	
	
	
	
	
	
	
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
		 String gid = req.getParameter("gid");
		 //richiesta parametro
		 //prendo la mappa "gruppi"
		 
		//getAttribute sono attributi di contesto, come se fossero variabili globali
		 //getServletContext = Consente di accedere ai parametri di inizializzazione e agli attributi del contesto
		 //visibile da tutta la parte server-side
		 Map<String,GruppoUtenti> gruppi = (Map<String, GruppoUtenti>) this.getServletContext().getAttribute("gruppi");
		 
		 //nella mappa metto "gruppi"
		 this.getServletContext().setAttribute("gruppi", gruppi);
		 
		 //Per includere una risorsa si ricorre a un oggetto di tipo
		 //RequestDispatcher che può essere richiesto al
		 //contesto indicando la risorsa da includere
		 
		 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("home.html");
		 //con il forward noi deleghiamo a qualcun altro la gestione della risposta
		 rd.forward(req, res);	//quindi solo lui avrà il compito di gestire la risposta
		 return;
	 }

	 
	 
	 
	 
	 
	 
	 
	 
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
		 
		 
		 String username = req.getParameter("username");
		 String pwd = req.getParameter("pwd");
		 String gid = req.getParameter("gid");
		 
		 Map<String,GruppoUtenti> gruppi = (Map<String, GruppoUtenti>) this.getServletContext().getAttribute("gruppi");
		 GruppoUtenti g = gruppi.get(gid);
		 
		 //sessione = io creo una sessione per web dinamico e ci accedo tramite httpsession
		 HttpSession session = req.getSession();
		 
		 if(username.compareTo("admin")==0 && pwd.compareTo("admin")==0)
		 {
			 gruppi.put(g.getNomeGruppo(), g);
			 this.getServletContext().setAttribute("gruppi", gruppi);
			 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("admin.jsp");
			 rd.forward(req, res);
			 return;
		 }
	
		 
		 Utente currentUser = g.getUtenteByName(username);
		 if(currentUser ==null)
		 {
			 session.setAttribute("err", "2");
			 gruppi.put(g.getNomeGruppo(), g);
			 this.getServletContext().setAttribute("gruppi", gruppi);
			 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
			 rd.forward(req, res);
			 return;
		 }
		 
		 if(g.checkValidity()>=3)
		 {
			 session.setAttribute("err", "4");
			 //  pwd scaduta a 3 o piu del gruppo.. reset
			 gruppi.put(g.getNomeGruppo(), g);
			 this.getServletContext().setAttribute("gruppi", gruppi);
			 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
			 rd.forward(req, res);
			 return;
		 }
		 
		 if(currentUser.getPassword().compareTo(pwd)==0)
		 {
			 if(!currentUser.isStillValid())
			 {
				 session.setAttribute("err", "3");
				 //  pwd scaduta.. reset
				 gruppi.put(g.getNomeGruppo(), g);
				 this.getServletContext().setAttribute("gruppi", gruppi);
				 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
				 rd.forward(req, res);
				 return;
			 }
			 session.setAttribute("success", username);
			 //session.setAttribute(currentUser, 0);
			 session.removeAttribute(currentUser.getNomeUtente());
			 gruppi.put(g.getNomeGruppo(), g);
			 this.getServletContext().setAttribute("gruppi", gruppi);
			 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("main.jsp");
			 rd.forward(req, res);
			 return;
		
		 }
		 else
		 {
			 session.setAttribute("err", "5");
			 //  tentativo pwd sbagliata.. riprova
			 gruppi.put(g.getNomeGruppo(), g);
			 this.getServletContext().setAttribute("gruppi", gruppi);
			 Object tentativi = session.getAttribute(currentUser.getNomeUtente());
			 if(tentativi == null)
			 {
				 session.setAttribute(currentUser.getNomeUtente(), 1);
				 session.setAttribute("err", "5");
				 gruppi.put(g.getNomeGruppo(), g);
				 this.getServletContext().setAttribute("gruppi", gruppi);
				 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
				 rd.forward(req, res);
				 return;
			 }
			 else
			 {
				 int t = (Integer)session.getAttribute(currentUser.getNomeUtente());
				 if(t<3)
				 {
					 t++;
					 session.setAttribute(currentUser.getNomeUtente(), t);
					 session.setAttribute("err", "5");
					 gruppi.put(g.getNomeGruppo(), g);
					 this.getServletContext().setAttribute("gruppi", gruppi);
					 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
					 rd.forward(req, res);
					 return;
				 }
				 else
				 {
					 session.setAttribute("err", "6");
					 currentUser.setPassword("asifblsiudgfhspiugfhpsiegf"); // cosi non entra piu
					 gruppi.put(g.getNomeGruppo(), g);
					 this.getServletContext().setAttribute("gruppi", gruppi);
					 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
					 rd.forward(req, res);
					 return;
				 }
			 }
			 
		 }
	
	 }
}
