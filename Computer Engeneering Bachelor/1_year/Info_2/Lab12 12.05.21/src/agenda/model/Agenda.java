package agenda.model;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Agenda {
	private SortedSet<Contact> contactSet;
	
	public Agenda() {
		this.contactSet=new TreeSet<Contact>();
	}

	public Agenda(Collection<Contact> contactSet) {
		super();
		this.contactSet=new TreeSet<Contact>();
		for(Contact c: contactSet) {
			this.contactSet.add(c);
		}
	}
	
	public void addContact(Contact c) {
		this.contactSet.add(c);
	}
	
	public Optional<Contact> getContact(String firstName, String SecondName) {
		Optional<Contact> result =Optional.empty();
		for(Contact c: this.contactSet) {
			if(c.getName()==firstName && c.getSurname()==SecondName) {
				result=Optional.of(c);
			}
		}
		return result;
	}
	
	public Optional<Contact> getContact(int index){
		Optional<Contact> result =Optional.empty();
		int i=0;
		for(Contact c: this.contactSet) {
			if(i==index) {
				return Optional.of(c);
			}
			i++;
		}
		return result;
	}
	
	public void removeContact(Contact c) {
		this.contactSet.remove(c);
	}
	
	public SortedSet<Contact> searchContacts(String secondName){
		SortedSet<Contact> result=new TreeSet<Contact>();
		for(Contact c: this.contactSet) {
			if(c.getSurname()==secondName) {
				result.add(c);
			}
		}
		return result;
	}
	
	public Set<Contact> getContacts(){
		return Set.copyOf(this.contactSet);
	}
	
}
