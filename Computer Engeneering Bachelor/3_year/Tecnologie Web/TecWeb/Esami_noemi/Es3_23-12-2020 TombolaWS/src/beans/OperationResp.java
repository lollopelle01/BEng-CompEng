package beans;

public class OperationResp {
	
	String msg;
	int num;
	public OperationResp(String msg, int num) {
		super();
		this.msg = msg;
		this.num = num;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
