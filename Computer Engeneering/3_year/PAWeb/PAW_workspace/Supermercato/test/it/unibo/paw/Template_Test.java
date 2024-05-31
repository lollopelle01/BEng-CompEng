package it.unibo.paw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import it.unibo.paw.db.*;
import it.unibo.paw.model.*;

public class Template_Test {

	private static Supermercato_Repository sr;
	private static Prodotto_Repository pr;

	private static boolean prodottoOfferto (String NomeSupermercato, String Descrizione, String Marca) throws PersistenceException {
		for(ProdottoOfferto p : sr.readProdotti(NomeSupermercato)) {
			if(p.getMarca().equals(Marca) && p.getDescrizione().equals(Descrizione)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] arg) throws PersistenceException {
		// Invocazione dei repository
		pr = new Prodotto_Repository(DataSource.DB2);
		sr = new Supermercato_Repository(DataSource.DB2);

		// Pulizia tabelle
		pr.dropTable();
		sr.dropTable();
		sr.createTable();
		pr.createTable();

		System.out.println("Everything is fine so far");

		// Creazione beans x test da inserire in tabelle
		Supermercato s = new Supermercato();
		s.setCodiceSuper(1);
		s.setNome("Supermercato 1");
		s.setRatingGradimento(3);
		sr.persist(s);
		
		Supermercato s1 = new Supermercato();
		s1.setCodiceSuper(2);
		s1.setNome("Supermercato 2");
		s1.setRatingGradimento(5);
		sr.persist(s1);
		
		ProdottoOfferto p = new ProdottoOfferto();
		p.setCodiceProdotto(1);
		p.setDescrizione("Descrizione 1");
		p.setMarca("Marca 1");
		p.setPrezzo(10.00f);
		p.setIdSupermercato(1);
		pr.persist(p);
		
		ProdottoOfferto p1 = new ProdottoOfferto();
		p1.setCodiceProdotto(2);
		p1.setDescrizione("Descrizione 1");
		p1.setMarca("Marca 2");
		p1.setPrezzo(15.00f);
		p1.setIdSupermercato(2);
		pr.persist(p1);
		
		ProdottoOfferto p2 = new ProdottoOfferto();
		p2.setCodiceProdotto(3);
		p2.setDescrizione("Descrizione 2");
		p2.setMarca("Marca 1");
		p2.setPrezzo(20.00f);
		p2.setIdSupermercato(1);
		pr.persist(p2);
		
		try {
			FileWriter fw = new FileWriter("Supermercato.txt");

			fw.write("E' presente nel Supermercato 1 il prodotto (Descrizione 1 , Marca 1 )?\n");
			fw.write((prodottoOfferto("Supermercato 1", "Descrizione 1", "Marca 1") ? "si" : "no"));
			fw.write("\nE' presente nel Supermercato 2 il prodotto (Descrizione 1 , Marca 1 )?\n");
			fw.write((prodottoOfferto("Supermercato 2", "Descrizione 1", "Marca 1") ? "si" : "no"));
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
