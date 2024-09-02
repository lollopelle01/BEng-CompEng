package it.unibo.paw.dao.db2;

import java.util.ArrayList;
import java.util.List;

import it.unibo.paw.dao.*;

// PER LAZY-LOAD
public class Db2IngredienteDTOProxy extends IngredienteDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Db2IngredienteDTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Metodo per fare caricamento se non già avvenuto
	@Override
	public ArrayList<RicettaDTO> getRicette() {
		if (isAlreadyLoaded())
			return super.getRicette();
		else {
			Db2TemplateMappingDAO rpm = new Db2TemplateMappingDAO();
			setRicette(rpm.getRicetteByIngrediente(this.getId())); // Facciamo completamento solo in questo punto
			setAlreadyLoaded(true); // Settiamo il loaded a true
			return getRicette();
		}
	}

}
