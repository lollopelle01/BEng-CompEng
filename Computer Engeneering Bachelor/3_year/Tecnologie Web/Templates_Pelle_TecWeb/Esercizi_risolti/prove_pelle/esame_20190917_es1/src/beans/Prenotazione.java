package beans;

import java.time.LocalDateTime;

public class Prenotazione {
	private int id_hotel;
	private int check_in;
	private int check_out;
	private float prezzo_finale;
	
	public Prenotazione() {
		super();
	}

	public int getId_hotel() {
		return id_hotel;
	}

	public void setId_hotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}

	public int getCheck_in() {
		return check_in;
	}

	public void setCheck_in(int check_in) {
		this.check_in = check_in;
	}

	public int getCheck_out() {
		return check_out;
	}

	public void setCheck_out(int check_out) {
		this.check_out = check_out;
	}

	public float getPrezzo_finale() {
		return prezzo_finale;
	}

	public void setPrezzo_finale(float prezzo_finale) {
		this.prezzo_finale = prezzo_finale;
	}
	
	// Metodo per vedere se due prenotazioni si sovrappongono
	public boolean isOverlapped(Prenotazione p) {
		int a,b,c,d;
		a=this.getCheck_in(); b=this.getCheck_out();
		c=p.getCheck_in(); d=p.getCheck_out();
		
		return (p.getId_hotel() == this.getId_hotel()) && ((a<=c && c<=b)||(a<=d && d<=b)) ;
	}
	
	
}
