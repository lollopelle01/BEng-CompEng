package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unibo.paw.dao.*;

public class Db2RicettaDAO implements RicettaDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "Ricetta";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String NOMERICETTA = "nomeRicetta";
	static final String TEMPOPREPARAZIONE = "tempoPreparazione";
	static final String LIVELLODIFFICOLTA = "livelloDifficolta";
	static final String CALORIE = "calorie";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID + ", " + 
			NOMERICETTA + ", " + 
			TEMPOPREPARAZIONE + ", " +
			LIVELLODIFFICOLTA + ", " +
			CALORIE + " " +
			") " +
			"VALUES (?,?,?,?,?) ";

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
			NOMERICETTA + " = ? , " + 
			TEMPOPREPARAZIONE + " = ? , " +
			LIVELLODIFFICOLTA + " = ? , " +
			CALORIE + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " +
			"FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL PRIMARY KEY, " +
			NOMERICETTA + " VARCHAR(100) NOT NULL, " +
			TEMPOPREPARAZIONE + " INT NOT NULL, " +
			LIVELLODIFFICOLTA + " INT NOT NULL, " +
			CALORIE + " INT NOT NULL " +
			") ";
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// === METODI DAO =========================================================================

	@Override
	public void create(RicettaDTO obj) {
		Connection conn = Db2DAOFactory.createConnection();
		if (obj == null) {
			System.err.println("create(): failed to insert a null entry");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, obj.getId()); 
			prep_stmt.setString(2, obj.getNomeRicetta());
			prep_stmt.setInt(3, obj.getTempoPreparazione()); 
			prep_stmt.setInt(4, obj.getLivelloDifficolta()); 
			prep_stmt.setInt(5, obj.getCalorie());
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
	public RicettaDTO read(int id) {
		RicettaDTO result = null;
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
				RicettaDTO entry = new RicettaDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomeRicetta(rs.getString(NOMERICETTA));
				entry.setTempoPreparazione(rs.getInt(TEMPOPREPARAZIONE));
				entry.setLivelloDifficolta(rs.getInt(LIVELLODIFFICOLTA));
				entry.setCalorie(rs.getInt(CALORIE));
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
	public boolean update(RicettaDTO obj) {
		boolean result = false;
		if (obj == null) {
			System.err.println("update(): failed to update a null entry");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNomeRicetta());
			prep_stmt.setInt(3, obj.getTempoPreparazione());
			prep_stmt.setInt(4, obj.getLivelloDifficolta());
			prep_stmt.setInt(5, obj.getCalorie());
			
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
			prep_stmt.setInt(1, id);
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
