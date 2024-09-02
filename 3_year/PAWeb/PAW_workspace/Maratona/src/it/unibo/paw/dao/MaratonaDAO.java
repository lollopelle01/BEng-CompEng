package it.unibo.paw.dao;


import java.util.List;

public interface MaratonaDAO {

	// --- CRUD -------------

	public void create(MaratonaDTO m);

	public MaratonaDTO read(int code);

	public boolean update(MaratonaDTO m);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
