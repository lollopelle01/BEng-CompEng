// giulianelli nicole 0000976239
#include "RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    
    // Output (*)
    Output * out;
    int * esito;

    // Input
    nome brand;
    nome id;

    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( V = visualizzazione prenotazione post 2011 con dato brand, E = elimina prenotazioni non attuali )?\n");
    while(gets(servizio)){
        
        if(strcmp(servizio, "V")==0){
            /*Lettura parametri*/
            printf("Inserisci il brand su cui iterare: \n");
            gets(brand);

            /*Controllo parametri*/
            if(strcmp(brand, "brand1")!=0 && (strcmp(brand, "brand2")!= 0) && (strcmp(brand, "brand3")!= 0)){
                printf("Inserisci un brand valido!\n");
                printf("Quale servizio vuoi usare ( V = visualizzazione prenotazione post 2011 con dato brand, E = elimina prenotazioni non attuali )?\n");
                continue;
            }

            /*Invio parametri*/
            out=visualizza_prenotazione_1(&brand, cl);
            if (out == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(out->errCode != -1){
                for(int i = 0; i < N; i++){
                    printf("%d) \t%s\n", i, (out->lista[i]));
                }
            }
            
        }
        else if(strcmp(servizio, "E")==0){
            
            /*Lettura parametri*/
            printf("Inserisci l'identificativo della prenotazione da eliminare: \n");
            gets(id);

            /*Controllo parametri*/

            /*Invio parametri*/
            esito=elimina_prenotazioni_1(&id, cl);
            if (esito == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(*(esito) != -1){
                printf("Errore nel server..\n");
            }else{
                printf("Eliminazione della prenotazione andato a buon fine!\n");
            }
        }
        else{
            printf("Argomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( V = visualizzazione prenotazione post 2011 con dato brand, E = elimina prenotazioni non attuali )?\n");
    }
    clnt_destroy(cl);
    exit(0);
}
