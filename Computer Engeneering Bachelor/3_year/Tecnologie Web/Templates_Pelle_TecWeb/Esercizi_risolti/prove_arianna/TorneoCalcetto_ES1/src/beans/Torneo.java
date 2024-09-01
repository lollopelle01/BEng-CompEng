package beans;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class Torneo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Socio[] squadra1;
	private Socio[] squadra2;
	private Socio[] squadra3;
	private Socio[] squadra4;
	
	// array di due interi, che possono essere 1,2,3,4
	private int[] semifinale1 = new int[3];
	private int[] semifinale2 = new int[3];
	
	Instant startTime;
	private boolean started;
	private boolean faseiscrizione;
	private boolean fasefinale;
	private String finale;
	
    
	// --- constructor ----------
	public Torneo() {
		this.squadra1 = new Socio[5];
		this.squadra2 = new Socio[5];
		this.squadra3 = new Socio[5];
		this.squadra4 = new Socio[5];
        this.started = false;
        this.faseiscrizione = true;
        this.fasefinale = false;
        
        for(int i=0; i<5; i++) {
        	this.squadra1[i] = null;
        	this.squadra2[i] = null;
        	this.squadra3[i] = null;
        	this.squadra4[i] = null;
        }
        
        //nella cella 0 memorizzo i numero della semifinale
        this.semifinale1[0] = 1;
        this.semifinale1[0] = 2;
        
        this.semifinale1[1] = -1;
        this.semifinale1[2] = -1;
        this.semifinale2[1] = -1;
        this.semifinale2[2] = -1;
	}

	// --- getters and setters --------------
	public int[] getSemifinale1() {
		return this.semifinale1;
	}
	public int[] getSemifinale2() {
		return this.semifinale2;
	}
	
	public String getFinale() {
		return this.finale;
	}
	public void setFinale(String f) {
		this.finale = f;
	}
	
	public boolean isStarted() {
		return this.started;
	}
	public void setStarted(boolean b) {
		this.started = b;
	}
	
	public boolean isFaseiscrizione() {
		return this.faseiscrizione;
	}
	public void setFaseiscrizione(boolean b) {
		this.faseiscrizione = b;
	}
	
	public boolean isFasefinale() {
		return this.fasefinale;
	}
	public void setFasefinale(boolean b) {
		this.fasefinale = b;
	}
	
	public Socio[] getSquadra1() {
		return this.squadra1;
	}
	public Socio[] getSquadra2() {
		return this.squadra2;
	}
	public Socio[] getSquadra3() {
		return this.squadra3;
	}
	public Socio[] getSquadra4() {
		return this.squadra4;
	}
	// --- utilities ----------------------------
	public boolean isFull(Socio[] squadra) {
		for(int i=0; i<5; i++) {
			if(this.squadra1[i] != null)
				return false;
		}
		return true;
	}
	
	public boolean tempoScaduto() {
		if(Duration.between(startTime, Instant.now()).toHours()>=24) {
			return true;
		}
		return false;
	}
	
	public int addSocio(Socio[] squadra, Socio s) {
		int res = -1;
		for(int i=0; i<5; i++) {
			if(squadra[i] == null) {
				squadra[i] = s;
				res = i;
				break;
			}
		}
		return res;
	}
	
	public void init() {
		
		//estrazione semifinali
		int[] estratti = new int[5];
		boolean done = false, ok = false;
		Random r = new Random();
		
		int s, i=0;
		estratti[i] = 0; 
		i++;
		
		do {
			ok = true;
		
			s = r.nextInt(4+1);  
			for(int j=0; j<estratti.length; j++) {
				if(estratti[j] == s)
					ok = false;
			}
			
			if(ok) {
				estratti[i] = s;
				if(i==1)
					this.semifinale1[1] = s;
				if(i==2)
					this.semifinale1[2] = s;
				if(i==3)
					this.semifinale2[1] = s;
				if(i==4) {
					this.semifinale2[2] = s;
					done = true;
				}
				i++;
			}
			
		} while(!done);
		
		//inizio 24h
		this.startTime = Instant.now();
	}
	
}
