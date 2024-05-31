package it.unibo.paw.dao.db2;

import java.util.List;

import it.unibo.paw.dao.*;

// PER LAZY-LOAD
public class Db2MaratonaDTOProxy extends MaratonaDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Db2MaratonaDTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Metodo per fare caricamento se non già avvenuto
	@Override
	public CittaDTO getCitta() {
		if (isAlreadyLoaded())
			return super.getCitta();
		else {
			Db2CittaDAO rpm = new Db2CittaDAO();
			setCitta(rpm.read(super.getId())); // Facciamo completamento solo in questo punto
			setAlreadyLoaded(true); // Settiamo il loaded a true
			return getCitta();
		}
	}

}
