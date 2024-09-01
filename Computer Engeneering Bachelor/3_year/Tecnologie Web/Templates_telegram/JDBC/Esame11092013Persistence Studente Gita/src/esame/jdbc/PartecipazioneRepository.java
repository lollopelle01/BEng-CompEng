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
 
public class PartecipazioneRepository 
{ 
	private DataSource dataSource; 
 
	public PartecipazioneRepository () throws PersistenceException 
	{ 
		dataSource = new DataSource(DataSource.DB2); 
	} 
 
	// == STATEMENT SQL preconfigurati 
 
	//statement metodi CRUD 
 
	//INSERT 
	private static final String insert = "INSERT INTO partecipazione (studente, gita) VALUES (?,?)";
 
	//UPDATE
	//Il metodo di update e' privo di significato visto che la tabella ha solo 2 attributi, entrambi FK, e la chiave primaria composta da essi
 
	//DELETE 
	private static final String delete = "DELETE FROM partecipazione WHERE studente=? AND gita=?";
	private static final String delete_by_gita = "DELETE FROM partecipazione WHERE gita=?";
 
	//READ 
	//Hanno significato solo le read non "puntuali", poichè se volessi fare una read specificando entrambi gli attributi
	//sarei allora già in possesso di tutte le informazioni che compongono l'oggetto.
	private static String read_by_studente = "SELECT * FROM partecipazione WHERE studente=?";
	private static String read_by_gita = "SELECT * FROM partecipazione WHERE gita=?";
 
 
	// implementazione metodi CRUD 
 
	// create table 
	private static String create = 
"CREATE TABLE partecipazione (studente BIGINT NOT NULL REFERENCES studente, gita BIGINT NOT NULL references gita, PRIMARY KEY (studente, gita) )"; 
 
	//drop table 
	private static String drop = "DROP TABLE partecipazione";
 
 
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
 
	public void insert( Partecipazione obj ) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		if (new StudenteRepository().read(obj.getStudente()) == null || new GitaRepository().read(obj.getGita()) == null) {
			try {
				PrintWriter file = new PrintWriter(new FileOutputStream("persistenza.txt"));
				file.append("FK non rispettata: Studente "+obj.getStudente()+", Gita "+obj.getGita()+"\n");
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
			statement.setLong(1,obj.getStudente());
			statement.setLong(2,obj.getGita());
 
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
 
	public void delete(long studente, long gita) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete); 
			statement.setLong(1, studente); 
			statement.setLong(2, gita); 
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
	
	public void deleteByGita(long gita) throws PersistenceException 
	{ 
		Connection connection = null; 
		PreparedStatement statement = null; 
		try 
		{ 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(delete_by_gita); 
			statement.setLong(1, gita); 
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
 
	
	public Set<Partecipazione> readByStudente(long id) throws PersistenceException 
	{ 
		Partecipazione res = null; 
		Set<Partecipazione> resSet = new HashSet<>();
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_studente); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery();
			while(results.next()) 
			{ 
				res = new Partecipazione (); 
				res.setStudente(results.getLong("studente"));
				res.setGita(results.getLong("gita"));
				
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
	
	public Set<Partecipazione> readByGita(long id) throws PersistenceException 
	{ 
		Partecipazione res = null; 
		Set<Partecipazione> resSet = new HashSet<>();
		Connection connection = null; 
		PreparedStatement statement = null; 
		try { 
			connection = this.dataSource.getConnection(); 
			statement = connection.prepareStatement(read_by_gita); 
			statement.setLong(1, id); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery();
			while(results.next()) 
			{ 
				res = new Partecipazione (); 
				res.setStudente(results.getLong("studente"));
				res.setGita(results.getLong("gita"));
				
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
