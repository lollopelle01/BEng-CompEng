package Matrici;

public class Matrix {
	private double[][] values;
	
	public int getCols() {
		return values[0].length;
	}
	public int getRows() {
		return values.length;
	}
	public double getValue(int row, int col) {
		if(row<=this.getRows() && col<=this.getCols())
			return values[row][col];
		else
			return Double.NaN;
	}
	private Matrix(int rows, int cols){
		this.values=new double[rows][cols];
	}
	public boolean isSquared() {
		if(this.values.length!=this.values[0].length)
			return false;
		else
			return true;
	}
	public Matrix (double[][] values) {
		this.values=values;
	}
	private void setValue(int row, int col, double value) {
		this.values[row][col]=value;
	}
	
	public Matrix sum(Matrix a) {
		Matrix c;
		if(a.getCols()!=this.getCols() || a.getRows()!=this.getRows())
			c=null;
		else {
				c=new Matrix(a.getRows(), a.getCols());
			for(int i=0; i<a.getRows(); i++) {
				for(int j=0; j<a.getCols(); j++) {
					c.setValue(i, j, this.getValue(i, j)+a.getValue(i, j));
				}
			}
		}
		return c;
	}
	public Matrix mul(Matrix a) {
		Matrix c;
		if(this.getCols()!=a.getRows())
			c=null;
		else {
			c=new Matrix(this.getRows(), a.getCols());
			for(int i=0; i<this.getRows(); i++) {
				for(int j=0; j<a.getCols(); j++) {
					double result=0;
					for(int k=0; k<this.getCols(); k++) {
						result=result+this.getValue(i, k)*a.getValue(k, j);
					}
					c.setValue(i, j, result);
				}
			}
		}
		return c;
	}
	public Matrix extractMinor(int row, int col) {
		Matrix result;
		if(row>=this.getRows()||col>=this.getCols() || !this.isSquared()) {
			result=null;
		}
		else {
			result=new Matrix(this.getRows()-1, this.getCols()-1);
			for(int i=0; i<this.getRows()-1; i++) {
					for(int j=0; j<this.getCols()-1; j++) {
						if(i<row && j<col) {
							result.setValue(i, j, this.getValue(i, j));
						}
						if(i>=row && j>=col) {
							result.setValue(i, j, this.getValue(i+1, j+1));
						}
						if(i<row && j>=col) {
							result.setValue(i, j, this.getValue(i, j+1));
						}
						if(i>=row && j<col) {
							result.setValue(i, j, this.getValue(i+1, j));
						}
					}
			}
		}
		return result;
	}
	public Matrix extractSubMatrix(int startRow, int startCol, int rowCount, int colCount) {
		Matrix result;
		if(startRow+rowCount>getRows() && startCol+colCount>getCols()) {
			result=null;
		}
		else {
			result=new Matrix(rowCount, colCount);
			for(int i=0; i<rowCount; i++) {
					for(int j=0; j<colCount; j++) {
							result.setValue(i, j, this.getValue(i+startRow, j+startCol));
					}
			}
		}
		return result;
	}
	
	private double calcDet() { //funziona solo fino all'ordine 3
		if(this.getCols()==1)
			return this.getValue(0, 0);
		if(this.getCols()==2)
			return this.getValue(0, 0)*this.getValue(1, 1)-this.getValue(0, 1)*this.getValue(1, 0);
		if(this.getCols()==3) {
			double d1=this.getValue(0,0)*this.getValue(1,1)*this.getValue(2,2);
			double d2=this.getValue(0,1)*this.getValue(1,2)*this.getValue(2,0);
			double d3=this.getValue(0,2)*this.getValue(1,0)*this.getValue(2,1);
			double d4=this.getValue(2,0)*this.getValue(1,1)*this.getValue(0,2);
			double d5=this.getValue(2,1)*this.getValue(1,2)*this.getValue(0,0);
			double d6=this.getValue(2,2)*this.getValue(1,0)*this.getValue(0,1);
			return d1+d2+d3-d4-d5-d6;
		}
		else { // errore
			double result = 0;
			for (int i = 0; i < this.getCols(); i++) {
				result = result + Math.pow(-1,  i + 2) * this.getValue(0, i) * (this.extractMinor(0, i)).calcDet();
			}
			return result;
		}
	}
	public double det() {
		if(this.isSquared()) {
			return this.calcDet();
		}
		else
			return Double.NaN;
	}
	public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                s.append(this.getValue(i, j));
                s.append("  ");
            }
            s.append("\n");
        }
        return s.toString();
	}
	
	public Matrix inverse() {
		Matrix result=new Matrix(this.getRows(), this.getCols());
		double value;
		if(this.det()==Double.NaN)
			result=null;
		else {
			for(int i=0; i<this.getRows(); i++) {
				for(int j=0; j<this.getCols(); j++) {
					value=Math.pow(-1, i+j)*(this.extractMinor(i, j)).calcDet()/this.det();
					result.setValue(j, i, value);
				}
			}
		}
		return result;
			
	}
	
}
