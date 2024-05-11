package it.unibo.tw.dao;

import java.util.Set;

public interface CinefiloDAO {

	// --- CRUD -------------

	public void create(CinefiloDTO obj);

	public CinefiloDTO readLazy(int id);

	public boolean update(CinefiloDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();
	
	Set<CinefiloDTO> readAllLazy();

}
