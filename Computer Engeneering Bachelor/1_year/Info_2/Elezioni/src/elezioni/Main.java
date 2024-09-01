package elezioni;

public class Main {
 public static void main(String[] args) {
	RisultatoDelPartito[] partiti = {new RisultatoDelPartito("Formiche rosse", -1, 10),
									new RisultatoDelPartito("Topolini grigi", -1, 100),
									new RisultatoDelPartito("Farfalle blu", -1, 200),
									new RisultatoDelPartito("Bruchi verdi", -1, 300),
									new RisultatoDelPartito("Canarini ocra", -1, 400)
									};
	long[] seggiAttesi = {1,10,20,30,39};
	for(RisultatoDelPartito p: partiti) {
		System.out.println(p.toString());
	}
	StringBuilder sb=new StringBuilder("");
	CalcolatoreSeggi Formiche=new CalcolatoreSeggi("Formiche rosse", sb);
	Formiche.calcolaSeggi(100, partiti);
	System.out.println(Formiche.getLogger().toString());
 }
}
