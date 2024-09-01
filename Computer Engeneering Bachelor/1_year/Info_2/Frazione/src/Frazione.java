import util.MyMath;

public class Frazione {
	private int num, den;
	
	public void inizializza(int n, int d) {
		num=n;
		den=d;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getDen() {
		return den;
	}
	
	public boolean equal(Frazione f) {
		if(den==f.den && num==f.num)
			return true;
		else
			return false;
	}
	
	public Frazione minTerm() {
		Frazione f;
		f=new Frazione();
		int a=num;
		int b=den;
		int cond=MyMath.mcd(a, b);
		while(cond!=1) {
			int div=MyMath.mcd(a, b);
			a=a/div;
			b=b/div;
			cond=MyMath.mcd(a, b);
		}
		f.inizializza(a, b);
		return f;
	}
	
	public void print(Frazione f) {
		System.out.println(f.num + "/" + f.den);
	}
}
