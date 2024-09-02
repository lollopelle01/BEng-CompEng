package it.unibo.tw.dao.db2;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.PartitaDTO;
import it.unibo.tw.dao.StadioDAO;
import it.unibo.tw.dao.StadioDTO;

public class Db2StadioDAO implements StadioDAO {
	
	//Do la possibilità di impostare l'ownership della relazione. Solo chi è owner si occupa
	//di gestire i riferimenti.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
		
	
	static final String TABLE = "stadi";
	static final String ID = "codice";

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nome VARCHAR(50) NOT NULL, citta VARCHAR(50) NOT NULL )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nome, citta "+
			") " +
			"VALUES (?,?,?) "
		;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;
	
	static String read_by_id_partita = "SELECT S.* FROM "+TABLE+" S, partite P WHERE S.codice = P.codice AND P.codicepartita = ? ";
//	static String read_by_indirizzo =
//		"SELECT * " +
//			"FROM " + TABLE + " " +
//			"WHERE indirizzo = ? "
//		;
	

	// SELECT * FROM table;
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
				"nome" + " = ?, " +
				"citta" + " = ? " +
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
	public void create(StadioDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setCodice(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getCodice());
			prep_stmt.setString(2, obj.getNome());
			prep_stmt.setString(3, obj.getCitta());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getPartite() != null && !obj.getPartite().isEmpty()) {
			Db2PartitaDAO dao = new Db2PartitaDAO();
			obj.getPartite().stream().forEach( p -> dao.updateFkById(p.getCodicePartita(), obj.getCodice()) );
		}
	}

	@Override
	public StadioDTO read(int id) {
		StadioDTO result = null;
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
				StadioDTO obj = new StadioDTO();
				obj.setCodice(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCitta(rs.getString("citta"));
				
				Db2PartitaDAO db2PartitaDAO = new Db2PartitaDAO();
				
				obj.setPartite(db2PartitaDAO.getPartiteByStadioId(obj.getCodice()));
				
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
	public StadioDTO readLazy(int id) {
		StadioDTO result = null;
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
				Db2StadioProxyDTO obj = new Db2StadioProxyDTO();
				obj.setCodice(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCitta(rs.getString("citta"));
				
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
	public Set<StadioDTO> readAll() {
		Set<StadioDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				StadioDTO obj = new StadioDTO();
				obj.setCodice(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCitta(rs.getString("citta"));
				
				Db2PartitaDAO db2PartitaDAO = new Db2PartitaDAO();
				
				obj.setPartite(db2PartitaDAO.getPartiteByStadioId(obj.getCodice()));
				
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
	public Set<StadioDTO> readAllLazy() {
		Set<StadioDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2StadioProxyDTO obj = new Db2StadioProxyDTO();
				obj.setCodice(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCitta(rs.getString("citta"));
				
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
	
	//Serve solo se l'altro è owner con lazy load
	//Ovviamente carica lazy, probabilmente bisognerà
	//eliminare le scritte "Proxy" da questo metodo
	@Override
	public StadioDTO readByIdPartitaLazy (int id) {
		StadioDTO result = null;
		if (id < 0) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_partita);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			if (rs.next()) {
				Db2StadioProxyDTO obj = new Db2StadioProxyDTO();
				obj.setCodice(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCitta(rs.getString("citta"));
				
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
	public boolean update(StadioDTO obj) {
		boolean result = false;
		if (obj == null) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNome());
			prep_stmt.setString(2, obj.getCitta());
			prep_stmt.setInt(4, obj.getCodice());
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
			Db2PartitaDAO partitaDAO = new Db2PartitaDAO();
			if (obj.getPartite() == null || obj.getPartite().size() == 0) {
				partitaDAO.getPartiteByStadioId(obj.getCodice()).stream().forEach(p -> partitaDAO.deleteFkById(p.getCodicePartita()));
			}
			else {
				Set<PartitaDTO> old = partitaDAO.getPartiteByStadioId(obj.getCodice());
				for (PartitaDTO o : old) {
					if (!obj.getPartite().contains(o)) {
						partitaDAO.deleteFkById(o.getCodicePartita());
					}
				}
				for (PartitaDTO n : obj.getPartite()) {
					if (!old.contains(n)) {
						partitaDAO.updateFkById(n.getCodicePartita(), obj.getCodice());
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;
		if (id < 0) {
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
			Db2PartitaDAO partitaDAO = new Db2PartitaDAO();
			new Db2PartitaDAO().getPartiteByStadioId(id).stream().forEach(p -> partitaDAO.deleteFkById(p.getCodicePartita()));
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
		}
		catch (Exception e) {
		}
		finally {
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
		}
		catch (Exception e) {
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}

		return result;
	}

}
