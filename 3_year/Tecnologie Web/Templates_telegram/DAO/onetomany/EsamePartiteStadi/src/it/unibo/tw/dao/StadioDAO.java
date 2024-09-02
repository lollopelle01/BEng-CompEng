package it.unibo.tw.dao;

import java.util.Set;

public interface StadioDAO {

	// --- CRUD -------------

	public void create(StadioDTO obj);

	public StadioDTO read(int id);

	public boolean update(StadioDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();

	StadioDTO readLazy(int id);

	Set<StadioDTO> readAll();

	Set<StadioDTO> readAllLazy();

	StadioDTO readByIdPartitaLazy(int id);

}
