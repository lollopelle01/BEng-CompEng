package it.unibo.paw.dao;


import java.util.ArrayList;
import java.util.List;

public interface TemplateMappingDAO {

	// --- CRUD ------------
	
	public void create(int idRicetta, int idIngrediente);

	public boolean delete(int idRicetta, int idIngrediente);
	
	// ----------------------------------

	public boolean createTable();

	public boolean dropTable();

	// Metodi di utility
	public ArrayList<RicettaDTO> getRicetteByIngrediente(int id);

}
