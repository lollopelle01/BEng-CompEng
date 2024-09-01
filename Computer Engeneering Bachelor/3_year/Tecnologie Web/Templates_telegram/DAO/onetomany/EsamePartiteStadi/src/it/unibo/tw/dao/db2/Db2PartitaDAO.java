package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.PartitaDAO;
import it.unibo.tw.dao.PartitaDTO;
import it.unibo.tw.dao.StadioDAO;
import utils.Utils;

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
public class Db2PartitaDAO implements PartitaDAO {	
	
	//Do la possibilità di impostare l'ownership della relazione. Solo chi è owner si occupa
	//di gestire i riferimenti.
	private final boolean isOwner = false;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
	
	
	static final String TABLE = "partite";

	// -------------------------------------------------------------------------------------

	static final String ID = "codicepartita";
	
	// == STATEMENT SQL ====================================================================

	static String update_fk_by_id = "UPDATE "+TABLE+" SET stadio = ? WHERE "+ID+" = ? ";
	static String delete_fk_by_id = "UPDATE "+TABLE+" SET stadio = null WHERE "+ID+" = ? ";
	static String get_partite_by_stadio_id = "SELECT * FROM "+TABLE+" WHERE stadio = ? ";
	
	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " categoria VARCHAR(50), girone VARCHAR(50), nomesquadracasa VARCHAR(50), nomesquadraospite VARCHAR(50), data DATE, stadio INT REFERENCES stadi )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , categoria, girone, nomesquadracasa, nomesquadraospite, data "+
			") " +
			"VALUES (?,?,?,?,?,?) "
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
	
//	static String find_student_by_lastname = 
//		read_all +
//			"WHERE " + LASTNAME + " = ? ";
//	
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
				"categoria" + " = ?, " +
				"girone" + " = ?, " +
				"nomesquadracasa" + " = ?, " +
				"nomesquadraospite" + " = ?, " +
				"date"+" = ? "+ 
				"stadio"+" = ? "+ //SOLO SE OWNER!!!!
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
	public void create(PartitaDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setCodicePartita(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getCodicePartita());
			prep_stmt.setString(2, obj.getCategoria());
			prep_stmt.setString(3, obj.getGirone());
			prep_stmt.setString(4, obj.getNomeSquadraCasa());
			prep_stmt.setString(5, obj.getNomeSquadraOspite());
			prep_stmt.setDate(6, Utils.toSQLDate(obj.getData()));
			//Se owner settare anche la relazione
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------

	@Override
	public PartitaDTO read(int id) {
		PartitaDTO result = null;
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
				PartitaDTO obj = new PartitaDTO();
				obj.setCodicePartita(rs.getInt(ID));
				obj.setCategoria(rs.getString("categoria"));
				obj.setGirone(rs.getString("girone"));
				obj.setNomeSquadraCasa(rs.getString("nomesquadracasa"));
				obj.setNomeSquadraOspite(rs.getString("nomesquadraospite"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				
				//Da cancellare se non è owner
				if (isOwner) {
					StadioDAO stadioDAO = new Db2StadioDAO();
					obj.setStadio(stadioDAO.read(rs.getInt("stadio")));
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
	
	@Override
	public PartitaDTO readLazy(int id) {
		PartitaDTO result = null;
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
				Db2PartitaProxyDTO obj = new Db2PartitaProxyDTO();
				obj.setCodicePartita(rs.getInt(ID));
				obj.setCategoria(rs.getString("categoria"));
				obj.setGirone(rs.getString("girone"));
				obj.setNomeSquadraCasa(rs.getString("nomesquadracasa"));
				obj.setNomeSquadraOspite(rs.getString("nomesquadraospite"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				
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
	public Set<PartitaDTO> readAll() {
		Set<PartitaDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				PartitaDTO obj = new PartitaDTO();
				obj.setCodicePartita(rs.getInt(ID));
				obj.setCategoria(rs.getString("categoria"));
				obj.setGirone(rs.getString("girone"));
				obj.setNomeSquadraCasa(rs.getString("nomesquadracasa"));
				obj.setNomeSquadraOspite(rs.getString("nomesquadraospite"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				
				//Da cancellare se non è owner
				if (isOwner) {
					StadioDAO stadioDAO = new Db2StadioDAO();
					obj.setStadio(stadioDAO.read(rs.getInt("stadio")));
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
	
	@Override
	public Set<PartitaDTO> readAllLazy() {
		Set<PartitaDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2PartitaProxyDTO obj = new Db2PartitaProxyDTO();
				obj.setCodicePartita(rs.getInt(ID));
				obj.setCategoria(rs.getString("categoria"));
				obj.setGirone(rs.getString("girone"));
				obj.setNomeSquadraCasa(rs.getString("nomesquadracasa"));
				obj.setNomeSquadraOspite(rs.getString("nomesquadraospite"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				
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
	public boolean update(PartitaDTO obj) {
		boolean result = false;
		if ( obj == null )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getCategoria());
			prep_stmt.setString(2, obj.getGirone());
			prep_stmt.setString(3, obj.getNomeSquadraCasa());
			prep_stmt.setString(4, obj.getNomeSquadraOspite());
			prep_stmt.setDate(5, Utils.toSQLDate(obj.getData()));
			
			//Da cancellare se non è owner
			if (isOwner) {
				prep_stmt.setInt(6, obj.getStadio().getCodice());
			}

			prep_stmt.setInt(7, obj.getCodicePartita());
			
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
		
		return result;
	}
	
	//Necessario se l'altra tabella è owner, altrimenti no
	@Override
	public boolean updateFkById(int id, int fk) {
		boolean result = false;
		if ( id < 0 || fk < 0 )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update_fk_by_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, fk);
			prep_stmt.setInt(2, id);
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
		
		return result;
	}
	
	//Necessario se l'altra tabella è owner, altrimenti no
	@Override
	public boolean deleteFkById(int id) {
		boolean result = false;
		if ( id < 0 )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_fk_by_id);
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
		
		return result;
	}
	
	//Necessario se l'altra tabella è owner, altrimenti dipende dalla richiesta
	@Override
	public Set<PartitaDTO> getPartiteByStadioId(int id) {
		Set<PartitaDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(get_partite_by_stadio_id);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				PartitaDTO obj = new PartitaDTO();
				obj.setCodicePartita(rs.getInt(ID));
				obj.setCategoria(rs.getString("categoria"));
				obj.setGirone(rs.getString("girone"));
				obj.setNomeSquadraCasa(rs.getString("nomesquadracasa"));
				obj.setNomeSquadraOspite(rs.getString("nomesquadraospite"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				
				//Owner?? Gestire
				
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
		
		return result;
	}

}