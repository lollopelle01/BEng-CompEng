package it.unibo.tw.es2.servlets;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setHeader("Content-Type","application/xml");
		
		String nomecitta = req.getParameter("citta");
		String file = "/WEB-INF/resources/"+nomecitta+".xml";
		
		// classe URLConnection utile per reperire il file xml rispetto al contesto della Servlet
		URL url = null;
	    URLConnection urlConn = null;
	    PrintWriter out = null;
	    BufferedInputStream buf = null;
	    try {
	    	out = resp.getWriter();
	        url = getServletContext().getResource(file);

	        urlConn = url.openConnection();
	        urlConn.connect();
	    	buf = new BufferedInputStream(urlConn.getInputStream());
	    	int readBytes = 0;
	    	while ((readBytes = buf.read()) != -1)
	    		out.write(readBytes);
	    }
	    catch (Exception e) {
	    	if (out != null){
	    		// documento xml con le sole informaizoni base
	    		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	    		out.write("<citta nome=\""+nomecitta+"\">\n");
	    		out.write("</citta>\n");
	    	}
	    }
	    finally {
	    	if (out != null){
	    		out.close();
	    	}
	    	if (buf != null){
	    		buf.close();
	    	}
	    }
		
	}
	
}
