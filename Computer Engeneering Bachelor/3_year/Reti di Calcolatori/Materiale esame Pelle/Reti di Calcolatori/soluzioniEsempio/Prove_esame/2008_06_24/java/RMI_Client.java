
/* giulianelli nicole 0000976239 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.rmi.*;

class RMI_Client{

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
            String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
            RMI_InterfaceFile serverRMI = (RMI_InterfaceFile) Naming.lookup(completeName);
            System.out.println("ClientRMI: Servizio \"" + serviceName + "\" connesso");

            System.out.println("\nRichieste di servizio fino a fine file");

            String service,nomeFile, parola, dir = null;
            int esito;
            
            System.out.print("Servizio ( C = Conta numero linee, V = Visualizza lista di file che contengono parola in linea): ");

            /* ciclo accettazione richieste utente */
           while ((service = stdIn.readLine()) != null) {

                if (service.equals("C")) {
                    System.out.print("Inserisci il nome del file ");
                    nomeFile = stdIn.readLine();
                    System.out.println("Inserisci la parola da cercare: ");
                    parola = stdIn.readLine();

                    esito = serverRMI.conta_numero_linee(nomeFile, parola);
                   if(esito >= 0){
                       System.out.println("Conteggio della parola: " + parola + " avvenuta con successo " + esito + " volte");
                   }
                   else{
                       System.out.println("Conteggio parola non riuscita");
                   }
               } // E = eliminazione

                else if (service.equals("V")) {
                   String[] pre = null;

                   System.out.print("Inserisci il nome del direttorio: ");
                   dir = stdIn.readLine();
                   
                   System.out.println("Inserisci la parola di interesse: ");
                   parola = stdIn.readLine();

                   String file;
                   pre = serverRMI.lista_nomi_file_contenenti_parola_in_linea(dir, parola);
                    if(pre == null){
                           System.out.println("Non c'è nessuna parola compatibile con i dati inseriti");
                       }else{
                           System.out.println("Stampo i nomi di file compatibili");
                           for(int i = 0; i < pre.length; i++){
                                file = pre[i];
                               System.out.println("-" + file);
                           }
                       }
                   

               } //  V = Visualizzazione

               else
                    System.out.println("Servizio non disponibile");

                    System.out.print("Servizio ( C = Conta numero linee, V = Visualizza lista di file che contengono parola in linea): ");
                } // while (!EOF), fine richieste utente

       } catch (Exception e) {
            System.err.println("ClientRMI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
       }
    }
}