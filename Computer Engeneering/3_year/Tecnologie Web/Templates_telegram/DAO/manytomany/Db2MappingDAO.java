package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.CinefiloDTO;
import it.unibo.tw.dao.ProiezioneDTO;

public class Db2MappingDAO implements MappingDAO {
	
	static final String TABLE = "mapping";

	// -------------------------------------------------------------------------------------

	static final String ID_PROIEZIONE = "idproiezione";
	static final String ID_CINEFILO = "idcinefilo";
	static final String TABLEPROIEZIONI = "proiezioni";
	static final String TABLECINEFILI = "cinefili";
	
	
	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( idCourse, idStudent ) VALUES ( ?,? );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
			ID_PROIEZIONE +", "+ID_CINEFILO + " " +
			") " +
			"VALUES (?,?) "
		;
	
	
	static String delete_by_cinefilo = "DELETE FROM "+TABLE+" WHERE "+ID_CINEFILO+" = ? ";
	static String delete_by_proiezione = "DELETE FROM "+TABLE+" WHERE "+ID_PROIEZIONE+" = ? ";
	
//	static String proiezioni_by_cinefilo =
//			"SELECT * FROM "+TABLE+" MAP, "+TABLEPROIEZIONI+" T1 " +
//					"WHERE MAP."+ID_PROIEZIONE+" = T1.id AND "+ID_CINEFILO+" = ? ";
	static String proiezioni_by_cinefilo = "SELECT "+ID_PROIEZIONE+" FROM "+TABLE+" WHERE "+ID_CINEFILO+" = ? ";
	
//	static String cinefili_by_proiezione =
//			"SELECT * FROM "+TABLE+" MAP, "+TABLECINEFILI+" T2 " +
//					"WHERE MAP."+ID_CINEFILO+" = T2.id AND "+ID_PROIEZIONE+" = ? ";
	static String cinefili_by_proiezione = "SELECT "+ID_CINEFILO+" FROM "+TABLE+" WHERE "+ID_PROIEZIONE+" = ? ";
	
	
	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_PROIEZIONE + " = ? " +
			"AND " + ID_CINEFILO + " = ? "
		;

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_proiezione = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_PROIEZIONE + " = ? "
		;

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_cinefilo = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_CINEFILO + " = ? "
		;
	
	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = 
		"SELECT * " +
		"FROM " + TABLE + " ";
	
	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = 
		"DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_PROIEZIONE + " = ? " +
			"AND " + ID_CINEFILO + " = ? "
		;

	// SELECT * FROM table;
	static String query = 
		"SELECT * " +
			"FROM " + TABLE + " "
		;

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
		"CREATE " +
			"TABLE " + TABLE +" ( " +
			ID_PROIEZIONE + " INT NOT NULL, " +
			ID_CINEFILO + " INT NOT NULL, " +
				"PRIMARY KEY (" + ID_PROIEZIONE +","+ ID_CINEFILO + " ), " +
				"FOREIGN KEY ("+ID_PROIEZIONE+") REFERENCES "+TABLEPROIEZIONI+"(id), " +
				"FOREIGN KEY ("+ID_CINEFILO+") REFERENCES "+TABLECINEFILI+"(id) " +
			") "
		;

	static String drop = 
		"DROP " +
			"TABLE " + TABLE + " "
		;
	
	
	// === METODI DAO =========================================================================
	
	@Override
	public void create(int id1, int id2) {
		if ( id1 < 0 || id2 < 0 )  {
			System.out.println("create(): cannot create an entry with an invalid id ");
			return;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id1);
			prep_stmt.setInt(2, id2);
			prep_stmt.executeUpdate();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean delete(int id1, int id2) {
		boolean result = false;
		if ( id1 < 0 || id2 < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id1);
			prep_stmt.setInt(2, id2);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with id1 = " + id1 +" and id2 = " + id2 + ": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	@Override
	public boolean deleteByCinefilo(int id) {
		boolean result = false;
		if ( id < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_cinefilo);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with id = " + id +": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	@Override
	public boolean deleteByProiezione(int id) {
		boolean result = false;
		if ( id < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_proiezione);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			prep_stmt.executeUpdate();
			result = true;
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("delete(): failed to delete entry with id = " + id +": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
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
		}
		catch (Exception e) {
			System.out.println("createTable(): failed to create table '"+TABLE+"': "+e.getMessage());
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
			System.out.println("dropTable(): failed to drop table '"+TABLE+"': "+e.getMessage());
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}
	
	@Override
	public Set<CinefiloDTO> getCinefiliByProiezioneId(int id) {
		Set<CinefiloDTO> result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			result = new HashSet<>();
			PreparedStatement prep_stmt = conn.prepareStatement(cinefili_by_proiezione);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			Db2CinefiloDAO cinefiloDAO = new Db2CinefiloDAO();
			while ( rs.next() ) {
				result.add(cinefiloDAO.readLazy(rs.getInt(ID_CINEFILO)));
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("read(): failed to retrieve entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

	@Override
	public Set<ProiezioneDTO> getProiezioniByCinefiloId(int id) {
		Set<ProiezioneDTO> result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			result = new HashSet<>();
			PreparedStatement prep_stmt = conn.prepareStatement(proiezioni_by_cinefilo);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			Db2ProiezioneDAO proiezioneDAO = new Db2ProiezioneDAO();
			while ( rs.next() ) {
				result.add(proiezioneDAO.readLazy(rs.getInt(ID_PROIEZIONE)));
			}
			rs.close();
			prep_stmt.close();
		}
		catch (Exception e) {
			System.out.println("read(): failed to retrieve entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		return result;
	}

}
