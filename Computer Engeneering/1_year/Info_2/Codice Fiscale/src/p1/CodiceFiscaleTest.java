package p1;

public class CodiceFiscaleTest {
	public static void main(String[] args) {
		CodiceFiscale c=new CodiceFiscale();
	System.out.println(c.calcolaCodiceFiscale("Pellegrino", "LORENZO", 9, 2, 2001, 'M', "E289"));
	System.out.println(c.calcolaCodiceFiscale("Rossi", "Mario", 12, 6, 1976, 'M', "A944").equals("RSSMRA76H12A944I"));
	System.out.println(c.calcolaCodiceFiscale("Verdi", "Lucia", 25, 12, 1998, 'F', "H223"));
	System.out.println(c.verificaCodiceFiscale("Mario", "Rossi", 12, 6, 1976, 'M', "A944", "RSSMRA76H12A94QF"));
	
	System.out.println("Katia "+c.calcolaCodiceFiscale("Mascagni", "Katia", 29, 3, 1970, 'F', "A944"));
	System.out.println("Alessandro "+c.calcolaCodiceFiscale("Pellegrino", "Alessandro", 25, 9, 1968, 'M', "A944"));
	System.out.println("Antonella "+c.calcolaCodiceFiscale("DiBenedetto", "Maria", 11, 11, 1949, 'F', "E716"));
	System.out.println("Riccardo "+c.calcolaCodiceFiscale("Pellegrino", "Riccardo", 21, 11, 2004, 'M', "E289"));
	System.out.println("Matteo "+c.calcolaCodiceFiscale("Cava", "Matteo", 29, 9, 2001, 'M', "A944"));
	//System.out.println("Antonella "+c.calcolaCodiceFiscale("DiBenedetto", "Re", 11, 11, 1949, 'F', "E716"));
	System.out.println("Lucia "+c.calcolaCodiceFiscale("Ghini", "Lucia", 15, 12, 2000, 'F', "A944"));
	
	
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 12, 6, 1976, 'M', "A944", "RSSMRA76H12A9Q4U"));
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 1, 1, 1990, 'M', "F205", "RSSMRA90A01F20RU"));
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 1, 1, 1990, 'M', "F205", "RSSMRA90A01F2L5K"));
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 1, 1, 1990, 'M', "F205", "RSSMRA90A01FN05O"));
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 1, 1, 1990, 'M', "F205", "RSSMRA90A0MF205R"));
	assert(c.verificaCodiceFiscale("Mario", "Rossi", 1, 1, 1990, 'M', "F205", "RSSMRA90AL1F205K"));
	}
}
