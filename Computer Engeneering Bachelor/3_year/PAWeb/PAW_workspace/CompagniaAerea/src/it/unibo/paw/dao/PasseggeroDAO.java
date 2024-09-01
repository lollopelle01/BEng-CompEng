package it.unibo.paw.dao;


import java.util.List;

public interface PasseggeroDAO {

	// --- CRUD -------------

	public void create(PasseggeroDTO p);

	public PasseggeroDTO read(int id);

	public boolean update(PasseggeroDTO p);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
