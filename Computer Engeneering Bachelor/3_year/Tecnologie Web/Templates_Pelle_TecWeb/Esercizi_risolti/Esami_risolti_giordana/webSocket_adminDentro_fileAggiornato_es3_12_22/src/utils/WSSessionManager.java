package utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.Session;

public class WSSessionManager {
	protected final List<Session> sessions = new CopyOnWriteArrayList<>();
	protected final Map<Session, Date> date = new HashMap<>();

	public WSSessionManager() {
	}

	public void addSession(Session session) {
		sessions.add(session);
		date.put(session, new Date());
	}

	public void removeSession(Session session) {
		sessions.remove(session);
		date.remove(session);
	}

	public List<Session> getSessions() {
		return Collections.unmodifiableList(sessions);
	}
	
	public Map<Session, Date> getDates() {
		return this.date;
	}

	public void broadcast(String message) throws IOException {
		for (Session s : sessions) {
			s.getBasicRemote().sendText(message);
		}
	}

	public void broadcastExcept(String message, Session session) throws IOException {
		for (Session s : sessions) {
			if (s != session) {
				s.getBasicRemote().sendText(message);
			}
		}
	}
}
