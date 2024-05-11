package beans;

public class OperationReq {
	
	private String operazione;

	public String getOperazione() {
		return operazione;
	}
	public void setOperazione(String operazione, String giocatore) {
		this.operazione = operazione;
	}
	public OperationReq(String operazione) {
		super();
		this.operazione = operazione;
	}

}
