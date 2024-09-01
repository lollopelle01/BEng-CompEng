package frazione;
import util.MyMath;

/**
 * Frazione come tipo di dato astratto (ADT)
 * 
 * @author Fondamenti di Informatica T-2
 * @version Feb 2021
 */
public class Frazione {
	private int num, den;

	/**
	 * Costruttore della Frazione
	 * 
	 * @param num
	 *            Numeratore
	 * @param den
	 *            Denominatore
	 */
	public Frazione(int num, int den) {
		boolean negativo = num * den < 0;
		this.num = negativo ? -Math.abs(num) : Math.abs(num);
		this.den = Math.abs(den);
	}

	/**
	 * Costruttore della Frazione
	 * 
	 * @param num
	 *            Numeratore
	 */
	public Frazione(int num) {
		this(num, 1);
	}

	/**
	 * Recupera il numeratore
	 * 
	 * @return Numeratore della frazione
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Recupera il denominatore
	 * 
	 * @return Denominatore della frazione
	 */
	public int getDen() {
		return den;
	}

	/**
	 * Calcola la funzione ridotta ai minimi termini.
	 * 
	 * @return Una nuova funzione equivalente all'attuale, ridotta ai minimi
	 *         termini.
	 */
	public Frazione minTerm() {
		if (getNum()==0) return new Frazione(getNum(), getDen());
		int mcd = MyMath.mcd(Math.abs(getNum()), getDen());
		int n = getNum() / mcd;
		int d = getDen() / mcd;
		return new Frazione(n, d);
	}
	
	/**
	 * Sono uguali?
	 * 
	 * @param f
	 * @return se le due frazioni sono uguali
	 */
	public boolean equals(Frazione f) {
		return f.getNum() * getDen() == f.getDen() * getNum();
	}
	
	@Override
	public String toString() {
		return getNum() + "/" + getDen();
	}

	/**
	 * Somma tra frazioni
	 * 
	 * @param f
	 * @return somma 
	 */
	public Frazione sum(Frazione f) {
		Frazione f1;
		int num_sum, den_sum;
		num_sum=this.num*f.den+this.den*f.num;
		den_sum=this.den*f.den;
		f1=new Frazione(num_sum, den_sum);
		return f1.minTerm();
	}
	
	/**
	 * Prodotto tra frazioni
	 * 
	 * @param f
	 * @return prodotto
	 */
	public Frazione mul(Frazione f) {
		Frazione f1;
		f1=new Frazione(this.num*f.num, this.den*f.den);
		return f1.minTerm();
	}
	
	/**
	 * Sottrazione tra frazioni
	 * 
	 * @param f
	 * @return differenza
	 */
	public Frazione sub(Frazione f) {
		Frazione f1;
		int num_s, den_s;
		num_s=this.num*f.den-this.den*f.num;
		den_s=this.den*f.den;
		f1=new Frazione(num_s, den_s);
		return f1.minTerm();
	}
	
	/**
	 * Inverso di una frazione
	 * 
	 * @return reciproco
	 */
	public Frazione reciprocal() {
		Frazione f;
		f=new Frazione(this.den, this.num);
		return f.minTerm();
	}
	
	/**
	 * Divisione tra frazioni
	 * 
	 * @param f
	 * @return quoziente
	 */
	public Frazione div(Frazione f) {
		Frazione f1, f2;
		f1=new Frazione(this.num, this.den);
		f2=f1.mul(f.reciprocal());
		return f2.minTerm();
	}
	
	/**
	 * Confronto tra frazioni
	 * @param f
	 * @return 0(f1=f2), 1(f1>f2), -1(f1<f2)
	 */
	public int compareTo(Frazione f) {
		int result=0;
		if(this.equals(f))
			result=0;
		else {
			if(this.num*f.den>this.den*f.num) //la prima frazione > seconda
				result=1;
			if(this.num*f.den<this.den*f.num) //la prima frazione < seconda
				result=-1;
		}
		return result;
	}
	
	/**
	 * Risultato di una frazione
	 * @return valore
	 */
	public Double getDouble() {
		double result= (double) getNum()/getDen();
		return result;
	}
	
	public static Frazione sum(Frazione[] tutte) {
		Frazione result=new Frazione(0);
		for(int i=0; i<tutte.length; i++) {
			result=result.sum(tutte[i]);
		}
		return result;
	}
	
	public static Frazione mul(Frazione[] tutte) {
	Frazione result=new Frazione(1);
	for(int i=0; i<tutte.length; i++) {
		result=result.mul(tutte[i]);
	}
	return  result;
	}
	
	public static int size(Frazione[] tutte) {
		int i, stop=0;
		for(i=0; i<tutte.length && stop==0; i++) {
			if(tutte[i]==null)
				stop=1;
		}
		return i-1;
	}
	
	static Frazione[] sum(Frazione[] setA, Frazione[] setB) {
		Frazione[] result;
		if(size(setA)==size(setB)) {
			result=new Frazione[size(setA)];
			for(int i=0; i<size(setA); i++) {
				result[i]=setA[i].sum(setB[i]);
			}
		}
		else 
			result=null;
		return result;
	}
	
	static Frazione[] mul(Frazione[] setA, Frazione[] setB) {
		Frazione[] result=null;
		if(size(setA)==size(setB)) {
			result=new Frazione[size(setA)];
			for(int i=0; i<size(setA); i++) {
				result[i]=setA[i].mul(setB[i]);
			}
		}
		return result;
	}
	
	public static String convertToString(Frazione[] tutte) { 
		String res = "[";
		for (int k=0; k<size(tutte); k++){
			res += tutte[k].toString() + ", "; 
		}
		res += "]";
		return res; 
	}
}
