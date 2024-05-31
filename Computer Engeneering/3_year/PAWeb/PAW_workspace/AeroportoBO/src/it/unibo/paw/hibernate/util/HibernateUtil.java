package it.unibo.paw.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();

	private static final String CREATE_PASSEGGERO = "CREATE TABLE Passeggero(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	codPasseggero varchar(50) NOT NULL UNIQUE," +
			"	nome varchar(50)," +
			"	cognome varchar(50)," +
			"	numPassaporto int " +
			")";

	private static final String CREATE_PV = "CREATE TABLE PV(" +
			"	idPasseggero INT NOT NULL," +
			"	idVolo INT NOT NULL," +
			"	PRIMARY KEY(idPasseggero, idVolo)," +
			"	FOREIGN KEY (idPasseggero) REFERENCES Passeggero(id)," +
			"	FOREIGN KEY (idVolo) REFERENCES VoloAeroportoBO(id) " +
			")";
	private static final String CREATE_VOLO = "CREATE TABLE VoloAeroportoBO(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	codVolo varchar(50) NOT NULL UNIQUE," +
			"	compagniaAerea varchar(50) NOT NULL ," +
			"	localitaDestinazione varchar(50)," +
			"	dataPartenza date," +
			"	orarioPartenza time " +
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
				session.createSQLQuery("DROP TABLE accertamenti").executeUpdate();
				session.createSQLQuery("DROP TABLE tipoAccertamento_ospedale").executeUpdate();
				session.createSQLQuery("DROP TABLE tipoAccertamento").executeUpdate();
				session.createSQLQuery("DROP TABLE ospedali").executeUpdate();
			} catch (HibernateException e) {
				System.out.println("dropTable(): failed to drop tables " + e.getMessage());
			}
			session.createSQLQuery(CREATE_PASSEGGERO).executeUpdate();
			session.createSQLQuery(CREATE_VOLO).executeUpdate();
			session.createSQLQuery(CREATE_PV).executeUpdate();
			
			tx.commit();
		} finally {
			session.close();
		}
	}
}
