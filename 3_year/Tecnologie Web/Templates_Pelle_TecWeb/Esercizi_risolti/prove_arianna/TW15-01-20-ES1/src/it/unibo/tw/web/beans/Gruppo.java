package it.unibo.tw.web.beans;

import java.util.*;

public class Gruppo{
	
	private List<Utente> utenti;
	private String nomeGruppo;
	
	//GETTERS END SETTERS
	public List<Utente> getUtenti() {
		return utenti;
	}
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}
	public String getNomeGruppo() {
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}
	
	//COSTRUCTOR
	public Gruppo() {
		this.utenti = new ArrayList<Utente>();
	}
	
	//UTILITIES
	public void addUtente(Utente u)
	{
		if( u == null)
			return;
		
		if(this.utenti.contains(u))  //se c'è già sovrascrivo
		{
			this.utenti.remove(u);
			this.utenti.add(u);
		}
		else	//sennò aggiungo normalmente
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
	
	public Utente getUtenteByName(String userName)
	{
		if(userName.isEmpty() || userName == null)
			return null;
		for(Utente u : this.utenti)
		{
			if(u.getNomeUtente().compareTo(userName)==0)
				return u;
		}
		return null;
	}
	
	public int checkValidity()
	{
		int result =0;
		for(Utente u : this.utenti)
		{
			if(!u.isStillValid())
			{
				result++;
			}
		}
		return result;
	}
	
	
	
	
	
	

}
