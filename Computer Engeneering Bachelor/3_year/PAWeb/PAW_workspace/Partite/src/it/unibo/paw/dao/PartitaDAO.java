package it.unibo.paw.dao;


import java.util.List;

public interface PartitaDAO {

	// --- CRUD -------------

	public void create(PartitaDTO p);

	public PartitaDTO read(int code);

	public boolean update(PartitaDTO p);

	public boolean delete(int code);

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
