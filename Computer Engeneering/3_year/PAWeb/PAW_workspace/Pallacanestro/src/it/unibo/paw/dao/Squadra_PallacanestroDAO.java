package it.unibo.paw.dao;


import java.util.List;

public interface Squadra_PallacanestroDAO {

	// --- CRUD -------------

	public int create(Squadra_PallacanestroDTO squadra);

	public Squadra_PallacanestroDTO read(int code);

	public boolean update(Squadra_PallacanestroDTO squadra);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
