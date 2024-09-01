import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class pellegrino_lorenzo_0000971455_RMI_Server extends UnicastRemoteObject implements pellegrino_lorenzo_0000971455_RMI_interfaceFile {
    private int N;
    private String[][] tabella;

    private void stampa(){
        for(int i = 0; i<N; i++){
            System.out.println((i+1) + ") ------------------------------------------//");
            System.out.println("Targa: " + tabella[i][0]);
            System.out.println("Patente: " + tabella[i][1]);
            System.out.println("Tipo: " + tabella[i][2]);
            System.out.println("Folder: " + tabella[i][3]);
        }
    }

	// Costruttore
	public pellegrino_lorenzo_0000971455_RMI_Server() throws RemoteException {
        super();
        this.N=5;
        this.tabella = new String[N][4];
        
        // inizializzazione tabella
        for(int i=0; i<N; i++){
            tabella[i][0]="L";
            tabella[i][1]="0";
            tabella[i][2]="L";
            tabella[i][3]="L";
        }

        // inizializzazione utile
        String[]    targhe = {"AN745NL", "FE457GF", "NU547PL", "LR897AH", "MD506DW"},
                    tipi = {"auto", "camper"};
        for(int i=0; i<N; i++){
            tabella[i][0] = targhe[i];
            tabella[i][1] = "" + 99999*i/N ;
            tabella[i][2] = tipi[i%2];
            tabella[i][3]="L";
        }

        System.out.println("Tabella inizializzata");
        stampa();
    }
    
    @Override
    public int elimina_prenotazione(String numTarga) throws RemoteException {
        int result = -1;
        for(int i=0; i<N && result==-1; i++){
            if(numTarga.compareTo(this.tabella[i][0])==0){
                tabella[i][0]="L";
                tabella[i][1]="0";
                tabella[i][2]="L";
                tabella[i][3]="L";

                result=0;
            }
        }
        return result;
    }

    @Override
    public String[] visualizza_prenotazioni(String tipoVeicolo) throws RemoteException {
        String[] result = null;
        int trovati = 0;
        for(int i=0; i<N; i++){
            if(tipoVeicolo.compareTo(this.tabella[i][2])==0 && this.tabella[i][0].compareTo("ED000AA") > 0){
                trovati++;
            }
        }

        result = new String[trovati];
        trovati = 0;

        for(int i=0; i<N; i++){
            if(tipoVeicolo.compareTo(this.tabella[i][2])==0 && this.tabella[i][0].compareTo("ED000AA") > 0){
                result[trovati] = this.tabella[i][0];
                trovati++;
            }
        }

        return result;
    }

	public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: Server [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) {
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: Server [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			pellegrino_lorenzo_0000971455_RMI_Server serverRMI = new pellegrino_lorenzo_0000971455_RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}