package it.unibo.tw.dao;

import java.util.Set;

public interface ChefDAO {

	// --- CRUD -------------

	public void create(ChefDTO obj);

	public ChefDTO read(int id);

	public boolean update(ChefDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();

	Set<ChefDTO> readAll();

	Set<ChefDTO> readAlmenoStelle(int numStelle);


}
