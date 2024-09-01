package it.unibo.paw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.paw.dao.*; // si può fare anche selezione più specifica

public class Db2IdBroker {

	public int newId() throws Exception {
	    int newId = -1;
	    Connection conn = Db2DAOFactory.createConnection();
	    try {
	        String q = "SELECT NEXTVAL FOR sequenza_id FROM SYSIBM.SYSDUMMY1";
	        PreparedStatement prep_stmt = conn.prepareStatement(q);
	        prep_stmt.clearParameters();
	        
	        // Esegui la query SELECT dopo NEXTVAL FOR per ottenere il valore successivo
	        ResultSet result = prep_stmt.executeQuery();
	        
	        if (result.next()) {
	            newId = result.getInt(1);
	        } else {
	            throw new Exception("ID non valido");
	        }
	        
	        prep_stmt.close();
	    } catch (Exception e) {
	        System.err.println("Errore nella generazione dell'ID");
	        e.printStackTrace();
	    } finally {
	        Db2DAOFactory.closeConnection(conn);
	    }
	    return newId;
	}


}
