package Beans;

public class Prenotazione {
	
	private int codiceHotel;
	private int checkIn;
	private int checkOut;
	private float prezzo;
	
	public int getCodiceHotel() {
		return codiceHotel;
	}
	public void setCodiceHotel(int codiceHotel) {
		this.codiceHotel = codiceHotel;
	}
	public int getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(int checkIn) {
		this.checkIn = checkIn;
	}
	public int getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(int checkOut) {
		this.checkOut = checkOut;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	public Prenotazione() {
		super();
		// TODO Auto-generated constructor stub
		this.prezzo=0;
	}
	
	public  boolean IsOverlapped(Prenotazione p)
	{
		if(p.checkIn<=this.checkIn && p.checkOut>this.checkIn)
			return true;
		if(p.checkIn<this.checkOut && p.checkOut>=this.checkOut)
			return true;
		if(p.checkIn>=this.checkIn && p.checkIn<this.checkOut && p.checkOut>this.checkIn && p.checkOut<=this.checkOut)
			return true;
		return false;
	}
	
	public int CompareTo(Object o)
	{
		Prenotazione p = (Prenotazione)o;
		if(p.getCodiceHotel() == this.codiceHotel)
			return (this.checkIn - p.checkIn) + (this.checkOut - p.checkOut);
		else
			return this.codiceHotel - p.getCodiceHotel();
	}
	
	

}
