package esame.hibernate.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Uno implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String uno1;
	private String uno2;
	private Set<Due> dues = new HashSet<>();
	
	public Uno () {
		
	}
	
	public Uno (String uno1, String uno2) {
		this.uno1 = uno1;
		this.uno2 = uno2;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUno1() {
		return uno1;
	}
	public void setUno1(String uno1) {
		this.uno1 = uno1;
	}
	public String getUno2() {
		return uno2;
	}
	public void setUno2(String uno2) {
		this.uno2 = uno2;
	}

	public Set<Due> getDues() {
		return dues;
	}

	public void setDues(Set<Due> dues) {
		this.dues = dues;
	}
	
	// --- utilities ----------------------------

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = (prime * result + id);
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Uno other = (Uno) obj;
		if (id != other.id)
			return false;
		return true;
	}
		
}