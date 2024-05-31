package it.unibo.paw.hibernate;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import it.unibo.paw.hibernate.util.HibernateUtil;

public class HibernateTest {
	private static final String FIRSTQUERY_STR = 
			"select p.nome , p.cognome "
			+ "from Passeggero p "
			+ "join p.voliAeroportoBO VoloAeroportoBO "
			+ "where VoloAeroportoBO.localitaDestinazione = ? ";

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

			Passeggero p1 = new Passeggero();
			p1.setId(1);
			p1.setCodPasseggero("Passeggero 1");
			p1.setNome("Lorenzo");
			p1.setCognome("Pellegrino");
			p1.setNumPassaporto(123456789);
			session.persist(p1);
			
			Passeggero p2 = new Passeggero();
			p2.setId(2);
			p2.setCodPasseggero("Passeggero 2");
			p2.setNome("Riccardo");
			p2.setCognome("Pellegrino");
			p2.setNumPassaporto(987654321);
			session.persist(p2);
			
			VoloAeroportoBO v1 = new VoloAeroportoBO();
			v1.setId(1);
			v1.setCodVolo("Volo 1");
			v1.setCompagniaAerea("Ryanair");
			v1.setLocalitaDestinazione("Amsterdam");
			v1.setDataPartenza(new Date(5000));
			v1.setOrarioPartenza(new Time(700));
			Set<Passeggero> passeggeri = new HashSet<Passeggero>();
			passeggeri.add(p1); passeggeri.add(p2);
			v1.setPasseggeri(passeggeri);
			session.persist(v1);
			
			VoloAeroportoBO v2 = new VoloAeroportoBO();
			v2.setId(2);
			v2.setCodVolo("Volo 2");
			v2.setCompagniaAerea("Luftansa");
			v2.setLocalitaDestinazione("Crotone");
			v2.setDataPartenza(new Date(5000));
			v2.setOrarioPartenza(new Time(700));
			Set<Passeggero> passeggeri1 = new HashSet<Passeggero>();
			passeggeri1.add(p1);
			v2.setPasseggeri(passeggeri1);
			session.persist(v1);
 
			tx.commit();
			session.close();

			// Apro sessione per utilizzare database
			session = HibernateUtil.getSessionFactory().openSession();

			// Query
			String firstQueryResult = "I passeggeri che andranno ad Amsterdam:\n";
			Query secondQuery = session.createQuery(FIRSTQUERY_STR);
			secondQuery.setString(0, "Amsterdam");
			List<Object[]> secondQueryRecords = secondQuery.list();
			for (Object[] arr : secondQueryRecords) {
				firstQueryResult += arr[0] + " " + arr[1] + "\n";
			}

			// Stampa del risultato su file
			PrintWriter pw = new PrintWriter(new FileWriter("AeroportoBO.txt"));
			pw.println(firstQueryResult);
			pw.close();
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
