package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tabellone {
	private Map<Integer, ArrayList<Tennista>> database;

	public Tabellone() {
		super();
		this.database = new HashMap<Integer, ArrayList<Tennista>>();
	}

	public Map<Integer, ArrayList<Tennista>> getDatabase() {
		return database;
	}

	public void setDatabase(Map<Integer, ArrayList<Tennista>> database) {
		this.database = database;
	}
	
	public ArrayList<Tennista> getTennisti(String tennistaNome) {
		ArrayList<Tennista> result;
		
		for (int parte : this.database.keySet()) {
			result = this.database.get(parte);
			for(Tennista t : result) {
				if (t.getNome().compareTo(tennistaNome)==0) {return result;}
			}
		}
		return new ArrayList<Tennista>(); 
	}
	
	
}
