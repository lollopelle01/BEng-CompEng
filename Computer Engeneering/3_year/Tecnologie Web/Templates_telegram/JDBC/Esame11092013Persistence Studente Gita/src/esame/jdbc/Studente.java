package esame.jdbc;

import java.io.Serializable;

public class Studente implements Serializable{
	private static final long serialVersionUID = 1L;

	private long CodStud;
	private String cognome;
	private String nome;
	private String nickName;
	
	public Studente() {
		
	}
	
	public Studente (long CodStud, String cognome, String nome, String nickName) {
		this.CodStud = CodStud;
		this.cognome = cognome;
		this.nome = nome;
		this.nickName = nickName;
	}
	
	public long getCodStud() {
		return CodStud;
	}
	public void setCodStud(long codStud) {
		CodStud = codStud;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}