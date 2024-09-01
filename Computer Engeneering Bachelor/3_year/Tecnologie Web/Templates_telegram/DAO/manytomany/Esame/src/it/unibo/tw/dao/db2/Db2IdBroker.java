package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.persistence.PersistenceException;

import it.unibo.tw.dao.IdBroker;

public class Db2IdBroker implements IdBroker {

	@Override
	public boolean createSequence() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE SEQUENCE sequenza_id AS INTEGER START WITH 1 INCREMENT BY 1 MINVALUE 0 MAXVALUE 9999999 NOCYCLE ");
			result = true;
			stmt.close();
		}
		catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean deleteSequence() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(
					"DROP SEQUENCE sequenza_id ");
			result = true;
			stmt.close();
		}
		catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public int newId() throws PersistenceException {
		Connection conn = Db2DAOFactory.createConnection();
		int newId;
		try {
			String sqlQuery = "SELECT (NEXTVAL FOR sequenza_id) as newId from sysibm.sysdummy1";
			PreparedStatement statement = conn.prepareStatement(sqlQuery);
			ResultSet result = statement.executeQuery();
			if (result.next()) 
				newId = result.getInt("newId");
			else throw 
				new PersistenceException("invalid id");
			result.close();
			statement.close();
		}
		catch (Exception e) {
			throw new PersistenceException ("Errore in IdBroker per DB2", e);
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return newId;
	}

}
