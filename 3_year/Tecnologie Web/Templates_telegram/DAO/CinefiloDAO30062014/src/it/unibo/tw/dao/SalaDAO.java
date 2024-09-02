package it.unibo.tw.dao;

import java.util.Set;

public interface SalaDAO {

	// --- CRUD -------------

	public void create(SalaDTO obj);

	public SalaDTO read(int id);

	public boolean update(SalaDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();

	Set<SalaDTO> readAll();

	SalaDTO readByIdProiezioneLazy(int id);

}
