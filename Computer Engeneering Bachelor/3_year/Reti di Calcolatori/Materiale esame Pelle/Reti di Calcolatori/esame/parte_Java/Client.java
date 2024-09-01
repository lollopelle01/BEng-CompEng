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
        String servizio;

        System.out.print("Immetti: -- , --");
        while ((servizio=stdIn.readLine()) != null) { // --> chiusura dolce: socket.shutdownInput(); socket.shutdownOutput();

            if(servizio.compareTo("--")==0){
                /*Lettura parametri*/

                /*Controllo parametri*/

                /*Invio parametri*/
                outSock.writeUTF(servizio);

                /*Ricezione risposta*/

                /*Gestione risposta*/
            }
            else if(servizio.compareTo("--")==0){
                /*Lettura parametri*/

                /*Controllo parametri*/

                /*Invio parametri*/
                outSock.writeUTF(servizio);

                /*Ricezione risposta*/

                /*Gestione risposta*/
            }
            else{

            }

            System.out.print("Immetti: -- , --");        
        }
    }
}
