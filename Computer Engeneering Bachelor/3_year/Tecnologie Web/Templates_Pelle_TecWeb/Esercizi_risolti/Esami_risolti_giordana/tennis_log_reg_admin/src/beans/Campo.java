package beans;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private int id;
	private List<Prenotazione> prenotazioni;
	
	public Campo(int id) {
		this.id=id;
		this.prenotazioni = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void addP(Prenotazione p) {
		this.prenotazioni.add(p);
	}
	
	public void removeP(Prenotazione p) {
		this.prenotazioni.remove(p);
	}
	
	public Prenotazione findPrenotazione(int giorno, int orario, String username) {
		Prenotazione a=null;
		
		for(Prenotazione p: this.prenotazioni) {
			if(p.getGiorno()==giorno && p.getOrario()==orario && p.getUsername1().equals(username)) {
				a=p;
				break;
			}
		}
		return a;
	}
}
