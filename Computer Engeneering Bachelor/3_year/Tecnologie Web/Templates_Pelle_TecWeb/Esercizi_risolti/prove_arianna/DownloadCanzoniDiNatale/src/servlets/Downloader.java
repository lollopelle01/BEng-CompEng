package servlets;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

import beans.FileResult;

public class Downloader extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
		
		File directory = new File("CanzoniNatalizie");
		File[] files = directory.listFiles();
		this.getServletContext().setAttribute("files", files);
		
		//man mano che li scarico li setto a null
	}
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		FileResult result = null;
		File fcorr = null;
		
		synchronized(this) {
			File[] files = (File[])this.getServletContext().getAttribute("files");
			
			for(File f : files) {
				if(f != null) {  //la servlet ha trovato un file non null, lo scarica
					fcorr = f;
					f = null;   //le altre servlet capiscono che è già stato trasferito
					break;
				}
			}
		}
		
		if(fcorr != null) {
			String canzone = fcorr.getName();
			String formato = fcorr.getName().substring(fcorr.getName().lastIndexOf('.'));
			long size = fcorr.length();
			String binario = "";
				
			FileReader fr = new FileReader(fcorr);
			int x;
			String daBloccare = (String)this.getServletContext().getAttribute("bloccaDownload");
			
			while((x = fr.read()) > 0 ) {
				
				if(daBloccare.equals(fcorr.getName())) {
					this.getServletContext().removeAttribute("bloccaDownload");
					break;
				}
				
				binario += (char)x;
				daBloccare = (String)this.getServletContext().getAttribute("bloccaDownload");
			}
			fr.close();
			
			result = new FileResult(canzone, size, formato, binario);
		}
		
		//se invia null, vuol dire che sono finiti i file da trasferire
		response.getWriter().println(gson.toJson(result)); 
	}
}
