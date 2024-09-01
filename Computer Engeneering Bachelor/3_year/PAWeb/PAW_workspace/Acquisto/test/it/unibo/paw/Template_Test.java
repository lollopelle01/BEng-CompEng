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

	public static void main(String[] arg) throws PersistenceException {
		// Invocazione dei repository
		pr = new Template_Repository(DataSource.DB2);

		// Pulizia tabelle
		pr.dropTable();
		pr.createTable();

		System.out.println("Everything is fine so far");

		// Instanziare almeno 3 javabean
		Acquisto a = new Acquisto();
		a.setId(1);
		a.setCodiceAcquisto("Acquisto1");
		a.setImporto(5.50);
		a.setNomeAcquirente("Lorenzo");
		a.setCognomeAcquirente("Pellegrino");
		pr.persist(a);
		
		a.setId(2);
		a.setCodiceAcquisto("Acquisto2");
		a.setImporto(1000.50);
		a.setNomeAcquirente("Lorenzo");
		a.setCognomeAcquirente("Pellegrino");
		pr.persist(a);
		
		a.setId(3);
		a.setCodiceAcquisto("Acquisto3");
		a.setImporto(1500.50);
		a.setNomeAcquirente("Riccardo");
		a.setCognomeAcquirente("Pellegrino");
		pr.persist(a);
		
		a.setId(4);
		a.setCodiceAcquisto("Acquisto4");
		a.setImporto(2000.50);
		a.setNomeAcquirente("Riccardo");
		a.setCognomeAcquirente("Pellegrino");
		pr.persist(a);
		
		// Modificare 1 javabrean
		a.setId(4);
		a.setCodiceAcquisto("Acquisto4");
		a.setImporto(30.50);
		a.setNomeAcquirente("Riccardo");
		a.setCognomeAcquirente("Pellegrino");
		pr.update(a);
		
		// Uso metodo nuovo
		try {
			FileWriter fw = new FileWriter("Acquisto.txt");
			fw.write("Gli acquisti superiori a 1000:\n");
			for(String codice : pr.getAcquistiPerSoglia(1000)) {
				fw.write(codice + "\n");
			}
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Elimino acquisti
		pr.delete(1);
		pr.delete(2);
		pr.delete(3);
		pr.delete(4);
		
	}

}
