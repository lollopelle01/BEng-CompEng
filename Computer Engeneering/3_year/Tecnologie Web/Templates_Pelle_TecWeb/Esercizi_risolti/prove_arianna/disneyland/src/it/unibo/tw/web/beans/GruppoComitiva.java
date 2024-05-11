package it.unibo.tw.web.beans;

import java.util.ArrayList;
import java.util.List;

public class GruppoComitiva {

	//variabili globali
    private List<Utente> utenti;
	private String nomeGruppo;
	private int biglietti;
	private boolean gestitoDaAdmin;

    // --- constructor ----------
    public GruppoComitiva() {
		this.utenti = new ArrayList<Utente>();
		this.biglietti = 0;
		this.gestitoDaAdmin = false;
	}

    // --- getters and setters --------------
	public List<Utente> getLista() {
		return utenti;
	}

	public void setLista(List<Utente> lista) {
		this.utenti = lista;
	}

	public String getNomeGruppo() {
		return nomeGruppo;
	}

	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}
	
	public int getBiglietti() {
		return this.biglietti;
	}
	
	public void setBiglietti(int biglietti) {
		this.biglietti = biglietti;
	}
	
	public boolean getGestitoDaAdmin() {
		return this.gestitoDaAdmin;
	}
	
	public void setGestitoDaAdmin(boolean g) {
		this.gestitoDaAdmin = g;
	}
	
	// --- utilities ----------------------------
	public void addUtente(Utente u)
	{
		if( u == null)
			return;
		
		if(this.utenti.contains(u)) {
			this.utenti.remove(u);
			this.utenti.add(u);
		}
		else
			this.utenti.add(u);
	}
	
	public void removeUtente(Utente u)
	{
		this.utenti.remove(u);
	}
	
	public boolean containsUser(Utente u)
	{
		return this.utenti.contains(u);
	}
	
	public void addBiglietti(int b) {
		this.biglietti += b;
	}

	public void empty() {
		this.biglietti = 0;
	}
	
	public Utente getUserByName(String userName)
	{
		if(userName.isEmpty() || userName == null)
			return null;
		for(Utente u : this.utenti)
		{
			if(u.getUsername().compareTo(userName)==0)
				return u;
		}
		return null;
	}
}
