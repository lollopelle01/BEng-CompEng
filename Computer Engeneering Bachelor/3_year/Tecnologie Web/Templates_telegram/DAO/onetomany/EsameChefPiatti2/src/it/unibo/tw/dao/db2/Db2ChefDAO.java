package it.unibo.tw.dao.db2;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.ChefDAO;
import it.unibo.tw.dao.ChefDTO;

public class Db2ChefDAO implements ChefDAO {
	
	//Do la possibilità di impostare l'ownership della relazione. Solo chi è owner si occupa
	//di gestire i riferimenti.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
		
	
	static final String TABLE = "chefs";
	static final String ID = "id";

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nomechef varchar(255) unique not null, numerostelle int not null, nomeristorante varchar(255) not null, check (numerostelle >= 0) )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nomechef, numerostelle, nomeristorante "+
			") " +
			"VALUES (?,?,?,?) "
		;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;
	
	
	
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
				"nomechef" + " = ?, " +
				"numerostelle" + " = ?, " +
				"nomeristorante" + " = ? " +
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
	public void create(ChefDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNomeChef());
			prep_stmt.setInt(3, obj.getNumeroStelle());
			prep_stmt.setString(4, obj.getNomeRistorante());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getPiatti() != null && !obj.getPiatti().isEmpty()) {
			Db2PiattoDAO dao = new Db2PiattoDAO();
			obj.getPiatti().stream().forEach( p -> dao.updateFkById(p.getId(), obj.getId()) );
		}
	}

	@Override
	public ChefDTO read(int id) {
		ChefDTO result = null;
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
				ChefDTO obj = new ChefDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeChef(rs.getString("nomeChef"));
				obj.setNumeroStelle(rs.getInt("numerostelle"));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				
				Db2PiattoDAO db2PiattoDAO = new Db2PiattoDAO();
				
				obj.setPiatti(db2PiattoDAO.getPiattiByChefId(obj.getId()));
				
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
	public Set<ChefDTO> readAll() {
		Set<ChefDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				ChefDTO obj = new ChefDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeChef(rs.getString("nomeChef"));
				obj.setNumeroStelle(rs.getInt("numerostelle"));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				
				Db2PiattoDAO db2PiattoDAO = new Db2PiattoDAO();
				
				obj.setPiatti(db2PiattoDAO.getPiattiByChefId(obj.getId()));
				
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
	public boolean update(ChefDTO obj) {
		boolean result = false;
		if (obj == null) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNomeChef());
			prep_stmt.setInt(2, obj.getNumeroStelle());
			prep_stmt.setString(3, obj.getNomeRistorante());
			
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
			Db2PiattoDAO piattoDAO = new Db2PiattoDAO();
			if (obj.getPiatti() == null || obj.getPiatti().size() == 0) {
				piattoDAO.getPiattiByChefId(obj.getId()).stream().forEach(p -> piattoDAO.deleteFkById(p.getId()));
			}
			else {
				Set<PiattoDTO> old = piattoDAO.getPiattiByChefId(obj.getId());
				for (PiattoDTO o : old) {
					if (!obj.getPiatti().contains(o)) {
						piattoDAO.deleteFkById(o.getId());
					}
				}
				for (PiattoDTO n : obj.getPiatti()) {
					if (!old.contains(n)) {
						piattoDAO.updateFkById(n.getId(), obj.getId());
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
			Db2PiattoDAO piattoDAO = new Db2PiattoDAO();
			new Db2PiattoDAO().getPiattiByChefId(id).stream().forEach(p -> piattoDAO.deleteFkById(p.getId()));
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
	
	static String read_almeno_stelle = "SELECT * FROM "+TABLE+" WHERE numerostelle >= ? ";
	
	@Override
	public Set<ChefDTO> readAlmenoStelle(int numStelle) {
		Set<ChefDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_almeno_stelle);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, numStelle);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				ChefDTO obj = new ChefDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeChef(rs.getString("nomeChef"));
				obj.setNumeroStelle(rs.getInt("numerostelle"));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				
				Db2PiattoDAO db2PiattoDAO = new Db2PiattoDAO();
				
				obj.setPiatti(db2PiattoDAO.getPiattiByChefId(obj.getId()));
				
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

}
