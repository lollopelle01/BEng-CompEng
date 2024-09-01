// pellegrino lorenzo 0000971455
import java.io.*;
import java.net.*;

class ServerThread extends Thread {
    private Socket clientSocket = null;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
                String servizio, linea, dirName, fileName;
                char carattere;
                int numOccMin, result, buff, esito;
                File dir;
                BufferedReader br;
                FileReader fr;
                long dimByte;

                while ((servizio = inSock.readUTF()) != null) {
                    // algoritmo
                    if(servizio.compareTo("contare")==0){
                        System.out.println(servizio);
                        try{
                            carattere = inSock.readChar();
                            numOccMin = inSock.readInt();
                            System.out.println(carattere +"\n"+numOccMin);
                            dir = new File("."); // direttorio corrente

                            result=0;
                            for(File entry : dir.listFiles()){
                                if(entry.getName().endsWith(".txt")){
                                    br = new BufferedReader(new FileReader(entry));
                                    while((linea=br.readLine())!=null){
                                        if(linea.length() - linea.replaceAll(""+carattere, "").length() >= numOccMin){result++;}
                                    }
                                }
                            }
                        }catch(Exception e){
                            result = -1;
                        }

                        outSock.writeInt(result);
                    }
                    else{ // trasferire
                        dirName = inSock.readUTF();

                        if((dir = new File(dirName)).isDirectory()){esito = 1;}
                        else{esito = -1;}

                        outSock.writeInt(esito);

                        if(esito > 0){
                            for(File entry : dir.listFiles()){
                                if(!entry.getName().endsWith(".txt")){
                                    fr = new FileReader(entry);

                                    fileName = entry.getName();
                                    dimByte = entry.length();

                                    outSock.writeUTF(fileName);
                                    outSock.writeLong(dimByte);

                                    while((buff=fr.read()) > 0){
                                        outSock.writeByte(buff);
                                    }
                                    System.out.println(fileName + " trasferito!");
                                    fr.close();
                                }
                            }
                            outSock.writeUTF("Fine_stream.txt");
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
                    new ServerThread(clientSocket).start();
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
