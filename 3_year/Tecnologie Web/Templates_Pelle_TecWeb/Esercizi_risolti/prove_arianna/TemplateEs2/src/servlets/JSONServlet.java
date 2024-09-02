package servlets;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class JSONServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
		
		// e altre info memorizzate nel servletContext
		this.getServletContext().setAttribute("index", 0);
		
		// e tutti i settaggi di informazioni utili
		// che posso riprendere o qui o da altre jsp e servlet
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		
		
		//attenzione: se ho piu servlet che lavorano contemporaneamente serve un blocco syncronized
		synchronized(this) {
			
		}
		
		//se invece multiconcorrenza con thread, avvio quelli ma qui ci arrivo con una sola istanza

		response.getWriter().println(gson.toJson(new Object())); //in responseText
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
	}
}
