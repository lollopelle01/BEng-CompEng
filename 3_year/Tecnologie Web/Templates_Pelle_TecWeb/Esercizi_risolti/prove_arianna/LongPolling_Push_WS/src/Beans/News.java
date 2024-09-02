package Beans;


public class News {

	private String action = "news";
	private String news;
	
	public News(String news) {
		super();
		this.news = news;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}
}
