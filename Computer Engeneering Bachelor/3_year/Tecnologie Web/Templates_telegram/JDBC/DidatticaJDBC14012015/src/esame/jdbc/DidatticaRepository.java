package esame.jdbc; 
 
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set; 
 
public class DidatticaRepository 
{ 
	private DataSource dataSource; 
 
	public DidatticaRepository () throws PersistenceException 
	{ 
		dataSource = new DataSource(DataSource.DB2); 
	} 
 
	// == STATEMENT SQL preconfigurati 
 
	//statement metodi CRUD 
 
	//INSERT 
	private static final String insert = "INSERT INTO didattica (Corso, docente) VALUES (?,?)";
 
	//UPDATE
	//Il metodo di update e' privo di significato visto che la tabella ha solo 2 attributi, entrambi FK, e la chiave primaria composta da essi
 
	//DELETE 
	private static final String delete = "DELETE FROM didattica WHERE Corso=? AND docente=?";
	private static final String delete_by_docente = "DELETE FROM didattica WHERE docente=?";
 
	//READ 
	//Hanno significato solo le read non "puntuali", poichè se volessi fare una read specificando entrambi gli attributi
	//sarei allora già in possesso di tutte le informazioni che compongono l'oggetto.
	private static String read_by_Corso = "SELECT * FROM didattica WHERE Corso=?";
	private static String read_by_docente = "SELECT * FROM didattica WHERE docente=?";
 
 
	// implementazione metodi CRUD 
 
	// create table 
	private static String create = 
"CREATE TABLE didattica (Corso VARCHAR(50) NOT NULL REFERENCES Corso, docente VARCHAR(50) NOT NULL references docente, PRIMARY KEY (Corso, docente) )"; 
 
	//drop table 
	private static String drop = "DROP TABLE didattica";
 
 
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
 
	public void insert( Didattica obj ) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		if (new CorsoRepository().read(obj.getCorso()) == null || new DocenteRepository().read(obj.getDocente()) == null) {
			try {
				PrintWriter file = new PrintWriter(new FileOutputStream("didattica.txt"));
				file.append("FK non rispettata: Corso "+obj.getCorso()+", docente "+obj.getDocente()+"\n");
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new PersistenceException ("FK non rispettata");
		}
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(insert); 
			statement.setString(1,obj.getCorso());
			statement.setString(2,obj.getDocente());
 
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
 
	public void delete(String Corso, String docente) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete); 
			statement.setString(1, Corso); 
			statement.setString(2, docente); 
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
	
	public void deleteBydocente(String docente) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete_by_docente); 
			statement.setString(1, docente); 
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
 
	
	public Set<Didattica> readByCorso(String id) throws PersistenceException 
	{ 
		Didattica res = null; 
		Set<Didattica> resSet = new HashSet<>();
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_Corso); 
			statement.setString(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery();
			while(results.next()) 
			{ 
				res = new Didattica (); 
				res.setCorso(results.getString("Corso"));
				res.setDocente(results.getString("docente"));
				
				resSet.add(res);
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
		return resSet; 
	} 
	
	public Set<Didattica> readBydocente(long id) throws PersistenceException 
	{ 
		Didattica res = null; 
		Set<Didattica> resSet = new HashSet<>();
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_docente); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery();
			while(results.next()) 
			{ 
				res = new Didattica (); 
				res.setCorso(results.getString("Corso"));
				res.setDocente(results.getString("docente"));
				
				resSet.add(res);
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
		return resSet; 
	} 
 
} 
