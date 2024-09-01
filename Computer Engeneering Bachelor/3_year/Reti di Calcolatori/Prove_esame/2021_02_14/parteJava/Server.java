// pellegrino lorenzo 0000971455
import java.io.*;
import java.net.*;

class ServerThread extends Thread {
    private Socket clientSocket = null;
    private String[][] tabella = null;
    private int N;

    public ServerThread(Socket clientSocket, String[][] tabella, int N) {
        this.clientSocket = clientSocket;
        this.tabella = tabella;
        this.N = N;
    }

    public void run() {
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
                String service, id, codice, fileName, folder, dir;
                int i, esito, buff;
                long dimFile, byteLetti;
                FileInputStream fis;
                File immagine ,cartella=null;

                while ((service=inSock.readUTF()) != null) {
                    // algoritmo

                    if(service.compareTo("aggiorna")==0){
                        id=inSock.readUTF();
                        codice=inSock.readUTF();

                        esito = -1;
                        for(i=0; i<N && esito!=0; i++){
                            if(tabella[i][0].compareTo(id)==0){
                                tabella[i][1] = codice;
                                esito = 0;
                            }
                        }

                        outSock.writeInt(esito);
                    }
                    else{ //download
                        id=inSock.readUTF();

                        esito = -1;
                        for(i=0; i<N && esito!=0; i++){
                            if(tabella[i][0].compareTo(id)==0){esito = 0;}
                            cartella = new File(tabella[i][3]);
                            if(!cartella.isDirectory()){esito=-1;}
                        }

                        outSock.writeInt(esito);

                        if(esito==0){
                            for(File img : cartella.listFiles()){
                                System.out.println(img.getName() +"\t" + img.length());
                                outSock.writeUTF(img.getName());
                                outSock.writeLong(img.length());
                                fis= new FileInputStream(img);
                                while((buff=fis.read())>=0){
                                    outSock.writeByte(buff);
                                }
                                fis.close();
                            }
                            outSock.writeUTF("Fine_stream_foto.txt");
                        }
                    }
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

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        int N = 7, i;
        String[][] tabella= new String[N][4];

        // prima inizializzazione --> da fare
        for(i=0; i<N; i++){
            tabella[i][0]="L"; // 0 --> id
            tabella[i][1]="L"; // 1 --> codice
            tabella[i][2]="L"; // 2 --> brand
            tabella[i][3]="L"; // 3 --> folder
        }

        // seconda inizializzazione --> utile
        i=1;
        tabella[i][0]="AN745NL";
        tabella[i][1]="000003";
        tabella[i][2]="brand1";
        tabella[i][3]="AN745NL_img";

        i=3;
        tabella[i][0]="FE457GF";
        tabella[i][1]="L";
        tabella[i][2]="brand2";
        tabella[i][3]="FE457GF_img";

        i=5;
        tabella[i][0]="NU547PL";
        tabella[i][1]="40063";
        tabella[i][2]="brand1";
        tabella[i][3]="NU547PL_img";

        System.out.println("Tabella inizializzata:");
        for(i=0; i<N; i++){
            System.out.println(tabella[i][0]);
            System.out.println(tabella[i][1]);
            System.out.println(tabella[i][2]);
            System.out.println(tabella[i][3]);
            System.out.println("//------------------------------------------------------------------------------------------------------//");
        }
        System.out.println("\n\n");


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
                    new ServerThread(clientSocket, tabella, N).start();
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
