package esame.hibernate.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Due implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String due1;
	private String due2;
	private Set<Uno> unos = new HashSet<>();
	private Set<Tre> tres = new HashSet<>();
	
	public Due () {
		
	}
	public Due (String due1, String due2) {
		this.due1 = due1;
		this.due2 = due2;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDue1() {
		return due1;
	}
	public void setDue1(String due1) {
		this.due1 = due1;
	}
	public String getDue2() {
		return due2;
	}
	public void setDue2(String due2) {
		this.due2 = due2;
	}
	public Set<Uno> getUnos() {
		return unos;
	}
	public void setUnos(Set<Uno> unos) {
		this.unos = unos;
	}
	public Set<Tre> getTres() {
		return tres;
	}
	public void setTres(Set<Tre> tres) {
		this.tres = tres;
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
		Due other = (Due) obj;
		if (id != other.id)
			return false;
		return true;
	}
		
	
}