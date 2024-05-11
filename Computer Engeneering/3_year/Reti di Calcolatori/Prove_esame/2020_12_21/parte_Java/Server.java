// pellegrino lorenzo 0000971455
import java.io.*;
import java.net.*;

class ServerThread extends Thread {
    private Socket clientSocket = null;
    private Treno[] treni = null;

    public ServerThread(Socket clientSocket, Treno[] treni) {
        this.clientSocket = clientSocket;
        this.treni = treni;
    }

    public synchronized void run() {
        System.out.println("Attivazione figlio: " + Thread.currentThread().getName());

        DataInputStream inSock;
        DataOutputStream outSock;

        try {
            inSock = new DataInputStream(clientSocket.getInputStream());
            outSock = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Problemi nella creazione degli stream di input/output su socket: ");
            ioe.printStackTrace();
            return;
        }
        try {
            try {
               // parametri x algoritmo
               String servizio=null, id=null, tipo=null, audio=null, partenza=null, arrivo=null;
               int hh, mm, ritardo, i, postoLibero, fileTrovati;
               long numByte, j; 
               FileOutputStream mp4_out = null;
               FileInputStream mp4_in = null;
               File file=null;

               System.out.println("Ci sono");

                while ((servizio = inSock.readUTF()) != null) {
                    if(servizio.compareTo("inserimento")==0){

                        /* Ricezione parametri */
                        id=inSock.readUTF();
                        tipo=inSock.readUTF();
                        partenza=inSock.readUTF();
                        arrivo=inSock.readUTF();
                        hh=inSock.readInt();
                        mm=inSock.readInt();
                        ritardo=inSock.readInt();
                        audio=inSock.readUTF();

                        /* Inserimento treno */
                        postoLibero = -1;
                        for(i=0; i<treni.length; i++){
                            if(treni[i].getId().compareTo("L")==0 && postoLibero==-1){postoLibero=i;} //c'è posto libero
                            if(treni[i].getId().compareTo(id)==0){ postoLibero=-2;} //c'è già id
                            System.out.println("ID: " + treni[i].getId());
                        }
                        if(postoLibero >= 0){ //non c'è id e c'è posto
                            treni[postoLibero].setId(id);
                            treni[postoLibero].setTipo(tipo);
                            treni[postoLibero].setPartenza(partenza);
                            treni[postoLibero].setArrivo(arrivo);
                            treni[postoLibero].setHh(hh);
                            treni[postoLibero].setMm(mm);
                            treni[postoLibero].setRitardo(ritardo);
                            treni[postoLibero].setAudio(audio);
                            treni[postoLibero].stampa(); //per debug

                            outSock.writeInt(postoLibero);

                             /* Salvataggio file audio */
                            mp4_out = new FileOutputStream(new File("new_" + audio));
                            numByte = inSock.readLong();

                            for(j=0; j<numByte; j++){
                                mp4_out.write(inSock.readByte());
                            }
                            mp4_out.close();
                        }
                    }
                    else{//download
                        hh=inSock.readInt();
                        mm=inSock.readInt();
                        int distTempo;

                        fileTrovati = 0;
                        for(i=0; i<treni.length; i++){
                            distTempo = ((treni[i].getHh()*60+treni[i].getMm()+treni[i].getRitardo()) - (hh*60+mm));
                            System.out.println(treni[i].getId() + "\t" + distTempo);
                            if(  distTempo<=60 && distTempo>=0 && treni[i].getTipo().compareTo("Arrivo")==0){
                                fileTrovati++;
                            }
                        }

                        outSock.writeInt(fileTrovati);

                        for(i=0; i<treni.length; i++){
                            distTempo = ((treni[i].getHh()*60+treni[i].getMm()+treni[i].getRitardo()) - (hh*60+mm));
                            if( distTempo<=60 && distTempo>=0 && treni[i].getTipo().compareTo("Arrivo")==0){
                                file = new File(treni[i].getAudio());
                                outSock.writeUTF(treni[i].getAudio()); //scrivo nome file

                                outSock.writeLong(file.length()); //scrivo lunghezza file
                                
                                mp4_in = new FileInputStream(file); //scrivo file
                                for(j=0; j<file.length(); j+=4){
                                    outSock.writeInt(mp4_in.read());
                                }
                                mp4_in.close();
                            }
                        }
                    }
                    // algoritmo
                }
            } catch (EOFException eof) {
                System.out.println("Raggiunta la fine delle ricezioni, chiudo...");
                clientSocket.close();
                System.out.println("Server: termino...");
                System.exit(0);
            } catch (SocketTimeoutException ste) {
                System.out.println("Timeout scattato: ");
                ste.printStackTrace();
                clientSocket.close();
                System.exit(1);
            } catch (Exception e) {
                System.out.println("Problemi, i seguenti : ");
                e.printStackTrace();
                System.out.println("Chiudo ed esco...");
                clientSocket.close();
                System.exit(2);
            }
        } catch (IOException ioe) {
            System.out.println("Problemi nella chiusura della socket: ");
            ioe.printStackTrace();
            System.out.println("Chiudo ed esco...");
            System.exit(3);
        }
    }

}// thread

public class Server {

    public static void main(String[] args) throws IOException {
        int port = -1;

        try {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
                // controllo che la porta sia nel range consentito 1024-65535
                if (port < 1024 || port > 65535) {
                    System.out.println("Usage: java Server [serverPort>1024]");
                    System.exit(1);
                }
            } else {
                System.out.println("Usage: java Server port");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Problemi, i seguenti: ");
            e.printStackTrace();
            System.out.println("Usage: java Server port");
            System.exit(1);
        }

        /* Inizializzazione struttura */
        int N = 5, i;
        Treno treni[] = new Treno[N];
        for(i=0; i<N; i++){
            treni[i] = new Treno("L", "L", "L", "L", "L", -1, -1, -1);
        }

        treni[3] = new Treno("id3", "Arrivo", "Bologna", "Milano", "audio2.mp4", 9, 30, 0);

        for(i=0; i<N; i++){
            treni[i].stampa();
        }

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println("Server: avviato ");
            System.out.println("Server: creata la server socket: " + serverSocket);
        } catch (Exception e) {
            System.err.println("Server: problemi nella creazione della server socket: " + e.getMessage());
            e.printStackTrace();
            serverSocket.close();
            System.exit(1);
        }

        try {
            while (true) {
                System.out.println("Server: in attesa di richieste...\n");

                try {
                    clientSocket = serverSocket.accept(); // bloccante!!!
                    System.out.println("Server: connessione accettata: " + clientSocket);
                } catch (Exception e) {
                    System.err.println("Server: problemi nella accettazione della connessione: " + e.getMessage());
                    e.printStackTrace();
                    continue;
                }

                try {
                    new ServerThread(clientSocket, treni).start();
                } catch (Exception e) {
                    System.err.println("Server: problemi nel server thread: " + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            } // while true
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server: termino...");
            System.exit(2);
        }
    }
}
