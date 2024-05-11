package it.unibo.tw.dao.db2;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDAO;
import it.unibo.tw.dao.RistoranteDTO;

public class Db2RistoranteDAO implements RistoranteDAO {
	
	//Do la possibilità di impostare l'ownership della tabella di mapping. Solo il lato della relazione
	//che detiene l'ownership si deve fare carico delle operazioni sulla tabella di mapping.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
		
	
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	static final String TABLE = "ristoranti";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	
	
	// == STATEMENT SQL ====================================================================

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " nomeristorante VARCHAR(50), indirizzo VARCHAR(50), rating INT, CHECK (rating >=1 AND rating <= 5) )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , nomeristorante, indirizzo, rating "+
			") " +
			"VALUES (?,?,?,?) "
		;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;
	
	static String read_by_indirizzo =
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE indirizzo = ? "
		;
	
	static String read_by_fascia =
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE rating >= ? AND rating <= ? "
		;

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
				"nomeristorante" + " = ?, " +
				"indirizzo" + " = ?, " +
				"rating" + " = ? " +
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
	public void create(RistoranteDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getNomeRistorante());
			prep_stmt.setString(3, obj.getIndirizzo());
			prep_stmt.setInt(4, obj.getRating());
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getPiatti() != null && !obj.getPiatti().isEmpty()) {
			Db2MappingDAO dao = new Db2MappingDAO();
			//Occhio all'ordine di p e obj
			obj.getPiatti().stream().forEach( p -> dao.create(obj.getId(), p.getId()) );
		}
	}

	@Override
	public RistoranteDTO read(int id) {
		RistoranteDTO result = null;
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
				RistoranteDTO obj = new RistoranteDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
				if (isOwner) {
					MappingDAO db2MappingDAO = new Db2MappingDAO();
					obj.setPiatti(db2MappingDAO.getPiattiByRistoranteId(obj.getId()));
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
	
	
	//Ha senso solo se owner
	@Override
	public RistoranteDTO readLazy(int id) {
		RistoranteDTO result = null;
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
				Db2RistoranteProxyDTO obj = new Db2RistoranteProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
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
	public Set<RistoranteDTO> readAll() {
		Set<RistoranteDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				RistoranteDTO obj = new RistoranteDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
				//Da eliminare se non è owner
				if (isOwner)
					obj.setPiatti(new Db2MappingDAO().getPiattiByRistoranteId(obj.getId()));
				
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
	public Set<RistoranteDTO> readAllLazy() {
		Set<RistoranteDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2RistoranteProxyDTO obj = new Db2RistoranteProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
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
	public Set<RistoranteDTO> readLazyByIndirizzo(String indirizzo) {
		Set<RistoranteDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_indirizzo);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, indirizzo);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2RistoranteProxyDTO obj = new Db2RistoranteProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
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
	public Set<RistoranteDTO> readLazyByFascia(int minimo, int massimo) {
		Set<RistoranteDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_fascia);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, minimo);
			prep_stmt.setInt(2, massimo);
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2RistoranteProxyDTO obj = new Db2RistoranteProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				
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
	public boolean update(RistoranteDTO obj) {
		boolean result = false;
		if (obj == null) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getNomeRistorante());
			prep_stmt.setString(2, obj.getIndirizzo());
			prep_stmt.setInt(3, obj.getRating());
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
			if (obj.getPiatti() == null || obj.getPiatti().size() == 0) {
				mappingDAO.deleteByRistorante(obj.getId());
			}
			else {
				Set<PiattoDTO> old = mappingDAO.getPiattiByRistoranteId(obj.getId());
				for (PiattoDTO o : old) {
					if (!obj.getPiatti().contains(o)) {
						//Occhio all'ordine di obj e o
						mappingDAO.delete(obj.getId(), o.getId());
					}
				}
				for (PiattoDTO n : obj.getPiatti()) {
					if (!old.contains(n)) {
						//Occhio all'ordine di obj e o
						mappingDAO.create(obj.getId(), n.getId());
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
			new Db2MappingDAO().deleteByRistorante(id);
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

		if (isOwner) {
			new Db2MappingDAO().createTable();
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

		if (isOwner) {
			new Db2MappingDAO().dropTable();
		}

		return result;
	}

}
