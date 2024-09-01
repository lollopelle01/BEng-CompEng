package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Servlet2 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String vocali = "aeiouAEIOU";
	
	@Override
	public void init() throws ServletException {
			
		super.init();
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String input2 = request.getParameter("testo");
		input2 = input2.replace("$", "");
		int count = 0;
		List<String> parole1 = new ArrayList<String>();
		while (count<10) {
			parole1.add(genera(input2));
			count++;
		}
		response.getWriter().println(parole1.toString()); //js non conosce le liste, devo mandare array
		
	}
	
	private String genera(String input) {
		Random r = new Random();
		char [] nuova = new char [input.length()];
		int count = 0;
		int lungh = input.length();
		while (count!= lungh) {
			char posto = input.charAt(r.nextInt(input.length()));
			if (count == 0 && !vocali.contains(posto+"")) {
				input = input.replaceFirst(posto+"", "");
				nuova[count]=posto;
				count++;
			} else if (count>0) {
				input = input.replaceFirst(posto+"", "");
				nuova[count]=posto;
				count++;
			}	
		}
		String nuovaP = "";
		for (int i=0; i<nuova.length; i++) {
			nuovaP +=nuova[i];
		}
		return nuovaP;
		
	}
}