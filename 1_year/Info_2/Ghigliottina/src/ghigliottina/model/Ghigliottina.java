package ghigliottina.model;

import java.util.Iterator;
import java.util.List;

public class Ghigliottina {
	private Iterator<Terna> iterator;
	private String rispostaEsatta;
	private List<Terna> terne;
	
	public String getRispostaEsatta() {
		return rispostaEsatta;
	}
	public List<Terna> getTerne() {
		return terne;
	}
	public Ghigliottina(List<Terna> terne, String rispostaEsatta) {
		super();
		if(rispostaEsatta==null || rispostaEsatta.isBlank() || rispostaEsatta.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if(terne==null||terne.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.terne = terne;
		this.rispostaEsatta = rispostaEsatta;
		this.iterator=this.terne.iterator();
	}
	
	public boolean hasNext() {
		return this.iterator.hasNext();
	}
	
	public Terna next() {
		return this.iterator.next();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iterator == null) ? 0 : iterator.hashCode());
		result = prime * result + ((rispostaEsatta == null) ? 0 : rispostaEsatta.hashCode());
		result = prime * result + ((terne == null) ? 0 : terne.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ghigliottina other = (Ghigliottina) obj;
		if (iterator == null) {
			if (other.iterator != null)
				return false;
		} else if (!iterator.equals(other.iterator))
			return false;
		if (rispostaEsatta == null) {
			if (other.rispostaEsatta != null)
				return false;
		} else if (!rispostaEsatta.equals(other.rispostaEsatta))
			return false;
		if (terne == null) {
			if (other.terne != null)
				return false;
		} else if (!terne.equals(other.terne))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Ghigliottina [rispostaEsatta=" + rispostaEsatta + ", \nterne=" + terne + "]";
	}
	
	
}
