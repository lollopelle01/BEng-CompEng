package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unibo.paw.dao.*;

public class Db2GiocatoreDAO implements GiocatoreDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "GIocatore";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String CODICEFISCALE = "codiceFiscale";
	static final String COGNOME = "cognome";
	static final String NOME = "nome";
	static final String ETA = "eta";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	private static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID + ", " + 
			CODICEFISCALE + ", " + 
			COGNOME + ", " + 
			NOME + ", " + 
			ETA + " " +
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
	private static final String update = "UPDATE " + TABLE + " " +
			"SET " +
			CODICEFISCALE + " = ?, " +
			COGNOME + " = ?, " +
			NOME + " = ?, " +
			ETA + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " +
			"FROM " + TABLE + " ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	private static final String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL PRIMARY KEY, " +
			CODICEFISCALE + " VARCHAR(50) NOT NULL, " +
			COGNOME + " VARCHAR(50), " +
			NOME + " VARCHAR(50), " +
			ETA + " INT, " +
			"UNIQUE ( " + CODICEFISCALE + " ) " +
			") ";

	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// === METODI DAO =========================================================================

	@Override
	public int create(GiocatoreDTO giocatore) {
		int result = -1;
		Connection conn = Db2DAOFactory.createConnection();
		if (giocatore == null) {
			System.err.println("create(): failed to insert a null entry");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			Db2IdBroker idBroker = new Db2IdBroker();
			result = idBroker.newId();
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, result); //IDBROKER
			prep_stmt.setString(2, giocatore.getCodiceFiscale());
			prep_stmt.setString(3, giocatore.getCognome());
			prep_stmt.setString(4, giocatore.getNome());
			prep_stmt.setInt(5, giocatore.getEta());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		} catch (Exception e) {
			System.err.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
		
	}

	@Override
	public GiocatoreDTO read(int id) {
		GiocatoreDTO result = null;
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
				GiocatoreDTO entry = new GiocatoreDTO();
				entry.setId(rs.getInt(ID));
				entry.setCodiceFiscale(rs.getString(CODICEFISCALE));
				entry.setCognome(rs.getString(COGNOME));
				entry.setNome(rs.getString(NOME));
				entry.setEta(rs.getInt(ETA));
				
				// Eventualmente uso classi di mapping
				Db2TemplateMappingDAO csmdao = new Db2TemplateMappingDAO();
				entry.setSquadre(csmdao.getSquadreByGiocatore(entry.getId()));
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
	public boolean update(GiocatoreDTO giocatore) {
		boolean result = false;
		if (giocatore == null) {
			System.err.println("update(): failed to update a null entry");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, giocatore.getId());
			prep_stmt.setString(2, giocatore.getCodiceFiscale());
			prep_stmt.setString(3, giocatore.getCognome());
			prep_stmt.setString(4, giocatore.getNome());
			prep_stmt.setInt(5, giocatore.getEta());
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
