package beans;

public class ExecThread extends Thread{
	private int[][] matrix;
	private boolean result;
	private int operazione;
	private int somma;
	
	public ExecThread(int op, int[][] matrix) {
		this.operazione=op;
		this.matrix=matrix;
		this.somma=0;
	}
	
	@Override
	public void run() {
		System.out.println("Thread avviato: " + this.getName());
		int somma[]=new int [5];
		this.result=false;
		
		
		try {
			Thread.sleep(10000 * 60); //1 minuto
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(this.operazione==1) {
			//devo controllarre la somma delle righe
			for(int i=0; i<5; i++) {
				somma[i] =0;
				for(int j=0; j<5; j++) {
					somma[i] += matrix[i][j];
				}
			}
			
			if(somma[0] == somma[1] && somma[0] == somma[2] && somma[0] == somma[3] && somma[0]==somma[4]) {
				this.result = true;
				this.somma=somma[0];
			}
		}
		else {
			if(this.operazione==2) {
				//devo controllare la somma delle colonne
				for(int i=0; i<5; i++) {
					somma[i] =0;
					for(int j=0; j<5; j++) {
						somma[i] += matrix[j][i];
					}
				}
				
				if(somma[0] == somma[1] && somma[0] == somma[2] && somma[0] == somma[3] && somma[0]==somma[4]) {
					this.result = true;
					this.somma=somma[0];
				}
			}
			else {
				if(this.operazione==3) {
					//devo controllare la somma delle diagonali
					int sommad[] = new int[2];
					sommad[0] = matrix[0][0] + matrix[1][1] + matrix[2][2] + matrix[3][3] + matrix[4][4];
					sommad[1] = matrix[4][0] + matrix[3][1] + matrix[2][2] + matrix[1][3] + matrix[0][4];
					
					if(sommad[0] == sommad[1]) {
						this.result = true;
						this.somma=sommad[0];
					}
				}
				else 
					this.result=false;
			}
		}
		
		System.out.println("Thread ha finito l'operazione: " + this.getName());
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getSomma() {
		return somma;
	}

	public void setSomma(int somma) {
		this.somma = somma;
	}
	
	
}
