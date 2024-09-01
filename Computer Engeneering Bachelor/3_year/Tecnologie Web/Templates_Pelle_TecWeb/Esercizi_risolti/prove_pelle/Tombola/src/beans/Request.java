package beans;

import java.util.ArrayList;

public class Request {
	
	private String fase_partita; // autenticazione, inizio, svolgimento, fine
	private ArrayList<String> nome_password;
	private String messaggio; // 

	public Request() {

	}

	public String getFase_partita() {
		return fase_partita;
	}

	public void setFase_partita(String fase_partita) {
		this.fase_partita = fase_partita;
	}

	public ArrayList<String> getNome_password() {
		return nome_password;
	}

	public void setNome_password(ArrayList<String> nome_password) {
		this.nome_password = nome_password;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

}
