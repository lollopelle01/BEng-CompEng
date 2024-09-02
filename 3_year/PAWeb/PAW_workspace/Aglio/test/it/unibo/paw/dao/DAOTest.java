package it.unibo.paw.dao;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		// Creo istanza DAO
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		
		// Creo database
		RicettaDAO ricettaDAO = daoFactoryInstance.getRicettaDAO();
		ricettaDAO.dropTable();
		ricettaDAO.createTable();
		
		// Creo i DTO e li inserico
		RicettaDTO s = new RicettaDTO();
		s.setId(1);
		s.setNomeRicetta("Pasta al burro");
		s.setTempoPreparazione(15);
		s.setLivelloDifficolta(1);
		s.setCalorie(500);
		ricettaDAO.create(s);

		s.setId(2);
		s.setNomeRicetta("Pasta aglio e pomodoro");
		s.setTempoPreparazione(30);
		s.setLivelloDifficolta(3);
		s.setCalorie(700);
		ricettaDAO.create(s);
		
		// Creo altro Database
		IngredienteDAO ingredienteDAO = daoFactoryInstance.getIngredienteDAO();
		ingredienteDAO.dropTable();
		ingredienteDAO.createTable();
		
		// Creo altri DTO e li inserisco
		IngredienteDTO i = new IngredienteDTO();
		i.setId(1);
		i.setNomeIngrediente("pasta");
		i.setQuantita(100);
		ingredienteDAO.create(i);
		
		i.setId(2);
		i.setNomeIngrediente("pomodoro");
		i.setQuantita(50);
		ingredienteDAO.create(i);
		
		i.setId(3);
		i.setNomeIngrediente("burro");
		i.setQuantita(50);
		ingredienteDAO.create(i);
		
		i.setId(4);
		i.setNomeIngrediente("aglio");
		i.setQuantita(30);
		ingredienteDAO.create(i);
				
		
		// Creo altro database (mapping)
		TemplateMappingDAO mappingDAO = daoFactoryInstance.getTemplateMappingDAO();
		mappingDAO.dropTable();
		mappingDAO.createTable();

		// Compilo tabella
		mappingDAO.create(1, 1);
		mappingDAO.create(1, 3);
		mappingDAO.create(1, 4); // solo per debug
		
		mappingDAO.create(2, 1);
		mappingDAO.create(2, 2);
		mappingDAO.create(2, 4);
		
		// Faccio richieste ed analizzo i risultati
		try {
			FileWriter fw = new FileWriter("Aglio.txt");
			fw.write("Le ricette con aglio:\n");
			for (RicettaDTO r : ingredienteDAO.read(4).getRicette()) {
				fw.write(r.getNomeRicetta() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
