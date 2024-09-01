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
			System.out.print("Servizio ( -- , -- ): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("--")) {

					/*Lettura parametri*/

					/*Invio dei parametri e ricezione della risposta*/
					try {
						risposta = serverRMI.nomeMetodo();

						/*Gestione della risposta*/
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("--")) {

						/*Lettura parametri*/

						/*Invio dei parametri e ricezione della risposta*/
						try {
							risposta = serverRMI.nomeMetodo();

							/*Gestione della risposta*/
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // S

					else System.out.println("Servizio non disponibile");

				System.out.print("Servizio ( -- , -- ): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}