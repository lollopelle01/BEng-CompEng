package esame.jdbc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {

	private static CorsoRepository cr;
	private static DocenteRepository dr;
	private static DidatticaRepository mappingr;

	public static void main(String[] args) throws PersistenceException {
		cr = new CorsoRepository();
		dr = new DocenteRepository();
		mappingr = new DidatticaRepository();
		
		cr.dropTable();
		dr.dropTable();
		mappingr.dropTable();
		
		cr.createTable();
		dr.createTable();
		mappingr.createTable();
		
		
		persistCorso("01", "Corso1", 5);
		persistCorso("02", "Corso2", 9);
		
		persistDocente("00123", "Cognome1", "Nome1");
		persistDocente("00124", "Cognome2", "Nome2");
		
		persistDidattica("00123", "01");
		persistDidattica("00123", "02");
		persistDidattica("00124", "01");

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("didattica.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		query1(writer);
		writer.close();
	}
	
	private static void persistCorso(String codiceCorso, String nome, int creditiFormativi) {
		try {
			cr.insert(new Corso(codiceCorso, nome, creditiFormativi));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void persistDocente(String matricolaDocente, String cognome, String nome) {
		try {
			dr.insert(new Docente(matricolaDocente, cognome, nome));
		} catch(PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void persistDidattica (String docente, String corso) {
		try {
			mappingr.insert(new Didattica(docente, corso));
		} catch(PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void query1 (PrintWriter writer) throws PersistenceException {
		writer.append("Nome e Cognome dei docenti che hanno tenuto il massimo numero di corsi da 6 crediti formativi:\n");
		Connection connection = new DataSource(DataSource.DB2).getConnection(); 
		Statement statement = null; 
		String query = " select d.*, count(c.codicecorso) as num from docente d, corso c, didattica did where d.matricoladocente = did.docente and c.codicecorso = did.corso and c.creditiformativi = 6 group by d.matricoladocente,d.nome,d.cognome order by count(c.codicecorso) desc";
		try {  
			statement = connection.createStatement(); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery(query);
			if(results.next()) 
			{ 
				int max = results.getInt("num");
				writer.append("Il massimo numero di corsi da 6 crediti e': "+max);
				writer.append(results.getString("nome") + " " + results.getString("cognome"));
				while (results.next()) {
					if (results.getInt("num") < max)
						break;;
					writer.append(results.getString("nome") + " " + results.getString("cognome"));
				}
			} else
				writer.append("Nessun risultato trovato\n");
		} catch (SQLException e) { 
			e.printStackTrace(); 
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
	


}
