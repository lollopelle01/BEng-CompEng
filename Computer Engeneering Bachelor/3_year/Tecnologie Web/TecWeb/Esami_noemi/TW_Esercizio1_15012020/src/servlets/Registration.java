package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.GruppoUtenti;
import beans.Utente;

public class Registration extends HttpServlet{
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		//inizializzo una mappa con nome del gruppo e relativi utentu parteicpanti
		//lo faccio per tutti i gruppi
		Map<String,GruppoUtenti> gruppi = new HashMap<String,GruppoUtenti>();
		//creo nuovo gruppo utente
		
		GruppoUtenti g = new GruppoUtenti();
		g.setNomeGruppo("g1");
	
		gruppi.put(g.getNomeGruppo(), g);
		//nella mappa ci mettoil nome e gruppo utenti NEW QUINDI VUOTO
		g = new GruppoUtenti();
		g.setNomeGruppo("g2");
		//lo faccio per tutti i gruupi (3)
		gruppi.put(g.getNomeGruppo(), g);
		
		g = new GruppoUtenti();
		g.setNomeGruppo("g3");
		
		gruppi.put(g.getNomeGruppo(), g);
		
		//ORA CARICO I VALORI NELLA SERVLET - GLI PASSO GRUPPI E LA MAPPA CON LE TRE COPPIE
		// "G1" GRUPPO UTENTI
		//  G2 GRUPPO UTENTI
		//  G3 GRUPPO UTENTI
		this.getServletContext().setAttribute("gruppi", gruppi);	
	}
	
	
	
	
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
		//vuota bo
	}
	
	
	
	
	
	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
	
	 //prendo i valori con il getParameter
		//come prima cosa prendo username, password e il gruppo relativo
	 String username = req.getParameter("username");
	 String pwd = req.getParameter("password");
	 String group = req.getParameter("group");
	 
	 //getAttribute sono attributi di contesto, come se fossero variabili globali
	 //getServletContext = Consente di accedere ai parametri di inizializzazione e agli attributi del contesto
	 //visibile da tutta la parte server-side
	 //PRENDO LA MAP CON NOME GRUPPO E GRUPPO UTENTI --> NOTA = CREATA IN INIT
	 Map<String,GruppoUtenti> gruppi = (Map<String, GruppoUtenti>) this.getServletContext().getAttribute("gruppi");
	 
	 //prendo dalla mappa il gruppo corrispondente = ovvero quello con "quel nome"
	 GruppoUtenti g = gruppi.get(group);
	 
	 //per un nuovo utente metto ciò che ho appena preso da getParameter 
	 //quindi il group, username, pwd
	 //g preso dalla mappa dopo mi serve per controllare che anche nella mappa quell'utente sia in quel dato gruppo
	 Utente u = new Utente();
	 u.setGruppo(group);
	 u.setNomeUtente(username);
	 u.setPassword(pwd);
	 
	 //sessione = io creo una sessione per web dinamico e ci accedo tramite httpsession
	 HttpSession session = req.getSession();
	 
	 // IF DI "CONTROLLO"
	 if(g == null)
	 {
		 //se il g non esiste???
		 session.setAttribute("err", "1");
		 
		 gruppi.put(g.getNomeGruppo(), g);
		 this.getServletContext().setAttribute("gruppi", gruppi);
		 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
		 //inoltro
		 rd.forward(req, res);
		 return;
	 }
	 
	 //SE NON LO CONTIENE VUOL DIRE CHE NON HA ANCORA UN PROFILO QUINDI SI DEVE REGISTRARE
	 //AGGIUNGO L'UTENTE
	 if( !g.containsUser(u))
	 {
		 //se non contiente user allora inoltro il valore
		 g.addUtente(u);
	 }
	 
	 
	 if(g.containsUser(u) && !g.getUtenteByName(u.getNomeUtente()).isValid())
	 {
		 Utente ut = g.getUtenteByName(u.getNomeUtente());
		 ut.setPassword(pwd);
		 ut.setZeroTime(new Date());
		 ut.setValid(true);
		 g.addUtente(ut);
		 
	 }
	 
	 //una volta passati tutti questi if:
	 //metto nel map di gruppi il nome gruppo + gruppo ("dopo i vari perfezionamenti")
	 gruppi.put(g.getNomeGruppo(), g);
	 //come se "rendessi disponibili" queste variabili
	 this.getServletContext().setAttribute("gruppi", gruppi);
	 
	 //chiedo di includere index.jsp
	 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
	 //e poi inoltro questa gestione
	 rd.forward(req, res);
	 return;
	}
}
