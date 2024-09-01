package beans;

import java.util.Date;

public class UserInfo {
	private String username;
	private String password;
	private Date dataPasswd;
	private int errori;
	
	public UserInfo() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDataPasswd() {
		return dataPasswd;
	}

	public void setDataPasswd(Date dataPasswd) {
		this.dataPasswd = dataPasswd;
	}

	public int getErrori() {
		return errori;
	}

	public void setErrori(int errori) {
		this.errori = errori;
	}

	
}
