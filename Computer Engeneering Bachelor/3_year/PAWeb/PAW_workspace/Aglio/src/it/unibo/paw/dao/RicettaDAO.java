package it.unibo.paw.dao;


import java.util.List;

public interface RicettaDAO {

	// --- CRUD -------------

	public void create(RicettaDTO obj);

	public RicettaDTO read(int code);

	public boolean update(RicettaDTO obj);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
