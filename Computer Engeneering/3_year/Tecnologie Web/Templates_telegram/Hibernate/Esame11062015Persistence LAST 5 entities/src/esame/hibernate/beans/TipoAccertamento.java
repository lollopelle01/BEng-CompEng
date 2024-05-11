package esame.hibernate.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TipoAccertamento implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codice;
	private String descrizione;
	private Set<Accertamento> accertamenti = new HashSet<>();
	private Set<Struttura> strutture = new HashSet<>();

	public TipoAccertamento() {
		
	}
	
	public TipoAccertamento(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Set<Accertamento> getAccertamenti() {
		return accertamenti;
	}
	public void setAccertamenti(Set<Accertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}
	public Set<Struttura> getStrutture() {
		return strutture;
	}
	public void setStrutture(Set<Struttura> strutture) {
		this.strutture = strutture;
	}
	
	
}