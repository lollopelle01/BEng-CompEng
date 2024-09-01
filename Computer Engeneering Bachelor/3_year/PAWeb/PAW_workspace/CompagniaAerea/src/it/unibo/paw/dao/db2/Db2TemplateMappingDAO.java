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

	static final String TABLE = "PV";

	// -------------------------------------------------------------------------------------

	static final String ID_P = "idPasseggero";
	static final String ID_V = "idVolo";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( idCourse, idStudent ) VALUES ( ?,? );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID_P + ", " + ID_V + " " +
			") " +
			"VALUES (?,?) ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_P + " = ? " +
			"AND " + ID_V + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_passeggero = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_P + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_volo = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_V + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = "SELECT * " +
			"FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_P + " = ? " +
			"AND " + ID_V + " = ? ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID_P + " INT NOT NULL, " +
			ID_V + " INT NOT NULL, " +
			"PRIMARY KEY (" + ID_P + "," + ID_V + " ), " +
			"FOREIGN KEY (" + ID_P + ") REFERENCES Passeggero(id), " +
			"FOREIGN KEY (" + ID_V + ") REFERENCES Volo(id) " +
			") ";
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String voliByPasseggero_query = "SELECT * " +
			"FROM " + TABLE + " PV, Volo v " +
			"WHERE PV.idVolo = v.id AND " + ID_P + " = ? ";

	// SELECT * FROM table WHERE idcolumns = ?;
	static String passeggeriByVolo_query = "SELECT * " +
			"FROM " + TABLE + " PV, Passeggero p " +
			"WHERE PV.idPasseggero = p.id AND " + ID_V + " = ? ";
	
	// Query specifica
	static String compagnieByDestinazione = 
			"SELECT v.compagniaAerea " +
			"FROM Passeggero p, PV pv, Volo v " +
			"WHERE p.id = pv.idPasseggero AND v.id = pv.idVolo AND v.localitaDestinazione = ? " +
			"GROUP BY v.compagniaAerea " +
			"ORDER BY COUNT(p.id) " + 
			"FETCH FIRST 1 ROW ONLY" ;
			

	// === METODI DAO =========================================================================

	@Override
	public void create(int idPasseggero, int idVolo) {
		Connection conn = Db2DAOFactory.createConnection();
		if (idPasseggero < 0 || idVolo < 0) {
			System.err.println("create(): cannot insert an entry with an invalid id");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idPasseggero);
			prep_stmt.setInt(2, idVolo);
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
	public boolean delete(int idPasseggero, int idVolo) {
		boolean result = false;
		if (idPasseggero < 0 || idVolo < 0) {
			System.err.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idPasseggero);
			prep_stmt.setInt(2, idVolo);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println(
					"delete(): failed to delete entry with idCourse = " + idPasseggero + " and idStudent = " + idVolo + ": " + e.getMessage());
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
	public ArrayList<PasseggeroDTO> passeggeriByVolo(int idVolo) {
		ArrayList<PasseggeroDTO> result = null;
		if (idVolo < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		result = new ArrayList<PasseggeroDTO>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(passeggeriByVolo_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idVolo);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				PasseggeroDTO c = new PasseggeroDTO();
				c.setId(rs.getInt("id"));
				c.setCodPasseggero(rs.getString("codPasseggero"));
				c.setNome(rs.getString("nome"));
				c.setCognome(rs.getString("cognome"));
				c.setNumPassaporto(rs.getInt("numPassaporto"));
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

	@Override
	public ArrayList<VoloAeroportoBoDTO> voliByPasseggero(int id) {
		ArrayList<VoloAeroportoBoDTO> result = null;
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		result = new ArrayList<VoloAeroportoBoDTO>();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(voliByPasseggero_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				VoloAeroportoBoDTO s = new VoloAeroportoBoDTO();
				s.setId(rs.getInt("id"));
				s.setCodVolo(rs.getString("codVolo"));
				s.setCompagniaAerea(rs.getString("compagniaAerea"));
				s.setLocalitaDestinazione(rs.getString("localitaDestinazione"));
				s.setDataPartenza(rs.getDate("dataPartenza"));
				s.setOrarioPartenza(rs.getTime("orarioPartenza"));
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

	public String compagniaSpecifiche(String destinazione) {
		Connection conn = Db2DAOFactory.createConnection();
		String result = null;
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(compagnieByDestinazione);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, destinazione);
			ResultSet rs = prep_stmt.executeQuery();
			rs.next();
			result = rs.getString("compagniaAerea");
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
