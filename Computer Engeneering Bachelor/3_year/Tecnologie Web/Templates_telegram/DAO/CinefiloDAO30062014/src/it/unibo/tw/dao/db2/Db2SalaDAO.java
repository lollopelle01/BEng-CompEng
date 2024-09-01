package it.unibo.tw.dao.db2;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.*;

public class Db2SalaDAO implements SalaDAO {
	
	//Do la possibilità di impostare l'ownership della relazione. Solo chi è owner si occupa
	//di gestire i riferimenti.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
		
	
	static final String TABLE = "sale";
	static final String ID = "id";

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nome VARCHAR(50) UNIQUE NOT NULL, capienza int, check (capienza >= 0) )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nome, capienza "+
			") " +
			"VALUES (?,?,?) "
		;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;
	
	static String read_by_id_proiezione = "SELECT S.* FROM "+TABLE+" S, proiezioni P WHERE S.id = P.sala AND P.id = ? ";
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
				"capienza" + " = ? " +
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
	public void create(SalaDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNome());
			prep_stmt.setInt(3, obj.getCapienza());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getProiezioni() != null && !obj.getProiezioni().isEmpty()) {
			Db2ProiezioneDAO dao = new Db2ProiezioneDAO();
			obj.getProiezioni().stream().forEach( p -> dao.updateFkById(p.getId(), obj.getId()) );
		}
	}

	@Override
	public SalaDTO read(int id) {
		SalaDTO result = null;
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
				SalaDTO obj = new SalaDTO();
				obj.setId(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCapienza(rs.getInt("capienza"));
				
				Db2ProiezioneDAO db2ProiezioneDAO = new Db2ProiezioneDAO();
				
				obj.setProiezioni(db2ProiezioneDAO.getProiezioniBySalaId(obj.getId()));
				
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
	public Set<SalaDTO> readAll() {
		Set<SalaDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				SalaDTO obj = new SalaDTO();
				obj.setId(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCapienza(rs.getInt("capienza"));
				
				Db2ProiezioneDAO db2ProiezioneDAO = new Db2ProiezioneDAO();
				
				obj.setProiezioni(db2ProiezioneDAO.getProiezioniBySalaId(obj.getId()));
				
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
	public SalaDTO readByIdProiezioneLazy (int id) {
		SalaDTO result = null;
		if (id < 0) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id_proiezione);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			if (rs.next()) {
				SalaDTO obj = new SalaDTO();
				obj.setId(rs.getInt(ID));
				obj.setNome(rs.getString("nome"));
				obj.setCapienza(rs.getInt("capienza"));
				
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
	public boolean update(SalaDTO obj) {
		boolean result = false;
		if (obj == null) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNome());
			prep_stmt.setInt(2, obj.getCapienza());
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
			Db2ProiezioneDAO proiezioneDAO = new Db2ProiezioneDAO();
			if (obj.getProiezioni() == null || obj.getProiezioni().size() == 0) {
				proiezioneDAO.getProiezioniBySalaId(obj.getId()).stream().forEach(p -> proiezioneDAO.deleteFkById(p.getId()));
			}
			else {
				Set<ProiezioneDTO> old = proiezioneDAO.getProiezioniBySalaId(obj.getId());
				for (ProiezioneDTO o : old) {
					if (!obj.getProiezioni().contains(o)) {
						proiezioneDAO.deleteFkById(o.getId());
					}
				}
				for (ProiezioneDTO n : obj.getProiezioni()) {
					if (!old.contains(n)) {
						proiezioneDAO.updateFkById(n.getId(), obj.getId());
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
			Db2ProiezioneDAO proiezioneDAO = new Db2ProiezioneDAO();
			new Db2ProiezioneDAO().getProiezioniBySalaId(id).stream().forEach(p -> proiezioneDAO.deleteFkById(p.getId()));
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
			e.printStackTrace();
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
