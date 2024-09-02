package elezioni;

public class CalcolatoreSeggi {
	private String nome;
	private StringBuilder sb;
	private RestoDelPartito trovaMax(RestoDelPartito[] elenco) {
		RestoDelPartito result=elenco[0];
		for(int i=1; i<elenco.length; i++) {
			if(elenco[i].getResto()>result.getResto()) {
				result=elenco[i];
			}
		}
		return result;
	}
	private void incSeggiPartito(String partito, RisultatoDelPartito[] elenco) {
		for(int i=0; i<elenco.length; i++) {
			if(partito==(elenco[i].getNome())) {
				elenco[i].setSeggi(elenco[i].getSeggi()+1);
			}
		}
	}
	private long getVotiTotali(RisultatoDelPartito[] elenco) {
		long result=0;
		for(int i=0; i<elenco.length; i++) {
			result=result+elenco[i].getVoti();
		}
		return result;
	}
	
	public String getNome() {
		return this.nome;
	}
	public StringBuilder getLogger() {
		return this.sb;
	}
	public CalcolatoreSeggi(String nome, StringBuilder sb) {
		this.nome=nome;
		this.sb=sb;
	}

	public void calcolaSeggi(long seggi, RisultatoDelPartito[] elenco) {
		long votiT=this.getVotiTotali(elenco);
		double quoziente=(double) votiT/seggi;
		long seggiP=0;
		long seggiS;
		RestoDelPartito[] secondo = new RestoDelPartito[elenco.length];
		
		sb.setLength(0);
		for(RisultatoDelPartito p: elenco) {
			sb.append(p+System.lineSeparator());
		}
		sb.append("Seggi da assegnare "+seggi+System.lineSeparator());
		sb.append("Quoziente elettorale "+quoziente+System.lineSeparator());
		
		for(int i=0; i<elenco.length; i++) { //primo giro
			elenco[i].setSeggi((long)(Math.floor((elenco[i].getVoti()/quoziente))));
			seggiP=seggiP+elenco[i].getSeggi();
			long resto=(long) (elenco[i].getVoti()%quoziente);
			secondo[i]=new RestoDelPartito(elenco[i].getNome(), resto);
			
		}
		sb.append("Seggi assegnati al primo giro: "+System.lineSeparator());
		for(RisultatoDelPartito p: elenco) {
			sb.append(p+System.lineSeparator());
		}
		sb.append("Resti dopo il primo giro: " + System.lineSeparator()); 
		for (RestoDelPartito r : secondo) {
			sb.append(r + System.lineSeparator());
		}
		seggiS=seggi-seggiP;
		sb.append("Seggi ancora da assegnare: " +(seggiS) + System.lineSeparator());
		
		while(seggiS!=0){ //secondo giro
			RestoDelPartito max=this.trovaMax(secondo);
			this.incSeggiPartito(max.getPartito(), elenco);
			seggiS--;
			max.azzeraResto();
		}
		sb.append("Seggi assegnati alla fine: " + System.lineSeparator());
		for (RisultatoDelPartito p : elenco) {
			 sb.append(p + System.lineSeparator());
		}
	}
}
