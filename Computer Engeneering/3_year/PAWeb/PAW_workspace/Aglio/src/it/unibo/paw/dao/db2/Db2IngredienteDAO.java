package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import it.unibo.paw.dao.*;

/**
 * Implementazione di query jdbc su una versione hqldb della tabella entry
 * 
 * La scrittura dei metodi DAO con JDBC segue sempre e comunque questo schema:
 * 
	// --- 1. Dichiarazione della variabile per il risultato ---
	// --- 2. Controlli preliminari sui dati in ingresso ---
	// --- 3. Apertura della connessione ---
	// --- 4. Tentativo di accesso al db e impostazione del risultato ---
	// --- 5. Gestione di eventuali eccezioni ---
	// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
	// --- 7. Restituzione del risultato (eventualmente di fallimento)
 * 
 * Inoltre, all'interno di uno statement JDBC si segue lo schema generale
 * 
	// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
	// --- b. Pulisci e imposta i parametri (se ve ne sono)
	// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
	// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
	// --- e. Rilascia la struttura dati del risultato
	// --- f. Rilascia la struttura dati dello statement
 * 
 */

public class Db2IngredienteDAO implements IngredienteDAO { // NON AGGIUNGERE UNIMPLEMENTED METHODS, SCRIVI PRIMA QUELLI CHE CI SONO
	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	private static final String TABLE = "Ingrediente";

	// -------------------------------------------------------------------------------------

	private static final String ID = "id";
	private static final String NOMEINGREDIENTE = "nomeIngrediente";
	private static final String QUANTITA = "quantita";

	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	private static final String insert = "INSERT " +
			"INTO " + TABLE + " ( " +
			ID + ", " + NOMEINGREDIENTE + ", " + QUANTITA + " " +
			") " +
			"VALUES (?,?,?) ";

	// SELECT * FROM table WHERE idcolumn = ?;
	private static final String read_by_id = "SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// SELECT * FROM table WHERE stringcolumn = ?;
	private static final String read_all = "SELECT * " +
			"FROM " + TABLE + " ";

	// DELETE FROM table WHERE idcolumn = ?;
	private static final String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	private static final String update = "UPDATE " + TABLE + " " +
			"SET " +
			NOMEINGREDIENTE + " = ?, " +
			QUANTITA + " = ? " +
			"WHERE " + ID + " = ? ";

	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	private static final String create = "CREATE " +
			"TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL PRIMARY KEY, " +
			NOMEINGREDIENTE + " VARCHAR(50) NOT NULL, " +
			QUANTITA + " INT NOT NULL " +
			") ";

	private static final String drop = "DROP " +
			"TABLE " + TABLE + " ";

	// === METODI DAO =========================================================================

	/**
	 * C
	 */
	public void create(IngredienteDTO t) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		//Long result = new Long(-1);
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (t == null) {
			System.err.println("create(): failed to insert a null entry");
			return;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, t.getId());
			prep_stmt.setString(2, t.getNomeIngrediente());
			prep_stmt.setInt(3, t.getQuantita());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d.
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
			//result = new Long(-2);
		} finally {
			Db2DAOFactory.closeConnection(conn);
		}

		// Nel caso della creazione di una nuova tupla eseguo un secondo accesso per sapere che code le e' stato assegnato
		// Chiaramente e' inutile farlo se gia'� il primo accesso ha prodotto errori
		// Devo inoltre preoccuparmi di rimuovere la chiusura dalla connessione dal blocco finally definito precedentemente in quanto riutilizzata
		// --- 6./7. Chiusura della connessione in caso di errori e restituizione prematura di un risultato di fallimento
		/*if ( result == -2 ) {
			Db2DAOFactory.closeConnection(conn);
			return result;
		}
		
		// --- 1. Dichiarazione della variabile per il risultato ---
		// riutilizziamo quella di prima
		
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// gia'� fatti
		
		// --- 3. Apertura della connessione ---
		// ce n'e' una gia'� aperta, se siamo qui
		
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Db2StudentDAO.lastInsert);
			if ( rs.next() ) {
				result = rs.getLong(1);
			}		
			rs.close();
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("create(): failed to retrieve id of last inserted entry: "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;*/
	}

	// -------------------------------------------------------------------------------------

	/**
	 * R
	 */
	public IngredienteDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		IngredienteDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (id < 0) {
			System.err.println("read(): cannot read an entry with a negative id");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if (rs.next()) {
				IngredienteDTO entry = new IngredienteDTO();
				entry.setId(rs.getInt(ID));
				entry.setNomeIngrediente(rs.getString(NOMEINGREDIENTE));
				entry.setQuantita(rs.getInt(QUANTITA));
				
				Db2TemplateMappingDAO csmdao = new Db2TemplateMappingDAO();
				entry.setRicette(csmdao.getRicetteByIngrediente(entry.getId()));
				result = entry;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("read(): failed to retrieve entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	/**
	 * U
	 */
	public boolean update(IngredienteDTO t) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (t == null) {
			System.err.println("update(): failed to update a null entry");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, t.getId());
			prep_stmt.setString(2, t.getNomeIngrediente());
			prep_stmt.setInt(3, t.getQuantita());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("insert(): failed to update entry: " + e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	/**
	 * D
	 */
	public boolean delete(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if (id < 0) {
			System.err.println("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(delete);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("delete(): failed to delete entry with id = " + id + ": " + e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	
	/**
	 * Creazione della table
	 */
	public boolean createTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(create);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("createTable(): failed to create table '" + TABLE + "': " + e.getMessage());
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	/**
	 * Rimozione della table
	 */
	public boolean dropTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(drop);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto a posto.
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			System.err.println("dropTable(): failed to drop table '" + TABLE + "': " + e.getMessage());
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}


	
}