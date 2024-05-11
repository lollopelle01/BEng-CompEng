/* giulianelli nicole 0000976239 */

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;

public class RMI_Server extends UnicastRemoteObject implements RMI_InterfaceFile {
    
    private static int N = 4;

	// Costruttore
	public RMI_Server() throws RemoteException {
        super();
	}

	public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: ServerImpl [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) { 
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: ServerImpl [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			RMI_Server serverRMI = new RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
    }

    @Override
    public int conta_numero_linee(String fileName, String parola) throws RemoteException {
        int risultato = 0;
        String nextst = null;
        try {
            BufferedReader f = new BufferedReader(new FileReader(new File(fileName)));
            while((nextst = f.readLine()) != null){
                if(nextst.contains(" " + parola + " ") || nextst.contains(parola + " ") || nextst.contains(" " + parola) || (nextst.toLowerCase().contains(parola) && !nextst.toLowerCase().contains(" "))){
                    risultato++;
                }
            }
            if(risultato == 0){
                risultato = -1;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultato;
    }
    //esempio di contatore di token usando lo spazio come delimitatore
        //StringTokenizer stringa = new StringTokenizer("Un esempio di stringtokenizer"," ");
       //System.out.println("Contatore tokens: " + stringa.countTokens());
        //while (stringa.hasMoreElements()) {
         //   String token = stringa.nextElement().toString();
         //   System.out.println("token = " + token);
        //}

    //separa una stringa usando uno slash come delimitatore
        //stringa = new StringTokenizer("2020/05/11","/");
        //System.out.println("Contatore tokens: " + stringa.countTokens());
        //while (stringa.hasMoreElements()) {
          //  String token = stringa.nextToken();
            //System.out.println("token = " + token);
	//}
    @Override
    public String[] lista_nomi_file_contenenti_parola_in_linea(String direttorio, String parolaa)throws RemoteException {
        String[] lista = null;
        File dir = new File(direttorio);
        int trovati = 0, temp;

        for(File f : dir.listFiles()){
            if((conta_numero_linee(f.getAbsolutePath(), parolaa)) > 0){
                trovati++;
            }
        }

        lista = new String[trovati];
        trovati = 0;

        for(File f : dir.listFiles()){
            if((conta_numero_linee(f.getAbsolutePath(), parolaa)) > 0){
                lista[trovati] = f.getName();
                trovati++; 
            }
        }
        
        
        return lista;
    }
}

