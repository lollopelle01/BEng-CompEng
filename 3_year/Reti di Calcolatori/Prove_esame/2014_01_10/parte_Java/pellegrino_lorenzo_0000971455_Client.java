import java.net.*;
import java.io.*;

public class pellegrino_lorenzo_0000971455_Client {

    public static void main(String[] args) throws IOException {
        InetAddress addr = null;
        int port = -1;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String service = null;
        int dim_min = 0;
        // We set the buffer size for the file transfer as a static member.
        // This is the default value: the user has the option to modify it through a cmd
        // parameter. Note that adding OPTIONAL parameters does not violate the project
        // specification.
        int buffer_size = 4096;

        try {
            if (args.length == 2 || args.length == 3) {
                addr = InetAddress.getByName(args[0]);
                port = Integer.parseInt(args[1]);
                if (port < 1024 || port > 65535) {
                    System.out.println(
                            "Usage: java Client serverAddr serverPort [transferBufferSize]");
                    System.exit(1);
                }
                if (args.length == 3) {
                    buffer_size = Integer.parseInt(args[2]);
                }
            } else {
                System.out.println("Usage: java Client serverAddr serverPort [transferBufferSize]");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Problemi, i seguenti: ");
            e.printStackTrace();
            System.out.println("Usage: java Client serverAddr serverPort [transferBufferSize]");
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

        System.out.print("\nQuale servizio vuoi usare (filtro_tipo_luogo, filtro_disp_prezzo)?: ");
        String arg1, arg2;
        int trovati;
        while ((service = stdIn.readLine()) != null) {
            if(service.compareTo("filtro_tipo_luogo")==0){
                outSock.writeUTF(service);

                System.out.print("Inserisci tipo: ");
                arg1 = stdIn.readLine();
                System.out.print("Inserisci luogo: ");
                arg2 = stdIn.readLine();

                outSock.writeUTF(arg1);
                outSock.writeUTF(arg2);

                trovati = inSock.readInt();

                System.out.println("Ho trovato " + trovati + " risultati");
                if(trovati > 0){
                    for(int i=0; i<trovati; i++){
                        System.out.println( (i+1) + ") " + inSock.readUTF());
                    }
                }
            }
            else if(service.compareTo("filtro_disp_prezzo")==0){
                outSock.writeUTF(service);

                System.out.print("Inserisci prezzo: ");
                arg1 = stdIn.readLine();

                outSock.writeUTF(arg1);

                trovati = inSock.readInt();

                System.out.println("Ho trovato " + trovati + " risultati");
                if(trovati > 0){
                    for(int i=0; i<trovati; i++){
                        System.out.println( (i+1) + ") " + inSock.readUTF());
                    }
                }
            }
            else{
                System.out.println("Servizio inesistente");
            }
            System.out.print("\nQuale servizio vuoi usare (filtro_tipo_luogo, filtro_disp_prezzo)?: ");
        }
    }
}
