package it.unibo.paw.dao;


import java.util.List;

public interface IngredienteDAO {

	// --- CRUD -------------

	public void create(IngredienteDTO t);

	public IngredienteDTO read(int code);

	public boolean update(IngredienteDTO t);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
