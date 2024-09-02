package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.paw.dao.*; // si può fare anche selezione più specifica

public class Db2TemplateMappingDAO implements TemplateMappingDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "SG";

	// -------------------------------------------------------------------------------------

	static final String ID_S = "idSquadra";
	static final String ID_G = "idGiocatore";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( idCourse, idStudent ) VALUES ( ?,? );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID_S + ", " + ID_G + " " +
			") " +
			"VALUES (?,?) ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_S + " = ? " +
			"AND " + ID_G + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_squadra = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_S + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_giocatore = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_G + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " +
			"FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_S + " = ? " +
			"AND " + ID_G + " = ? ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID_S + " INT NOT NULL, " +
			ID_G + " INT NOT NULL, " +
			"PRIMARY KEY (" + ID_S + "," + ID_G + " ), " +
			"FOREIGN KEY (" + ID_S + ") REFERENCES Squadra(id), " +
			"FOREIGN KEY (" + ID_G + ") REFERENCES Giocatore(id) " +
			") ";
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String squadreByGiocatore = "SELECT * " +
			"FROM " + TABLE + " SG, Squadra S " +
			"WHERE SG.idSquadra = S.id AND " + ID_G + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String giocatoriBySquadra_query = "SELECT * " +
			"FROM " + TABLE + " SG, Giocatore G " +
			"WHERE SG.idGiocatore = G.id AND " + ID_S + " = ? ";

	// === METODI DAO =========================================================================

	@Override
	public void create(int idCourse, int idStud) {
		Connection conn = Db2DAOFactory.createConnection();
		if (idCourse < 0 || idStud < 0) {
			System.err.println("create(): cannot insert an entry with an invalid id");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idCourse);
			prep_stmt.setInt(2, idStud);
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
	public boolean delete(int idCourse, int idStudent) {
		boolean result = false;
		if (idCourse < 0 || idStudent < 0) {
			System.err.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idCourse);
			prep_stmt.setInt(2, idStudent);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println(
					"delete(): failed to delete entry with idCourse = " + idCourse + " and idStudent = " + idStudent + ": " + e.getMessage());
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
	public Set<Squadra_PallacanestroDTO> getSquadreByGiocatore(int id) {
		Set<Squadra_PallacanestroDTO> result = null;
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		result = new HashSet<Squadra_PallacanestroDTO>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(squadreByGiocatore);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Squadra_PallacanestroDTO s = new Squadra_PallacanestroDTO();
				s.setId(rs.getInt("id"));
				s.setNome(rs.getString("nome"));
				s.setTorneo(rs.getString("torneo"));
				s.setAllenatore(rs.getString("allenatore"));
				result.add(s);
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

	@Override
	public Set<GiocatoreDTO> getGiocatoriBySquadra(int id) {
		Set<GiocatoreDTO> result = null;
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		result = new HashSet<GiocatoreDTO>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(giocatoriBySquadra_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				GiocatoreDTO g = new GiocatoreDTO();
				g.setId(rs.getInt("id"));
				g.setCodiceFiscale(rs.getString("codiceFiscale"));
				g.setCognome(rs.getString("cognome"));
				g.setNome(rs.getString("nome"));
				g.setEta(rs.getInt("eta"));
				result.add(g);
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
