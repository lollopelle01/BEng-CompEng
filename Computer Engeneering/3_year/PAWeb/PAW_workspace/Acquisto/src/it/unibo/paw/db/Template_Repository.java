package it.unibo.paw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unibo.paw.model.Acquisto;

public class Template_Repository {
	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	private static final String TABLE = "Acquisto";

	// -------------------------------------------------------------------------------------

	private static final String ID = "id";
	private static final String CODICEACQUISTO = "codiceAcquisto";
	private static final String IMPORTO = "importo";
	private static final String NOMEACQUIRENTE = "nomeAcquirente";
	private static final String COGNOMEACQUIRENTE = "cognomeAcquirente";

	// == STATEMENT SQL ====================================================================

	// create table
	private static String create = "CREATE TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL, " + // GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
			CODICEACQUISTO + " VARCHAR(20) NOT NULL, " +
			IMPORTO + " FLOAT NOT NULL, " +
			NOMEACQUIRENTE + " VARCHAR(10) NOT NULL, " +
			COGNOMEACQUIRENTE + " VARCHAR(10) NOT NULL, " +
			"PRIMARY KEY (" + ID + ") " +
			") ";

	// drop table
	private static String drop = "DROP TABLE "+TABLE;

	// -------------------------------------------------------------------------------------

	private static final String insert = "INSERT INTO " + TABLE + " ( " + ID + ", " +
			CODICEACQUISTO + ", " + IMPORTO + ", " + NOMEACQUIRENTE + ", " + COGNOMEACQUIRENTE  + " " +
			") " +
			"VALUES (?,?,?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " SET " +
			CODICEACQUISTO + " = ?, " +
			IMPORTO + " = ?, " +
			NOMEACQUIRENTE + " = ?, " +
			COGNOMEACQUIRENTE + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT check postinsert
	static String check_query = "SELECT " + CODICEACQUISTO
			+ " FROM " + TABLE + " " +
			" WHERE " + ID + " = ? ";
	
	static String read_all = "SELECT * " 
			+ " FROM " + TABLE;
	
	static String readId = "SELECT * " 
			+ " FROM " + TABLE + " " +
			" WHERE " + CODICEACQUISTO + " = ? ";

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

	public void persist(Acquisto pr) throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtCheck = null;

		try {
			connection = this.dataSource.getConnection();
			pstmtInsert = connection.prepareStatement(insert);
			pstmtInsert.setInt(1, pr.getId());
			pstmtInsert.setString(2, pr.getCodiceAcquisto());
			pstmtInsert.setDouble(3, pr.getImporto());
			pstmtInsert.setString(4, pr.getNomeAcquirente());
			pstmtInsert.setString(5, pr.getCognomeAcquirente());
			// ... completare parametri insert ...
			pstmtInsert.executeUpdate();

			// Verifichiamo che dato inserito sia presente
			pstmtCheck = connection.prepareStatement(check_query);
			pstmtCheck.setInt(1, pr.getId());
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

	public void update(Acquisto pr) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			statement.setString(1, pr.getCodiceAcquisto());
			statement.setDouble(2, pr.getImporto());
			statement.setString(3, pr.getNomeAcquirente());
			statement.setString(4, pr.getCognomeAcquirente());
			statement.setInt(5, pr.getId());
			// ... completare parametri update ...
			statement.executeUpdate();

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
	
	public List<Acquisto> readAll() throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		List<Acquisto> result =  new ArrayList<>();;
		Acquisto entry = null;
		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(read_all);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				entry = new Acquisto();
				entry.setId(rs.getInt(ID));
				entry.setCodiceAcquisto(rs.getString(CODICEACQUISTO));
				entry.setImporto(rs.getDouble(IMPORTO));
				entry.setNomeAcquirente(rs.getString(NOMEACQUIRENTE));
				entry.setCognomeAcquirente(rs.getString(COGNOMEACQUIRENTE));
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

	public void delete(int id) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(delete);

			statement.setInt(1, id);
			// ... completare parametri update ...
			statement.executeUpdate();

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
	
	// OPERAZIONI SPECIFICHE 
	public List<String> getAcquistiPerSoglia(int soglia) throws PersistenceException{
		Connection connection = null;
		PreparedStatement pstmt = null;
		List<String> result =  new ArrayList<>();;
		Acquisto entry = null;
		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(read_all);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				entry = new Acquisto();
				entry.setId(rs.getInt(ID));
				entry.setCodiceAcquisto(rs.getString(CODICEACQUISTO));
				entry.setImporto(rs.getDouble(IMPORTO));
				entry.setNomeAcquirente(rs.getString(NOMEACQUIRENTE));
				entry.setCognomeAcquirente(rs.getString(COGNOMEACQUIRENTE));
				// ... inserire altri attributi dell'entry ...
				if(entry.getImporto() > soglia) {result.add(entry.getCodiceAcquisto());};
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
 
}
