package sudoku.model;

import java.util.OptionalInt;

public class SudokuBoard {
	private OptionalInt[][] board;
	private final int DIM=9;
	private int fullCellNumber;
	
	private boolean isInseribile(int digit, OptionalInt[] rigaOcolonna) {
		
		for(int i=0; i<rigaOcolonna.length; i++) {
			if(rigaOcolonna[i].isPresent() && rigaOcolonna[i].getAsInt()==digit) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isInseribile2(int digit, OptionalInt[][] rigaOcolonna) {
		
		for(int i=0; i<rigaOcolonna.length; i++) {
			for(int j=0; j<rigaOcolonna.length; j++) {
				if(rigaOcolonna[i][j].isPresent() && rigaOcolonna[i][j].getAsInt()==digit) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public SudokuBoard() {
		this.board=new OptionalInt[DIM][DIM];
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				this.board[i][j]=OptionalInt.empty();
			}
		}
		this.fullCellNumber=0;
	}
	
	public void setCellRow(int row, int[] numValues) {
		if(numValues.length!=DIM) {
			throw new IllegalArgumentException("Dimensione lista da inserire non compatibile");
		}
		for(int i=0; i<numValues.length; i++) {
			if(i<0 || i>9) {
				throw new IllegalArgumentException("Valori della lista non compatibili");
			}
		}
		if(row<0 || row>=DIM) {
			throw new IllegalArgumentException("Dimensione righe non compatibile");
		}
		
		
		for(int i=0; i<DIM; i++) {
			if(i==row) {
				for(int j=0; j<numValues.length; j++) {
					if(numValues[j]==0) {
						this.board[row][j]=OptionalInt.empty();
					}
					else {
						this.board[row][j]=OptionalInt.of(numValues[j]);
					}
				}
			}
		}
	}
	
	public OptionalInt getCell(int row, int col) {
		if(row<0 || row>=DIM) {
			throw new IllegalArgumentException("Dimensione righe non compatibile");
		}
		if(col<0 || col>=DIM) {
			throw new IllegalArgumentException("Dimensione colonne non compatibile");
		}
		
		for(int i=0; i<DIM; i++) {
			if(i==row) {
				for(int j=0; j<DIM; j++) {
					if(j==col) {
						return this.board[i][j];
					}
				}
			}
		}
		
		throw new IllegalArgumentException("elemento non trovato");
	}
	
	public int getSize() {
		return this.DIM;
	}
	
	public void clearCell(int row, int col) {
		if(row<0 || row>=DIM) {
			throw new IllegalArgumentException("Dimensione righe non compatibile");
		}
		if(col<0 || col>=DIM) {
			throw new IllegalArgumentException("Dimensione colonne non compatibile");
		}
		
		for(int i=0; i<DIM; i++) {
			if(i==row) {
				for(int j=0; j<DIM; j++) {
					if(j==col) {
						this.board[i][j]=OptionalInt.empty();
					}
				}
			}
		}
	}
	
	public boolean setCell(int row, int col, int digit) {
		if(row<0 || row>=DIM) {
			throw new IllegalArgumentException("Dimensione righe non compatibile");
		}
		if(col<0 || col>=DIM) {
			throw new IllegalArgumentException("Dimensione colonne non compatibile");
		}
		if(digit>0 || digit<9) {
			for(int i=0; i<DIM; i++) {
				if(i==row && this.isInseribile(digit, this.board[i])) {
					for(int j=0; j<DIM; j++) {
						if()
						if(j==col) {
							if(this.board[i][j].isPresent())
								return false;
							else {
								this.board[i][j]=OptionalInt.of(digit);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public int getEmpyCellNumber() {
		int result=0;
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				if(!this.board[i][j].isPresent()) {
					result++;
				}
			}
		}
		return result;
	}
	
	public boolean isFull() {
		return this.getEmpyCellNumber()==0;
	}
	
	
}
