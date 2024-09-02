// pellegrino lorenzo 0000971455
import java.io.*;
import java.net.*;

class ServerThread extends Thread {
    private Socket clientSocket = null;
    // Opzionalmente, anche questo potrebbe diventare un parametro (opzionale)!
    private int buffer_size = 4096;

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
                // Per algoritmo
                String service=null, fileName=null, parola=null, buff=null, dirName=null;
                File fileOld, fileNew, dir;
                int carattere, eliminazioni=0, fileTrovati;
                long soglia, dimensione; // per poter esprimere più di 2GB
                char c;
                FileReader fr=null;
                FileWriter fw=null;
                BufferedReader br=null;

                while((service = inSock.readUTF())!=null){
                    if(service.compareTo("eliminazione_occorrenze")==0){
                        try{
                            fileName = inSock.readUTF();
                            parola = inSock.readUTF();
                            System.out.println("Richiesta eliminazione di " + parola + " da " + fileName);

                            fileOld = new File(fileName);
                            fileNew = new File(fileName + "_temp");
                            fr = new FileReader(fileOld);
                            fw = new FileWriter(fileNew);
    
                            buff="";
                            while((carattere=fr.read())>0){
                                c = (char) carattere;
                                if(c=='\n' || c==' '){
                                    if(buff.compareTo(parola)!=0) {fw.write(buff+c);}
                                    else{
                                        eliminazioni++;
                                        fw.write(""+c);
                                    }
                                    buff="";
                                }
                                else {
                                    buff += c;
                                }
                            }
    
                            fw.close();
                            fr.close();
    
                            fileOld.delete();
                            fileNew.renameTo(fileOld);
                        }catch(Exception e){
                            eliminazioni = -1;
                        }

                        outSock.writeInt(eliminazioni);
                    }
                    else{ //trasferimento_file
                        dirName = inSock.readUTF();
                        soglia = inSock.readLong();
                        System.out.println("Richiesto trasferimento di file lunghi almeno " + soglia + " bytes da " + dirName);
                        dir = new File(dirName);

                        fileTrovati=0;
                        for(File file : dir.listFiles()){
                            if((dimensione=file.length())>=soglia && file.getName().endsWith(".txt")){
                                fileTrovati++;
                            }
                        }
                        outSock.writeInt(fileTrovati);

                        dir = new File(dirName);
                        for(File file : dir.listFiles()){
                            if((dimensione=file.length())>=soglia && file.getName().endsWith(".txt")){
                                outSock.writeUTF(file.getName());
                                outSock.writeLong(dimensione);
                                br = new BufferedReader(new FileReader(file));
                                System.out.println("Trasferisco " + file.getName() + " lungo " + dimensione);
                                while((buff=br.readLine())!=null){
                                    outSock.writeUTF(buff + "\n");
                                }
                            }
                        }
                    }
                }
                
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
