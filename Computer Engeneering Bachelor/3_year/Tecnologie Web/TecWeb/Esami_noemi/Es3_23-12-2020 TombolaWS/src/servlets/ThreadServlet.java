package servlets;

import beans.Partita;

public class ThreadServlet extends Thread{
	private int result;
//	private Sacchetto sacchetto;
	private Partita partita;

	public ThreadServlet(Partita partita) {
		super();
		this.result=-1;
//		this.sacchetto=sacchetto;
		this.partita=partita;
	}


	public void run() {
		try {
			while(partita.getPartita_avviata()==true) {
				Thread.sleep(1000*60);
		//		this.partita.setUltimoNumeroPescato(sacchetto.pescaNumero());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
