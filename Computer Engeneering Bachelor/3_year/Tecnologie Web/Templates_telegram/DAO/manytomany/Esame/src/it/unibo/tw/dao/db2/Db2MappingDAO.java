package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;

public class Db2MappingDAO implements MappingDAO {
	
	static final String TABLE = "mapping";

	// -------------------------------------------------------------------------------------

	static final String ID_RISTORANTE = "idristorante";
	static final String ID_PIATTO = "idpiatto";
	static final String TABLERISTORANTI = "ristoranti";
	static final String TABLEPIATTI = "piatti";
	
	
	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( idCourse, idStudent ) VALUES ( ?,? );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
			ID_RISTORANTE +", "+ID_PIATTO + " " +
			") " +
			"VALUES (?,?) "
		;
	
	
	static String delete_by_piatto = "DELETE FROM "+TABLE+" WHERE "+ID_PIATTO+" = ? ";
	static String delete_by_ristorante = "DELETE FROM "+TABLE+" WHERE "+ID_RISTORANTE+" = ? ";
	
	static String ristoranti_by_piatto =
			"SELECT * FROM "+TABLE+" MAP, "+TABLERISTORANTI+" T1 " +
					"WHERE MAP."+ID_RISTORANTE+" = T1.id AND "+ID_PIATTO+" = ? ";
	
	static String piatti_by_ristorante =
			"SELECT * FROM "+TABLE+" MAP, "+TABLEPIATTI+" T2 " +
					"WHERE MAP."+ID_PIATTO+" = T2.id AND "+ID_RISTORANTE+" = ? ";
	
	
	
	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_ids = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_RISTORANTE + " = ? " +
			"AND " + ID_PIATTO + " = ? "
		;

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_ristorante = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_RISTORANTE + " = ? "
		;

	// SELECT * FROM table WHERE idcolumns = ?;
	static String read_by_id_piatto = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_PIATTO + " = ? "
		;
	
	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = 
		"SELECT * " +
		"FROM " + TABLE + " ";
	
	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = 
		"DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID_RISTORANTE + " = ? " +
			"AND " + ID_PIATTO + " = ? "
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
			ID_RISTORANTE + " INT NOT NULL, " +
			ID_PIATTO + " INT NOT NULL, " +
				"PRIMARY KEY (" + ID_RISTORANTE +","+ ID_PIATTO + " ), " +
				"FOREIGN KEY ("+ID_RISTORANTE+") REFERENCES "+TABLERISTORANTI+"(id), " +
				"FOREIGN KEY ("+ID_PIATTO+") REFERENCES "+TABLEPIATTI+"(id) " +
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
	public boolean deleteByPiatto(int id) {
		boolean result = false;
		if ( id < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_piatto);
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
	public boolean deleteByRistorante(int id) {
		boolean result = false;
		if ( id < 0 )  {
			System.out.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			PreparedStatement prep_stmt = conn.prepareStatement(delete_by_ristorante);
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
	public Set<PiattoDTO> getPiattiByRistoranteId(int id) {
		Set<PiattoDTO> result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			result = new HashSet<>();
			PreparedStatement prep_stmt = conn.prepareStatement(piatti_by_ristorante);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				PiattoDTO obj = new PiattoDTO();
				obj.setId(rs.getInt("id"));
				obj.setNomePiatto(rs.getString("nomepiatto"));
				obj.setTipo(rs.getString("tipo"));
				result.add(obj);
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
	public Set<RistoranteDTO> getRistorantiByPiattoId(int id) {
		Set<RistoranteDTO> result = null;
		if ( id < 0 )  {
			System.out.println("read(): cannot read an entry with a negative id");
			return result;
		}
		Connection conn = Db2DAOFactory.createConnection();
		try {
			result = new HashSet<>();
			PreparedStatement prep_stmt = conn.prepareStatement(ristoranti_by_piatto);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			ResultSet rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				RistoranteDTO obj = new RistoranteDTO();
				obj.setId(rs.getInt("id"));
				obj.setNomeRistorante(rs.getString("nomeristorante"));
				obj.setIndirizzo(rs.getString("indirizzo"));
				obj.setRating(rs.getInt("rating"));
				result.add(obj);
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
