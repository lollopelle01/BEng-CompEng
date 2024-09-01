package it.unibo.paw.dao;


import java.util.List;
import java.util.Set;

public interface CittaDAO {

	// --- CRUD -------------

	public void create(CittaDTO c);

	public CittaDTO read(int code);

	public boolean update(CittaDTO c);

	public boolean delete(int code);

	
	// ----------------------------------
	// Metodi specifici
	public int getNumMaratoneSpecifiche(String tipo, String nazione);
	public Set<MaratonaDTO> getMaratoneByCitta(int idCitta);

	// ----------------------------------
	// Gestione tabelle
	
	public boolean createTable();

	public boolean dropTable();

}
