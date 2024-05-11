package esame.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RichiestaMedica implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private Paziente paziente;
	private Date data;
	private String nomemedico;
	private Set<Accertamento> accertamenti = new HashSet<>();
	
	public RichiestaMedica() {
		
	}
	
	public RichiestaMedica(Paziente paziente, Date data, String nomemedico) {
		super();
		this.paziente = paziente;
		this.data = data;
		this.nomemedico = nomemedico;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Paziente getPaziente() {
		return paziente;
	}
	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNomemedico() {
		return nomemedico;
	}
	public void setNomemedico(String nomemedico) {
		this.nomemedico = nomemedico;
	}
	public Set<Accertamento> getAccertamenti() {
		return accertamenti;
	}
	public void setAccertamenti(Set<Accertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}
	
	
}