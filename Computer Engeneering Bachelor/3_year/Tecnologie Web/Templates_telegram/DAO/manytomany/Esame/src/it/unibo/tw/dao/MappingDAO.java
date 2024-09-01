package it.unibo.tw.dao;

import java.util.Set;

public interface MappingDAO {

	// --- CRUD -------------

	public void create(int idRistorante, int idPiatto);

	public boolean delete(int idRistorante, int idPiatto);
	
	//Update e read non hanno senso nella tabella di mapping, se questa possiede
	//come attributi le sole chiavi esterne messe come input al metodo.

	
	// ----------------------------------

	public Set<PiattoDTO> getPiattiByRistoranteId(int id);
	public Set<RistoranteDTO> getRistorantiByPiattoId(int id);

	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

	boolean deleteByPiatto(int id);

	boolean deleteByRistorante(int id);

}
