package Beans;

import java.util.ArrayList;
import java.util.List;


public class Tavolo {

	private String id;
	private List<Utente> users;
	private boolean status;
	private double tot;
	
	public Tavolo(String id) {
		super();
		this.id = id;
		this.tot = 0;
		users = new ArrayList<Utente>();	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Utente> getUsers() {
		return users;
	}

	public void setUsers(List<Utente> users) {
		this.users = users;
	}
	
	public void addUser(Utente user) {
		users.add(user);
	}
	
	
	public double getTotTableCons() {
		
		double totTable = 0.0;
		
		for(Utente utenti : users) {
			totTable += utenti.getTotCons();
		}
		
		this.tot = totTable;
		return totTable;
	}
	
	public double getTot() {
		return tot;
	}
	
	public void setTotTable(double totale) {
		tot = totale;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
