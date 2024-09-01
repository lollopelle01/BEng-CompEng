package it.unibo.tw.dao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import utils.Utils;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		//All'interno dei DAO è presente l'attributo isOwner, che indica quale lato si deve
		//occupare della relazione. Ovviamente sarà sempre il lato N della relazione 1-N a mantenere
		//l'attributo che referenzia l'altra tabella.
		//Si noti che l'ownership così implementata gestisce i SOLI riferimenti, perciò se un DTO
		//ha un riferimento a un altro DTO non ancora salvato su database, una persist del primo DTO 
		//fallirà per mancanza di chiave. Si noti anche che nel caso di ownership dal lato 1 della 1-N,
		//si può giungere a una situazione in cui gli oggetti del lato N hanno impostato a null il valore 
		//della foreign key in caso di eliminazione di tuple dalla tabella lato 1. Questo è quasi sempre poco
		//corretto da fare, ma ai fini di un semplice test non ci sono grossi problemi e inoltre si ricade
		//in problematiche di logica applicativa, che esulano dall'esercizio. E' chiaro che la soluzione
		//migliore a questo problema è far gestire in modo naturale la relazione al lato N.
		PartitaDAO partitaDAO = daoFactoryInstance.getPartitaDAO();
		StadioDAO stadioDAO = daoFactoryInstance.getStadioDAO();
		daoFactoryInstance.getIdBroker().deleteSequence();
		daoFactoryInstance.getIdBroker().createSequence();
		
		//Creazione tabella, per ultima quella con FK
		stadioDAO.dropTable();
		stadioDAO.createTable();
		partitaDAO.dropTable();
		partitaDAO.createTable();
		
		//Creazione dati
		PartitaDTO partita1 = new PartitaDTO("categoria1", "g1", "Bologna", "Rimini", Utils.newJavaDate(10, 10, 2010));
		PartitaDTO partita2 = new PartitaDTO("categoria2", "g1", "Bologna", "Milano", Utils.newJavaDate(9, 10, 2010));
		PartitaDTO partita3 = new PartitaDTO("categoria3", "g1", "Rimini", "Bologna", Utils.newJavaDate(11, 10 , 2010));
		PartitaDTO partita4 = new PartitaDTO("categoria1", "g2", "Rimini", "Milano", Utils.newJavaDate(8, 10, 2010));
		
		StadioDTO stadio1 = new StadioDTO("stadio1", "Bologna");
		StadioDTO stadio2 = new StadioDTO("stadio2", "Bologna");
		StadioDTO stadio3 = new StadioDTO("stadio3", "Rimini");
		
		
		stadio2.getPartite().add(partita1);
		stadio2.getPartite().add(partita2);
		stadio3.getPartite().add(partita3);
		stadio3.getPartite().add(partita4);
		
		//Persist dei dati, owner per ultimo
		partitaDAO.create(partita1);
		partitaDAO.create(partita2);
		partitaDAO.create(partita3);
		partitaDAO.create(partita4);
		stadioDAO.create(stadio1);
		stadioDAO.create(stadio2);
		stadioDAO.create(stadio3);
		
//		stadio2.setPartite(null);
//		stadioDAO.update(stadio2);

		//Stampe
		try {
			PrintWriter writer = new PrintWriter("Partite.txt");
			print(writer, stadio2);
			print(new PrintWriter(System.out), stadio2);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void print(PrintWriter writer, StadioDTO stadio) {
		
		HashMap<String, Integer> result = countPartiteGroupCategoriaByStadio(stadio);
		for (String key : result.keySet()) {
			writer.println(key+": "+result.get(key));
		}
		
		writer.flush();
	}
	
	public static HashMap<String, Integer> countPartiteGroupCategoriaByStadio (StadioDTO stadio) {
		//Il caricamento è eager, perciò non conviene fare il metodo con JDBC: ho già gli oggetti java, uso quelli
		HashMap<String, Integer> result = new HashMap<>();
		for (PartitaDTO partita : stadio.getPartite()) {
			if (result.containsKey(partita.getCategoria())) {
				result.put(partita.getCategoria(), result.get(partita.getCategoria())+1);
			}
			else {
				result.put(partita.getCategoria(), 1);
			}
		}
		return result;
	}

}
