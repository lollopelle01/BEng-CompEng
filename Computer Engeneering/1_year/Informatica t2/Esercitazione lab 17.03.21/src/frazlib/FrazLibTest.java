package frazlib;
import frazione.Frazione;

public class FrazLibTest {
	public static void main(String[] args) {
		Frazione f1=new Frazione(1, 2);
		Frazione f2=new Frazione(1, 4);
		Frazione f3=new Frazione(1, 8);
		Frazione mulResult=new Frazione(1, 64);
		Frazione sumResult=new Frazione(7, 8);
		Frazione[] insieme= {f1, f2, f3};
		assert(FrazLib.mul(insieme).equals(mulResult));
		//System.out.println(FrazLib.mul(insieme).toString());
		assert(FrazLib.sum(insieme).equals(sumResult));
		//System.out.println(FrazLib.sum(insieme).toString());
	}
}
