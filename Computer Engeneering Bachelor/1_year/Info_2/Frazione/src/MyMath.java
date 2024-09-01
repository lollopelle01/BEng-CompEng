

/** Libreria Matematica
 * 
 * @author pelle
 *mcd
 *mcm
 *fattoriale
 *valore assoluto
 */

public class MyMath {
	//calcolo massimo comune divisore
	public static int mcd(int a, int b) {
		int resto=0;
		if (b > a){ 
			int tmp=a;
			a=b;
			b = tmp;
		} 
		do {
			resto = a % b;
			a = b;
			b = resto;
		} 
		while (resto != 0);
		return a;
	}
	
	//calcolo minimo comune multiplo
	public static int mcm(int a, int b) {
		int result;
		result=(a*b)/MyMath.mcd(a, b);
		return result;
	}
	
	//calcolo del fattoriale
	public static int fattoriale(int a) {
		if(a>0) {
			if(a==1)
				a=1;
			else
				a=a*fattoriale(a-1);
		}
		else {
			System.out.print("Errore, numero negativo" + "\t");
		}
		return a;
	}
	
	//calcolo del valore assoluto
	public static int abs(int a) {
		if(a<0)
			a=-a;
		return a;
	}
}




