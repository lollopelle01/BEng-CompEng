package Beans;

import java.util.ArrayList;
import java.util.List;

public class Articolo {

	private List<String> users;
	private boolean accesso;
	private String nome;
	private String text;
	
	public Articolo(String nome) {
		super();
		this.nome = nome;
		this.text = "";
		this.users = new ArrayList<String>();
		this.accesso = false;
	}

	public List<String> getUsers() {
		return users;
	}

	public void addUser(String users) {
		this.users.add(users);
	}

	public boolean isAccesso() {
		return accesso;
	}

	public void setAccesso(boolean accesso) {
		this.accesso = accesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
