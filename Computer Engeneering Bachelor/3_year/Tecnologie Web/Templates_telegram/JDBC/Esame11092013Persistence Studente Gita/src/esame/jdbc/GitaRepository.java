package esame.jdbc; 
 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;

import utils.Utils; 
 
public class GitaRepository 
{ 
	private DataSource dataSource; 
 
	public GitaRepository () throws PersistenceException 
	{ 
		dataSource = new DataSource(DataSource.DB2); 
	} 
 
	// == STATEMENT SQL preconfigurati 
 
	//statement metodi CRUD 
 
	//INSERT 
	private static final String insert = "INSERT INTO gita (CodGita,luogo,data,costo) VALUES (?,?,?,?)";
 
 
	//DELETE 
	private static final String delete = "DELETE FROM gita WHERE CodGita=?";
 
 
	//UPDATE 
	private static final String update = "UPDATE gita SET luogo=?,data=?,costo=? WHERE CodGita=?";
 
 
	//READ 
	private static String read_by_id = "SELECT * FROM gita WHERE CodGita=?";
 
 
	// implementazione metodi CRUD 
 
	// create table 
	private static String create = 
"CREATE TABLE gita (CodGita BIGINT NOT NULL PRIMARY KEY,luogo VARCHAR(50) NOT NULL,data DATE NOT NULL,costo DOUBLE NOT NULL)"; 
 
	//drop table 
	private static String drop = "DROP TABLE gita";
 
 
	// init 
	public void dropAndCreateTable() throws PersistenceException { 
		dropTable(); 
		createTable(); 
	} 
 
	public void dropTable() throws PersistenceException { 
		Connection connection = this.dataSource.getConnection(); 
		Statement statement = null; 
		try { 
			statement = connection.createStatement(); 
			System.out.println(statement); 
			statement.executeUpdate(drop); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
			// the table does not exist or is referenced by a FK 
			// that prevents the drop 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void createTable() throws PersistenceException { 
		Connection connection = this.dataSource.getConnection(); 
		Statement statement = null; 
		try { 
			statement = connection.createStatement(); 
			System.out.println(statement); 
			statement.executeUpdate(create); 
		} catch (SQLException e) { 
			throw new PersistenceException(e.getMessage()); 
			// the table might be referenced by a FK 
			// that prevents the drop 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void insert( Gita obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(insert); 
			statement.setLong(1,obj.getCodGita());
			statement.setString(2,obj.getLuogo());
			statement.setDate(3,Utils.toSQLDate(obj.getData()));
			statement.setDouble(4,obj.getCosto());
 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void delete(long id) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public void update( Gita obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(update); 
			statement.setString(1,obj.getLuogo());
			statement.setDate(2,Utils.toSQLDate(obj.getData()));
			statement.setDouble(3,obj.getCosto());
			statement.setLong(4,obj.getCodGita());
 
			System.out.println(statement); 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
	} 
 
	public Gita read(long id) throws PersistenceException 
	{ 
Gita res = null; 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_id); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery(); 
			while(results.next()) 
			{ 
				res = new Gita (); 
				res.setCodGita(results.getLong("CodGita"));
				res.setLuogo(results.getString("luogo"));
				res.setData(Utils.toJavaDate(results.getDate("data")));
				res.setCosto(results.getDouble("costo"));
 
			} 
		} 
		catch (SQLException e) 
		{ 
			throw new PersistenceException(e.getMessage()); 
		} finally { 
			try { 
				if (statement != null) 
					statement.close(); 
				if (connection != null) 
					connection.close(); 
			} catch (SQLException e) { 
				throw new PersistenceException(e.getMessage()); 
			} 
		} 
		return res; 
	} 
 
} 
