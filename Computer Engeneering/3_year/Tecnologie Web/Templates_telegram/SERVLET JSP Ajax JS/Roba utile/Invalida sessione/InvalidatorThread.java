package beans;

import javax.servlet.http.HttpSession;

public class InvalidatorThread extends Thread {
	
	private HttpSession session;

	public InvalidatorThread(HttpSession session) {
		this.session = session;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(10*60*1000);
		} catch (Exception e) {
			
		}
		session.invalidate();
	}

}
