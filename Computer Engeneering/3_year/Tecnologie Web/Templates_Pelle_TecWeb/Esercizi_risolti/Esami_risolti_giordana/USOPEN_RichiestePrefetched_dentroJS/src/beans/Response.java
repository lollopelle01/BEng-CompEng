package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7070964292182511686L;
	
	private List<Player> prefetched;
	private String message;
	private Player player;
	
	public Response() {
		this.prefetched = new ArrayList<>();
	}
	
	public List<Player> getPrefetched() {
		return prefetched;
	}
	public void setPrefetched(List<Player> prefetched) {
		this.prefetched = prefetched;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
