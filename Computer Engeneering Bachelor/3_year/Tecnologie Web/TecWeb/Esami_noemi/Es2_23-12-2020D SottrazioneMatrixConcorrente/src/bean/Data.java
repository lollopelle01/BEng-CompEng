package bean;

import java.io.Serializable;

import java.io.Serializable;
import java.util.Arrays;

public class Data implements Serializable {

	private static final long serialVersionUID = 1L;

	private int[][] A;
	private int[][] B;

	public Data(int[][] A , int [][] B) {
		super();
		this.A=new int [A.length][A.length];
		for(int i=0;i<A.length;i++) {
			for(int j=0;j<A[i].length;j++) {
				this.A[i][j]=A[i][j];
			}
		}
		this.B=new int [B.length][B.length];
		for(int i=0;i<B.length;i++) {
			for(int j=0;j<B[i].length;j++) {
				this.B[i][j]=B[i][j];
			}
		}
	}

	public int[][] getA() {
		return A;
	}

	public void setA(int[][] a) {
		A = a;
	}

	public int[][] getB() {
		return B;
	}

	public void setB(int[][] b) {
		B = b;
	}
	
	public int[][] getRows(char id, int parte){
		//parte==0 prime n/2 righe, parte 1 resto
		int[][] res=new int[getA().length][getA().length];
		
		if(id=='A') {
			if(parte==1) {
				for(int i=0;i<getA().length/2;i++) {
					for(int j=0;j<getA()[i].length;j++) {
						res[i][j]=getA()[i][j];
					}
				}
			}
			if(parte==2) {
				for(int i=getA().length/2;i<getA().length;i++) {
					for(int j=0;j<getA()[i].length;j++) {
						res[i][j]=getA()[i][j];
					}
				}
			}

		}
		if(id=='B') {
			if(parte==1) {
				for(int i=0;i<getB().length/2;i++) {
					for(int j=0;j<getB()[i].length;j++) {
						res[i][j]=getB()[i][j];
					}
				}
			}
			if(parte==2) {
				for(int i=getB().length/2;i<getB().length;i++) {
					for(int j=0;j<getB()[i].length;j++) {
						res[i][j]=getB()[i][j];
					}
				}
			}
		}
		return res;
	}

	@Override
	public String toString() {
		return "Data [A=" + Arrays.toString(A) + ", B=" + Arrays.toString(B) + "]";
	}
}




/*
//classe dei dati che arrivano
public class Data implements Serializable {

	private Matrix matrA;
	private Matrix matrB;

	
	public Data(Matrix submatrA, Matrix submatrB) {
		super();
        this.matrA = submatrA;
        this.matrB = submatrB;
	}

	public Matrix getMatrA() {
		return matrA;
	}


	public void setMatrA(Matrix matrA) {
		this.matrA = matrA;
	}


	public Matrix getMatrB() {
		return matrB;
	}


	public void setMatrB(Matrix matrB) {
		this.matrB = matrB;
	}
}
*/