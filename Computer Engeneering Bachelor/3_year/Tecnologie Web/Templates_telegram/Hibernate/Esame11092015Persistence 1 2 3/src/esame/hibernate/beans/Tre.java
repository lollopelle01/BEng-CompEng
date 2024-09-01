package esame.hibernate.beans;

import java.io.Serializable;

public class Tre implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String tre1;
	private String tre2;
	private Due due;

	public Tre() {
		
	}
	
	public Tre(String tre1, String tre2) {
		this.tre1 = tre1;
		this.tre2 = tre2;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTre1() {
		return tre1;
	}

	public void setTre1(String tre1) {
		this.tre1 = tre1;
	}

	public String getTre2() {
		return tre2;
	}

	public void setTre2(String tre2) {
		this.tre2 = tre2;
	}

	public Due getDue() {
		return due;
	}

	public void setDue(Due due) {
		this.due = due;
	}
	
	// --- utilities ----------------------------

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
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
		Tre other = (Tre) obj;
		if (id != other.id)
			return false;
		return true;
	}
			

}