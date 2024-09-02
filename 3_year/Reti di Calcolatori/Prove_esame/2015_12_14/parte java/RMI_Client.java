
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
 
             String service;
             String id, tipo_pre;
             int esito, people;
             System.out.print("Servizio (E=Elimina prenotazione, V=Visualizza prenotazione): ");
 
             /* ciclo accettazione richieste utente */
            while ((service = stdIn.readLine()) != null) {
 
                 if (service.equals("E")) {
                    System.out.print("Inserisci l'identificatore della prenotazione che vuoi eliminare ");
                    id = stdIn.readLine();
                    esito = serverRMI.elimina_prenotazione(id);

                    if(esito >= 0){
                        System.out.println("Eliminazione della prenotazione: " + id + " avvenuta con successo");
                    }
                    else{
                        System.out.println("Eliminazione stanza non riuscita");
                    }
                } // E = eliminazione
 
                 else if (service.equals("V")) {
                    Prenotazioni[] pre = null;

                    System.out.print("Inserisci il numero di persone minimo: ");
                    people = Integer.parseInt(stdIn.readLine());
                    
                    System.out.println("Inserisci il tipo di prenotazione di interesse: ");
                    tipo_pre = stdIn.readLine();

                    //controllo parametri
                    while( people <= 0){
                        people = Integer.parseInt(stdIn.readLine());
                    }
                    while(!tipo_pre.equals("mezza piazzola") && !tipo_pre.equals("piazzola") && !tipo_pre.equals("piazzola deluxe")){
                        tipo_pre = stdIn.readLine();
                    }

                    try{
                        pre = serverRMI.visualizza_prenotazione(people, tipo_pre);
                        if(pre == null){
                            System.out.println("Non c'è nessuna prenotazione compatibile con i dati inseriti");
                        }else{
                            System.out.println("Stampo le prenotazioni compatibili");
                            for(int i = 0; i < pre.length; i++){
                                pre[i].stampa();
                            }
                        }
                    }catch(RemoteException re){
                        System.out.println("Errore remote "+ re);
                    }

                } //  V = Visualizzazione
 
                else
                     System.out.println("Servizio non disponibile");
 
                System.out.print("Servizio (E=Elimina prenotazione, V=Visualizza prenotazione): ");
            } // while (!EOF), fine richieste utente
 
        } catch (Exception e) {
             System.err.println("ClientRMI: " + e.getMessage());
             e.printStackTrace();
             System.exit(1);
        }
     }
 }