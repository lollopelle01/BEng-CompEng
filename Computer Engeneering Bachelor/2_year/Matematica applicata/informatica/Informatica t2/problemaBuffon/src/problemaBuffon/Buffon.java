package problemaBuffon;

public class Buffon {
	static double getRand(double first, double last){
	    return Math.random()*(last-first);
	}
	static double calcolaPI(double a, double b, double numEsperimenti){
	    return (2*a)/(b*Buffon.calcolaIntersezioni(a, b, numEsperimenti));
	}
	static double calcolaIntersezioni(double a, double b, double numEsperimenti){
	    double intersezioni=0;
	    for(double i=0; i<=numEsperimenti; i++){
	        double distanza=Buffon.getRand(0, b);
	        double angolo=Buffon.getRand(0, Math.PI);
	        if(distanza<=a*Math.sin(angolo)) {
	        	intersezioni++;
	        }
	        System.out.println("sto lavorando: "+i);
	    }
	    return intersezioni;
	}

	public static void main(String[] args) {
		
	   System.out.println(Buffon.calcolaPI(1, 2, 1000000000));
	}
}
