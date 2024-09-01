// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"
#include "RPC_xFile.h"
#include <rpc/rpc.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <signal.h>
//#include <sys/ttycom.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <syslog.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/errno.h>
#include <rpc/pmap_clnt.h>
#include <dirent.h>
#include <sys/stat.h>
#include <string.h>

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    int i;
    
    // Output (*)
    Output* out;
    int* risposta;

    // Input
    nome id_monopattino;
    nome brand_monopattino;

    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( elimina_monopattino , visualizza_prenotazioni )?\n");
    while(gets(servizio)){
        
        if(strcmp(servizio, "elimina_monopattino")==0){
            /*Lettura parametri*/
            printf("Id monopattino: "); gets(id_monopattino);

            /*Controllo parametri*/

            /*Invio parametri*/
            risposta=elimina_monopattino_1(id_monopattino, cl);
            if (risposta == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            switch(*(risposta)){
                case 0 : printf("Eliminazione effettuata correttamente\n"); break;
                case 1 : printf("Direttorio non presente\n"); break;
                case -1 : printf("Problema nell'eliminazione di alcuni file\n"); break;
                case 2 : printf("%s è già prenotato\n", id_monopattino); break;
                case 3 : printf("%s non è un monopattino esistente\n", id_monopattino); break;
            }
        }
        else if(strcmp(servizio, "visualizza_prenotazioni")==0){
            
            /*Lettura parametri*/
            printf("Brand monopattino: "); gets(brand_monopattino);

            /*Controllo parametri*/
            if(strcmp(brand_monopattino, "brand1")!=0 && strcmp(brand_monopattino, "brand2")!=0 && strcmp(brand_monopattino, "brand3")!=0){
                printf("Errore nella scrittura del brand: sono ammessi solo brand1, brand2, brand3\n");
                printf("Quale servizio vuoi usare ( elimina_monopattino , visualizza_prenotazioni )?\n");
                continue;
            }

            /*Invio parametri*/
            out=visualizza_prenotazioni_1(brand_monopattino, cl);
            if (out == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            printf("Ho ottenuto i seguenti risultati:\n");
            for(i=0; i<6; i++){
                printf("%d) %s\n", i+1, out->ids[i]);
            }
        }
        else{
            printf("Agomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( elimina_monopattino , visualizza_prenotazioni )?\n");

    }
    clnt_destroy(cl);
    exit(0);
}
