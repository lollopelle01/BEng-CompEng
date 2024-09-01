package servlets;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Risultato;

public class S1 extends HttpServlet {
	
	private static final int lines = 5;
	private static final int column = 5;

	private static final long serialVersionUID = 1L;
	private Gson gson;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
	}	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		//settaggio matrici
		int[][] matrix = new int[lines][column];
		StringTokenizer tokenizer = new StringTokenizer(request.getParameter("values"),"@");
		for(int i=0; i<lines; i++) {
			for(int j=0; j<column; j++) {
				matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
			}
		}
		
		int lastCount = 0, currentCount = 0;
		boolean res = true;
		
		for(int i=0; i<lines; i++) {
			for(int j=0; j<column; j++) {
				currentCount += matrix[i][j]; //scorro le righe
			}
			
			if (lastCount != 0 && currentCount != lastCount) {
				res = false;
				break;
			} else
				lastCount = currentCount;
		}
		
		Risultato risultato = new Risultato(res, lastCount);

		response.getWriter().println(gson.toJson(risultato)); //in responseText
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
	}
}
