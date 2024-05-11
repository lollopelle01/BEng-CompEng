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
                System.out.println("Usage: java Client serverAddr serverPort ");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Problemi, i seguenti: ");
            e.printStackTrace();
            System.out.println("Usage: java Client serverAddr serverPort ");
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
        
        System.out.print("Che servizio vuoi usare? (eliminazione_occorrenze, trasferimento_file) ");

        String service = null, fileName=null, parola=null, dirName=null, buff=null;
        int eliminazioni, fileTrovati, i;
        long soglia, dimensione, byteLetti;
        BufferedWriter bw = null;
        while ((service = stdIn.readLine()) != null) {
            if(service.compareTo("eliminazione_occorrenze")==0){
                System.out.print("Che file vuoi modificare? ");
                fileName = stdIn.readLine();
                if(!fileName.endsWith(".txt")){
                    System.out.println("Solo file di testo!");
                    System.out.print("Che servizio vuoi usare? (eliminazione_occorrenze, trasferimento_file) ");
                    continue;
                }
                System.out.print("Che parola vuoi eliminare? ");
                parola = stdIn.readLine();

                outSock.writeUTF(service);
                outSock.writeUTF(fileName);
                outSock.writeUTF(parola);

                eliminazioni = inSock.readInt();
                System.out.println("Sono state effettuate " + eliminazioni + " eliminazioni");
            }
            else if(service.compareTo("trasferimento_file")==0){
                System.out.print("Che directory vuoi ispezionare? ");
                dirName = stdIn.readLine();
                System.out.print("I file quanto devono essere lunghi almeno (in byte)? ");
                soglia = Long.parseLong(stdIn.readLine());

                outSock.writeUTF(service);
                outSock.writeUTF(dirName);
                outSock.writeLong(soglia);

                fileTrovati = inSock.readInt();

                for(i=0; i<fileTrovati; i++){
                    fileName = inSock.readUTF();
                    dimensione = inSock.readLong();
                    System.out.println("Trasferisco " + fileName + " lungo " + dimensione);
                    bw = new BufferedWriter(new FileWriter(new File(fileName)));
                    byteLetti=0;
                    do{
                        buff = inSock.readUTF();
                        bw.write(buff);
                        byteLetti+=buff.getBytes().length;
                    }while(byteLetti<=dimensione);
                    bw.close();
                }

                System.out.println("Sono stati trasferiti " + fileTrovati + " file");
            }
            else{
                System.out.println("Servizio non disponibile");
            }

            System.out.print("Che servizio vuoi usare? (eliminazione_occorrenze, trasferimento_file) ");
        }

    }
}
