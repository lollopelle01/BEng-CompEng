import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class HIBERNATEgenerator {
	static String gestioneClasse;
	static String tableName;
	static LinkedHashSet<String> attr;
	static String objName;
	static String lastOne;
	static int size = 0;

	public static void main(String[] args) throws IOException {
		attr = new LinkedHashSet<String>();

		// CAMBIARE DA QUI...
		objName = "Party"; // nome del bean (iniziale maiuscola!!)
		tableName = "parties"; // nome della tabella

		// non inserire "long id"!! (generato automaticamente)
		attr.add("String titolo"); // coppie "tipo nome" separate da uno spazio
		attr.add("String luogo"); // NON inserire qui eventuali set (sono da scrivere a mano dopo la generazione!!)
		attr.add("Date data");
		/// ...A QUI

		size = attr.size();

		// GENERAZIONE Nomeoggetto.hbm.xml
		FileWriter fstream = new FileWriter(objName + ".hbm.xml");
		BufferedWriter out = new BufferedWriter(fstream);

		out.write("<?xml version=\"1.0\"?>\n\n");
		out.write("<!DOCTYPE hibernate-mapping PUBLIC\n");
		out.write("\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n");
		out.write("\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n\n");
		out.write("<hibernate-mapping>\n");
		out.write("\t<class name=\"esame.hibernate.beans." + objName + "\" table=\"" + tableName + "\">\n");
		out.write("\t\t<id name=\"id\" column=\"id\">\n");
		out.write("\t\t\t<generator class=\"increment\"></generator>\n");
		out.write("\t\t</id>\n");
		for (String temp : attr)
			out.write("\t\t<property name=\"" + temp.split(" ")[1] + "\" column=\"" + temp.split(" ")[1] + "\"/>\n");
		out.write("\t</class>\n");
		out.write("</hibernate-mapping>");
		out.close();
		///////////////////////////

		// GENERAZIONE Nomeoggetto.java
		fstream = new FileWriter(objName + ".java");
		out = new BufferedWriter(fstream);

		out.write("package esame.hibernate.beans;\n\n");
		out.write("import java.io.Serializable;\n\n");
		out.write("public class " + objName + " implements Serializable{\n");
		out.write("\tprivate static final long serialVersionUID = 1L;\n");
		out.write("\n\tprivate long id;\n");
		for (String temp : attr)
			out.write("\tprivate " + temp + ";\n");
		out.write("}");
		out.close();
		///////////////////////////
	}

}
