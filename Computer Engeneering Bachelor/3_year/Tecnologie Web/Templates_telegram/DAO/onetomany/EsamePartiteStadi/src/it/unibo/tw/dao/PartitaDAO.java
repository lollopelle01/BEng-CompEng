package it.unibo.tw.dao;

import java.util.Set;

public interface PartitaDAO {

	// --- CRUD -------------

	public void create(PartitaDTO obj);

	public PartitaDTO read(int id);

	public boolean update(PartitaDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

	Set<PartitaDTO> readAll();

	Set<PartitaDTO> getPartiteByStadioId(int id);

	boolean deleteFkById(int id);

	boolean updateFkById(int id, int fk);

	boolean isOwner();

	PartitaDTO readLazy(int id);

	Set<PartitaDTO> readAllLazy();

}
