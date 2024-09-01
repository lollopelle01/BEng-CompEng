package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unibo.paw.dao.*; // si può fare anche selezione più specifica

public class Db2TemplateMappingDAO implements TemplateMappingDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "RI";

	// -------------------------------------------------------------------------------------

	static final String ID_R = "idRicetta";
	static final String ID_I = "idIngrediente";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( idCourse, idStudent ) VALUES ( ?,? );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID_R + ", " + ID_I + " " +
			") " +
			"VALUES (?,?) ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_R + " = ? " +
			"AND " + ID_I + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_ricetta = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_R + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_ingrediente = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_I + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " +
			"FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_R + " = ? " +
			"AND " + ID_I + " = ? ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID_R + " INT NOT NULL, " +
			ID_I + " INT NOT NULL, " +
			"PRIMARY KEY (" + ID_R + "," + ID_I + " ), " +
			"FOREIGN KEY (" + ID_R + ") REFERENCES Ricetta(id), " +
			"FOREIGN KEY (" + ID_I + ") REFERENCES Ingrediente(id) " +
			") ";
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String ricetteByIngrediente_query = "SELECT * " +
			"FROM " + TABLE + " RI, Ricetta R " +
			"WHERE RI.idRicetta = R.id AND " + ID_I + " = ? ";

	// === METODI DAO =========================================================================

	@Override
	public void create(int idRicetta, int idIngrediente) {
		Connection conn = Db2DAOFactory.createConnection();
		if (idRicetta < 0 || idIngrediente < 0) {
			System.err.println("create(): cannot insert an entry with an invalid id");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idRicetta);
			prep_stmt.setInt(2, idIngrediente);
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
	public boolean delete(int idRicetta, int idIngrediente) {
		boolean result = false;
		if (idRicetta < 0 || idIngrediente < 0) {
			System.err.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idRicetta);
			prep_stmt.setInt(2, idIngrediente);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println(
					"delete(): failed to delete entry with idRicetta = " + idRicetta + " and idIngrediente = " + idIngrediente + ": " + e.getMessage());
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

	@Override
	public ArrayList<RicettaDTO> getRicetteByIngrediente(int id) {
		ArrayList<RicettaDTO> result = null;
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		result = new ArrayList<RicettaDTO>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(ricetteByIngrediente_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				RicettaDTO c = new RicettaDTO();
				c.setId(rs.getInt("id"));
				c.setNomeRicetta(rs.getString("nomeRicetta"));
				c.setTempoPreparazione(rs.getInt("tempoPreparazione"));
				c.setLivelloDifficolta(rs.getInt("livelloDifficolta"));
				c.setCalorie(rs.getInt("calorie"));
				
				result.add(c);
			}
			rs.close();
			prep_stmt.close();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

}
