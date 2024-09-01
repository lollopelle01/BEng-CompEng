package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.ExecThread;
import beans.Request;
import beans.Result;

public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1679299513106245654L;
	
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		Map<String, ExecThread> threads = new HashMap<>();
		getServletContext().setAttribute("threads", threads);
		this.gson = new Gson();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		Request request = gson.fromJson(req.getParameter("request"), Request.class);
		ExecThread thread = new ExecThread();
		
		Map<String, ExecThread> threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads");
		threads.put(thread.getName(), thread);
		if(request.getIndex() == 0) {
			thread.setMatrix(request.getSubmatrix_1());
		} else {
			thread.setMatrix(request.getSubmatrix_2());
		}
		
		try {
			thread.start();
			thread.join();
			
			threads.remove(thread.getName());
			
			Result result = new Result();
			result.setResult(thread.isResult());
			System.out.println(thread.isResult());
			resp.getWriter().write(gson.toJson(result));
			
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted");
			e.printStackTrace();
			Result result = new Result();
			result.setMessage("Richiesta interrotta dall'amministratore");
			resp.getWriter().write(gson.toJson(result));
		}
	}
}
