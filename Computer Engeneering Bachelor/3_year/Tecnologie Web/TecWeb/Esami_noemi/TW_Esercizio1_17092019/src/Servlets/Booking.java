package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Beans.Hotel;
import Beans.Prenotazione;

public class Booking extends HttpServlet{

	/**
	 * 
	 */
	Gson g;
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		g = new Gson();
	}
	
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 int codAlb = Integer.parseInt(req.getParameter("codice"));
		 int ci = Integer.parseInt(req.getParameter("checkin"));
		 int co = Integer.parseInt(req.getParameter("checkout"));
		 List<Prenotazione> pre = (List<Prenotazione>) this.getServletContext().getAttribute("prenotazioni");
		 Prenotazione p = new Prenotazione();
		 p.setCodiceHotel(codAlb);
		 p.setCheckIn(ci);
		 p.setCheckOut(co);
		 pre.add(p);
		 this.getServletContext().setAttribute("prenotazioni", pre);
		 int overlapCounter=-1;
		 for(Prenotazione myP : pre)
		 {
			 if(p.IsOverlapped(myP))
				 overlapCounter++;
		 }
		 List<Hotel> hotels = (List<Hotel>) this.getServletContext().getAttribute("hotels");
		 for(Hotel h : hotels)
		 {
			 if (h.getCodice() == p.getCodiceHotel()) {
				 p.setPrezzo(h.getPrezzo());
				 for(int i=0; i<overlapCounter;i++)
				 {
					 p.setPrezzo((float) (p.getPrezzo()+ p.getPrezzo()*0.1));
				 }
				
			}
		 }
		 
		 String ris = g.toJson(p);
		 res.getWriter().println(ris);
	 }
	 
	 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
		 int codice = Integer.parseInt(req.getParameter("risCod"));
		 int checkIn = Integer.parseInt(req.getParameter("risCI"));
		 int checkOut = Integer.parseInt(req.getParameter("risCO"));
		 float prezzo = Float.parseFloat(req.getParameter("prezzo"));
		 List<Hotel> hotels = (List<Hotel>) this.getServletContext().getAttribute("hotels");
		 for(Hotel h : hotels)
		 {
			 if(h.getCodice()==codice)
			 {
				 h.setCodice(h.getCodice()-1);
			 }
				 
		 }
		 this.getServletContext().setAttribute("hotels", hotels);
		 List<Prenotazione> preFin = (List<Prenotazione>)this.getServletContext().getAttribute("oreFin");
		 Prenotazione p = new Prenotazione();
		 p.setCodiceHotel(codice);
		 p.setCheckIn(checkIn);
		 p.setCheckOut(checkOut);
		 p.setPrezzo(prezzo);
		 preFin.add(p);
		 this.getServletContext().setAttribute("preFin", preFin);
		 RequestDispatcher rd = this.getServletContext().getRequestDispatcher("index.jsp");
		 rd.forward(req, res);
		 return;
	 }
}
