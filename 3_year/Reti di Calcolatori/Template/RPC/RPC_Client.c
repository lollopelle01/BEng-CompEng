// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    
    // Output (*)

    // Input

    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( -- , -- )?\n");
    while(gets(servizio)){
        
        if(strcmp(servizio, "--")==0){
            /*Lettura parametri*/

            /*Controllo parametri*/

            /*Invio parametri*/
            risposta=nomeMetodo_1();
            if (risposta == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            
        }
        else if(strcmp(servizio, "--")==0){
            
            /*Lettura parametri*/

            /*Controllo parametri*/

            /*Invio parametri*/
            risposta=nomeMetodo_1();
            if (risposta == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
        }
        else{
            printf("Srgomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( -- , -- )?\n");
    }
    clnt_destroy(cl);
    exit(0);
}
