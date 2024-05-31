package it.unibo.paw.dao;

import it.unibo.paw.dao.db2.Db2DAOFactory;
//import it.unibo.paw.dao.db2.lazy.Db2DAOFactory; //Lazy Solution

public abstract class DAOFactory {

	// --- List of supported DAO types ---

	/**
	 * Numeric constant '0' corresponds to explicit DB2 choice
	 */
	public static final int DB2 = 0;
	
	// /**
	//  * Numeric constant '1' corresponds to explicit Hsqldb choice
	//  */
	// public static final int HSQLDB = 1;
	
	// /**
	//  * Numeric constant '2' corresponds to explicit MySQL choice
	//  */
	// public static final int MYSQL = 2;
	
	
	// --- Actual factory method ---
	
	/**
	 * Depending on the input parameter
	 * this method returns one out of several possible 
	 * implementations of this factory spec 
	 */
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		case DB2:
			return new Db2DAOFactory();
		default:
			return null;
		}
	}
	
	
	
	// --- Factory specification: concrete factories implementing this spec must provide this methods! ---
	
	/**
	 * Method to obtain a DATA ACCESS OBJECT
	 * for the datatype 'Student'
	 */
	public abstract VoloAeroportoBoDAO getVoloAeroportoBoDAO();
	
	public abstract PasseggeroDAO getPasseggeroDAO();

	/**
	 * Method to obtain a DATA ACCESS OBJECT
	 * for the datatype 'Student'
	 */
	public abstract TemplateMappingDAO getTemplateMappingDAO();
	
}
