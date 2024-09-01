package servlets;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.News;
import beans.NewsList;

public class NotizieDb extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	private LocalTime lastInteraction; 
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
		
		NewsList databasenotizie = (NewsList)this.getServletContext().getAttribute("dbnotizie");
		if ( databasenotizie == null ){
			databasenotizie = new NewsList();
			databasenotizie.news.add(new News("Economia", "pil", new Date()));
			databasenotizie.news.add(new News("Politica", "bu Meloni", new Date()));
			databasenotizie.news.add(new News("Sport", "basket", new Date()));
			
			this.getServletContext().setAttribute("dbnotizie",databasenotizie );  
		}
		this.lastInteraction = LocalTime.now();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		NewsList databasenotizie = (NewsList)this.getServletContext().getAttribute("dbnotizie");
		NewsList listNewsAggiornata = new NewsList();
		
		String attivo = request.getParameter("attivo");
		
		if(attivo!=null) {
			this.lastInteraction = LocalTime.now();
			return;
		} else {
			LocalTime ora = LocalTime.now();
			
			if(this.lastInteraction.getMinute()-ora.getMinute()>5) { 
				listNewsAggiornata.valid = false;
				request.getSession().invalidate(); 
			}
			else {
				//controllo che siano recenti di 60 minuti
				for(News n : databasenotizie.getNews()) {
					if(n.isRecente(60)) {
						listNewsAggiornata.news.add(n);
					}
				}
				listNewsAggiornata.valid = true;
			}
		}
		
		response.getWriter().println(gson.toJson(listNewsAggiornata.news.toArray()));
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
	}
}
