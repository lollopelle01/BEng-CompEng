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

        System.out.print("Immetti servizii: contare , trasferire: ");

        String service, direttorio, fileName;
        char carattere;
        int numOcc, numLineOcc, esito;
        long dimFile, byteLetti;
        FileWriter fw;


        while (( service  = stdIn.readLine()) != null) {

            if(service.compareTo("contare")==0){
                /*Lettura parametri*/
                System.out.print("Carattere: "); carattere = stdIn.readLine().charAt(0);
                System.out.print("Occorrenze: "); numOcc = Integer.parseInt(stdIn.readLine());

                /*Controllo parametri*/
                if(numOcc <= 0){
                    System.out.println("Occorrenze > 0 !!");
                    System.out.print("Immetti servizii: contare , trasferire: ");
                    continue;
                }

                /*Invio parametri*/
                outSock.writeUTF(service);
                outSock.writeChar(carattere);
                outSock.writeInt(numOcc);

                /*Ricezione risposta*/
                numLineOcc = inSock.readInt();

                /*Gestione risposta*/
                if(numLineOcc < 0){System.out.println("Errore elaborazione");}
                else{System.out.println("Ho trovato " + numLineOcc + " occorrenze");}

            }
            else if(service.compareTo("trasferire")==0){
                /*Lettura parametri*/
                System.out.print("Direttorio: "); direttorio = stdIn.readLine();

                /*Controllo parametri*/

                /*Invio parametri*/
                outSock.writeUTF(service);
                outSock.writeUTF(direttorio);

                /*Ricezione risposta*/
                esito = inSock.readInt();
                
                /*Gestione risposta*/
                if(esito < 0){System.out.println("Direttorio non esistente");}
                else{
                    while((fileName=inSock.readUTF()).compareTo("Fine_stream.txt")!=0){
                        dimFile = inSock.readLong(); 
                        fw = new FileWriter(new File(fileName));
                        byteLetti=0;
                        System.out.println("Trasferisco " + fileName + "\t" + dimFile);
                        while(byteLetti<dimFile){
                            fw.write(inSock.readByte());
                            byteLetti++;
                        }
                        fw.close();
                    }
                    System.out.println("Download completato");
                }
            }
            else{
                System.out.println("Servizio non disponibile");
            }

            System.out.print("Immetti servizii: contare , trasferire: ");        
        }
        inSock.close();
        outSock.close();
    }
}
