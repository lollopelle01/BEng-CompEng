package Beans;

public class Action {
	
	private String action;
	private String nickname;
	private String password;
	private String news;
	
	public Action(String action) {
		super();
		this.action = action;
	}
	
	public Action(String action, String nickname, String password, String news) {
		super();
		this.action = action;
		this.nickname = nickname;
		this.password = password;
		this.news = news;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}
}
