package it.unibo.paw.dao;


import java.util.List;

public interface VoloAeroportoBoDAO {

	// --- CRUD -------------

	public void create(VoloAeroportoBoDTO v);

	public VoloAeroportoBoDTO read(int id);

	public boolean update(VoloAeroportoBoDTO v);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
