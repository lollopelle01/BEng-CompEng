package beans;

public class Utente {
	
	private String username;
	private String password;
	private int accessoVersion;
	private int actualVersion;
	
	public Utente() {
		
	}
	


		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + username.hashCode();
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
		Utente other = (Utente) obj;
		if (!username.equals(other.username))
			return false;
		return true;
	}

	public Utente(String username, String password, int accessoVersion) {
		super();
		this.username = username;
		this.password = password;
		this.accessoVersion = accessoVersion;
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



	public int getActualVersion() {
		return actualVersion;
	}



	public void setActualVersion(int actualVersion) {
		this.actualVersion = actualVersion;
	}



	public int getAccessoVersion() {
		return accessoVersion;
	}



	public void setAccessoVersion(int accessoVersion) {
		this.accessoVersion = accessoVersion;
	}
	

}
