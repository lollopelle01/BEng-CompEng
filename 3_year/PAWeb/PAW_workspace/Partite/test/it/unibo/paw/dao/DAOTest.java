package it.unibo.paw.dao;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		// Creo istanza DAO
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		
		// Creo database
		StadioDAO sDAO = daoFactoryInstance.getStadioDAO();
		sDAO.dropTable();
		sDAO.createTable();
		
		// Creo i DTO e li inserico
		StadioDTO s = new StadioDTO();
		s.setId(1);
		s.setCodice("Codice 1");
		s.setNome("Stadio 1");
		s.setCitta("Citta 1");
		sDAO.create(s);

		StadioDTO s1 = new StadioDTO();
		s1.setId(2);
		s1.setCodice("Codice 2");
		s1.setNome("Stadio 2");
		s1.setCitta("Citta 2");
		sDAO.create(s1);
		
		// Creo altro Database
		PartitaDAO pDAO = daoFactoryInstance.getPartitaDAO();
		pDAO.dropTable();
		pDAO.createTable();
		
		// Creo altri DTO e li inserisco
		PartitaDTO p = new PartitaDTO();
		p.setId(1);
		p.setCodicePartita("Codice 1");
		p.setCategoria("Calcio");
		p.setGirone("Girone 1");
		p.setNomeSquadraCasa("Juve");
		p.setNomeSquadraOspite("Bolo");
		p.setData(new Date(74278274));
		p.setIdStadio(1);
		pDAO.create(p);

		PartitaDTO p1 = new PartitaDTO();
		p1.setId(2);
		p1.setCodicePartita("Codice 2");
		p1.setCategoria("Calcio");
		p1.setGirone("Girone 2");
		p1.setNomeSquadraCasa("Juve");
		p1.setNomeSquadraOspite("Mil");
		p1.setData(new Date(74278274));
		p1.setIdStadio(1);
		pDAO.create(p1);
		
		PartitaDTO p3 = new PartitaDTO();
		p3.setId(3);
		p3.setCodicePartita("Codice 3");
		p3.setCategoria("Pallavolo");
		p3.setGirone("Girone 2");
		p3.setNomeSquadraCasa("Juve");
		p3.setNomeSquadraOspite("Mil");
		p3.setData(new Date(74278274));
		p3.setIdStadio(1);
		pDAO.create(p3);
		
		PartitaDTO p2 = new PartitaDTO();
		p2.setId(4);
		p2.setCodicePartita("Codice 4");
		p2.setCategoria("Calcio");
		p2.setGirone("Girone 2");
		p2.setNomeSquadraCasa("Juve");
		p2.setNomeSquadraOspite("Mil");
		p2.setData(new Date(74278274));
		p2.setIdStadio(2);
		pDAO.create(p2);

				
		// Faccio richiesta
		try {
			FileWriter fw = new FileWriter("Partite.txt");
			
			fw.write("Stadio s1:\n");
			for(String str : sDAO.getNumPartitePerCategoria(s.getId())) {
				fw.write(str + "\n");
			}
			
			fw.write("Stadio s2:\n");
			for(String str : sDAO.getNumPartitePerCategoria(s1.getId())) {
				fw.write(str + "\n");
			}		
			
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
