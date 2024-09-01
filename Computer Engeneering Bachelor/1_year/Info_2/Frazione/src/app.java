import util.MyMath;

public class app {
	public static void main(String[] args) {
		Frazione f=new Frazione(); 
		f.inizializza(245, 45);
		f=f.minTerm();
		f.print(f);
		int result=MyMath.fattoriale(-1);
		System.out.println(result);
		int result1=MyMath.abs(-20);
		System.out.println(result1);
	}
}
