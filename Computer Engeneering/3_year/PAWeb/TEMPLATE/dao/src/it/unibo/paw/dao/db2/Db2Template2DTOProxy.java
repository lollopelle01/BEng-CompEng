package it.unibo.paw.dao.db2;

import java.util.List;

import it.unibo.paw.dao.*;

// PER LAZY-LOAD
public class Db2Template2DTOProxy extends Template2DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Db2Template2DTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Metodo per fare caricamento se non già avvenuto
	@Override
	public List<entry> getLista() {
		if (isAlreadyLoaded())
			return super.getLista();
		else {
			Db2TemplateMappingDAO rpm = new Db2TemplateMappingDAO();
			setPiatti(rpm.getPiattiFromResturant(this.getId())); // Facciamo completamento solo in questo punto
			isAlreadyLoaded(true); // Settiamo il loaded a true
			return getPiatti();
		}
	}

}
