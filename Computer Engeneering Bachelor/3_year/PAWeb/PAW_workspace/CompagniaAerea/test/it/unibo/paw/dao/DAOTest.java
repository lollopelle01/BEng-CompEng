package it.unibo.paw.dao;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		// Creo istanza DAO
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		
		// Creo database
		PasseggeroDAO passeggeroDAO = daoFactoryInstance.getPasseggeroDAO();
		passeggeroDAO.dropTable();
		passeggeroDAO.createTable();
		
		// Inserisco dati
		PasseggeroDTO p = new PasseggeroDTO();
		
		p.setId(1);
		p.setCodPasseggero("Passeggero 1");
		p.setNome("Lorenzo");
		p.setCognome("Pellegrino");
		p.setNumPassaporto(123456789);
		passeggeroDAO.create(p);
		
		p.setId(2);
		p.setCodPasseggero("Passeggero 2");
		p.setNome("Riccardo");
		p.setCognome("Pellegrino");
		p.setNumPassaporto(987654321);
		passeggeroDAO.create(p);
		
		// Creo database
		VoloAeroportoBoDAO voloDAO = daoFactoryInstance.getVoloAeroportoBoDAO();
		voloDAO.dropTable();
		voloDAO.createTable();
		
		// Inserisco dati
		VoloAeroportoBoDTO v = new VoloAeroportoBoDTO();
		
		v.setId(1);
		v.setCodVolo("Volo 1");
		v.setCompagniaAerea("Ryanair");
		v.setLocalitaDestinazione("Monaco di Baviera");
		v.setDataPartenza(new java.sql.Date(2023, 8, 31));
		v.setOrarioPartenza(new java.sql.Time(9, 50, 0));
		voloDAO.create(v);
		
		v.setId(2);
		v.setCodVolo("Volo 2");
		v.setCompagniaAerea("Lufthansa");
		v.setLocalitaDestinazione("Monaco di Baviera");
		v.setDataPartenza(new java.sql.Date(2023, 8, 31));
		v.setOrarioPartenza(new java.sql.Time(9, 55, 0));
		voloDAO.create(v);
		
		
		// Creo database
		TemplateMappingDAO mappingDAO = daoFactoryInstance.getTemplateMappingDAO();
		mappingDAO.dropTable();
		mappingDAO.createTable();
		
		// creo mapping
		mappingDAO.create(1, 1);
		mappingDAO.create(1, 2);
		mappingDAO.create(2, 2);
		
		// Richiedo metodo specifico
		String result = mappingDAO.compagniaSpecifiche("Monaco di Baviera");
		try {
			FileWriter fw = new FileWriter("CompagniaAerea.txt");
			fw.write("La compagnia aerea è: \n");
			fw.write(result);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
