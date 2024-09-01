package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.*;

public class Servlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Gson g;
	Map<ThreadCalcolo, Risposta> threads;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		g = new Gson();
		if(getServletContext().getAttribute("threads")==null){
			getServletContext().setAttribute("threads", new HashMap<ThreadCalcolo, Risposta>());
		}
	}

    @SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int[][] matrice = g.fromJson(request.getParameter("matrix"), int[][].class);

		ThreadCalcolo myThread = new ThreadCalcolo(matrice, 1);

		try{
			myThread.start(); // lo faccio partire
			myThread.join(); // lo aspetto per analizzarlo
		}
		catch(Exception e){

		}
	} 
}
