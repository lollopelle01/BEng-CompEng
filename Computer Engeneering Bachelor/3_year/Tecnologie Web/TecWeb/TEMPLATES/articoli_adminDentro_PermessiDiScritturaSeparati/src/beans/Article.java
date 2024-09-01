package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8383284799146397712L;
	
	private String text;
	private String name;
	private List<String> users;
	private String currentSession;
	
	public Article() {
		this.users = new ArrayList<>();
	}
	
	
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	public String getCurrentSession() {
		return currentSession;
	}
	public void setCurrentSession(String currentSession) {
		this.currentSession = currentSession;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void append(String s) {
		this.text += s;
	}
}
