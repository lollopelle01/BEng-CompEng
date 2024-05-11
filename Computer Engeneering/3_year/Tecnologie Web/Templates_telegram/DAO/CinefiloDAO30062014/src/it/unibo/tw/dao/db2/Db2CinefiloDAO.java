package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.CinefiloDAO;
import it.unibo.tw.dao.CinefiloDTO;
import it.unibo.tw.dao.ProiezioneDTO;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Db2CinefiloDAO implements CinefiloDAO {
	
	//Do la possibilità di impostare l'ownership della tabella di mapping. Solo il lato della relazione
	//che detiene l'ownership si deve fare carico delle operazioni sulla tabella di mapping.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
	

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	static final String TABLE = "cinefili";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	
	// == STATEMENT SQL ====================================================================

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nomecinefilo VARCHAR(50) UNIQUE NOT NULL, sesso VARCHAR(5), eta int, CHECK (sesso in ('M', 'F', 'altro')) )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nomecinefilo, sesso, eta "+
			") " +
			"VALUES (?,?,?,?) "
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
				"nomecinefilo" + " = ?, " +
				"sesso" + " = ?, " +
				"eta" + " = ? " +
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
	public void create(CinefiloDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNomeCinefilo());
			prep_stmt.setString(3, obj.getSesso());
			prep_stmt.setInt(4, obj.getEta());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getProiezioni() != null && !obj.getProiezioni().isEmpty()) {
			Db2MappingDAO dao = new Db2MappingDAO();
			//Occhio all'ordine di p e obj
			obj.getProiezioni().stream().forEach( p -> dao.create(p.getId(), obj.getId()) );
		}
	}

	// -------------------------------------------------------------------------------------
	
	
	@Override
	public CinefiloDTO readLazy(int id) {
		CinefiloDTO result = null;
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
				Db2CinefiloProxyDTO obj = new Db2CinefiloProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeCinefilo(rs.getString("nomecinefilo"));
				obj.setSesso(rs.getString("sesso"));
				obj.setEta(rs.getInt("eta"));
				
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
	public Set<CinefiloDTO> readAllLazy() {
		Set<CinefiloDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2CinefiloProxyDTO obj = new Db2CinefiloProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeCinefilo(rs.getString("nomecinefilo"));
				obj.setSesso(rs.getString("sesso"));
				obj.setEta(rs.getInt("eta"));
				
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
	public boolean update(CinefiloDTO obj) {
		boolean result = false;
		if ( obj == null )  {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNomeCinefilo());
			prep_stmt.setString(2, obj.getSesso());
			prep_stmt.setInt(3, obj.getEta());
			prep_stmt.setInt(4, obj.getId());
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
			if (obj.getProiezioni() == null || obj.getProiezioni().size() == 0) {
				mappingDAO.deleteByCinefilo(obj.getId());
			}
			else {
				Set<ProiezioneDTO> old = mappingDAO.getProiezioniByCinefiloId(obj.getId());
				for (ProiezioneDTO o : old) {
					if (!obj.getProiezioni().contains(o)) {
						//Occhio all'ordine di obj e o
						mappingDAO.delete(o.getId(), obj.getId());
					}
				}
				for (ProiezioneDTO n : obj.getProiezioni()) {
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
			new Db2MappingDAO().deleteByCinefilo(id);
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