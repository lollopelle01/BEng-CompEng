import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements RemOp {
    private static int N = 10;
    private static Stanza[] tabella = null;

	// Costruttore
	public Server() throws RemoteException {
        super();
	}

	public static void main(String[] args) {
        tabella = new Stanza[N];
        System.out.println("Tabella inizializzata:");
        for(int i=0; i<N; i++){
            tabella[i] = new Stanza();
            if(i==0 || i==1 || i==3){
                tabella[i].Nome = "Sala" + i;
                tabella[i].Stato = "M";
                tabella[i].Utenti[i] = "Pelle";
            }
            else if(i==2 | i==4) {
                tabella[i].Nome = "Sala" + i;
                tabella[i].Stato = "M";
                tabella[i].Utenti[i] = "Nicole";
            }
            tabella[i].stampa();
        }

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: ServerImpl [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) { 
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: ServerImpl [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			Server serverRMI = new Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
    }

    @Override
    public boolean aggiungi_stanza(String nomeStanza, char tipoComunicazione) throws RemoteException {
        System.out.println("Richiesta di creazione: " + nomeStanza + " " + tipoComunicazione);
        int i, primaPosLibera = -1;
        for(i=0; i<N ; i++){
            if(tabella[i].Nome.compareTo(nomeStanza)==0){
                return false;
            }
            if(tabella[i].Nome.compareTo("L")==0 && primaPosLibera == -1){
                primaPosLibera = i;
            }
        }

        if(primaPosLibera == -1) {return false;}
        else{
            tabella[primaPosLibera].Nome = nomeStanza;
            tabella[primaPosLibera].Stato = "" + tipoComunicazione;
            return true;
        }
    }

    @Override
    public Stanza[] elimina_utente(String nomeUtente) throws RemoteException {
        Stanza[] result = null;
        int liberate = 0, i, j;
        for(i=0; i<N; i++){
            if(tabella[i].Nome.compareTo("L")!=0){
                for(j=0; j<tabella[i].Utenti.length; j++){
                    if(tabella[i].Utenti[j].compareTo(nomeUtente)==0){
                        liberate++;
                    }
                }
           }
       }
        
        result = new Stanza[liberate];
        liberate = 0;

        for(i=0; i<N; i++){
           if(tabella[i].Nome.compareTo("L")!=0){
                for(j=0; j<tabella[i].Utenti.length; j++){
                    if(tabella[i].Utenti[j].compareTo(nomeUtente)==0){
                        tabella[i].Utenti[j] = "L";
                        result[liberate] = tabella[i];
                        liberate++;
                    }
                }
            }
        }

        return result;
    }
}
