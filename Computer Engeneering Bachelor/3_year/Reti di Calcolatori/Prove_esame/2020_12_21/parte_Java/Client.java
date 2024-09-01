// pellegrino lorenzo 0000971455
import java.net.*;

import javax.swing.plaf.synth.SynthSpinnerUI;

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

        System.out.print("Immetti servizio ( inserimento , download ): ");
        String servizio=null, id=null, tipo=null, audio=null, partenza=null, arrivo=null, fileName=null;
        int hh, mm, ritardo, risposta;
        long j, numByte;
        File file;
        FileInputStream mp4_in = null;
        FileOutputStream mp4_out = null;
        
        while ((servizio = stdIn.readLine()) != null) {
            if(servizio.compareTo("inserimento")==0){
                /*Lettura parametri*/
                System.out.print("Id: "); id=stdIn.readLine();
                System.out.print("Tipo: "); tipo=stdIn.readLine();
                System.out.print("Partenza: "); partenza=stdIn.readLine();
                System.out.print("Arrivo: "); arrivo=stdIn.readLine();
                System.out.print("Ora: "); hh=Integer.parseInt(stdIn.readLine());
                System.out.print("Min: "); mm=Integer.parseInt(stdIn.readLine()); 
                System.out.print("Ritardo: "); ritardo=Integer.parseInt(stdIn.readLine());
                System.out.print("Audio: "); audio=stdIn.readLine();
                file = new File(audio);
                mp4_in = new FileInputStream(file);

                /*Controllo parametri*/
                if(tipo.compareTo("Partenza")!=0 && tipo.compareTo("Arrivo")!=0){
                    System.out.println("Tipo può essere o Partenza o Arrivo");
                    continue;
                }
                if((hh<0 || hh>23) || (mm<0 || mm>60) || ritardo<0){
                    System.out.println("Interi formattati male: devono essere positivi e h[0-23] e m[0-59]");
                    continue;
                }
                if(!file.isFile() || !file.exists()){
                    System.out.println("Il file non esiste o non è un file");
                    continue;
                }

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeUTF(id);
                outSock.writeUTF(tipo);
                outSock.writeUTF(partenza);
                outSock.writeUTF(arrivo);
                outSock.writeInt(hh);
                outSock.writeInt(mm);
                outSock.writeInt(ritardo);
                outSock.writeUTF(audio);

                /*Ricezione risposta*/
                risposta = inSock.readInt();

                /*Gestione risposta*/
                if(risposta >= 0){
                    System.out.println("Treno aggiunto correttamente alla posizione " + risposta);
                    outSock.writeLong(file.length());
                    for(j=0; j<file.length(); j++){
                        outSock.writeByte(mp4_in.read());
                    }
                    mp4_in.close();
                }
                else{
                    System.out.println("Inserimento fallito");
                }
            }
            else if(servizio.compareTo("download")==0){
                /*Lettura parametri*/
                System.out.print("Ora: ");
                hh=Integer.parseInt(stdIn.readLine());
                System.out.print("Min: ");
                mm=Integer.parseInt(stdIn.readLine());

                /*Controllo parametri*/
                if((hh<0 || hh>23) || (mm<0 || mm>60)){
                    System.out.println("Interi formattati male: devono essere positivi e h[0-23] e m[0-59]");
                    continue;
                }

                /*Invio parametri*/
                outSock.writeUTF(servizio);
                outSock.writeInt(hh);
                outSock.writeInt(mm);

                /*Ricezione risposta*/
                risposta = inSock.readInt();

                /*Gestione risposta*/
                for(int i=0; i<risposta; i++){
                    fileName=inSock.readUTF();
                    numByte=inSock.readLong();
                    mp4_out=new FileOutputStream("client_" + fileName);
                    for(j=0; j<numByte; j++){
                        mp4_out.write(inSock.readByte());
                    }
                    mp4_out.close();
                }
            }
            else{
                System.out.println("Servizio non disponibile");
            }
            System.out.print("Immetti servizio ( inserimento , download ): ");       
        }
    }
}
