// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    
    Output * out;

    Input in;
    char tipo[20];
    int*result;


    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( V , C )?\n");
    while(gets(servizio)){
        
        if(strcmp(servizio, "V")==0){
            /*Lettura parametri*/
            printf("Dammi il tipo: \n");
            gets(tipo);

            /*Controllo parametri*/
            if(strcmp(tipo, "arrivo") != 0 && strcmp(tipo, "partenza")!= 0){
                printf("Inserisci parametri giusti\n");
                printf("Quale servizio vuoi usare ( V , C )?\n");
                continue;
            }
            /*Invio parametri*/
            out=visualizza_lista_1(tipo, cl);
            if (out == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }else{
                for(int i = 0; i < out->dimL; i++){
                    printf("%s\n", out->corse[i]);
                }
            }

            
            
        }
        else if(strcmp(servizio, "C")==0){
            
            /*Lettura parametri*/
            printf("Dammi l'id del treno: \n");
            gets(in.id);
            printf("Dammi il nuovo ritardo da settare: \n");
            scanf("%d", &in.newRitardo);
            /*Controllo parametri*/

            /*Invio parametri*/
            result= esprimi_voto_1(&in, cl);
            if (*(result) == -1) {
                clnt_perror(cl, argv[1]);
                exit(1);
                printf("Errore nel cambio ritardo\n");
            }else{
                printf("Cambio andato bene!!\n");
            }

            /*Gestione del risultato*/
        }
        else{
            printf("Srgomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( V , C )?\n");
    }
    clnt_destroy(cl);
    exit(0);
}
