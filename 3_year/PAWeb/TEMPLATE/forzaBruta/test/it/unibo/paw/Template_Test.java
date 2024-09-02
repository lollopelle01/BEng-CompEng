package it.unibo.paw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import it.unibo.paw.db.*;
import it.unibo.paw.model.*;

public class Template_Test {

	private static Template_Repository pr;
	private static TavoloRepository tr;

	private static String DisponibilitaTavolo(Date data, int persone) throws PersistenceException {

		return tr.availableTable(data, persone);
	}

	private static boolean RichiestaPrenotazione(String cognome, Date data, int persone, String cellulare) throws PersistenceException {
		String tableavailable = DisponibilitaTavolo(data, persone);
		if (tableavailable == null)
			return false;
		int numTavolo = tr.getIdFromNumber(tableavailable);
		PrenotazioneRistorante prr = new PrenotazioneRistorante();
		prr.setCellularePrenotazione(cellulare);
		prr.setCognomePrenotazione(cognome);
		prr.setDataPrenotazione(data);
		prr.setIdTavoloPrenotazione(numTavolo);
		prr.setNumeroPersonePrenotazione(persone);
		pr.persist(prr);
		return true;
	}

	public static void main(String[] arg) throws PersistenceException {
		// Invocazione dei repository
		pr = new Template_Repository(DataSource.DB2);

		// Pulizia tabelle
		pr.dropTable();
		pr.createTable();

		System.out.println("Everything is fine so far");

		// Creazione beans x test da inserire in tabelle
		Tavolo t = new Tavolo();
		t.setNumeroTavolo("1");
		t.setCapienzaTavolo(3);
		tr.persist(t);
		t = new Tavolo();
		t.setNumeroTavolo("2");
		t.setCapienzaTavolo(2);
		tr.persist(t);
		t = new Tavolo();
		t.setNumeroTavolo("4");
		t.setCapienzaTavolo(6);
		tr.persist(t);
		t = new Tavolo();
		t.setNumeroTavolo("3");
		t.setCapienzaTavolo(10);
		tr.persist(t);

		// Creazione richiesta
		PrenotazioneRistorante prr = new PrenotazioneRistorante();
		LocalDate d = LocalDate.of(2018, 1, 14);
		prr.setCognomePrenotazione("Pallino Pinco");
		prr.setDataPrenotazione(Date.valueOf(d));
		prr.setCellularePrenotazione("blablabla");
		prr.setIdTavoloPrenotazione(4);
		prr.setNumeroPersonePrenotazione(5);
		pr.persist(prr);

		PrintWriter pw = null;
		try {
			// File dove inserire risposta/e a richiesta
			pw = new PrintWriter(new FileWriter("resources/Prenotazione.txt"));
			
			// Ricavo lista di entry da scrivere nel file
			List<entry> prenotazioni = pr.readAll();
			if(prenotazioni.size() == 0)
				pw.println("Non sono presenti prenotazioni iniziali");
			else {
				pw.println("Prenotazioni iniziali: ");
				for(entry p : prenotazioni) {
					pw.println("-Prenotazione " + p.getIdPrenotazione()+ ": ");
					pw.println("	Cognome: " + p.getCognomePrenotazione());
					pw.println("	Data: " + p.getDataPrenotazione());
					pw.println("	Numero persone: " + p.getNumeroPersonePrenotazione());
					pw.println("	Cellulare: " + p.getCellularePrenotazione());
					pw.println("	Tavolo: " + tr.getNumberFromId(p.getIdTavoloPrenotazione()));
					pw.println("");
				}
			}
			
			String result = "";
			d = LocalDate.of(2017, 1, 18);
			if (RichiestaPrenotazione("PincoPanco", Date.valueOf(d), 4, "asdasd")) {
				result = "La prenotazione � avvenuta con successo ";
			} else
				result = "Non � stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit� di tavolo";
			System.out.println(result);
			d = LocalDate.of(2017, 1, 18);
			if (RichiestaPrenotazione("PincoPallino", Date.valueOf(d), 10, "asdasd")) {
				result = "La prenotazione � avvenuta con successo ";
			} else
				result = "Non � stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit� di tavolo";
			System.out.println(result);
			d = LocalDate.of(2017, 1, 18);
			if (RichiestaPrenotazione("PancoPinco", Date.valueOf(d), 5, "asdasd")) {
				result = "La prenotazione � avvenuta con successo ";
			} else
				result = "Non � stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit� di tavolo";
			System.out.println(result);
			d = LocalDate.of(2017, 1, 18);
			if (RichiestaPrenotazione("PallinoPanco", Date.valueOf(d), 3, "asdasd")) {
				result = "La prenotazione � avvenuta con successo ";
			} else
				result = "Non � stato possibile prenotare un tavolo per la data e/o le persone indicate causa indisponibilit� di tavolo";
			System.out.println(result);
			
			prenotazioni = pr.readAll();
			pw.println("Prenotazioni dopo l'inserimento: ");
			for(PrenotazioneRistorante p : prenotazioni) {
				pw.println("-Prenotazione " + p.getIdPrenotazione()+ ": ");
				pw.println("	Cognome: " + p.getCognomePrenotazione());
				pw.println("	Data: " + p.getDataPrenotazione());
				pw.println("	Numero persone: " + p.getNumeroPersonePrenotazione());
				pw.println("	Cellulare: " + p.getCellularePrenotazione());
				pw.println("	Tavolo: " + tr.getNumberFromId(p.getIdTavoloPrenotazione()));
				pw.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

	}

}
