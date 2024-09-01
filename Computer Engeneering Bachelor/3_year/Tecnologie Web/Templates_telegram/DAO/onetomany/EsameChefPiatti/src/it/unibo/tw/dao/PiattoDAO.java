package it.unibo.tw.dao;

import java.util.Set;

public interface PiattoDAO {

	// --- CRUD -------------

	public void create(PiattoDTO obj);

	public PiattoDTO read(int id);

	public boolean update(PiattoDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

	Set<PiattoDTO> readAll();

	Set<PiattoDTO> getPiattiByChefId(int id);

	boolean deleteFkById(int id);

	boolean updateFkById(int id, int fk);

	boolean isOwner();

}
