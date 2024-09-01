package beans;

import java.util.HashMap;
import java.util.Map;

public class Utente {
	
	private String nome;
	private String cognome;
	private Map<String, Double> ultimaSessione;
	private Map<String, Double> sessioneCorrente;
	private Map<String, Integer> carrello;
	
	public Utente() {
		ultimaSessione = null;
		sessioneCorrente = null;
		carrello = new HashMap<>();
	}
	
	public void aggiungiAlCarrello(String id, int quantita) {
		Integer oldQ = carrello.get(id);
		if (oldQ == null)
			carrello.put(id, quantita);
		else
			carrello.put(id, oldQ + quantita);
	}
	
	public void svuotaCarello() {
		carrello = new HashMap<>();
	}
	
	public double getTotale() {
		double result = 0;
		for (String id : carrello.keySet()) {
			result += carrello.get(id) * sessioneCorrente.get(id);
		}
		return result;
	}
	
	
	public Map<String, Integer> getCarrello() {
		return carrello;
	}

	public Utente(String nome, String cognome) {
		this();
		this.nome = nome;
		this.cognome = cognome;
	}

	public void popolaSessioneCorrente(Catalogo catalogo) {
		sessioneCorrente = new HashMap<>();
		for (Prodotto p : catalogo.getProdotti()) {
			double prezzoPers;
			if (ultimaSessione != null) {
				prezzoPers = ultimaSessione.get(p.getId()) + p.getPrezzo()*(p.getNumVenduti()+p.getNumDisponibili())/p.getNumDisponibili();
			} else {
				prezzoPers = p.getPrezzo();
			}
			sessioneCorrente.put(p.getId(), prezzoPers);
		}
	}
	
	public void finalizza() {
		ultimaSessione = sessioneCorrente;
		sessioneCorrente = null;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Map<String, Double> getUltimaSessione() {
		return ultimaSessione;
	}
	public void setUltimaSessione(Map<String, Double> ultimaSessione) {
		this.ultimaSessione = ultimaSessione;
	}
	public Map<String, Double> getSessioneCorrente() {
		return sessioneCorrente;
	}
	public void setSessioneCorrente(Map<String, Double> sessioneCorrente) {
		this.sessioneCorrente = sessioneCorrente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nome.hashCode() + cognome.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		if (!nome.equals(other.nome) || !cognome.equals(other.cognome))
			return false;
		return true;
	}
	

}
