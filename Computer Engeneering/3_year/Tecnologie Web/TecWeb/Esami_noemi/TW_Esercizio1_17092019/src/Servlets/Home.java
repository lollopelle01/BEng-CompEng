package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import Beans.Hotel;
import Beans.Prenotazione;

import java.io.*;
import java.util.*;

public class Home extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		List<Hotel> hotels = new ArrayList<Hotel>();
		int n= 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("alberghi.txt"));
			String line = reader.readLine();
			
			while(line!=null)
			{
				StringTokenizer st = new StringTokenizer(line,",");
				Hotel h = new Hotel();
				h.setCodice(Integer.parseInt(st.nextToken()));
				h.setNumCam(Integer.parseInt(st.nextToken()));
				h.setPrezzo(Float.parseFloat(st.nextToken()));
				hotels.add(h);
				n++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().setAttribute("hotels", hotels);
		this.getServletContext().setAttribute("prenotazioni", new ArrayList<Prenotazione>());
		this.getServletContext().setAttribute("prenFin", new ArrayList<Prenotazione>());
		this.getServletContext().setAttribute("n", n);
	}
	
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	 }
	 
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
		 String u = req.getParameter("userName");
		 String p = req.getParameter("pwd");
		 if(u.compareTo("admin")==0 && p.compareTo("admin")==0)
		 {
			 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("admin.jsp");
			 rd.forward(req, res);
			 return;
		 }
		 
		 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
		 rd.forward(req, res);
		 return;
	 }
}
