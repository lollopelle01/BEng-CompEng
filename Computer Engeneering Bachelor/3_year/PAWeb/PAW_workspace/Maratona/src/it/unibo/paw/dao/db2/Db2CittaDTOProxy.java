package it.unibo.paw.dao.db2;

import java.util.List;
import java.util.Set;

import it.unibo.paw.dao.*;

// PER LAZY-LOAD
public class Db2CittaDTOProxy extends CittaDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Db2CittaDTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Metodo per fare caricamento se non già avvenuto
	@Override
	public Set<MaratonaDTO> getMaratone() {
		if (isAlreadyLoaded())
			return super.getMaratone();
		else {
			Db2CittaDAO rpm = new Db2CittaDAO();
			setMaratone(rpm.getMaratoneByCitta(this.getId())); // Facciamo completamento solo in questo punto
			setAlreadyLoaded(true); // Settiamo il loaded a true
			return getMaratone();
		}
	}

}
