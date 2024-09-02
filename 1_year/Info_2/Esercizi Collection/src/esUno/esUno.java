package esUno;

import java.util.*;

public class esUno {
	public static void main(String[] args) {
		String[] args1={new String("io"), new String("sono"), new String("io")};
		var s=new HashSet<String>();
		for (int i=0; i<args1.length; i++) {
			if(!s.add(args1[i])) {
				System.out.println("Parola duplicata: " + args1[i]);
			}
		}
		System.out.println("numero parole effettive: " + s.size()+"\nparole: "+s);
	}
}
