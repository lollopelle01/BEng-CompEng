package it.unibo.tw.dao.db2;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.CinefiloDTO;
import it.unibo.tw.dao.ProiezioneDAO;
import it.unibo.tw.dao.ProiezioneDTO;
import utils.*;

public class Db2ProiezioneDAO implements ProiezioneDAO {
	
	//Do la possibilità di impostare l'ownership della tabella di mapping. Solo il lato della relazione
	//che detiene l'ownership si deve fare carico delle operazioni sulla tabella di mapping.
	private final boolean isOwner = true;
	@Override
	public boolean isOwner() {
		return isOwner;
	}
		
	
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	static final String TABLE = "proiezioni";

	// -------------------------------------------------------------------------------------

	static final String ID = "id";
	
	
	// == STATEMENT SQL ====================================================================
	
	static String update_fk_by_id = "UPDATE "+TABLE+" SET sala = ? WHERE "+ID+" = ? ";
	static String delete_fk_by_id = "UPDATE "+TABLE+" SET sala = null WHERE "+ID+" = ? ";
	static String get_proiezioni_by_sala_id = "SELECT * FROM "+TABLE+" WHERE sala = ? ";

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
			"CREATE TABLE "+TABLE+" ( "+ID+" INT NOT NULL PRIMARY KEY,"
					+ " titolo VARCHAR(50) NOT NULL UNIQUE, regista VARCHAR(50), genere VARCHAR(50), data DATE, sala INT REFERENCES sale )";
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
	
	// INSERT INTO table ( name,description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				ID+" , titolo, regista, genere, data "+
			") " +
			"VALUES (?,?,?,?,?) "
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
//	
//	static String read_by_fascia =
//		"SELECT * " +
//			"FROM " + TABLE + " " +
//			"WHERE rating >= ? AND rating <= ? "
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
				"titolo" + " = ?, " +
				"regista" + " = ?, " +
				"genere" + " = ?, " +
				"data" + " = ?, " +
				"sala" + " = ? " +
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
	public void create(ProiezioneDTO obj) {
		if (obj == null) {
			throw new InvalidParameterException("null object");
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			obj.setId(new Db2IdBroker().newId());
			prep_stmt.setInt(1, obj.getId());
			prep_stmt.setString(2, obj.getTitolo());
			prep_stmt.setString(3, obj.getRegista());
			prep_stmt.setString(4, obj.getGenere());
			prep_stmt.setDate(5, Utils.toSQLDate(obj.getData()));
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isOwner && obj.getCinefili() != null && !obj.getCinefili().isEmpty()) {
			Db2MappingDAO dao = new Db2MappingDAO();
			//Occhio all'ordine di p e obj
			obj.getCinefili().stream().forEach( p -> dao.create(obj.getId(), p.getId()) );
		}
	}

	@Override
	public ProiezioneDTO readLazy(int id) {
		ProiezioneDTO result = null;
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
				Db2ProiezioneProxyDTO obj = new Db2ProiezioneProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setTitolo(rs.getString("titolo"));
				obj.setRegista(rs.getString("regista"));
				obj.setGenere(rs.getString("genere"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				MappingDAO db2MappingDAO = new Db2MappingDAO();
				obj.setCinefili(db2MappingDAO.getCinefiliByProiezioneId(obj.getId()));
				
				
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
	public Set<ProiezioneDTO> readAllLazy() {
		Set<ProiezioneDTO> result = new HashSet<>();
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			prep_stmt.clearParameters();
			ResultSet rs = prep_stmt.executeQuery();
			while (rs.next()) {
				Db2ProiezioneProxyDTO obj = new Db2ProiezioneProxyDTO();
				obj.setId(rs.getInt(ID));
				obj.setTitolo(rs.getString("titolo"));
				obj.setRegista(rs.getString("regista"));
				obj.setGenere(rs.getString("genere"));
				obj.setData(Utils.toJavaDate(rs.getDate("data")));
				MappingDAO db2MappingDAO = new Db2MappingDAO();
				obj.setCinefili(db2MappingDAO.getCinefiliByProiezioneId(obj.getId()));
								
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
	
//	//Ha senso solo se owner
//	@Override
//	public Set<ProiezioneDTO> readLazyByIndirizzo(String indirizzo) {
//		Set<ProiezioneDTO> result = new HashSet<>();
//		Connection conn = Db2DAOFactory.createConnection();
//		try {
//			PreparedStatement prep_stmt = conn.prepareStatement(read_by_indirizzo);
//			prep_stmt.clearParameters();
//			prep_stmt.setString(1, indirizzo);
//			ResultSet rs = prep_stmt.executeQuery();
//			while (rs.next()) {
//				Db2ProiezioneProxyDTO obj = new Db2ProiezioneProxyDTO();
//				obj.setId(rs.getInt(ID));
//				obj.setNomeProiezione(rs.getString("nomeproiezione"));
//				obj.setIndirizzo(rs.getString("indirizzo"));
//				obj.setRating(rs.getInt("rating"));
//				
//				result.add(obj);
//			}
//			rs.close();
//			prep_stmt.close();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			Db2DAOFactory.closeConnection(conn);
//		}
//		return result;
//	}
	
	
//	//Ha senso solo se owner
//	@Override
//	public Set<ProiezioneDTO> readLazyByFascia(int minimo, int massimo) {
//		Set<ProiezioneDTO> result = new HashSet<>();
//		Connection conn = Db2DAOFactory.createConnection();
//		try {
//			PreparedStatement prep_stmt = conn.prepareStatement(read_by_fascia);
//			prep_stmt.clearParameters();
//			prep_stmt.setInt(1, minimo);
//			prep_stmt.setInt(2, massimo);
//			ResultSet rs = prep_stmt.executeQuery();
//			while (rs.next()) {
//				Db2ProiezioneProxyDTO obj = new Db2ProiezioneProxyDTO();
//				obj.setId(rs.getInt(ID));
//				obj.setNomeProiezione(rs.getString("nomeproiezione"));
//				obj.setIndirizzo(rs.getString("indirizzo"));
//				obj.setRating(rs.getInt("rating"));
//				
//				result.add(obj);
//			}
//			rs.close();
//			prep_stmt.close();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			Db2DAOFactory.closeConnection(conn);
//		}
//		return result;
//	}

	@Override
	public boolean update(ProiezioneDTO obj) {
		boolean result = false;
		if (obj == null) {
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			prep_stmt.clearParameters();
			prep_stmt.setString(1, obj.getTitolo());
			prep_stmt.setString(2, obj.getRegista());
			prep_stmt.setString(3, obj.getGenere());
			prep_stmt.setDate(4, Utils.toSQLDate(obj.getData()));
			prep_stmt.setInt(5, obj.getSala().getId());
			prep_stmt.setInt(6, obj.getId());
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
			if (obj.getCinefili() == null || obj.getCinefili().size() == 0) {
				mappingDAO.deleteByProiezione(obj.getId());
			}
			else {
				Set<CinefiloDTO> old = mappingDAO.getCinefiliByProiezioneId(obj.getId());
				for (CinefiloDTO o : old) {
					if (!obj.getCinefili().contains(o)) {
						//Occhio all'ordine di obj e o
						mappingDAO.delete(obj.getId(), o.getId());
					}
				}
				for (CinefiloDTO n : obj.getCinefili()) {
					if (!old.contains(n)) {
						//Occhio all'ordine di obj e o
						mappingDAO.create(obj.getId(), n.getId());
					}
				}
			}
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
		
		
		@Override
		public Set<ProiezioneDTO> getProiezioniBySalaId(int id) {
			Set<ProiezioneDTO> result = new HashSet<>();
			Connection conn = Db2DAOFactory.createConnection();
			try {
				PreparedStatement prep_stmt = conn.prepareStatement(get_proiezioni_by_sala_id);
				prep_stmt.clearParameters();
				prep_stmt.setInt(1, id);
				ResultSet rs = prep_stmt.executeQuery();
				while (rs.next()) {
					Db2ProiezioneProxyDTO obj = new Db2ProiezioneProxyDTO();
					obj.setId(rs.getInt(ID));
					obj.setTitolo(rs.getString("titolo"));
					obj.setRegista(rs.getString("regista"));
					obj.setGenere(rs.getString("genere"));
					obj.setData(Utils.toJavaDate(rs.getDate("data")));
					MappingDAO db2MappingDAO = new Db2MappingDAO();
					obj.setCinefili(db2MappingDAO.getCinefiliByProiezioneId(obj.getId()));

					
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
			new Db2MappingDAO().deleteByProiezione(id);
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
