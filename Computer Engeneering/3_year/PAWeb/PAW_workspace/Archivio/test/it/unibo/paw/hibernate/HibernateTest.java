package it.unibo.paw.hibernate;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import it.unibo.paw.hibernate.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import it.unibo.paw.hibernate.util.HibernateUtil;

public class HibernateTest {
	private static final String FIRSTQUERY_STR = 
			"select a.codiceArchivio, count(distinct o.formato) "
			+ "from Archivio_Fisico a "
			+ "join a.materiali  m "
			+ "join m.oggetti  o "
			+ "where year(a.dataCreazione) >= 2013 "
			+ "group by a.codiceArchivio ";

	private static final String SECONDQUERY_STR =
			"select distinct a.codiceArchivio "
					+ "from Archivio_Fisico a "
					+ "join a.materiali  m "
					+ "join m.oggetti  o "
					+ "where o.formato = 'jpeg' "
					+ "group by a.codiceArchivio ";

	public static void main(String[] argv) {

		Session session = null;
		Transaction tx = null;

		try {
			
			// Prepario tabelle su cui lavoro
			HibernateUtil.dropAndCreateTables();

			// Inizio sessione
			session = HibernateUtil.getSessionFactory().openSession();

			// Inizio transizione per riempire database
			tx = session.beginTransaction();
			
			// Archivio
			Archivio_Fisico archivio = new Archivio_Fisico();
			archivio.setId(1);
			archivio.setCodiceArchivio("CodiceArchivio 1");
			archivio.setNome("Archivio 1");
			archivio.setDescrizione("Descrizione 1");
			archivio.setDataCreazione(Date.valueOf(LocalDate.parse("2013-09-07")));
//			Set<Materiale_Fisico> materiali = archivio.getMateriali();
//			materiali.add(materiale); materiali.add(materiale1); materiali.add(materiale2); 
//			archivio.setMateriali(materiali);
			session.persist(archivio);
			
			Archivio_Fisico archivio1 = new Archivio_Fisico();
			archivio1.setId(2);
			archivio1.setCodiceArchivio("CodiceArchivio 2");
			archivio1.setNome("Archivio 2");
			archivio1.setDescrizione("Descrizione 2");
			archivio1.setDataCreazione(Date.valueOf(LocalDate.parse("2014-09-07")));
//			Set<Materiale_Fisico> materiali1 = archivio1.getMateriali();
//			materiali1.add(materiale); materiali1.add(materiale1); 
//			archivio.setMateriali(materiali1);
			session.persist(archivio1);

			Archivio_Fisico archivio2 = new Archivio_Fisico();
			archivio2.setId(3);
			archivio2.setCodiceArchivio("CodiceArchivio 3");
			archivio2.setNome("Archivio 3");
			archivio2.setDescrizione("Descrizione 3");
			archivio2.setDataCreazione(Date.valueOf(LocalDate.parse("2023-09-07")));
//			Set<Materiale_Fisico> materiali2 = archivio2.getMateriali();
//			materiali2.add(materiale); materiali2.add(materiale1); 
//			archivio.setMateriali(materiali2);
			session.persist(archivio2);
			
			// Materiale
			Materiale_Fisico materiale = new Materiale_Fisico();
			materiale.setId(1);
			materiale.setCodiceMateriale("CodiceMateriale 1");
			materiale.setNome("Materiale 1");
			materiale.setDescrizione("Descrizione 1");
			materiale.setDataCreazione(Date.valueOf(LocalDate.parse("2023-09-07")));
//			Set<Oggetto_Digitale> oggetti = materiale.getOggetti();
//			oggetti.add(oggetto); oggetti.add(oggetto1); oggetti.add(oggetto2);
//			materiale.setOggetti(oggetti);
			materiale.setIdArchivio(2);
			session.persist(materiale);

			Materiale_Fisico materiale1 = new Materiale_Fisico();
			materiale1.setId(2);
			materiale1.setCodiceMateriale("CodiceMateriale 2");
			materiale1.setNome("Materiale 2");
			materiale1.setDescrizione("Descrizione 2");
			materiale1.setDataCreazione(Date.valueOf(LocalDate.parse("2023-09-07")));
//			Set<Oggetto_Digitale> oggetti1 = materiale1.getOggetti();
//			oggetti1.add(oggetto); oggetti1.add(oggetto1);
//			materiale.setOggetti(oggetti1);
			materiale1.setIdArchivio(1);
			session.persist(materiale1);
			
			Materiale_Fisico materiale2 = new Materiale_Fisico();
			materiale2.setId(3);
			materiale2.setCodiceMateriale("CodiceMateriale 3");
			materiale2.setNome("Materiale 3");
			materiale2.setDescrizione("Descrizione 3");
			materiale2.setDataCreazione(Date.valueOf(LocalDate.parse("2023-09-07")));
//			Set<Oggetto_Digitale> oggetti2 = materiale2.getOggetti();
//			oggetti2.add(oggetto);
//			materiale.setOggetti(oggetti2);
			materiale2.setIdArchivio(1);
			session.persist(materiale2);

			// Oggetto
			Oggetto_Digitale oggetto = new Oggetto_Digitale();
			oggetto.setId(1);
			oggetto.setCodiceOggetto("CodiceOggetto 1");
			oggetto.setNome("Oggetto 1");
			oggetto.setFormato("jpeg");
			oggetto.setDataDigitalizzazione(Date.valueOf(LocalDate.parse("2023-09-07")));
			oggetto.setIdMateriale(1);
			session.persist(oggetto);
			
			Oggetto_Digitale oggetto1 = new Oggetto_Digitale();
			oggetto1.setId(2);
			oggetto1.setCodiceOggetto("CodiceOggetto 2");
			oggetto1.setNome("Oggetto 2");
			oggetto1.setFormato("xsd");
			oggetto1.setDataDigitalizzazione(Date.valueOf(LocalDate.parse("2023-09-07")));
			oggetto1.setIdMateriale(2);
			session.persist(oggetto1);
			
			Oggetto_Digitale oggetto2 = new Oggetto_Digitale();
			oggetto2.setId(3);
			oggetto2.setCodiceOggetto("CodiceOggetto 3");
			oggetto2.setNome("Oggetto 3");
			oggetto2.setFormato("xml");
			oggetto2.setDataDigitalizzazione(Date.valueOf(LocalDate.parse("2023-09-07")));
			oggetto2.setIdMateriale(1);
			session.persist(oggetto2);

			tx.commit();
			session.close();
			
			// Apro sessione per utilizzare database
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			String firstQueryResult = "";
			String secondQueryResult = "";

			//Approccio DB-Oriented --> meglio

			// Prima query
			Query firstQuery = session.createQuery(FIRSTQUERY_STR);
//			firstQuery.setString(1, "2014-01-01");
			List<Object[]> firstQueryRecords = firstQuery.list();
			for (Object[] arr : firstQueryRecords) {
				firstQueryResult += arr[0] + ":\t" + arr[1] + " formati diversi\n";
			}

			// Seconda query
			Query secondQuery = session.createQuery(SECONDQUERY_STR);
			List<Object[]> secondQueryRecords = secondQuery.list();
			for (Object arr : secondQueryRecords) {
				secondQueryResult += arr + "\n";
			}

			// Stampa del risultato su file
			FileWriter fw = new FileWriter("Archivio.txt");
			fw.write("Prima query:\n" + firstQueryResult + "\n\nSeconda query:\n");
			fw.write(secondQueryResult);
			fw.close();
		} catch (Exception e1) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
