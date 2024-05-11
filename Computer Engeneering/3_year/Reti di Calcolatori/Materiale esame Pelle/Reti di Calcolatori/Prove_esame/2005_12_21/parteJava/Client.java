import java.rmi.*;
import java.io.*;

public class Client {

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
			RemOp serverRMI = (RemOp) Naming.lookup(completeName);
			System.out.println("Client RMI: Servizio \"" + serviceName
					+ "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out.print("Servizio (aggiungi_stanza, elimina_utente): ");

			while ((service = stdIn.readLine()) != null) {

                if (service.equals("aggiungi_stanza")) {

                    String stanza, tipoComunicazione;
					System.out.print("Nome stanza? ");
                    stanza = stdIn.readLine();
                    System.out.print("tipo di comunicazione?(M-multicast / P-puntopunto) ");
                    tipoComunicazione = stdIn.readLine();
                    if(tipoComunicazione.compareTo("P")!=0 && tipoComunicazione.compareTo("M")!=0){
                        System.out.println("Tipo di comunicazione non esistente");
                        System.out.print("Servizio (aggiungi_stanza, elimina_utente): ");
                        continue;
                    }

					// Invocazione remota
					try {
                        boolean result = serverRMI.aggiungi_stanza(stanza, tipoComunicazione.charAt(0));
                        if(result){
                            System.out.println("Operazione avvenuta con successo");
                        }
                        else{
                            System.out.println("Operazione fallita");
                        }
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("elimina_utente")) {

						String nomeUtente;
						System.out.print("Nome utente? ");
						nomeUtente = stdIn.readLine();

						try {
							Stanza[] res = serverRMI.elimina_utente(nomeUtente);
							if(res!=null){
                                System.out.println("Risultato ottenuto:");
                                for(int i=0; i<res.length; i++){
                                    res[i].stampa();
                                }
                            }
                            else{
                                System.out.println("Operazione fallita");
                            }
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // S

					else System.out.println("Servizio non disponibile");

                System.out.print("Servizio (aggiungi_stanza, elimina_utente): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
