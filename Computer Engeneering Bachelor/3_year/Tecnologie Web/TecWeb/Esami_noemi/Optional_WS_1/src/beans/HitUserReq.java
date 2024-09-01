package beans;

public class HitUserReq {
	
	String row;
	int column;
	
	public String getRow() {
		return this.row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public int getCol() {
		return this.column;
	}
	public void setCol(int col) {
		this.column = col;
	}
	public HitUserReq(String row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "HitReq [row=" + row + ", column=" + column + "]";
	}

	
	

}
