package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.PiattoDAO;
import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementazione di query jdbc su una versione hqldb della tabella entry
 * 
 * La scrittura dei metodi DAO con JDBC segue sempre e comunque questo schema:
 * 
	// --- 1. Dichiarazione della variabile per il risultato ---
	// --- 2. Controlli preliminari sui dati in ingresso ---
	// --- 3. Apertura della connessione ---
	// --- 4. Tentativo di accesso al db e impostazione del risultato ---
	// --- 5. Gestione di eventuali eccezioni ---
	// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
	// --- 7. Restituzione del risultato (eventualmente di fallimento)
 * 
 * Inoltre, all'interno di uno statement JDBC si segue lo schema generale
 * 
	// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
	// --- b. Pulisci e imposta i parametri (se ve ne sono)
	// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
	// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
	// --- e. Rilascia la struttura dati del risultato
	// --- f. Rilascia la struttura dati dello statement
 * 
 * @author pisi79
 *
 */
public class Db2PiattoDAO implements PiattoDAO {
	
	//Do la possibilità di impostare l'ownership della tabella di mapping. Solo il lato della relazione
	//che detiene l'ownership si deve fare carico delle operazioni sulla tabella di mapping.
	private final boolean isOwner = false;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
	

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	static final String TABLE = "piatti";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	
	// == STATEMENT SQL ====================================================================

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nomepiatto VARCHAR(50), tipo VARCHAR(50), CHECK (tipo in ('antipasto', 'primo', 'secondo')) )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nomepiatto, tipo "+
			") " +
			"VALUES (?,?,?) "
		;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = 
		"SELECT * " +
		"FROM " + TABLE + " ";
	
	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = 
		"DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = 
		"UPDATE " + TABLE + " " +
			"SET " + 
				"nomepiatto" + " = ?, " +
				"tipo" + " = ? " +
			"WHERE " + ID + " = ? "
		;

	// SELECT * FROM table;
	static String query = 
		"SELECT * " +
			"FROM " + TABLE + " "
		;

	// -------------------------------------------------------------------------------------

	
	
	// === METODI DAO =========================================================================
	
	@Override
	public void create(PiattoDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNomePiatto());
			prep_stmt.setString(3, obj.getTipo());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getRistoranti() != null && !obj.getRistoranti().isEmpty()) {
			Db2MappingDAO dao = new Db2MappingDAO();
			//Occhio all'ordine di p e obj
			obj.getRistoranti().stream().forEach( p -> dao.create(p.getId(), obj.getId()) );
		}
	}

	// -------------------------------------------------------------------------------------

	@Override
	public PiattoDTO read(int id) {
		PiattoDTO result = null;
		if ( id < 0 )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			if ( rs.next() ) {
				PiattoDTO obj = new PiattoDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomePiatto(rs.getString("nomepiatto"));
				obj.setTipo(rs.getString("tipo"));
				
				//Da eliminare se non è owner
				if (isOwner) {
					MappingDAO db2MappingDAO = new Db2MappingDAO();
					obj.setRistoranti(db2MappingDAO.getRistorantiByPiattoId(obj.getId()));
				}
				
				result = obj;
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	//Da eliminare se non è owner
	@Override
	public PiattoDTO readLazy(int id) {
		PiattoDTO result = null;
		if (id < 0) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			if (rs.next()) {
				Db2PiattoProxyDTO obj = new Db2PiattoProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomePiatto(rs.getString("nomepiatto"));
				obj.setTipo(rs.getString("tipo"));
				
				result = obj;
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	@Override
	public Set<PiattoDTO> readAll() {
		Set<PiattoDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				PiattoDTO obj = new PiattoDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomePiatto(rs.getString("nomepiatto"));
				obj.setTipo(rs.getString("tipo"));
				
				//Da eliminare se non è owner
				if (isOwner) {
					MappingDAO db2MappingDAO = new Db2MappingDAO();
					obj.setRistoranti(db2MappingDAO.getRistorantiByPiattoId(obj.getId()));
				}
				
				result.add(obj);
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	
	//Ha senso solo se owner
	@Override
	public Set<PiattoDTO> readAllLazy() {
		Set<PiattoDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2PiattoProxyDTO obj = new Db2PiattoProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomePiatto(rs.getString("nomepiatto"));
				obj.setTipo(rs.getString("tipo"));
				
				result.add(obj);
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	// -------------------------------------------------------------------------------------

	@Override
	public boolean update(PiattoDTO obj) {
		boolean result = false;
		if ( obj == null )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNomePiatto());
			prep_stmt.setString(2, obj.getTipo());
			prep_stmt.setInt(3, obj.getId());
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		
		if (isOwner) {
			Db2MappingDAO mappingDAO = new Db2MappingDAO();
			if (obj.getRistoranti() == null || obj.getRistoranti().size() == 0) {
				mappingDAO.deleteByPiatto(obj.getId());
			}
			else {
				Set<RistoranteDTO> old = mappingDAO.getRistorantiByPiattoId(obj.getId());
				for (RistoranteDTO o : old) {
					if (!obj.getRistoranti().contains(o)) {
						//Occhio all'ordine di obj e o
						mappingDAO.delete(o.getId(), obj.getId());
					}
				}
				for (RistoranteDTO n : obj.getRistoranti()) {
					if (!old.contains(n)) {
						//Occhio all'ordine di obj e o
						mappingDAO.create(n.getId(), obj.getId());
					}
				}
			}
		}
		
		return result;
	}

	// -------------------------------------------------------------------------------------

	@Override
	public boolean delete(int id) {
		boolean result = false;
		if ( id < 0 )  {
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (isOwner) {
			new Db2MappingDAO().deleteByPiatto(id);
		}
		
		return result;
	}

	// -------------------------------------------------------------------------------------

	@Override
	public boolean createTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(create);
			result = true;
			stmt.close();
		}
		catch (Exception e) {
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (isOwner) {
			new Db2MappingDAO().createTable();
		}
		
		return result;
	}

	// -------------------------------------------------------------------------------------

	@Override
	public boolean dropTable() {
		boolean result = false;
		Connection conn = Db2DAOFactory.createConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(drop);
			result = true;
			stmt.close();
		}
		catch (Exception e) {
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		
		if (isOwner) {
			new Db2MappingDAO().dropTable();
		}
		
		return result;
	}

}