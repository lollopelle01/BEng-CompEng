package bean;
import java.io.Serializable;

public class Matrix implements Serializable {

	private static final long serialVersionUID = 1L;

	private int[][] matr;   //matr deve avere lo stesso nome che ha nel json

	public Matrix(int[][] matr) {
		super();
		this.matr=new int [matr.length][matr[0].length];
		for(int i=0;i<matr.length;i++) {
			for(int j=0;j<matr[i].length/2;j++) {
				this.matr[i][j]=matr[i][j];
			}
		}

	}

	public int[][] getMatr() {
		return matr;
	}

	public void setMatr(int[][] a) {
		matr = a;
	}

   public int getMatrLength() {
	   return this.matr.length;
   }
}
