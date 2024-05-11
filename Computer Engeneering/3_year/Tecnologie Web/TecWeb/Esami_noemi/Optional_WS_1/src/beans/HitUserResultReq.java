package beans;

public class HitUserResultReq {
	
	String type;
	String content;
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public HitUserResultReq(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "HitResp [type=" + type + ", content=" + content + "]";
	}
	

	
	
	
	

}
