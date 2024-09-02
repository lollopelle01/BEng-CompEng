package utils;

import java.util.Calendar;
import java.util.Random;

public class Utils {

	//Crea una DATE SQL da una Date Java
//	new java.sql.Date(t.getBirthDate().getTime())
	public static java.sql.Date toSQLDate (java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	//Crea una Date Java da una DATE SQL
//	long secs = rs.getDate(BIRTHDATE).getTime();
//	java.util.Date birthDate = new java.util.Date(secs);
	public static java.util.Date toJavaDate (java.sql.Date date) {
		return new java.util.Date (date.getTime());
	}
	
	//Crea Date Java arbitraria
//	Calendar c = Calendar.getInstance();
//	c.set(1984, 1, 24);
//	c.getTime();
	public static java.util.Date newJavaDate (int giorno, int mese, int anno) {
		Calendar c = Calendar.getInstance();
		c.set(anno, mese, giorno);
		return c.getTime();
	}
	public static java.util.Date randomJavaDate () {
		Random r = new Random();
		return newJavaDate(r.nextInt(29), r.nextInt(12), r.nextInt(20)+2000);
	}
}
