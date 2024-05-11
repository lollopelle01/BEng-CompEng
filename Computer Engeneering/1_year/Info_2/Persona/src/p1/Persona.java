package p1;

public class Persona {
	private String nameSurname;
	private int yearOfBirth;
	
	public Persona (String name, int yearOfBirth) {
		this.nameSurname=name;
		this.yearOfBirth=yearOfBirth;
	}
	
	public Persona(String name, String yearOfBirth) {
		this.nameSurname=name;
		this.yearOfBirth=Integer.parseInt(yearOfBirth);
	}
	
	public String getNameSurname() {
		return nameSurname;
	}
	
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	
	public boolean omonimo(Persona p) {
		if(this.nameSurname.compareToIgnoreCase(p.nameSurname)==0)
			return true;
		else
			return false;
	}
	
	public int olderThan(Persona other) {
		int result=0;
		if(this.yearOfBirth<other.yearOfBirth)
			result=1;
		if(this.yearOfBirth==other.yearOfBirth)
			result=0;
		if(this.yearOfBirth>other.yearOfBirth)
			result=-1;
		return result;
	}
}
