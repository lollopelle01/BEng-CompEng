package frazlib;
import frazione.Frazione;

public class FrazLib {
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
}
