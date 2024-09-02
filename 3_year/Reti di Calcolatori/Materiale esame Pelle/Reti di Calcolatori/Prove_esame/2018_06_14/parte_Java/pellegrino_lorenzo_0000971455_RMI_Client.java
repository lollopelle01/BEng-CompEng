import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;

class pellegrino_lorenzo_0000971455_RMI_Client{
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
			String completeName = "//" + registryHost + ":" + registryPort + "/"+ serviceName;
            pellegrino_lorenzo_0000971455_RMI_InterfaceFile serverRMI = (pellegrino_lorenzo_0000971455_RMI_InterfaceFile) Naming.lookup(completeName);
			System.out.println("Client RMI: Servizio \"" + serviceName + "\" connesso");

			System.out.println("\nRichieste di servizio fino a fine file");

			String service;
			System.out.print("Servizio (L=lista_file, N=numerazione_righe): ");

			while ((service = stdIn.readLine()) != null) {

				if (service.equals("N")) {

                    String nomeFile;
                    int result;

					System.out.print("Nome file? ");
					nomeFile = stdIn.readLine();

                    // Invocazione remota
                    try{
                        result = serverRMI.numerazione_righe(nomeFile);
                    }
                    catch(Exception e){
                        result = -1;
                    }
                    
                    if(result == -1){
                        System.out.println("Errore durante la lettura di " + nomeFile);
                    }
                    else{
                        System.out.println("Il file " + nomeFile + " è stato numerato " + result + " volte");
                    }

				} // N

				else
					if (service.equals("L")) {

                        String dirName;
                        String[] fileList;

						System.out.print("Nome direttorio? ");
						dirName = stdIn.readLine();

						try {
							fileList = serverRMI.lista_file(dirName);
                            System.out.println("File trovati: ");
                            for(int i = 0; i<fileList.length; i++){
                                System.out.println(i+1 + ") " + fileList[i]);
                            }
						} catch (RemoteException re) {
							System.out.println("Errore remoto: " + re.toString());
						}
					} // L

					else System.out.println("Servizio non disponibile");

                System.out.print("Servizio (L=lista_file, N=numerazione_righe): ");
			} // !EOF

		} catch (Exception e) {
			System.err.println("ClientRMI: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}