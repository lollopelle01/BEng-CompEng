package it.unibo.tw.dao;

import java.util.Set;

public interface RistoranteDAO {

	// --- CRUD -------------

	public void create(RistoranteDTO obj);

	public RistoranteDTO read(int id);

	public boolean update(RistoranteDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();

	RistoranteDTO readLazy(int id);

	Set<RistoranteDTO> readAll();

	Set<RistoranteDTO> readAllLazy();

	Set<RistoranteDTO> readLazyByIndirizzo(String indirizzo);

	Set<RistoranteDTO> readLazyByFascia(int minimo, int massimo);

}
