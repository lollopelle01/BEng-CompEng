package it.unibo.paw.dao;


import java.util.*;

public interface TemplateMappingDAO {

	// --- CRUD ------------
	
	public void create(int idPasseggero, int idVolo);

	public boolean delete(int idPasseggero, int idVolo);
	
	// ----------------------------------

	public boolean createTable();

	public boolean dropTable();

	// Metodi di utility
	public ArrayList<PasseggeroDTO> passeggeriByVolo(int idVolo);
	public ArrayList<VoloAeroportoBoDTO> voliByPasseggero(int idPasseggero);
	public String compagniaSpecifiche(String destinazione);
}
