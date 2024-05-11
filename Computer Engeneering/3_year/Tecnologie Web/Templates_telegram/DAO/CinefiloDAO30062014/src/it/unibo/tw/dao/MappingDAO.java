package it.unibo.tw.dao;

import java.util.Set;

public interface MappingDAO {

	// --- CRUD -------------

	public void create(int idProiezione, int idCinefilo);

	public boolean delete(int idProiezione, int idCinefilo);
	
	//Update e read non hanno senso nella tabella di mapping, se questa possiede
	//come attributi le sole chiavi esterne messe come input al metodo.

	
	// ----------------------------------

	public Set<CinefiloDTO> getCinefiliByProiezioneId(int id);
	public Set<ProiezioneDTO> getProiezioniByCinefiloId(int id);

	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

	boolean deleteByCinefilo(int id);

	boolean deleteByProiezione(int id);

}
