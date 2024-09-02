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

import beans.CounterThread;
import beans.OperationLog;
import beans.Result;

/**
 * Servlet implementation class Dispatcher
 */
@WebServlet("/Dispatcher")
public class Dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OperationLog log;
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
		synchronized (application) {
			log = (OperationLog) application
					.getAttribute("log");
			if (log == null) {
				log = new OperationLog();
				application.setAttribute("log",
						log);
			}
		}
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String file = request.getParameter("file");
		String car = request.getParameter("car");
		if (file != null && file.length() != 0 && car != null && car.length() == 1 && Character.isLetterOrDigit(car.charAt(0)) ) {
			File filen = new File(request.getServletContext().getResource("/"+file).getFile());
			if (filen.canRead()) {
				List<CounterThread> threads = new ArrayList<CounterThread>();
				Result result = new Result();
				result.setCar(car.charAt(0));
				result.setFilename(file);
				for (long i=0; i<filen.length(); i+=1024) {
					RandomAccessFile stream = new RandomAccessFile(request.getServletContext().getResource("/"+file).getFile(), "r");
					stream.seek(i);
					CounterThread thread = new CounterThread(stream, result);
					threads.add(thread);
					thread.run();
				}
				for (CounterThread thread : threads) {
					try {
						thread.join();
					} catch (InterruptedException e) {
					}
				}
				
				request.setAttribute("result", result);
				log.addResult(result);
				this.getServletContext().getRequestDispatcher("/start.jsp").forward(request, response);
			}
			else {
				throw new ServletException("File "+filen.getAbsolutePath()+" non trovato");
			}
			
		}
		else {
			throw new ServletException("Parametri non corretti");
		}
	}


}
