import java.io.*;
import java.net.*;
import java.util.ArrayList;

class pellegrino_lorenzo_0000971455_Server_Thread extends Thread {
    private String[][] tabella = null;
    private int N = 0;
    private Socket clientSocket = null;
    // Opzionalmente, anche questo potrebbe diventare un parametro (opzionale)!
    private int buffer_size = 4096;

    public pellegrino_lorenzo_0000971455_Server_Thread(Socket clientSocket, int N, String[][] tabella) {
        this.clientSocket = clientSocket;
        this.N = N;
        this.tabella = tabella;
    }

    private void stampa(){
        System.out.println("\nTabella:");
        for(int i=0; i<N; i++){
            System.out.println( (i+1) + ") /-----------------------------------------------------/");
            System.out.println("Descrizione: " + tabella[i][0]);
            System.out.println("Tipo: " + tabella[i][1]);
            System.out.println("Data: " + tabella[i][2]);
            System.out.println("Luogo: " + tabella[i][3]);
            System.out.println("Disponibilità: " + tabella[i][4]);
            System.out.println("Prezzo: " + tabella[i][5]);
        }
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
                String servizio, arg1, arg2;

                System.out.println("Thread: Inizio a leggere il servizio...");
                while ((servizio = inSock.readUTF()) != null) {
                    String[] result = null;
                    int dimL = 0;
                    if(servizio.compareTo("filtro_tipo_luogo")==0){
                        arg1 = inSock.readUTF(); //tipo
                        arg2 = inSock.readUTF(); //luogo
                        for(int i = 0; i<N; i++){
                            if(this.tabella[i][1].compareTo(arg1)==0 && this.tabella[i][3].compareTo(arg2)==0){
                                dimL++;
                            }
                        }

                        System.out.println("Numero risultati: " + dimL);
                        outSock.writeInt(dimL);

                        if(dimL > 0){
                            result = new String[dimL];
                            dimL = 0;
                            for(int i = 0; i<N; i++){
                                if(this.tabella[i][1].compareTo(arg1)==0 && this.tabella[i][3].compareTo(arg2)==0){
                                result[dimL] = this.tabella[i][0];
                                dimL++;
                                }
                            }

                            for(int i=0; i<dimL; i++){
                                outSock.writeUTF(result[i]);
                            }
                        }
                    }
                    else if(servizio.compareTo("filtro_disp_prezzo")==0){
                        arg1 = inSock.readUTF(); //prezzo
                        for(int i = 0; i<N; i++){
                            if( Integer.parseInt(tabella[i][4]) > 0 && 
                                Integer.parseInt(tabella[i][5])<=Integer.parseInt(arg1)){
                                dimL++;
                            }
                        }

                        System.out.println("Numero risultati: " + dimL);
                        outSock.writeInt(dimL);

                        if(dimL > 0){
                            result = new String[dimL];
                            dimL = 0;
                            for(int i=0; i<N; i++){
                                if( Integer.parseInt(tabella[i][4]) > 0 && 
                                    Integer.parseInt(tabella[i][5])<=Integer.parseInt(arg1)){
                                        result[dimL] = this.tabella[i][0];
                                        dimL++;
                                }
                            }

                            for(int i=0; i<dimL; i++){
                                outSock.writeUTF(result[i]);
                            }
                        }    
                    }
                } // while
            } catch (EOFException eof) {
                System.out.println("Raggiunta la fine delle ricezioni, chiudo...");
                clientSocket.close();
                System.out.println("PutFileServer: termino...");
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

public class pellegrino_lorenzo_0000971455_Server {
    private static int N = 10;
    private static String[][] tabella = new String[N][6];

    private static void stampa(){
        System.out.println("\nTabella:");
        for(int i=0; i<N; i++){
            System.out.println( (i+1) + ") /-----------------------------------------------------/");
            System.out.println("Descrizione: " + tabella[i][0]);
            System.out.println("Tipo: " + tabella[i][1]);
            System.out.println("Data: " + tabella[i][2]);
            System.out.println("Luogo: " + tabella[i][3]);
            System.out.println("Disponibilità: " + tabella[i][4]);
            System.out.println("Prezzo: " + tabella[i][5]);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = -1, N = 10;

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

        // creazione tabella e inizializzazione
        for(int i=0; i<N; i++){ // inizializzazione corretta
            for(int j=0; j<6; j++){
                tabella[i][j] = "L";
            }
        }
         
        String[] Tipi = {"Calcio", "Formula1", "Concerto"},
                 Luoghi = { "Bologna", "Bologna", "Firenze", "Milano", "Firenze", "Catania", "Cagliari", 
                            "Rimini", "Rimini", "Rimini"};
        for(int i=0; i<N; i++){ // inizializzazione utile
            for(int j=0; j<6; j++){
                switch(j){
                    case 0: // Descrizione
                        tabella[i][j] = "Id" + (i+1); break;      
                    case 1: // Tipo
                        tabella[i][j] = Tipi[i%3]; break;
                    case 2: // Data
                        tabella[i][j] = "0" + (i+1) + "/01/2023"; break;
                    case 3: // Luogo
                        tabella[i][j] = Luoghi[i]; break;
                    case 4: // Disponibilità
                        tabella[i][j] = "" + (i+1)*100 ; break;
                    case 5: // Prezzo
                        tabella[i][j] = "" + (i+1)*15; break;

                }
            }
        }

        System.out.println("Inizializzazione effettuata"); stampa();

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println("PutFileServerCon: avviato ");
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
                    new pellegrino_lorenzo_0000971455_Server_Thread(clientSocket, N, tabella).start();
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
