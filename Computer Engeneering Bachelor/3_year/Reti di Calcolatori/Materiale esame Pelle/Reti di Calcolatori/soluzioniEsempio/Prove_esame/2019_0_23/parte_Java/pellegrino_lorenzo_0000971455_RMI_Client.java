import java.rmi.*;
import java.io.*;

class pellegrino_lorenzo_0000971455_RMI_Client {

	public static void main(String[] args) {
		int registryPort = 1099;
		String registryHost = null;
		String serviceName = "Server";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		// Controllo parametri
		if (args.length != 1 && args.length != 2) {
			System.out.println("Sintassi: Client RegistryHost [registryPort]");
			System.exit(1);
		} else {
			registryHost = args[0];
			if (args.length == 2) {
				try {
					registryPort = Integer.parseInt(args[1]);
				} catch (Exception e) {
					System.out
					.println("Sintassi: Client NomeHost [registryPort], registryPort intero");
					System.exit(1);
				}
			}
		}

		// Connessione al servizio RMI remoto
		try {
			String completeName = "//" + registryHost + ":" + registryPort + "/"
					+ serviceName;
            pellegrino_lorenzo_0000971455_RMI_interfaceFile serverRMI = (pellegrino_lorenzo_0000971455_RMI_interfaceFile) Naming.lookup(completeName);
			System.out.println("Client RMI: Servizio \"" + serviceName
					+ "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out.print("Servizio (elimina_prenotazione, visualizza_prenotazioni): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("elimina_prenotazione")) {

                    String targa;
                    int result;

					System.out.print("Targa: ");
                    targa = stdIn.readLine();

					if(targa.length()!=7) {
                        System.out.println("Errore: targa inserita male");
                        System.out.print("Servizio (elimina_prenotazione, visualizza_prenotazioni): ");
                        continue;
                    }
                    
					// Invocazione remota
					try {
                        result = serverRMI.elimina_prenotazione(targa);
                        if(result==0){
                            System.out.println("Eliminazione avvenuta con successo");
                        }
                        else{
                            System.out.println("Eliminazione fallita");
                        }
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // elimina_prenotazione

				else
					if (service.equals("visualizza_prenotazioni")) {

                        String tipo;
                        String[] result = null;

						System.out.print("Tipo: ");
						tipo = stdIn.readLine();

						try {
							result = serverRMI.visualizza_prenotazioni(tipo);
							if(result==null){
                                System.out.println("Visualizzazione fallita");
                                System.out.print("Servizio (elimina_prenotazione, visualizza_prenotazioni): ");
                                continue;
                            }
                            else{
                                System.out.println("Risultati:");
                                for(int i=0; i<result.length; i++){
                                    System.out.println((i+1) + ") " + result[i]);
                                }
                            }
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // visualizza_prenotazioni

					else System.out.println("Servizio non disponibile");

                System.out.print("Servizio (elimina_prenotazione, visualizza_prenotazioni): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}