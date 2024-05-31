package it.unibo.paw.dao;


import java.util.List;

public interface Template2DAO {

	// --- CRUD -------------

	public void create(TemplateDAO student);

	public TemplateDTO read(int code);

	public boolean update(TemplateDTO student);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	// ...

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
