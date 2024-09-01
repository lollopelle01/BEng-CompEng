package it.unibo.paw.dao;


import java.util.List;
import java.util.Set;

public interface StadioDAO {

	// --- CRUD -------------

	public void create(StadioDTO s);

	public StadioDTO read(int code);

	public boolean update(StadioDTO s);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	public Set<PartitaDTO> getPartiteByStadio(int idStadio);
	public Set<String> getNumPartitePerCategoria(int idStadio);

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
