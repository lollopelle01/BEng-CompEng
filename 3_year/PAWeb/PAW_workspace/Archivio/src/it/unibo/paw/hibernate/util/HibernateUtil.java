package it.unibo.paw.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();

	private static final String CREATE_ARCHIVIO = "CREATE TABLE Archivio(" +
			"	idArchivio INT NOT NULL PRIMARY KEY," +
			"	codiceArchivio varchar(50) NOT NULL UNIQUE," +
			"	nome varchar(50)," +
			"	descrizione varchar(50)," +
			"	dataCreazione date" +
			")";
	private static final String CREATE_MATERIALE = "CREATE TABLE Materiale(" +
			"	idMateriale INT NOT NULL PRIMARY KEY," +
			"	codiceMateriale varchar(50) NOT NULL UNIQUE," +
			"	nome varchar(50)," +
			"	descrizione varchar(50)," +
			"	dataCreazione date," +
			"	idArchivio INT NOT NULL," +
			"	FOREIGN KEY (idArchivio) REFERENCES Archivio(idArchivio) " +
			")";

	private static final String CREATE_OGGETTO = "CREATE TABLE Oggetto(" +
			"	idOggetto INT NOT NULL PRIMARY KEY," +
			"	codiceOggetto varchar(50) NOT NULL UNIQUE," +
			"	nome varchar(50)," +
			"	formato varchar(50)," +
			"	dataDigitalizzazione date," +
			"	idMateriale INT NOT NULL," +
			"	FOREIGN KEY (idMateriale) REFERENCES Materiale(idMateriale) " +
			")";

	private static SessionFactory initHibernateUtil() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	public static void dropAndCreateTables() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
					
			try {
				session.createSQLQuery("DROP TABLE Oggetto").executeUpdate();
				session.createSQLQuery("DROP TABLE Materiale").executeUpdate();
				session.createSQLQuery("DROP TABLE Archivio").executeUpdate();
			} catch (HibernateException e) {
				System.out.println("dropTable(): failed to drop tables " + e.getMessage());
			}
			session.createSQLQuery(CREATE_ARCHIVIO).executeUpdate();
			session.createSQLQuery(CREATE_MATERIALE).executeUpdate();
			session.createSQLQuery(CREATE_OGGETTO).executeUpdate();
			tx.commit();
		} finally {
			session.close();
		}
	}
}
