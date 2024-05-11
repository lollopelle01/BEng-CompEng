package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class Servlet extends HttpServlet{
	private static final long serialVersionUID = 3403693893991634090L;
	Random random;
	Gson gson;
    BufferedReader br;
    List<HttpSession> sessioni;

	@Override
	public void init() throws ServletException {
		this.random = new Random(System.currentTimeMillis());
		this.gson = new Gson();
		super.init();
		sessioni = new ArrayList<HttpSession>();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	if(req.getSession().isNew()) {
    		sessioni.add(req.getSession());
    	}
    	
    	if(sessioni.size()>= 3) {
    		for(HttpSession s : sessioni) {s.setMaxInactiveInterval(300);}
    	}
    	
    	System.out.println(sessioni.size());
    	System.out.println(new Date());
    	        
        // Faccio long-polling
        try {
			wait(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

    	System.out.println(new Date());
        
        // Invio notizie
        String content=null;
        try {
            content = new String(Files.readAllBytes(Path.of("./database.txt")));
            
            System.out.println("Contenuto del file:");
            System.out.println(content);
        } catch (IOException e) {
            System.out.println("Si è verificato un errore durante la lettura del file: " + e.getMessage());
        }
        System.out.println(content);
        System.out.println("\n\n\n\n");
        System.out.println(gson.toJson(content));
        
        resp.getWriter().write(gson.toJson(content));
        resp.getWriter().close();

    }
}