package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unibo.paw.dao.*;

public class Db2MaratonaDAO implements MaratonaDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "Maratona";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String CODICE = "codice";
	static final String TITOLO = "titolo";
	static final String DATA = "data";
	static final String TIPO = "tipo";
	static final String IDCITTA = "idCitta";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID + ", " +
			CODICE + ", " +
			TITOLO + ", " +
			DATA + ", " +
			TIPO + ", " +
			IDCITTA + " " +
			") " +
			"VALUES (?,?,?,?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " " +
			"SET " +
			CODICE + " = ?," +
			TITOLO + " = ?," +
			DATA + " = ?," +
			TIPO + " = ?," +
			IDCITTA + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " +
			"FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL, " +
			CODICE + " VARCHAR(50) NOT NULL UNIQUE, " +
			TITOLO + " VARCHAR(50) NOT NULL, " +
			DATA + " DATE NOT NULL, " +
			TIPO + " VARCHAR(50) NOT NULL, " +
			IDCITTA + " INT NOT NULL, " +
			"PRIMARY KEY (" + ID + "), " +
			"FOREIGN KEY (" + IDCITTA + ") REFERENCES Citta(id) " +
			") ";
	
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// === METODI DAO =========================================================================

	@Override
	public void create(MaratonaDTO m) {
		Connection conn = Db2DAOFactory.createConnection();
		if (m == null) {
			System.err.println("create(): failed to insert a null entry");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, m.getId());
			prep_stmt.setString(2, m.getCodiceMaratona());
			prep_stmt.setString(3, m.getTitolo());
			prep_stmt.setDate(4, m.getData());
			prep_stmt.setString(5, m.getTipo());
			prep_stmt.setInt(6, m.getCitta().getId());
			
			prep_stmt.executeUpdate();
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
	}

	@Override
	public MaratonaDTO read(int id) {
		MaratonaDTO result = null;
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			if (rs.next()) {
				// Creo risultato
				MaratonaDTO entry = new MaratonaDTO();
				entry.setId(rs.getInt(ID));
				entry.setCodiceMaratona(rs.getString(CODICE));
				entry.setTitolo(rs.getString(TITOLO));
				entry.setData(rs.getDate(DATA));
				entry.setTipo(rs.getString(TIPO));
				
				// Eventualmente uso classi di mapping
				Db2CittaDAO cdao = new Db2CittaDAO();
				entry.setCitta(cdao.read(rs.getInt(CODICE)));
				result = entry;
			}
			rs.close();
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println("read(): failed to retrieve entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean update(MaratonaDTO m) {
		boolean result = false;
		if (m == null) {
			System.err.println("update(): failed to update a null entry");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, m.getId());
			prep_stmt.setString(2, m.getCodiceMaratona());
			prep_stmt.setString(3, m.getTitolo());
			prep_stmt.setDate(4, m.getData());
			prep_stmt.setString(5, m.getTipo());
			prep_stmt.setInt(6, m.getCitta().getId());
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println("insert(): failed to update entry: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;
		if (id < 0) {
			System.err.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setLong(1, id);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println("delete(): failed to delete entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean createTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(create);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.err.println("createTable(): failed to create table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public boolean dropTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(drop);
			result = true;
			stmt.close();
		} catch (Exception e) {
			System.err.println("dropTable(): failed to drop table '" + TABLE + "': " + e.getMessage());
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

}
