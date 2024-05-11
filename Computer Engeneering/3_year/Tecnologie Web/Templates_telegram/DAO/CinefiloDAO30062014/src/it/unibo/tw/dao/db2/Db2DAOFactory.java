package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.ProiezioneDAO;
import it.unibo.tw.dao.SalaDAO;
import it.unibo.tw.dao.DAOFactory;
import it.unibo.tw.dao.IdBroker;
import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.CinefiloDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db2DAOFactory extends DAOFactory {

	/**
	 * Name of the class that holds the jdbc driver implementation for the DB2 database
	 */
	public static final String DRIVER = "com.ibm.db2.jcc.DB2Driver";
	
	/**
	 * URI of the database to connect to
	 */
	public static final String DBURL = "jdbc:db2://diva.deis.unibo.it:50000/tw_stud";

	public static final String USERNAME = "00718825";
	public static final String PASSWORD = "*";

	// --------------------------------------------

	// static initializer block to load db driver class in memory
	static {
		try {
			Class.forName(DRIVER);
		} 
		catch (Exception e) {
			System.err.println("HsqldbDAOFactory: failed to load DB2 JDBC driver" + "\n" + e.toString());
			e.printStackTrace();
		}
	}

	// --------------------------------------------

	public static Connection createConnection() {
		try {
			return DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
		} 
		catch (Exception e) {
			System.err.println(Db2DAOFactory.class.getName() + ".createConnection(): failed creating connection" + "\n" + e.toString());
			e.printStackTrace();
			System.err.println("Was the database started? Is the database URL right?");
			return null;
		}
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		}
		catch (Exception e) {
			System.err.println(Db2DAOFactory.class.getName() + ".closeConnection(): failed closing connection" + "\n" + e.toString());
			e.printStackTrace();
		}
	}

	// --------------------------------------------
	
	@Override
	public CinefiloDAO getCinefiloDAO() {
		return new Db2CinefiloDAO();
	}

	@Override
	public ProiezioneDAO getProiezioneDAO() {
		return new Db2ProiezioneDAO();
	}

	@Override
	public SalaDAO getSalaDAO() {
		return new Db2SalaDAO();
	}
	
	@Override
	public MappingDAO getMappingDAO() {
		return new Db2MappingDAO();
	}
	
	@Override
	public IdBroker getIdBroker() {
		return new Db2IdBroker();
	}
	
}
