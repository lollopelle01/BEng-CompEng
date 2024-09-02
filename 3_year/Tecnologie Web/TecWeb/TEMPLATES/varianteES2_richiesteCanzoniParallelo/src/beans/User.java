package beans;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSession;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

//	private Date creation;
//	private Date lastLogin;
//	private HttpSession session;
	
	
	public User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public Date getCreation() {
//		return creation;
//	}
//
//	public void setCreation(Date creation) {
//		this.creation = creation;
//	}
//
//	public Date getLastLogin() {
//		return lastLogin;
//	}
//
//	public void setLastLogin(Date lastLogin) {
//		this.lastLogin = lastLogin;
//	}
//
//	public HttpSession getSession() {
//		return session;
//	}
//
//	public void setSession(HttpSession session) {
//		this.session = session;
//	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public static boolean validUsername(String username) {
		
		return !username.isBlank();
	}
	
	public static boolean validPassword(String password) {
		return !password.isBlank();
	}
}
