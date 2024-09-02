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

			String service, fileName, dirName;
			int risposta;
			String[] lista;
			System.out.print("Servizio ( elimina_occorrenze , lista_filetesto ): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("elimina_occorrenze")) {

					/*Lettura parametri*/
					System.out.print("Nome del file: "); fileName = stdIn.readLine();

					/*Invio dei parametri e ricezione della risposta*/
					try {
						risposta = serverRMI.elimina_occorrenze(fileName);

						/*Gestione della risposta*/
						if(risposta < 0){System.out.println("Errore nell'eliminazione");}
						else{System.out.println("Ho effettuato " + risposta + " eliminazioni");}
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("lista_filetesto")) {

						/*Lettura parametri*/
						System.out.print("Nome del direttorio: "); dirName = stdIn.readLine();

						/*Invio dei parametri e ricezione della risposta*/
						try {
							lista = serverRMI.lista_filetesto(dirName);

							/*Gestione della risposta*/
							System.out.println("Risultato:");
							for(int i=0; i<6; i++){
								if(lista[i]==null){System.out.println("");}
								else{System.out.println(lista[i]);}
							}
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}

					} // S

					else System.out.println("Servizio non disponibile");

				System.out.print("Servizio ( elimina_occorrenze , lista_filetesto ): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}