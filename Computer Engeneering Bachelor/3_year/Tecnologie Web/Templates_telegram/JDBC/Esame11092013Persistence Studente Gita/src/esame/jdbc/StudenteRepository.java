package esame.jdbc; 
 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
 
public class StudenteRepository 
{ 
	private DataSource dataSource; 
 
	public StudenteRepository () throws PersistenceException 
	{ 
		dataSource = new DataSource(DataSource.DB2); 
	} 
 
	// == STATEMENT SQL preconfigurati 
 
	//statement metodi CRUD 
 
	//INSERT 
	private static final String insert = "INSERT INTO studente (CodStud,cognome,nome,nickName) VALUES (?,?,?,?)";
 
 
	//DELETE 
	private static final String delete = "DELETE FROM studente WHERE CodStud=?";
 
 
	//UPDATE 
	private static final String update = "UPDATE studente SET cognome=?,nome=?,nickName=? WHERE CodStud=?";
 
 
	//READ 
	private static String read_by_id = "SELECT * FROM studente WHERE CodStud=?";
 
 
	// implementazione metodi CRUD 
 
	// create table 
	private static String create = 
"CREATE TABLE studente (CodStud BIGINT NOT NULL PRIMARY KEY,cognome VARCHAR(50) NOT NULL,nome VARCHAR(50) NOT NULL,nickName VARCHAR(50) NOT NULL)"; 
 
	//drop table 
	private static String drop = "DROP TABLE studente";
 
 
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
 
	public void insert( Studente obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(insert); 
			statement.setLong(1,obj.getCodStud());
			statement.setString(2,obj.getCognome());
			statement.setString(3,obj.getNome());
			statement.setString(4,obj.getNickName());
 
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
 
	public void update( Studente obj) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(update); 
			statement.setString(1,obj.getCognome());
			statement.setString(2,obj.getNome());
			statement.setString(3,obj.getNickName());
			statement.setLong(4,obj.getCodStud());
 
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
 
	public Studente read(long id) throws PersistenceException 
	{ 
Studente res = null; 
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
				res = new Studente (); 
				res.setCodStud(results.getLong("CodStud"));
				res.setCognome(results.getString("cognome"));
				res.setNome(results.getString("nome"));
				res.setNickName(results.getString("nickName"));
 
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
