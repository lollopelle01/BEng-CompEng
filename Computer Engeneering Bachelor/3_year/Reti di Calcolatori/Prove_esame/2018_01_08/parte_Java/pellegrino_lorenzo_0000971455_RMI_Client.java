import java.rmi.*;
import java.io.*;

public class pellegrino_lorenzo_0000971455_RMI_Client{
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
            pellegrino_lorenzo_0000971455_RMI_interfaceFile serverRMI = (pellegrino_lorenzo_0000971455_RMI_interfaceFile) Naming.lookup(completeName);
			System.out.println("Client RMI: Servizio \"" + serviceName
					+ "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out.print("Cosa vuoi visualizzare? (L=lista, I=intero): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("L")) {
                    
                    System.out.println("Lista delle città:");
					// Invocazione remota
					try {
						String[] result  = serverRMI.visualizza_lista();
						for(int i=0; i<result.length; i++){
                            System.out.println( (i+1) + ") " + result[i]);
                        }
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // L

				else if (service.equals("I")) {

						String citta, via;
                        System.out.println("Che città vuoi considerare?");
                        citta = stdIn.readLine();
                        System.out.println("Che via vuoi considerare?");
                        via = stdIn.readLine();

						int res = -1;
						try {
							res = serverRMI.visualizza_numero(citta, via);
						} catch (RemoteException re) {
                            System.out.println("Errore remoto: " + re.toString());
                            res = -1;
                        }
                        
                        switch(res){
                            case -1 : System.out.println("(citta, via) non presenti oppure errore in lettura"); break;
                            default : System.out.println("In ("+citta+", "+via+") ci sono "+res+" calze");break;
                        }
					} // I

					else System.out.println("Servizio non disponibile");

                System.out.print("Cosa vuoi visualizzare? (L=lista, I=intero): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}