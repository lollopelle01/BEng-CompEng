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

        // per algoritmo
        String servizio, fileName, parola, dirName, prefisso;
        int risposta, len, i;
        long dimFile, charLetti;
        FileWriter fw;

        System.out.print("Immetti: eliminazione , trasferimento");
        while ((servizio=stdIn.readLine()) != null) { // --> chiusura dolce: socket.shutdownInput(); socket.shutdownOutput();

            if(servizio.compareTo("eliminazione")==0){
                /*Lettura parametri*/
                System.out.print("Nome file: "); fileName = stdIn.readLine();
                System.out.print("Parola: "); parola = stdIn.readLine(); 

                /*Controllo parametri*/

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeUTF(fileName);
                outSock.writeUTF(parola);
                socket.shutdownOutput(); // --

                /*Ricezione risposta*/
                risposta = inSock.readInt();
                socket.shutdownInput(); // --

                /*Gestione risposta*/
            }
            else if(servizio.compareTo("trasferimento")==0){
                /*Lettura parametri*/
                System.out.print("Nome direttorio: "); dirName = stdIn.readLine();
                System.out.print("Prefisso: "); prefisso = stdIn.readLine(); 

                /*Controllo parametri*/
                if((len=prefisso.length())==0 || len>4){
                    System.out.println("Prefisso errato");
                    System.out.print("Immetti: eliminazione , trasferimento");
                    continue;
                }
                for(i=1; i<=len; i++){
                    if( !(prefisso.charAt(i-1)>='a' && prefisso.charAt(i-1)<='z') || !(prefisso.charAt(i-1)>='A' && prefisso.charAt(i-1)<='Z') ){break;}
                }
                if(i!=len){ // sono uscito prima --> un carattere non va bene 
                    System.out.println("Un carattere nel prefisso non è [a-z] o [A-Z]");
                    System.out.print("Immetti: eliminazione , trasferimento");
                    continue;
                }

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeUTF(dirName);
                outSock.writeUTF(prefisso);
                socket.shutdownOutput(); // --

                /*Ricezione risposta*/
                risposta = inSock.readInt();

                /*Gestione risposta*/
                if(risposta<0){System.out.println("Direttorio non trovato o vuoto");}
                else{

                    while((fileName=inSock.readUTF()).compareTo("fine")!=0){
                        dimFile = inSock.readLong();
                        charLetti = 0;
                        fw = new FileWriter(fileName);

                        while(charLetti < dimFile){
                            fw.write(inSock.readChar());
                            charLetti++;
                        }
                        fw.close();
                    }
                    System.out.println("Download completato");;
                }
            }
            else{
                System.out.println("Servizio inesistente");
            }

            System.out.print("Immetti: eliminazione , trasferimento");        
        }
    }
}
