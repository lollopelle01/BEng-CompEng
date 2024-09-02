// pellegrino lorenzo 0000971455

import java.rmi.*;
import java.io.*;

class RMI_Client {

	public static void main(String[] args) {
		int registryPort = 1099;
		String registryHost = null;
		String serviceName = "Server";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		// Controllo parametri
		if (args.length != 1 && args.length != 2) {
			System.out.println("Sintassi: ClientFile RegistryHost [registryPort]");
			System.exit(1);
		} else {
			registryHost = args[0];
			if (args.length == 2) {
				try {
					registryPort = Integer.parseInt(args[1]);
				} catch (Exception e) {
					System.out
					.println("Sintassi: ClientFile NomeHost [registryPort], registryPort intero");
					System.exit(1);
				}
			}
		}

		// Connessione al servizio RMI remoto
		try {
			String completeName = "//" + registryHost + ":" + registryPort + "/"
					+ serviceName;
			RMI_interfaceFile serverRMI = (RMI_interfaceFile) Naming.lookup(completeName);
			System.out.println("Client RMI: Servizio \"" + serviceName
					+ "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			int gg, mm, aaaa, result, i;
			String[] lista = null;
			System.out.print("Servizio ( eliminazione , visualizzazione ): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("eliminazione")) {

					/*Lettura parametri*/
					System.out.print("Giorno: "); gg=Integer.parseInt(stdIn.readLine());
					System.out.print("Mese: "); mm=Integer.parseInt(stdIn.readLine());
					System.out.print("Anno: "); aaaa=Integer.parseInt(stdIn.readLine());

					/*Controllo dei parametri*/
					if((gg<=0 || gg>31) || (mm<=0 || mm>12) || aaaa <=0){
						System.out.println("Data inserita non correttamente");
						System.out.print("Servizio ( eliminazione , visualizzazione ): ");
						continue;
					}

					/*Invio dei parametri e ricezione della risposta*/
					try {
						result = serverRMI.elimina_prenotazioni(gg, mm, aaaa);

						/*Gestione della risposta*/
						if(result < 0){
							System.out.println("Eliminazione fallita");
						}
						else{
							System.out.println("Eliminazione effettuata " + result + " volte");
						}
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("visualizzazione")) {

						/*Lettura parametri*/
						System.out.print("Giorno: "); gg=Integer.parseInt(stdIn.readLine());
						System.out.print("Mese: "); mm=Integer.parseInt(stdIn.readLine());
						System.out.print("Anno: "); aaaa=Integer.parseInt(stdIn.readLine());

						/*Controllo dei parametri*/
						if((gg<=0 || gg>31) || (mm<=0 || mm>12) || aaaa <=0){
							System.out.println("Data inserita non correttamente");
							System.out.print("Servizio ( eliminazione , visualizzazione ): ");
							continue;
						}
						/*Invio dei parametri e ricezione della risposta*/
						try {
							lista = serverRMI.visualizza_prenotazioni(gg, mm, aaaa);

							/*Gestione della risposta*/
							System.out.println("Risultati:");
							for(i=0; i<6; i++){
								if(lista[i].compareTo("")!=0){System.out.println(lista[i]);}
							}
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // S

					else System.out.println("Servizio non disponibile");

				System.out.print("Servizio ( eliminazione , visualizzazione ): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}