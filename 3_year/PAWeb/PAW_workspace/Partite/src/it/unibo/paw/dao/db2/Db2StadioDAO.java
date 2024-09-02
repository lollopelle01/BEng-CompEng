package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.paw.dao.*;

public class Db2StadioDAO implements StadioDAO {

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	static final String TABLE = "Stadio";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	static final String CODICE = "codice";
	static final String NOME = "nome";
	static final String CITTA = "citta";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID + ", " + 
			CODICE + ", " + 
			NOME + ", " + 
			CITTA + " " +
			") " +
			"VALUES (?,?,?,?) ";

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
			CODICE + " = ?, " +
			NOME + " = ?, " +
			CITTA + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT * FROM table;
	static String query = "SELECT * " +
			"FROM " + TABLE + " ";
	
	static String partiteByStadio_query = 
			"SELECT p.* " 
			+ "FROM  Partita p "
			+ "WHERE p.idStadio = ?";
	
	static String numPartitePerCategoria = 
			"SELECT p.categoria, count(*) as numPartite"
			+ " FROM Partita p"
			+ " WHERE p.idStadio = ?"
			+ " GROUP BY p.categoria";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL PRIMARY KEY, " +
			CODICE + " VARCHAR(50), " +
			NOME + " VARCHAR(50), " +
			CITTA + " VARCHAR(50) " +
			") ";
	static String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// === METODI DAO =========================================================================

	@Override
	public void create(StadioDTO s) {
		Connection conn = Db2DAOFactory.createConnection();
		if (s == null) {
			System.err.println("create(): failed to insert a null entry");
			return;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, s.getId());
			prep_stmt.setString(2, s.getCodice());
			prep_stmt.setString(3, s.getNome());
			prep_stmt.setString(4, s.getCitta());
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
	public StadioDTO read(int id) {
		StadioDTO result = null;
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
				StadioDTO entry = new StadioDTO();
				entry.setId(rs.getInt(ID));
				entry.setNome(rs.getString(NOME));
				entry.setCodice(rs.getString(NOME));
				entry.setCitta(rs.getString(NOME));
				entry.setPartite(this.getPartiteByStadio(entry.getId()));
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
	public boolean update(StadioDTO s) {
		boolean result = false;
		if (s == null) {
			System.err.println("update(): failed to update a null entry");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setInt(4, s.getId());
			prep_stmt.setString(1, s.getCodice());
			prep_stmt.setString(2, s.getNome());
			prep_stmt.setString(3, s.getCitta());
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

	public Set<PartitaDTO> getPartiteByStadio(int idStadio) {
		Connection conn = Db2DAOFactory.createConnection();
		Set<PartitaDTO> result = new HashSet<PartitaDTO>();
		if (idStadio < 0l) {
			System.err.println("create(): failed to insert a null entry");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(partiteByStadio_query);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idStadio);
			prep_stmt.close();
			ResultSet rs =  prep_stmt.executeQuery();
			while (rs.next()) {
				// Creo risultato
				PartitaDTO entry = new PartitaDTO();
				entry.setId(rs.getInt("id"));
				entry.setCodicePartita(rs.getString("codicePartita"));
				entry.setGirone(rs.getString("girone"));
				entry.setNomeSquadraCasa(rs.getString("nomeSquadraCasa"));
				entry.setNomeSquadraOspite(rs.getString("nomeSquadraOspite"));
				entry.setData(rs.getDate("data"));
				entry.setIdStadio(idStadio);
				result.add(entry);
			}
			
		} catch (Exception e) {
			System.err.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public Set<String> getNumPartitePerCategoria(int idStadio) {
		Connection conn = Db2DAOFactory.createConnection();
		Set<String> result = new HashSet<String>();
		if (idStadio < 0l) {
			System.err.println("create(): failed to insert a null entry");
			return result;
		}
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(numPartitePerCategoria);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idStadio);
			//prep_stmt.close();
			ResultSet rs =  prep_stmt.executeQuery();
			while (rs.next()) {
				// Creo risultato
				String entry="";
				entry = "Categoria:" + rs.getString("categoria") + "\tNumPartite:" + rs.getInt("numPartite");
				result.add(entry);
			}
			
		} catch (Exception e) {
			System.err.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	} 
}
