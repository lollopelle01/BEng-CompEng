package esame.jdbc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import utils.Utils;

public class JdbcTest {

	private static StudenteRepository sr;
	private static GitaRepository gr;
	private static PartecipazioneRepository pr;

	public static void main(String[] args) throws PersistenceException {
		sr = new StudenteRepository();
		gr = new GitaRepository();
		pr = new PartecipazioneRepository();
		
		pr.dropTable();
		gr.dropTable();
		sr.dropTable();
		
		sr.createTable();
		gr.createTable();
		pr.createTable();
		
		
		persistStudente(1, "Cognome1", "Nome1", "Nick1");
		persistStudente(2, "Cognome2", "Nome2", "Nick2");
		persistStudente(3, "Cognome3", "Nome3", "Nick3");
		persistStudente(4, "Cognome4", "Nome4", "Nick4");
		
		persistGita(1, "Roma", Utils.randomJavaDate(), 100);
		persistGita(2, "Roma", Utils.randomJavaDate(), 50);
		persistGita(3, "Bologna", Utils.randomJavaDate(), 80);
		persistGita(4, "Rimini", Utils.randomJavaDate(), 50);
		
		persistPartecipazione(1, 1);
		persistPartecipazione(1, 2);
		persistPartecipazione(1, 3);
		persistPartecipazione(2, 2);
		persistPartecipazione(3, 4);
		persistPartecipazione(3, 1);
		persistPartecipazione(4, 4);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("persistenza.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		query1(writer);
		query2(writer);
		writer.close();
	}
	
	private static void persistStudente(long id, String cognome, String nome, String nickName) {
		try {
			sr.insert(new Studente(id, cognome, nome, nickName));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void persistGita(long id, String luogo, Date data, double costo) {
		try {
			gr.insert(new Gita(id, luogo, data, costo));
		} catch(PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void persistPartecipazione (long studente, long gita) {
		try {
			pr.insert(new Partecipazione(studente, gita));
		} catch(PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	private static void query1 (PrintWriter writer) throws PersistenceException {
		writer.append("Studenti che hanno partecipato a più di 2 gite in Agosto spendendo meno di € 150:\n");
		Connection connection = new DataSource(DataSource.DB2).getConnection(); 
		Statement statement = null; 
		String query = "select s.codstud from studente s, gita g, partecipazione p where s.codstud = p.studente and g.codgita = p.gita group by s.codstud having count(g.codgita) > 2 and sum(g.costo) < 150";
		try {  
			statement = connection.createStatement(); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery(query);
			while(results.next()) 
			{ 
				writer.append(sr.read(results.getLong("codstud")).getNickName()+"\n");
			} 
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
	
	private static void query2 (PrintWriter writer) throws PersistenceException {
		writer.append("Cancellazione di tutte le gite con destinazione “Roma”, unitamente alle tuple di partecipazione degli studenti ad esse collegate:\n");
		Connection connection = new DataSource(DataSource.DB2).getConnection(); 
		Statement statement = null; 
		String query = "select codgita from gita where luogo = 'Roma'";
		try {  
			statement = connection.createStatement(); 
			System.out.println(statement); 
			ResultSet results = statement.executeQuery(query);
			int num = 0;
			while(results.next()) 
			{ 
				long codgita = results.getLong("codgita");
				pr.deleteByGita(codgita);
				gr.delete(codgita);
				num++;
			} 
			writer.append(num+" gite eliminate\n");
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


//	private static void queries() {
//		String result = "";
//		String innerSql = "select D.MatricolaDocente as matricola, count(*) as count from docente D, corso C, didattica DC where D.MatricolaDocente=DC.Docente and C.CodiceCorso=DC.Corso and C.creditiformativi=6 group by D.MatricolaDocente";
//		String outerSql = "select D.nome, D.cognome, Dcount.count from (" + innerSql
//				+ ") Dcount, docente D where D.matricoladocente=Dcount.matricola";
//
//		try {
//			Statement statement = new DataSource(DataSource.DB2).getConnection().createStatement();
//			ResultSet results = statement.executeQuery(outerSql);
//			String nome = "";
//			String cognome = "";
//			int max = 0;
//			int count = 0;
//			while (results.next()) {
//				count = results.getInt("count");
//				if (count > max) {
//					max = count;
//					nome = results.getString("nome");
//					cognome = results.getString("cognome");
//				}
//			}
//			System.out.println(nome + " " + cognome + " " + count);
//
//		} catch (Exception e) {
//			throw new javax.persistence.PersistenceException(e);
//		}
//
//		try {
//			PrintWriter pw = new PrintWriter(new FileOutputStream("didattica.txt", true));
//			pw.println(result);
//			pw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
