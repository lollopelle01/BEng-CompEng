// pellegrino lorenzo 0000971455
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {
        InetAddress addr = null;
        int port = -1;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            if (args.length == 2) {
                addr = InetAddress.getByName(args[0]);
                port = Integer.parseInt(args[1]);
                if (port < 1024 || port > 65535) {
                    System.out.println(
                            "Usage: java Client serverAddr serverPort ");
                    System.exit(1);
                }
            } else {
                System.out.println("Usage: java Client serverAddr serverPort");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Problemi, i seguenti: ");
            e.printStackTrace();
            System.out.println("Usage: java Client serverAddr serverPort");
            System.exit(2);
        }

        // Variables for sockets
        Socket socket = null;
        DataInputStream inSock = null;
        DataOutputStream outSock = null;

        try {
            socket = new Socket(addr, port);
            socket.setSoTimeout(30000);
            System.out.println("Creata la socket: " + socket);
            inSock = new DataInputStream(socket.getInputStream());
            outSock = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Problemi nella creazione degli stream su socket: ");
            ioe.printStackTrace();
            System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, solo invio per continuare: ");
            System.exit(1);
        }

        System.out.print("Immetti aggiorna : download ");

        String servizio, id, codice, fileName;
        int esito;
        long dimFile, byteLetti;
        File immagine;
        FileOutputStream fos;

        while (( servizio=stdIn.readLine()) != null) {

            if(servizio.compareTo("aggiorna")==0){
                /*Lettura parametri*/
                System.out.print("Id: "); id=stdIn.readLine();
                System.out.print("Codice: "); codice=stdIn.readLine();

                /*Controllo parametri*/
                if(codice.length()!=5){
                    System.out.println("Il codice deve essere di 5 cifre");
                    System.out.print("Immetti aggiorna : download ");
                    continue;
                }
                try{
                    esito=Integer.parseInt(codice);
                }catch(Exception e){
                    System.out.println("Il codice deve essere numerico");
                    System.out.print("Immetti aggiorna : download ");
                    continue;
                }

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeUTF(id);
                outSock.writeUTF(codice);

                /*Ricezione risposta*/
                esito=inSock.readInt();

                /*Gestione risposta*/
                if(esito==-1){System.out.println("Aggiornamento fallito");}
                else{System.out.println("Aggiornamento avvenuto con successo");}
            }
            else if(servizio.compareTo("download")==0){
                /*Lettura parametri*/
                System.out.print("Id: "); id=stdIn.readLine();

                /*Controllo parametri*/

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeUTF(id);

                /*Ricezione risposta*/
                esito=inSock.readInt();

                /*Gestione risposta*/
                if(esito==-1){System.out.println("Id inesistente");}
                else{
                    while((fileName=inSock.readUTF()).compareTo("Fine_stream_foto.txt")!=0){
                        dimFile = inSock.readLong();
                        System.out.print("Scarico: " + fileName + "\t" + dimFile + " ...");
                        immagine = new File(fileName);
                        fos = new FileOutputStream(immagine);

                        byteLetti=0;
                        while(byteLetti < dimFile){
                            fos.write(inSock.readByte());
                            byteLetti++;
                        }
                        fos.close();
                        System.out.println(" ... scaricato");
                    }
                    System.out.println("Download completato!");
                }
            }
            else{System.out.println("Servizio non disponibili");}

            System.out.print("Immetti aggiorna : download ");        
        }
    }
}
