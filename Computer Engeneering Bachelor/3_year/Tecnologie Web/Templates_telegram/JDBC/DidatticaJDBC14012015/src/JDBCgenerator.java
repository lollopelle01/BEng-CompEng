import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class JDBCgenerator {
	static String ID_NAME = "id";
	static String gestioneClasse;
	static String tableName;
	static LinkedHashSet<String> attr;
	static String objName;
	static String lastOne;
	static int size = 0;

	public static void main(String[] args) throws IOException {
		attr = new LinkedHashSet<String>();

		// CAMBIARE DA QUI...
		objName = "Docente"; // nome del bean (iniziale maiuscola!!)
		tableName = "docente"; // nome della tabella

		ID_NAME = "MatricolaDocente"; // mettere maiuscola

		attr.add("String cognome");
		attr.add("String nome");
		/// ...A QUI

		size = attr.size();
		gestioneClasse = objName + "Repository";
		FileWriter fstream = new FileWriter(objName + "Repository.java");
		BufferedWriter out = new BufferedWriter(fstream);
		BufferedReader br = new BufferedReader(new FileReader("model_jdbc.txt"));

		String line = "";

		while ((line = br.readLine()) != null) {
			String modifiedLine = modifyLine(line);
			out.write(modifiedLine + "\n");
		}

		br.close();
		out.close();

		fstream = new FileWriter(objName + ".java");
		out = new BufferedWriter(fstream);

		out.write("package esame.jdbc;\n\n");
		out.write("import java.io.Serializable;\n\n");
		out.write("public class " + objName + " implements Serializable{\n");
		out.write("\tprivate static final long serialVersionUID = 1L;\n");
		out.write("\n\tprivate long " + ID_NAME + ";\n");
		for (String temp : attr)
			out.write("\tprivate " + temp + ";\n");
		out.write("}");
		out.close();
	}

	private static String modifyLine(String line) {
		String[] words = line.split(" ");
		String ret = "";
		for (int i = 0; i < words.length; i++) {
			ret += checkWord(words[i]) + " ";
		}
		return ret;
	}

	private static String checkWord(String word) {
		String ret = word;

		if (word.trim().equals("#GESTIONE_NOMECLASSE#"))
			ret = gestioneClasse;
		if (word.trim().equals("#NOMECLASSE#"))
			ret = objName;
		if (word.trim().equals("#NOMETABELLA#"))
			ret = tableName;
		if (word.trim().equals("#INSERT#"))
			ret = generateInsert(word);
		if (word.trim().equals("#DELETE#"))
			ret = generateDelete(word);
		if (word.trim().equals("#UPDATE#"))
			ret = generateUpdate(word);
		if (word.trim().equals("#READ#"))
			ret = generateReadById(word);
		if (word.trim().equals("#CREATE_TABLE#"))
			ret = createTable(word);
		if (word.trim().equals("#DROPTABLE#"))
			ret = generateDrop(word);
		if (word.trim().equals("#INSERT_STATEMENTS#"))
			ret = generateInsertCRUD(word);
		if (word.trim().equals("#UPDATE_STATEMENTS#"))
			ret = generateUpdateCRUD(word);
		if (word.trim().equals("#READ_STATEMENTS#"))
			ret = generateReadCRUD(word);

		return ret;
	}

	private static String generateInsert(String word) {
		String ret = "";
		ret += "\tprivate static final String insert = \"INSERT INTO ";
		ret += tableName + " (";
		int index = 0;
		ret += ID_NAME + ",";
		for (String temp : attr) {
			if (index < size - 1) {
				ret += temp.split(" ")[1] + ",";
				index++;
			} else
				ret += temp.split(" ")[1] + "";
		}
		ret += ") VALUES (?,";
		index = 0;
		for (@SuppressWarnings("unused")
		String temp : attr) {
			if (index < size - 1) {
				ret += "?,";
				index++;
			} else
				ret += "?)\";\n";
		}
		System.out.println(ret);
		return ret;
	}

	private static String generateDelete(String word) {
		String ret = "";
		ret += "\tprivate static final String delete = \"DELETE FROM " + tableName + " WHERE " + ID_NAME + "=?\";\n";

		System.out.println(ret);
		return ret;
	}

	private static String generateUpdate(String word) {
		String ret = "";
		ret += "\tprivate static final String update = \"UPDATE ";
		ret += tableName + " SET ";
		int index = 0;
		for (String temp : attr) {
			if (index < size - 1) {
				ret += temp.split(" ")[1] + "=?,";
				index++;
			} else
				ret += temp.split(" ")[1] + "=?";
		}
		ret += " WHERE " + ID_NAME + "=?\";\n";

		System.out.println(ret);
		return ret;
	}

	private static String generateReadById(String word) {
		String ret = "";
		ret += "\tprivate static String read_by_id = \"SELECT * FROM " + tableName + " WHERE " + ID_NAME + "=?\";\n";

		System.out.println(ret);
		return ret;
	}

	private static String createTable(String word) {
		String ret = "";
		ret += "\"CREATE TABLE " + tableName + " (" + ID_NAME + " BIGINT NOT NULL PRIMARY KEY,";
		int index = 0;
		for (String temp : attr) {
			String type = temp.split(" ")[0];
			String name = temp.split(" ")[1];
			String refClass = null;
			ret += name + " ";
			if (temp.split(" ").length == 3) {
				refClass = temp.split(" ")[2];
				if (type.toUpperCase().equals("STRING"))
					ret += "VARCHAR(50)";
				else if (type.toUpperCase().equals("LONG"))
					ret += "BIGINT";
				ret += " NOT NULL REFERENCES " + refClass;
			} else {
				if (type.toUpperCase().equals("STRING"))
					ret += "VARCHAR(50) NOT NULL";
				else if (type.toUpperCase().equals("LONG"))
					ret += "BIGINT NOT NULL";
				else
					ret += type.toUpperCase() + " NOT NULL";
			}
			if (index == size - 1)
				ret += ")\";";
			else
				ret += ",";
			index++;
		}
		System.out.println(ret);
		return ret;
	}

	private static String generateDrop(String word) {
		String ret = "";
		ret += "\tprivate static String drop = \"DROP TABLE " + tableName + "\";\n";
		System.out.println(ret);
		return ret;
	}

	private static String generateInsertCRUD(String word) {
		String ret = "";
		ret += "\t\t\tstatement.setLong(1,obj.get" + ID_NAME + "());\n";
		int index = 2;
		for (String temp : attr) {
			ret += "\t\t\tstatement.set" + temp.split(" ")[0].substring(0, 1).toUpperCase()
					+ temp.split(" ")[0].substring(1);
			ret += "(" + index + ",obj.get" + temp.split(" ")[1].substring(0, 1).toUpperCase()
					+ temp.split(" ")[1].substring(1) + "());\n";
			index++;
		}
		System.out.println(ret);
		return ret;
	}

	private static String generateUpdateCRUD(String word) {
		String ret = "";
		int index = 1;
		for (String temp : attr) {
			ret += "\t\t\tstatement.set" + temp.split(" ")[0].substring(0, 1).toUpperCase()
					+ temp.split(" ")[0].substring(1);
			ret += "(" + index + ",obj.get" + temp.split(" ")[1].substring(0, 1).toUpperCase()
					+ temp.split(" ")[1].substring(1) + "());\n";
			index++;
		}
		ret += "\t\t\tstatement.setLong(" + index + ",obj.get" + ID_NAME + "());\n";
		System.out.println(ret);
		return ret;
	}

	private static String generateReadCRUD(String word) {
		String ret = "";
		ret += "\t\t\t\tres.set" + ID_NAME + "(results.getLong(\"" + ID_NAME + "\"));\n";
		for (String temp : attr) {
			ret += "\t\t\t\tres.set" + temp.split(" ")[1].substring(0, 1).toUpperCase()
					+ temp.split(" ")[1].substring(1) + "(results.get";
			ret += temp.split(" ")[0].substring(0, 1).toUpperCase() + temp.split(" ")[0].substring(1) + "(\""
					+ temp.split(" ")[1] + "\"));\n";
		}
		System.out.println(ret);
		return ret;
	}
}
