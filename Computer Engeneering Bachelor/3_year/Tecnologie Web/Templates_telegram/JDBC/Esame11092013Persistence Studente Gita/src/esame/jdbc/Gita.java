package esame.jdbc;

import java.io.Serializable;
import java.util.Date;

public class Gita implements Serializable{
	private static final long serialVersionUID = 1L;

	private long CodGita;
	private String luogo;
	private Date data;
	private double costo;
	
	public Gita () {
		
	}
	
	public Gita (long CodGita, String luogo, Date data, double costo) {
		this.CodGita = CodGita;
		this.luogo = luogo;
		this.data = data;
		this.costo = costo;
	}
	
	public long getCodGita() {
		return CodGita;
	}
	public void setCodGita(long codGita) {
		CodGita = codGita;
	}
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
}