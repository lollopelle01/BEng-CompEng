package it.unibo.paw.hibernate;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import it.unibo.paw.hibernate.util.HibernateUtil;

public class HibernateTest {
	private static final String FIRSTQUERY_STR = 
			"SELECT " +
				"P.nomeProgetto AS NomeProgetto, " +
				"COUNT(DISTINCT WP.id) AS NumeroWorkPackage, " +
				"SUM(CASE WHEN Partner.id IS NOT NULL THEN 1 ELSE 0 END) AS NumeroPartner " +
		    "FROM Progetto P " +
		    	"JOIN P.workPackages WP " +
		    	"LEFT JOIN WP.partners Partner " +
		    "WHERE P.annoInizio = ? " +
		    "GROUP BY P.nomeProgetto";

	private static final String SECONDQUERY_STR = 
			"SELECT p.nome " +
			"FROM Partner p "+
					"JOIN p.workPackages WP " +
			"GROUP BY p.nome " +
			"HAVING COUNT(DISTINCT WP.id) > 1 " +
			"ORDER BY COUNT(DISTINCT WP.id) DESC " +
			"FETCH FIRST 1 ROW ONLY";

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

			Progetto p1 = new Progetto();
			p1.setId(1);
			p1.setCodPorgetto("Progetto 1");
			p1.setNomeProgetto("CashFlow");
			p1.setAnnoInizio(2018);
			p1.setDurata(10);
			session.persist(p1);
			
			Progetto p2 = new Progetto();
			p2.setId(2);
			p2.setCodPorgetto("Progetto 2");
			p2.setNomeProgetto("RISIKO");
			p2.setAnnoInizio(2018);
			p2.setDurata(100);
			session.persist(p2);
			
			WorkPackage wp1 = new WorkPackage();
			wp1.setId(1);
			wp1.setNomeWP("WP1");
			wp1.setProgetto(p1);
			wp1.setTitolo("Economia");
			wp1.setDescrizione("Economia per il sociale");
			session.persist(wp1);
			
			WorkPackage wp2 = new WorkPackage();
			wp2.setId(2);
			wp2.setNomeWP("WP2");
			wp2.setProgetto(p2);
			wp2.setTitolo("Gioco");
			wp2.setDescrizione("Giochiamo insieme");
			session.persist(wp2);
			
			WorkPackage wp3 = new WorkPackage();
			wp3.setId(3);
			wp3.setNomeWP("WP3");
			wp3.setProgetto(p2);
			wp3.setTitolo("Guerra");
			wp3.setDescrizione("Che la battaglia inizi");
			session.persist(wp3);
			
			Partner pn1 = new Partner();
			pn1.setId(1);
			pn1.setNome("Lorenzo");
			pn1.setSiglaPartner("Pelle");
			Set<WorkPackage> packages = new HashSet<WorkPackage>();
			packages.add(wp1);
			packages.add(wp2);
			packages.add(wp3);
			pn1.setWorkPackages(packages);
			session.persist(pn1);
			
			Partner pn2 = new Partner();
			pn2.setId(2);
			pn2.setNome("Riccardo");
			pn2.setSiglaPartner("Ricky");
			Set<WorkPackage> packages1 = new HashSet<WorkPackage>();
			packages1.add(wp1);
			packages1.add(wp2);
			pn2.setWorkPackages(packages1);
			session.persist(pn2);
			
			tx.commit();
			session.close();
			
			session = HibernateUtil.getSessionFactory().openSession();
		
			// Query 1
			String firstQueryResults = "";
			Query firstQuery = session.createQuery(FIRSTQUERY_STR);
			firstQuery.setInteger(0, 2018);
			List<Object[]> firstQueryRecords = firstQuery.list();
			for (Object[] arr : firstQueryRecords) {
				firstQueryResults += "Nel progetto " + arr[0] + " ci sono " + arr[1] + " packages in cui lavorano " + arr[2] + " partners \n";
			}
			
			// Query 2
			String secondQueryResults = "";
			Query secondQuery = session.createQuery(SECONDQUERY_STR);
			List<Object[]> secondQueryRecords = secondQuery.list();
			secondQueryResults = "Il partner che ha partecipato a più workspace è " + secondQueryRecords.get(0);
			
			// Stampa risultati
			FileWriter fw = new FileWriter("Progetti-2018.txt");
			fw.write(firstQueryResults+"\n\n\n");
			fw.write(secondQueryResults);
			fw.close();
			
			// eliminare progetti
			session.delete(p1);
			session.delete(p1);
			
			
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
