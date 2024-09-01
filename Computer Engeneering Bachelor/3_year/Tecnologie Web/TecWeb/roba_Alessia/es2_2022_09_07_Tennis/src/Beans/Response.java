package Beans;

import java.util.ArrayList;
import java.util.List;

public class Response {

	private boolean status;
	private Tennista tennista;
	private List<Tennista> tabTenn = new ArrayList<Tennista>();;
	
	public Response() {
		super();
		this.status = false;
	}
	
	public Response(Tennista tennista, List<Tennista> totTenn) {
		super();
		this.tennista = tennista;
		
		int tab = tennista.getTab();
		for(Tennista t : totTenn) {
			if(t.getTab() == tab) {
				this.tabTenn.add(t);
			}
		}
		this.status = true;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Tennista getTennista() {
		return tennista;
	}

	public void setTennista(Tennista tennista) {
		this.tennista = tennista;
	}

	public List<Tennista> getTabTenn() {
		return tabTenn;
	}

	public void setTabTenn(List<Tennista> tabTenn) {
		this.tabTenn = tabTenn;
	}
	
}
