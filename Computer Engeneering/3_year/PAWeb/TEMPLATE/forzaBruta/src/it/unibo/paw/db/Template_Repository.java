package it.unibo.paw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unibo.paw.model.entry;

public class Template_Repository {
	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	private static final String TABLE = "nome_tabella";

	// -------------------------------------------------------------------------------------

	private static final String ID = "id";
	// ... inserire altri attributi tabella ...

	// == STATEMENT SQL ====================================================================

	// create table
	private static String create = "CREATE TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
			COGNOME + " VARCHAR(20) NOT NULL, " +
			DATA + " DATE NOT NULL, " +
			NUMEROPERSONE + " INT NOT NULL, " +
			CELLULARE + " VARCHAR(10) NOT NULL, " +
			TAVOLO + " INT NOT NULL REFERENCES tavolo, " +
			"PRIMARY KEY (" + ID + "), " +
			"CONSTRAINT pt_PrenotazioneID UNIQUE (" + COGNOME + "," + DATA + "), " +
			"CONSTRAINT pr_PranotazioneTavoloID UNIQUE (" + DATA + "," + TAVOLO + ") " +
			") ";

	// drop table
	private static String drop = "DROP TABLE "+TABLE;

	// -------------------------------------------------------------------------------------

	private static final String insert = "INSERT INTO " + TABLE + " ( " +
			COGNOME + ", " + DATA + ", " + NUMEROPERSONE + ", " + CELLULARE + ", " + TAVOLO + " " +
			") " +
			"VALUES (?,?,?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + COGNOME + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " SET " +
			DATA + " = ?, " +
			NUMEROPERSONE + " = ?, " +
			CELLULARE + " = ?, " +
			TAVOLO + " = ? " +
			"WHERE " + COGNOME + " = ? ";

	// SELECT check postinsert
	static String check_query = "SELECT " + ID
			+ " FROM " + TABLE + " " +
			" WHERE " + COGNOME + " = ? AND " + DATA + " = ?";
	
	static String read_all = "SELECT * " 
			+ " FROM " + TABLE;

	// QUERY SPECIFICHE
	//..


	// =====================================================================================

	public Template_Repository(int databaseType) {
		dataSource = new DataSource(databaseType);
	}
	
	public void dropTable() throws PersistenceException {
		Connection conn = this.dataSource.getConnection();
		Statement stmt = null;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate(drop);
		}catch (SQLException e) {
			// the table does not exist
		}finally {
			try {
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
			}catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void createTable() throws PersistenceException {
		Connection connection = this.dataSource.getConnection();

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(create);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void persist(entry pr) throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtCheck = null;

		try {
			connection = this.dataSource.getConnection();
			pstmtInsert = connection.prepareStatement(insert);
			pstmtInsert.setString(1, pr. + "");
			// ... completare parametri insert ...
			pstmtInsert.executeUpdate();

			// Verifichiamo che dato inserito sia presente
			pstmtCheck = connection.prepareStatement(check_query);
			pstmtCheck.setString(1, pr. );
			pstmtCheck.setDate(2, pr. );
			ResultSet rs = pstmtCheck.executeQuery();
			rs.next();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (pstmtInsert != null)
					pstmtInsert.close();
				if (pstmtCheck != null)
					pstmtCheck.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void update(entry pr) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			statement.setString(1, pr.);
			// ... completare parametri update ...

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
	public List<bean> readAll() throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		List<bean> result =  new ArrayList<>();;
		bean entry = null;
		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(read_all);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				entry = new bean();
				entry.setIdPrenotazione(rs.getInt(ID));
				entry.setCognomePrenotazione(rs.getString(COGNOME));
				// ... inserire altri attributi dell'entry ...
				result.add(entry);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return result;
	}

	// OPERAZIONI SPECIFICHE 
	// ...

}
