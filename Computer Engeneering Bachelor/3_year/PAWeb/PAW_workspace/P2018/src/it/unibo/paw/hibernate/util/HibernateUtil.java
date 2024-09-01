package it.unibo.paw.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();

	private static final String CREATE_PROGETTO = "CREATE TABLE Progetto(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	codiceProgetto varchar(100) NOT NULL UNIQUE," +
			"	nomeProgetto varchar(100)," +
			"	annoInizio int," +
			"	durata int " +
			")";
	private static final String CREATE_WP = "CREATE TABLE WP(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	nomeWP VARCHAR(100) NOT NULL UNIQUE," +
			"	titolo VARCHAR(100) NOT NULL," +
			"	descrizione varchar(255), " +
			"	idProgetto int not null, " +
			"	FOREIGN KEY (idProgetto) REFERENCES Progetto(id) " +
			")";

	private static final String CREATE_MAPPING = "CREATE TABLE WP_P(" +
			"	idWP INT NOT NULL," +
			"	idPartner INT NOT NULL," +
			"	PRIMARY KEY(idWP, idPartner)," +
			"	FOREIGN KEY (idWP) REFERENCES WP(id)," +
			"	FOREIGN KEY (idPartner) REFERENCES Partner(id) " +
			")";
	private static final String CREATE_PARTNER = "CREATE TABLE Partner(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	siglaPartner varchar(100) NOT NULL UNIQUE," +
			"	nome varchar(100) " +
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
				session.createSQLQuery("DROP TABLE Progetto").executeUpdate();
				session.createSQLQuery("DROP TABLE WP").executeUpdate();
				session.createSQLQuery("DROP TABLE WP_P").executeUpdate();
				session.createSQLQuery("DROP TABLE Partner").executeUpdate();
			} catch (HibernateException e) {
				System.out.println("dropTable(): failed to drop tables " + e.getMessage());
			}
			session.createSQLQuery(CREATE_PROGETTO).executeUpdate();
			session.createSQLQuery(CREATE_WP).executeUpdate();
			session.createSQLQuery(CREATE_PARTNER).executeUpdate();
			session.createSQLQuery(CREATE_MAPPING).executeUpdate();
			
			tx.commit();
		} finally {
			session.close();
		}
	}
}
