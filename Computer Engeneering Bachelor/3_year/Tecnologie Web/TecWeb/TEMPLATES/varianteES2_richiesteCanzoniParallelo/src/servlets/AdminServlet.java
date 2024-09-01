package servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import beans.Canzone;
import beans.ErrorMessage;
import beans.GenericMessage;
import beans.StringMessage;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson G = new Gson();
	public static Integer canzoneBloccata = -1; 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		int i = Integer.parseInt(request.getParameter("i"));
		
		canzoneBloccata = i;
	}
}