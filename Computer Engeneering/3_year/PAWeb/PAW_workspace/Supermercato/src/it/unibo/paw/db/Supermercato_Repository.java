package it.unibo.paw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.paw.model.ProdottoOfferto;
import it.unibo.paw.model.Supermercato;

public class Supermercato_Repository {
	private DataSource dataSource;

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================

	private static final String TABLE = "Supermercato";

	// -------------------------------------------------------------------------------------

	private static final String ID = "id";
	private static final String NOME = "nome";
	private static final String RATING = "ratingGradimento";
	// ... inserire altri attributi tabella ...

	// == STATEMENT SQL ====================================================================

	// create table
	private static String create = "CREATE TABLE " + TABLE + " ( " +
			ID + " INT NOT NULL, " +
			NOME + " VARCHAR(50) NOT NULL UNIQUE, " +
			RATING + " INT NOT NULL, " +
			"PRIMARY KEY (" + ID + ") " +
			") ";

	// drop table
	private static String drop = "DROP TABLE "+TABLE;

	// -------------------------------------------------------------------------------------

	private static final String insert = "INSERT INTO " + TABLE + " ( " +
			ID + ", " + NOME + ", " + RATING + 
			") " +
			"VALUES (?,?,?) ";

	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = "DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? ";

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = "UPDATE " + TABLE + " SET " +
			NOME + " = ?, " +
			RATING + " = ? " +
			"WHERE " + ID + " = ? ";

	// SELECT check postinsert
	static String check_query = "SELECT " + ID
			+ " FROM " + TABLE + " " +
			" WHERE " + NOME + " = ? ";
	
	static String read = "SELECT * " 
			+ " FROM " + TABLE + 
			" WHERE id = ? ";;

	// QUERY SPECIFICHE
	static String readProdotti = "SELECT p.* " 
			+ " FROM " + TABLE + " s, Prodotto p "
			+ " WHERE s.id = p.idSupermercato AND s.nome = ? ";


	// =====================================================================================

	public Supermercato_Repository(int databaseType) {
		dataSource = new DataSource(databaseType);
	}
	
	public void dropTable() throws PersistenceException {
		Connection conn = this.dataSource.getConnection();
		Statement stmt = null;
		try {
			stmt=conn.createStatement();
			stmt.executeUpdate(drop);
		}catch (SQLException e) {
			// the table does not exist
		}finally {
			try {
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
			}catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void createTable() throws PersistenceException {
		Connection connection = this.dataSource.getConnection();

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(create);
		} catch (SQLException e) {
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

	public void persist(Supermercato s) throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtCheck = null;

		try {
			connection = this.dataSource.getConnection();
			pstmtInsert = connection.prepareStatement(insert);
			pstmtInsert.setInt(1, s.getCodiceSuper());
			pstmtInsert.setString(2, s.getNome());
			pstmtInsert.setInt(3, s.getRatingGradimento());
			pstmtInsert.executeUpdate();

			// Verifichiamo che dato inserito sia presente
			pstmtCheck = connection.prepareStatement(check_query);
			pstmtCheck.setString(1, s.getNome());
			ResultSet rs = pstmtCheck.executeQuery();
			rs.next();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (pstmtInsert != null)
					pstmtInsert.close();
				if (pstmtCheck != null)
					pstmtCheck.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void update(Supermercato s) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(update);

			statement.setString(1, s.getNome());
			statement.setInt(2, s.getRatingGradimento());
			statement.setInt(3, s.getCodiceSuper());
			statement.executeQuery();
			// ... completare parametri update ...

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
	
	public Supermercato read(int id) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		Supermercato s = null;

		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(read);

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			// ... completare parametri update ...
			s = new Supermercato();
			s.setCodiceSuper(id);
			s.setNome(rs.getString("nome"));
			s.setRatingGradimento(rs.getInt("ratingGradimento"));
			s.setProdottiOfferti(readProdotti(s.getNome()));

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return s;
	}

	// OPERAZIONI SPECIFICHE 
	public Set<ProdottoOfferto> readProdotti(String nome) throws PersistenceException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Set<ProdottoOfferto> result =  new HashSet<ProdottoOfferto>();;
		ProdottoOfferto entry = null;
		try {
			connection = this.dataSource.getConnection();
			pstmt = connection.prepareStatement(readProdotti);
			pstmt.setString(1, nome);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				entry = new ProdottoOfferto();
				entry.setCodiceProdotto(rs.getInt("id"));
				entry.setDescrizione(rs.getString("descrizione"));
				entry.setMarca(rs.getString("marca"));
				entry.setPrezzo(rs.getFloat("prezzo"));
				// ... inserire altri attributi dell'entry ...
				result.add(entry);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return result;
	}

}
