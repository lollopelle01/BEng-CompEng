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
			System.out.print("Servizio ( nomi_file_con_parola , num_righe_con_parola ): ");

			String fileName = null, parola = null;
			String[] listaNomi = null;
			int numRighe;

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("nomi_file_con_parola")) {

					/*Lettura parametri*/
					System.out.println("Quale directory vuoi ispezionare?");
					fileName = stdIn.readLine();
					System.out.println("Che parola vuoi considerare?");
					parola = stdIn.readLine();
					
					/*Invio dei parametri e ricezione della risposta*/
					try {
						listaNomi = serverRMI.lista_nomi_file_contenenti_parola_in_linea(fileName, parola);

						/*Gestione della risposta*/
						if(listaNomi == null){System.out.println("Errore nel trovare i nomi");}
						else{
							for(int i=0; i<listaNomi.length; i++){
								System.out.println((i+1) + ") " + listaNomi[i]);
							}
						}
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("num_righe_con_parola")) {

						/*Lettura parametri*/
						System.out.println("Quale file vuoi ispezionare?");
						fileName = stdIn.readLine();
						System.out.println("Che parola vuoi considerare?");
						parola = stdIn.readLine();
					
					/*Invio dei parametri e ricezione della risposta*/
					try {
						numRighe = serverRMI.conta_numero_linee(fileName, parola);

						/*Gestione della risposta*/
						if(numRighe < 0){System.out.println("Errore nel trovare il file o nome non presente");}
						else{System.out.println("Ho trovato " + numRighe + " righe con ' " + parola + " ' nel file " + fileName );}
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}
					} // S

					else System.out.println("Servizio non disponibile");

					System.out.print("Servizio ( nomi_file_con_parola , num_righe_con_parola ): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}