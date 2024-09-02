package beans;

import java.util.ArrayList;
import java.util.List;

public class NewsList {
	
	public List<News> news;
	public boolean valid;
	
	public NewsList() {
		this.news = new ArrayList<News>();
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}
	
}
