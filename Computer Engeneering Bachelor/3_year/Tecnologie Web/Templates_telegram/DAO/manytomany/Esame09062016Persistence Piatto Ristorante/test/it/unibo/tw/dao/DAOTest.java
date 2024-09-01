package it.unibo.tw.dao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		//All'interno dei DAO sono presnti degli attributi isOwner che indicano quale lato della relazione
		//detiene l'ownership. Qual lato si occuperà di creare/distruggere la tabella di mapping e di
		//gestire le sue tuple in caso di aggiornamento, nel relativo DTO, del Set che rappresenta la relazione.
		//Se entrambi i DAO detengono l'ownership non si hanno problemi di cicli infiniti di creazione poichè 
		//il primo renderà coerente col Set la tabella di mapping, il secondo prima di agire la verificherà e non
		//farà nulla, poichè sarà già corretta.
		//Si noti che ho gestito solo il mapping in modo automatico. Aggiungendo un nuovo oggetto, non precedentemente 
		//reso persistente, al Set di un altro DTO, l'update o la create falliranno perchè mancherà la chiave esterna.
		PiattoDAO piattoDAO = daoFactoryInstance.getPiattoDAO();
		RistoranteDAO ristoranteDAO = daoFactoryInstance.getRistoranteDAO();
		daoFactoryInstance.getIdBroker().deleteSequence();
		daoFactoryInstance.getIdBroker().createSequence();
		
		//Creazione tabella
		piattoDAO.dropTable();
		piattoDAO.createTable();
		//L'owner per ultimo
		//Se entrambe le tabelle sono owner la create della tabella di mapping da parte della prima tabella fallirà
		//ma avrà successo la seconda e il risultato sarà comunque consistente.
		ristoranteDAO.dropTable();
		ristoranteDAO.createTable();
		
		//Creazione dati
		PiattoDTO piatto1 = new PiattoDTO("piatto1", "primo");
		PiattoDTO piatto2 = new PiattoDTO("piatto2", "secondo");
		PiattoDTO piatto3 = new PiattoDTO("piatto3", "antipasto");
		PiattoDTO piatto4 = new PiattoDTO("seppie con piselli", "secondo");
		
		RistoranteDTO ristorante1 = new RistoranteDTO("ristorante1", "Bologna", 4);
		RistoranteDTO ristorante2 = new RistoranteDTO("ristorante2", "Bologna", 5);
		RistoranteDTO ristorante3 = new RistoranteDTO("ristorante3", "Rimini", 2);
		
		ristorante1.getPiatti().add(piatto1);
		ristorante2.getPiatti().add(piatto1);
		ristorante2.getPiatti().add(piatto2);
		ristorante2.getPiatti().add(piatto4);
		ristorante3.getPiatti().add(piatto1);
		ristorante3.getPiatti().add(piatto2);
		ristorante3.getPiatti().add(piatto3);
		
		//Persist dei dati, owner per ultimo
		piattoDAO.create(piatto1);
		piattoDAO.create(piatto2);
		piattoDAO.create(piatto3);
		piattoDAO.create(piatto4);
		ristoranteDAO.create(ristorante1);
		ristoranteDAO.create(ristorante2);
		ristoranteDAO.create(ristorante3);
		
//		ristorante2.setPiatti(null);
//		ristoranteDAO.update(ristorante2);

		//Stampe
		try {
			PrintWriter writer = new PrintWriter("ristorante.txt");
			print(writer);
			print(new PrintWriter(System.out));
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void print(PrintWriter writer) {
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		RistoranteDAO ristoranteDAO = daoFactoryInstance.getRistoranteDAO();
		
		writer.println("Lista primi dei ristoranti di Bologna:");
		Set<RistoranteDTO> ristoranti = ristoranteDAO.readLazyByIndirizzo("Bologna");
		for (RistoranteDTO ristorante : ristoranti) {
			writer.println(ristorante.getNomeRistorante());
			ristorante.getPiatti().stream().filter(p -> p.getTipo().equalsIgnoreCase("Primo")).forEach(p -> writer.println("  "+p.getNomePiatto()));
		}
		
		writer.println("Numero ristoranti tra 4 e 5 nel rating che offrono seppie con piselli tra i secondi: ");
		ristoranti = ristoranteDAO.readLazyByFascia(4, 5);
		int count = 0;
		for (RistoranteDTO ristorante : ristoranti) {
			Set<PiattoDTO> piatti = ristorante.getPiatti();
			for (PiattoDTO piatto : piatti) {
				if (piatto.getTipo().equalsIgnoreCase("secondo") && piatto.getNomePiatto().equalsIgnoreCase("seppie con piselli")) {
					count++;
					break;
				}
			}
		}
		writer.println(count);
		
		writer.flush();
	}

}
