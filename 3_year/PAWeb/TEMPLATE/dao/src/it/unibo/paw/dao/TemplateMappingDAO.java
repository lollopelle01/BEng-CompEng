package it.unibo.paw.dao;


import java.util.List;

public interface TemplateMappingDAO {

	// --- CRUD ------------
	
	public void create(int idCourse, int idStudent);

	public boolean delete(int idCourse, int idStudent);
	
	// ----------------------------------

	public boolean createTable();

	public boolean dropTable();

	// Metodi di utility
	// ...

}
