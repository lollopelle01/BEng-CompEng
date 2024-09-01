package beans;

public class ExecThread extends Thread{
	private int[][] matrix;
	private boolean result;

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public void run() {
		this.result = true;
		try {
			Thread.sleep(10000*60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < 6 && this.result; i++) {
			for(int j = 0; j < 6 && this.result; j++) {
				if(matrix[i][j] != 0 && matrix[i][j] != matrix[j][i]) {
					System.out.println(matrix[i][j] + " " + matrix[j][i]);
					this.result = false;
				}
			}
		}
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
