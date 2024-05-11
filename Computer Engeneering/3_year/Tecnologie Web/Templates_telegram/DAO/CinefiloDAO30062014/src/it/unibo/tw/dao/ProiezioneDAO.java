package it.unibo.tw.dao;

import java.util.Set;

public interface ProiezioneDAO {

	// --- CRUD -------------

	public void create(ProiezioneDTO obj);

	public ProiezioneDTO readLazy(int id);

	public boolean update(ProiezioneDTO obj);

	public boolean delete(int id);

	
	// ----------------------------------
	
	public boolean createTable();

	public boolean dropTable();

	boolean isOwner();

	Set<ProiezioneDTO> readAllLazy();
	
	Set<ProiezioneDTO> getProiezioniBySalaId(int id);

	boolean deleteFkById(int id);

	boolean updateFkById(int id, int fk);
//
//	Set<ProiezioneDTO> readLazyByIndirizzo(String indirizzo);
//
//	Set<ProiezioneDTO> readLazyByFascia(int minimo, int massimo);

}
