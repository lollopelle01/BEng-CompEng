package it.unibo.paw.dao;


import java.util.List;

public interface GiocatoreDAO {

	// --- CRUD -------------

	public int create(GiocatoreDTO giocatore);

	public GiocatoreDTO read(int code);

	public boolean update(GiocatoreDTO giocatore);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
