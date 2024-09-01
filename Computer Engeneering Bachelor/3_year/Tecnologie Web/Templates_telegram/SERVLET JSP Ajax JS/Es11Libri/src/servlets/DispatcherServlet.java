package servlets;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	private OperationLog log;
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
//		synchronized (application) {
//			//Inizializzazione stato di applicazione
//			log = (OperationLog) application.getAttribute("log");
//			if (log == null) {
//				log = new OperationLog();
//				application.setAttribute("log", log);
//			}
//		}
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
