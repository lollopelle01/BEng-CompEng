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
		CittaDAO cDAO = daoFactoryInstance.getCittaDAO();
		cDAO.dropTable();
		cDAO.createTable();
		
		// Creo i DTO e li inserico
		CittaDTO c = new CittaDTO();
		c.setId(1);
		c.setNome("Nome 1");
		c.setNazione("U.S.A.");
		cDAO.create(c);

		CittaDTO c1 = new CittaDTO();
		c1.setId(2);
		c1.setNome("Nome 2");
		c1.setNazione("U.S.A.");
		cDAO.create(c1);
		
		CittaDTO c2 = new CittaDTO();
		c2.setId(3);
		c2.setNome("Nome 3");
		c2.setNazione("Italia");
		cDAO.create(c2);

		
		// Creo altro Database
		MaratonaDAO mDAO = daoFactoryInstance.getMaratonaDAO();
		mDAO.dropTable();
		mDAO.createTable();
		
		// Creo altri DTO e li inserisco
		MaratonaDTO m = new MaratonaDTO();
		m.setId(1);
		m.setCodiceMaratona("Maratona 1");
		m.setTitolo("Titolo 1");
		m.setData(new Date(10, 12, 2000));
		m.setTipo("mezza-maratona");
		m.setCitta(c1);
		mDAO.create(m);

		MaratonaDTO m1 = new MaratonaDTO();
		m1.setId(2);
		m1.setCodiceMaratona("Maratona 2");
		m1.setTitolo("Titolo 2");
		m1.setData(new Date(10, 12, 2000));
		m1.setTipo("mezza-maratona");
		m1.setCitta(c2);
		mDAO.create(m1);
		
		MaratonaDTO m2 = new MaratonaDTO();
		m2.setId(3);
		m2.setCodiceMaratona("Maratona 3");
		m2.setTitolo("Titolo 3");
		m2.setData(new Date(10, 12, 2000));
		m2.setTipo("mezza-maratona");
		m2.setCitta(c1);
		mDAO.create(m2);
		
		// Faccio richieste ed analizzo i risultati
		try {
			FileWriter fw = new FileWriter("Maratona.txt");
			fw.write("Il numero totale di maratone di tipo mezza-maratona tenutesi negli U.S.A. :\n");
			fw.write("" + cDAO.getNumMaratoneSpecifiche("mezza-maratona", "U.S.A."));
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
