package esame.hibernate.beans;

import java.io.Serializable;

public class Accertamento implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codice;
	private String esito;
	private RichiestaMedica richiestamedica;
	private TipoAccertamento tipoaccertamento;
	
	public Accertamento() {
		
	}
	
	public Accertamento(String codice, String esito) {
		super();
		this.codice = codice;
		this.esito = esito;
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
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public RichiestaMedica getRichiestamedica() {
		return richiestamedica;
	}
	public void setRichiestamedica(RichiestaMedica richiestamedica) {
		this.richiestamedica = richiestamedica;
	}
	public TipoAccertamento getTipoaccertamento() {
		return tipoaccertamento;
	}
	public void setTipoaccertamento(TipoAccertamento tipoaccertamento) {
		this.tipoaccertamento = tipoaccertamento;
	}
	
	
}