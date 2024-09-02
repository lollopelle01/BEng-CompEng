/* giulianelli nicole 0000976239 */

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_InterfaceFile {
    
    private static int N = 4;
    private static Prenotazioni[] tabella = null;

	// Costruttore
	public RMI_Server() throws RemoteException {
        super();
	}

	public static void main(String[] args) {
        tabella = new Prenotazioni[N];
        System.out.println("Tabella inizializzata:");
        for(int i=0; i<N; i++){
            tabella[i] = new Prenotazioni();
        }
            tabella[0].identificatore ="HGFD89";
            tabella[0].persone = "4";
            tabella[0].tipo_prenotazione = "mezza piazzola";
            tabella[0].tipo_veicolo ="auto";
            tabella[0].targa_veicolo="AA567AA";
            tabella[0].immagine="piazz_deluxe.jpg "; 
            tabella[0].stampa();     
           
            tabella[1].identificatore ="GTPD76";
            tabella[1].persone = "2";
            tabella[1].tipo_prenotazione = "piazzola deluxe";
            tabella[1].tipo_veicolo ="camper";
            tabella[1].targa_veicolo="WE258RT";
            tabella[1].immagine="piazz2_deluxe.jpg"; 
            tabella[1].stampa();
        
            tabella[2].identificatore ="THKI34";
            tabella[2].persone = "1";
            tabella[2].tipo_prenotazione = "piazzola";
            tabella[2].tipo_veicolo ="camper";
            tabella[2].targa_veicolo="RF567GT";
            tabella[2].immagine="piazz.jpg";
            tabella[2].stampa();

            tabella[3].identificatore ="RFCS36";
            tabella[3].persone = "3";
            tabella[3].tipo_prenotazione = "mezza piazzola";
            tabella[3].tipo_veicolo ="niente";
            tabella[3].targa_veicolo="L";
            tabella[3].immagine="mezz_piazz.jpg";
            tabella[3].stampa();

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
			RMI_Server serverRMI = new RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
    }

    @Override
    public int elimina_prenotazione(String id) throws RemoteException {
        System.out.println("Richiesta di eliminazione di prenotazione: " + id);
        int result = -1;
        for(int i = 0; i < N; i++){
            if(tabella[i].identificatore.equals(id)){
                result = i;
                break;
            }
            if(result >= 0){
                tabella[i].libera();
            }
        }
        return result;
    }

    @Override
    public Prenotazioni[] visualizza_prenotazione(int num_persone, String tipo) throws RemoteException {
        Prenotazioni[] result = null;
        int cont = 0;
        int numero;

        for(int i=0; i<N; i++){
            numero = Integer.parseInt(tabella[i].persone);
            if(tabella[i].tipo_prenotazione.equals(tipo) && numero >= num_persone){
                cont++;
            }
        }
        result = new Prenotazioni[cont];
        cont = 0;

        for(int i=0; i<N; i++){
            numero = Integer.parseInt(tabella[i].persone);
            if(tabella[i].tipo_prenotazione.equals(tipo) && numero >= num_persone){
                result[cont] = tabella[i];
                cont++;
            }
        }
        return result;
    }
}
