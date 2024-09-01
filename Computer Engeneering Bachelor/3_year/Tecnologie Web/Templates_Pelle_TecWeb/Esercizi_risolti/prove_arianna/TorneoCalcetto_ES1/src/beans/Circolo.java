package beans;

import java.util.ArrayList;
import java.util.List;

public class Circolo {

    private List<Socio> soci;

    // --- constructor ----------
    public Circolo() {
		this.soci = new ArrayList<Socio>();
		
		Socio u = new Socio("admin","admin");
    	this.soci.add(u);
    	
    	u = new Socio("pippo", "p1");
    	this.soci.add(u);
    	u = new Socio("pluto", "p2");
    	this.soci.add(u);
    	u = new Socio("minni", "p3");
    	this.soci.add(u);
    	u = new Socio("paperino", "p4");
    	this.soci.add(u);
    	u = new Socio("topolino", "p5");
    	this.soci.add(u);
    	//devono essere almeno 20...affinchè il Torneo possa iniziare
	}
	
	// --- getters and setters --------------
    public List<Socio> getSoci() {
		return soci;
	}

	// --- utilities ----------------------------
    public void addSocio(String username, String password) {
		this.soci.add(new Socio(username, password));
	}
    
    public Socio findSocio(String username, String password) {
		for(Socio u : getSoci()) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password))
				return u;
		}
		return null;
	}
    
    public boolean isAutenticato(String username, String password) {
		if(this.findSocio(username, password)==null) 
			return false;
		else 
			return true;
	}
    
    public List<String> getUsernames(){
		List<String> res=new ArrayList<String>();
		for(Socio u:this.getSoci()) {
			res.add(u.getUsername());
		}
		return res;
	}
	
	public void removeUtente(Socio u) {
		this.soci.remove(u);
	}
	
	public Socio getSocioByName(String username) {
		if(username.isEmpty() || username == null)
			return null;
		for(Socio u : this.soci) {
			if(u.getUsername().compareTo(username)==0)
				return u;
		}
		return null;
	}
}
