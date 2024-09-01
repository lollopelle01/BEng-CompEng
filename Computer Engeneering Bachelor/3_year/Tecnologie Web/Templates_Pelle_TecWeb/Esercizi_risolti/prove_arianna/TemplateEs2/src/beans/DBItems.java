package beans;

import java.io.Serializable;

import java.util.*;

public class DBItems implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Item> dbItems;
	
	public DBItems() {
		super();
		this.dbItems = new ArrayList<>();
		Item item = new Item("cipolla");
		this.dbItems.add(item);
		Item item2 = new Item("carota");
		this.dbItems.add(item2);
	}

	public List<Item> getDbItems() {
		return dbItems;
	}

	public void setDbItems(List<Item> dbItems) {
		this.dbItems = dbItems;
	}
	
}
