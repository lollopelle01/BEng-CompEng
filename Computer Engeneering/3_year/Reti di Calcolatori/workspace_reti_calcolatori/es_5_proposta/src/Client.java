import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		String registryHost = null; // host remoto con registry
		String serviceName = "";
		int REGISTRYPORT;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		// Controllo dei parametri della riga di comando
		if (args.length != 2) {
			System.out.println("Sintassi: RMI_Registry_IP registryPort");
			System.exit(1);
		}
		registryHost = args[0]; // ip
		serviceName = "RemOp"; //servizio di default a RemOp
		REGISTRYPORT = Integer.parseInt(args[1]); // porta RMI
		
		System.out.println("Invio richieste a " + registryHost + " per il servizio di nome " + serviceName);
		
		// connessione a RMI remoto
		try {
			String completeName = "//" + registryHost + ":" + REGISTRYPORT + "/" + serviceName;
			RemOp operazione = (RemOp) Naming.lookup(completeName);
			System.out.println("ClientRMI: Servizio \"" + serviceName + "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out.print("Servizio (C=conta righe, E=elimina riga): ");
			
			// ciclo interazione con utente
			while((service = stdIn.readLine()) != null) {
				if(service.equals("C")) {
					boolean ok = false;
					
					String fileName;
					System.out.print("Nome del file: ");
					fileName = stdIn.readLine(); //no controllo sul nome
					
					int parole = -1;
					System.out.print("Quante parole devono avere le righe per essere contate: ");
					while(ok!=true) {
						parole = Integer.parseInt(stdIn.readLine());
						if(parole < 0) {
							System.out.println("Il numero di parole deve essere positivo");
							System.out.print("Quante parole devono avere le righe per essere contate: ");
							continue;
						}
						else
							ok = true; // numero parole inserito correttamente
					}
					System.out.println("In " + fileName + " sono state contate " + operazione.conta_righe(fileName, parole) + " righe");
				}// conta righe 
				
				else if(service.equals("E")) {
					boolean ok = false;
					Esito esito = null;
					
					String fileName;
					System.out.print("Nome del file: ");
					fileName = stdIn.readLine(); //no controllo sul nome
					
					int riga = -1;
					System.out.print("Quale riga vuoi eliminare (partono da 1): ");
					while(ok!=true) {
						riga = Integer.parseInt(stdIn.readLine());
						if(riga <= 0) {
							System.out.println("Il numero di parole deve partire da 1");
							System.out.print("Quale riga vuoi eliminare (partono da 1): ");
							continue;
						}
						else
							ok = true; // riga inserita correttamente
					}
					esito = operazione.elimina_riga(fileName, riga);
					System.out.println("In " + esito.getFile() + " c'è file con " + esito.getRighe() + " righe" );
				}// elimina riga
				System.out.print("Servizio (C=conta righe, E=elimina riga): ");
			}// while
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}
