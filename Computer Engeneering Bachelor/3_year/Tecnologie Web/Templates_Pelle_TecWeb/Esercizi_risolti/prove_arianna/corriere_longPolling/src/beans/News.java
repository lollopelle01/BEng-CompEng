package beans;

import java.util.Date;

public class News {
	
	public String titolo;
	public String notizia;
	public Date orario;
	
	public News(String t, String n, Date o) {
		this.titolo = t;
		this.notizia = n;
		this.orario = o;
	}
	
	public boolean isRecente(int s) {
		if( (new Date().getTime() - orario.getTime()) >= s*1000 )
			return false;
		else
			return true;
	}
}


