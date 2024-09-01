package servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Gioco;
import beans.Utente;

public class S2 extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		Gioco gioco = (Gioco) app.getAttribute("gioco");
		Utente u = (Utente) session.getAttribute("utente");
		int offerta = Integer.parseInt(request.getParameter("numero"));
		
		Instant now = gioco.ultimaOffertaTime();
		
		if(Duration.between(gioco.getStartTime(), Instant.now()).toMinutes()>20) {
			gioco.setGiocoCominciato(false);
			gioco.addMessaggio("Il gioco e' finito, accedere a j2.jsp");
			getServletContext().getRequestDispatcher("/j2.jsp").forward(request, response);
			return;
		}
		if(now!=null && Duration.between(now, Instant.now()).getSeconds()>60) {
			gioco.passaggio(gioco.utenteVenditore(), gioco.utenteVincitoreOfferta());
			gioco.addMessaggio("La compra/vendita si è conclusa alle "+Instant.now().toString());
			getServletContext().getRequestDispatcher("/mainUser.jsp").forward(request, response);;
			return;
		} 
		if(offerta<= u.getDenari()) {
			gioco.setVendita(true);
			u.setOfferta(offerta);
			u.setOffertaTime(Instant.now());
			gioco.addMessaggio("offerta da "+u.getNome()+" accettata alle ore "+Instant.now().toString());
			getServletContext().getRequestDispatcher("/mainUser.jsp").forward(request, response);
			return;
		}
		gioco.addMessaggio("I denari non sono abbastanza per fare un offerta cos�");
		getServletContext().getRequestDispatcher("/mainUser.jsp").forward(request, response);
		return;
	}
}
