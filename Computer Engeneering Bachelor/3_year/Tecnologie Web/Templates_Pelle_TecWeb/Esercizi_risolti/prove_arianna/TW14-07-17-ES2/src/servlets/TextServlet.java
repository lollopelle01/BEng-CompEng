package servlets;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.UpperThread;

public class TextServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		gson = new Gson();
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String filename = request.getParameter("fileName");
		String text = request.getParameter("text");
		String text1 = null, text2 = null;
		
		int chars = text.length(), middle = -1, result = -1;
		middle = chars/2;
		text1 = text.substring(0, middle);
		text2 = text.substring(middle+1);
		
		UpperThread t1 = new UpperThread();
		UpperThread t2 = new UpperThread();
		
		t1.setMaxchars(middle);
		t2.setMaxchars(chars-middle);
		t1.setText(text1);
		t2.setText(text2);
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		File f = new File(filename);
		FileWriter fw = new FileWriter(f);
		
		fw.write(t1.getTextchanged());
		fw.append(t2.getTextchanged());
		fw.close();
		
		result = t1.getConverted() + t2.getConverted();

		response.getWriter().println(gson.toJson(result)); //alla fine stampa una stringa json: responseText
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
