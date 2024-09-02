package agenda.model;

import java.util.ArrayList;
import java.util.List;

public class Contact implements Comparable<Contact>{
	private String name;
	private String surname;
	private List<Detail> detailList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<Detail> getDetailList() {
		return detailList;
	}
	public Contact(String name, String surname, List<Detail> detailList) {
		super();
		this.name = name;
		this.surname = surname;
		this.detailList = detailList;
	}
	
	public Contact(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
		this.detailList=new ArrayList<Detail>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detailList == null) ? 0 : detailList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (detailList == null) {
			if (other.detailList != null)
				return false;
		} else if (!detailList.equals(other.detailList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
	public int compareTo(Contact c) {
		if(this.surname.compareToIgnoreCase(c.surname)==0){
			return this.name.compareToIgnoreCase(c.name);
		}
		else
			return this.surname.compareToIgnoreCase(c.surname);
	}
	
	public String toString() {
		String result= this.name +" "+this.surname + "\n";
		for(Detail d: this.detailList) {
			result=result+d.toString()+"\n";
		}
		return result;
	}
	
	
	
}
