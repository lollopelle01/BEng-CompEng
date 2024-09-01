package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class Tavolo {

	private int id;
	private Map<HttpSession, List<Drink>> consumazioni;
	
	public Tavolo(int id) {
		this.id=id;
		this.consumazioni = new HashMap<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public Map<HttpSession, List<Drink>> getConsumazioni() {
		return consumazioni;
	}

	public void setConsumazioni(Map<HttpSession, List<Drink>> consumazioni) {
		this.consumazioni = consumazioni;
	}

	public boolean addUser(HttpSession session) {
		if(!this.consumazioni.containsKey(session)) {
			this.consumazioni.put(session, new ArrayList<>());
			return true;
		}
		else
			return false;
	}
	
	public boolean addDrink(HttpSession session, Drink drink) {
		if(this.consumazioni.containsKey(session)) {
			this.consumazioni.get(session).add(drink);
			return true;
		}
		else
			return false;
	}
}
