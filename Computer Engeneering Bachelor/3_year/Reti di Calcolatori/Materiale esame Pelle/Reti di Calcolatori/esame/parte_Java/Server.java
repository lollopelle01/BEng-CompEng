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
               String servizio, fileName, parola, linea, buffParola, buffLinea, prefisso, dirName;
               int numEliminazioni, carattere, paroleTrovate, risposta;
               FileReader fr;
               FileWriter fw;
               char c;
               File fileDest, fileSrc, dir;

                while ((servizio=inSock.readUTF()) != null) { // --> chiusura dolce: clientSocket.shutdownInput(); clientSocket.shutdownOutput();
                    // algoritmo

                    if(servizio.compareTo("eliminazione")==0){

                        /*Lettura parametri*/
                        fileName=inSock.readUTF();
                        parola=inSock.readUTF();
                        clientSocket.shutdownInput(); // non devo più leggere
                        
                        try{
                            fileSrc = new File(fileName);
                            if(!fileSrc.isFile() || !fileName.endsWith(".txt")){throw new Exception("File errato");}
                            fileDest = new File(fileName+"_temp");
                            fr = new FileReader(fileSrc);
                            fw = new FileWriter(fileDest);

                            numEliminazioni=0; buffLinea=""; buffParola=""; paroleTrovate=0;
                            while((carattere=fr.read()) > 0){
                                c = (char) carattere;

                                if(c==' ' || c=='\n'){// fine parola
                                    buffLinea += c;
                                    if(buffParola.compareTo(parola)==0){paroleTrovate++;}
                                    buffParola="";

                                    if(c=='\n'){ // fine riga
                                        if(paroleTrovate > 0){numEliminazioni++;}
                                        else{fw.write(buffLinea);}
                                        paroleTrovate=0;
                                        buffLinea="";
                                    }
                                }
                                else{
                                    buffParola += c;
                                    buffLinea += c;
                                }
                            }
                            // se file finisce senza '\n'
                            if(paroleTrovate > 0){numEliminazioni++;}
                            else{fw.write(buffLinea);}

                            fr.close();
                            fr.read();

                            fileSrc.delete();
                            fileDest.renameTo(fileSrc);

                        }catch(Exception e){numEliminazioni = -1;}
        
                        /*Invio parametri*/
                        outSock.writeInt(numEliminazioni);
                        clientSocket.shutdownOutput(); // non devo più scrivere

                    }
                    else { // trasferimento

                        /*Lettura parametri*/
                        dirName = inSock.readUTF();
                        prefisso = inSock.readUTF();
                        clientSocket.shutdownInput();
        
                        try{
                            dir = new File(dirName);
                            if(!dir.isDirectory()){throw new Exception("Direttorio errato");}
                            if(dir.listFiles().length==0){throw new Exception("Direttorio vuoto");}

                            for(File entry : dir.listFiles()){
                                if(entry.getName().endsWith(".txt") && entry.getName().startsWith(prefisso)){
                                    outSock.writeUTF(entry.getName());
                                    outSock.writeLong(entry.length());
                                    fr = new FileReader(entry);

                                    while((carattere=fr.read()) > 0){
                                        outSock.writeChar(carattere);
                                    }
                                    fr.close();
                                }
                            }
                            outSock.writeUTF("fine");
                        }catch(Exception e){risposta=-1; outSock.writeInt(risposta);}
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
