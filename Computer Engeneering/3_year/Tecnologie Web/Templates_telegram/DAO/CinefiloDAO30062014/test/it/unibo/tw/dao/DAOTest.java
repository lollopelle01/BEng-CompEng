package it.unibo.tw.dao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.Utils;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		//All'interno dei DAO sono presnti degli attributi isOwner che indicano quale lato della relazione
		//detiene l'ownership. Qual lato si occuperà di creare/distruggere la tabella di mapping e di
		//gestire le sue tuple in caso di aggiornamento, nel relativo DTO, del Set che rappresenta la relazione.
		//Se entrambi i DAO detengono l'ownership non si hanno problemi di cicli infiniti di creazione poichè 
		//il primo renderà coerente col Set la tabella di mapping, il secondo prima di agire la verificherà e non
		//farà nulla, poichè sarà già corretta. Lato Java i due oggetti devono pero' essere coerenti.
		//Si noti che ho gestito solo il mapping in modo automatico. Aggiungendo un nuovo oggetto, non precedentemente 
		//reso persistente, al Set di un altro DTO, l'update o la create falliranno perchè mancherà la chiave esterna.
		CinefiloDAO cinefiloDAO = daoFactoryInstance.getCinefiloDAO();
		ProiezioneDAO proiezioneDAO = daoFactoryInstance.getProiezioneDAO();
		SalaDAO salaDAO = daoFactoryInstance.getSalaDAO();
		daoFactoryInstance.getIdBroker().deleteSequence();
		daoFactoryInstance.getIdBroker().createSequence();
		
		//Creazione tabella
		salaDAO.dropTable();
		salaDAO.createTable();
		cinefiloDAO.dropTable();
		cinefiloDAO.createTable();
		//L'owner per ultimo
		//Se entrambe le tabelle sono owner la create della tabella di mapping da parte della prima tabella fallirà
		//ma avrà successo la seconda e il risultato sarà comunque consistente.
		proiezioneDAO.dropTable();
		proiezioneDAO.createTable();
		
		//Creazione dati
		CinefiloDTO cinefilo1 = new CinefiloDTO("cinefilo1", "M", 15);
		CinefiloDTO cinefilo2 = new CinefiloDTO("cinefilo2", "F", 30);
		CinefiloDTO cinefilo3 = new CinefiloDTO("cinefilo3", "altro", 40);
		
		ProiezioneDTO proiezione1 = new ProiezioneDTO("proiezione1", "Regista1", "Genere1", Utils.randomJavaDate());
		ProiezioneDTO proiezione2 = new ProiezioneDTO("proiezione2", "Regista2", "Genere2", Utils.randomJavaDate());
		ProiezioneDTO proiezione3 = new ProiezioneDTO("proiezione3", "Regista3", "Genere3", Utils.randomJavaDate());
		
		SalaDTO sala1 = new SalaDTO("Piazza Maggiore", 2000);
		SalaDTO sala2 = new SalaDTO("Sala2", 500);
		SalaDTO sala3 = new SalaDTO("Sala3", 300);
		
	
		
		//Persist dei dati, owner per ultimo
		proiezioneDAO.create(proiezione1);
		proiezioneDAO.create(proiezione2);
		proiezioneDAO.create(proiezione3);

		proiezione1.setSala(sala1);
		proiezione2.setSala(sala2);
		proiezione3.setSala(sala3);
		sala1.getProiezioni().add(proiezione1);
		sala2.getProiezioni().add(proiezione2);
		sala3.getProiezioni().add(proiezione3);
		
		salaDAO.create(sala1);
		salaDAO.create(sala2);
		salaDAO.create(sala3);
		cinefiloDAO.create(cinefilo1);
		cinefiloDAO.create(cinefilo2);
		cinefiloDAO.create(cinefilo3);
		
		
		proiezione1.getCinefili().add(cinefilo1);
		proiezione1.getCinefili().add(cinefilo2);
		proiezione2.getCinefili().add(cinefilo1);
		proiezione2.getCinefili().add(cinefilo2);
		proiezione3.getCinefili().add(cinefilo1);
		proiezione3.getCinefili().add(cinefilo2);
		proiezione3.getCinefili().add(cinefilo3);
		
		cinefilo1.getProiezioni().add(proiezione1);
		cinefilo1.getProiezioni().add(proiezione2);
		cinefilo1.getProiezioni().add(proiezione3);
		cinefilo2.getProiezioni().add(proiezione1);
		cinefilo2.getProiezioni().add(proiezione2);
		cinefilo2.getProiezioni().add(proiezione3);
		cinefilo3.getProiezioni().add(proiezione3);
		
		cinefiloDAO.update(cinefilo1);
		cinefiloDAO.update(cinefilo2);
		cinefiloDAO.update(cinefilo3);
		
//		proiezione2.setCinefili(null);
//		proiezioneDAO.update(proiezione2);

		//Stampe
		try {
			PrintWriter writer = new PrintWriter("statistica.txt");
			print(writer);
			print(new PrintWriter(System.out));
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void print(PrintWriter writer) {
		
		writer.println("Per ogni cinefilo minorenne, elenco distinto dei nomi delle sale in cui si sono tenute le proiezioni dei film a cui lo stesso ha partecipato:");
		Map<CinefiloDTO, Set<SalaDTO>> result = getElencoSalePartecipazioniPerCinefiloMinorenne();
		for (CinefiloDTO key : result.keySet()) {
			writer.println(key.getNomeCinefilo()+":");
			for (SalaDTO sala : result.get(key)) {
				writer.println(sala.getNome());
			}
		}
		
		writer.println();
		writer.println("Numero di cinefili donna che hanno partecipato alle proiezioni tenutesi in sala Piazza Maggiore: ");
		writer.println(numeroDonnePiazzaMaggiore());
		
		writer.flush();
	}
	
	public static Map<CinefiloDTO, Set<SalaDTO>> getElencoSalePartecipazioniPerCinefiloMinorenne() {
		Map<CinefiloDTO, Set<SalaDTO>> result = new HashMap<>();
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		CinefiloDAO cinefiloDAO = daoFactoryInstance.getCinefiloDAO();
		for (CinefiloDTO cinefilo : cinefiloDAO.readAllLazy()) {
			if (cinefilo.getEta() < 18) {
				Set<SalaDTO> sale = new HashSet<>();
				for (ProiezioneDTO proiezione : cinefilo.getProiezioni()) {
					sale.add(proiezione.getSala());
				}
				result.put(cinefilo, sale);
			}
		}
		return result;
	}
	
	public static int numeroDonnePiazzaMaggiore() {
		int result = 0;
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		CinefiloDAO cinefiloDAO = daoFactoryInstance.getCinefiloDAO();
		for (CinefiloDTO cinefilo : cinefiloDAO.readAllLazy()) {
			if (cinefilo.getSesso().equals("F")) {
				if (cinefilo.getProiezioni().stream().filter(p -> p.getSala().getNome().equals("Piazza Maggiore")).findAny().orElse(null) != null)
					result++;
			}
		}
		return result;
	}

}
