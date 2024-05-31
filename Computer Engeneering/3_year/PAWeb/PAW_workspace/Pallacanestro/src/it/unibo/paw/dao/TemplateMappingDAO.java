package it.unibo.paw.dao;


import java.util.List;
import java.util.Set;

import it.unibo.paw.dao.GiocatoreDTO;

public interface TemplateMappingDAO {

	// --- CRUD ------------
	
	public void create(int idSquadra, int idGiocatore);

	public boolean delete(int idSquadra, int idGiocatore);
	
	// ----------------------------------

	public boolean createTable();

	public boolean dropTable();

	Set<Squadra_PallacanestroDTO> getSquadreByGiocatore(int id);

	Set<GiocatoreDTO> getGiocatoriBySquadra(int id);

	// Metodi di utility
	// ...

}
