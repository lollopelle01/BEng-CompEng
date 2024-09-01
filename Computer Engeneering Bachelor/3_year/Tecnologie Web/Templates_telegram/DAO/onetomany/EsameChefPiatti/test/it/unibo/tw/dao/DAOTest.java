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
		PiattoDAO piattoDAO = daoFactoryInstance.getPiattoDAO();
		ChefDAO chefDAO = daoFactoryInstance.getChefDAO();
		daoFactoryInstance.getIdBroker().deleteSequence();
		daoFactoryInstance.getIdBroker().createSequence();
		
		//Creazione tabella, per ultima quella con FK
		chefDAO.dropTable();
		chefDAO.createTable();
		piattoDAO.dropTable();
		piattoDAO.createTable();
		
		//Creazione dati
		PiattoDTO piatto1 = new PiattoDTO("piatto1", 120, 500);
		PiattoDTO piatto2 = new PiattoDTO("piatto2", 60, 1000);
		PiattoDTO piatto3 = new PiattoDTO("piatto3", 50, 900);
		PiattoDTO piatto4 = new PiattoDTO("piatto4", 100, 300);
		
		ChefDTO chef1 = new ChefDTO("chef1", 1, "BolognaRes");
		ChefDTO chef2 = new ChefDTO("chef2", 3, "BolognaRes2");
		ChefDTO chef3 = new ChefDTO("chef3", 4, "RiminiRes");
		
		chef2.getPiatti().add(piatto1);
		chef2.getPiatti().add(piatto2);
		chef3.getPiatti().add(piatto3);
		chef3.getPiatti().add(piatto4);
		
		//Persist dei dati, owner per ultimo
		piattoDAO.create(piatto1);
		piattoDAO.create(piatto2);
		piattoDAO.create(piatto3);
		piattoDAO.create(piatto4);
		chefDAO.create(chef1);
		chefDAO.create(chef2);
		chefDAO.create(chef3);
		
//		chef2.setPiatti(null);
//		chefDAO.update(chef2);

		//Stampe
		try {
			PrintWriter writer = new PrintWriter("Chef.txt");
			print(writer, 2);
			print(new PrintWriter(System.out), 2); //debug mio
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void print(PrintWriter writer, int minimoStelle) {
		
		writer.println("Piatti piu brevi per ogni chef:");
		HashMap<ChefDTO, String> result = piattiPiuBrevi(DAOFactory.getDAOFactory(DAO).getChefDAO().readAlmenoStelle(minimoStelle));
		for (ChefDTO chef : result.keySet()) {
			writer.println(chef.getNomeChef()+": "+result.get(chef));
		}
		
		writer.flush();
	}
	
	public static HashMap<ChefDTO, String> piattiPiuBrevi(Set<ChefDTO> chefs) {
		HashMap<ChefDTO, String> result = new HashMap<>();
		for (ChefDTO chef : chefs)
			result.put(chef, nomePiattoPiuBreve(chef));
		return result;
	}
	
	public static String nomePiattoPiuBreve (ChefDTO chef) {
		String result = null;
		Integer tempoMin = null;
		for (PiattoDTO piatto : chef.getPiatti()) {
			if (tempoMin == null || piatto.getTempoPreparazione() < tempoMin) {
				tempoMin = piatto.getTempoPreparazione();
				result = piatto.getNomePiatto();
			}
		}
		return result;
	}

}
