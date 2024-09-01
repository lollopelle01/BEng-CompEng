package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.News;

public class GetNews extends HttpServlet {
    
	private Gson gson;

	
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gson = new Gson();
    }
    

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
     throws IOException, ServletException {
    	
        HttpSession session = request.getSession();
        Integer numberNews = (Integer)session.getAttribute("numberNews");
        if ( numberNews == null ) {
            numberNews = 0;
        }
        
        String letter = request.getParameter("letter");
        BufferedReader br = new BufferedReader(new FileReader("./news.txt"));

        int newNumberNews = 0;

        do {
            newNumberNews = 0;
            while (br.readLine() != null) {   // do per scontato che una riga sia una notizia
                newNumberNews++;
            }
            
            br.close();
            br = new BufferedReader(new FileReader("./news.txt"));  //per riportare la testina a inizio file
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(newNumberNews == numberNews); 			//finchè non ci sono nuove notizie
        
        
        System.out.println(numberNews + "-" + newNumberNews);
        session.setAttribute("numberNews", newNumberNews); 
        
        for ( int i = 0; i < newNumberNews; i++ ) {
            String line = br.readLine();
            
            if ( i == newNumberNews - 1 ) {    
               
                StringTokenizer st = new StringTokenizer(line, ",");
                if ( st.countTokens() == 2 ) {
                    br.close();
                    News news = new News(st.nextToken().trim(),st.nextToken().trim());
                    
                    if ( news.getName().startsWith(letter) ) {
                        response.getWriter().println(gson.toJson(news));
                        return;
                    }
                }
            }
        }

        response.getWriter().println(gson.toJson(false));
    }
}