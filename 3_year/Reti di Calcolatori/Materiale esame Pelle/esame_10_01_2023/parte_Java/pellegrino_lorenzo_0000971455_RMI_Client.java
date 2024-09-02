// pellegrino lorenzo 0000971455 prova2
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
			System.out.println("Sintassi: Cient RegistryHost [registryPort]");
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
			System.out.print("Servizio (elimina_occorrenze, lista_filetesto): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("elimina_occorrenze")) {

					String nomeFile;
					System.out.print("Nome file? ");
					nomeFile = stdIn.readLine();

					// Invocazione remota
					try {
						int numOccElim = serverRMI.elimina_occorrenze(nomeFile);
						if(numOccElim ==-1){
                            System.out.println("Operazione fallita");
                        }
                        else{
                            System.out.println("Operazione avvenuta correttamente, numero di occorrenze eliminate: " + numOccElim);
                        }
					} catch (RemoteException re) {
						System.out.println("Errore remoto: " + re.toString());
					}

				} // C

				else
					if (service.equals("lista_filetesto")) {

						String dirName;
						System.out.print("Nome directory? ");
						dirName = stdIn.readLine();

						try {
							String[] lista = serverRMI.lista_filetesto(dirName);
                            if(lista==null){
                                System.out.println("Operazione fallita");
                            }
                            else{
                                System.out.println("Operazione avvenuta con successo\nRisultato:");
                                for(int i=0; i<lista.length; i++){
                                    System.out.println((i+1) + ") " + lista[i]);
                                }
                            }
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // S

					else System.out.println("Servizio non disponibile");

                System.out.print("Servizio (elimina_occorrenze, lista_filetesto): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
