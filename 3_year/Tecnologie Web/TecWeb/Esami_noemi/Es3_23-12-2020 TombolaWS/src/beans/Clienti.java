package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.websocket.Session;


//classe singleton
public class Clienti {
	private List<Session> sessioni;
	 private static Clienti instance;
	 
	public List<Session> getSessioni() {
		return sessioni;
	}

	public void setSessioni(List<Session> sessioni) {
		this.sessioni = sessioni;
	}

	private  Clienti() {//private perch' puo' solo essere chiamato da questa classe
		super();
		this.sessioni =  Collections.synchronizedList(new ArrayList<Session>());
	}
	
	public void addCliente(Session sess) {
		this.sessioni.add(sess);
	}
	
	public void removeCliente(Session sess) {
		this.sessioni.remove(sess);
	}
	

    public static Clienti getInstance() {
        if (instance == null) {
            instance = new Clienti();
        }
        return instance;
    }

//	@Override
//	public int compareTo(Session o) {
//		for (Session session : sessioni) {
//			if(session.getId().equals(o.getId())) {
//				return 1;
//			}
//		}
//		return -1;
//
//	}
}
