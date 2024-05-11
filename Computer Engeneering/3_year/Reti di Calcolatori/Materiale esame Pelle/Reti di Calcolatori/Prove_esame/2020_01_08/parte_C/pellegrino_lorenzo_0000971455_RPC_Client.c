#include "pellegrino_lorenzo_0000971455_RPC_xFile.h"

//per quando si rigenera il .h
#include <rpc/rpc.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <signal.h>
#include <sys/ttycom.h>
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

int main(int argc, char **argv){
    CLIENT * cl;
    char* file[N];
    char servizio[256];

    int * esito;
    Output * out;
    Input in;

    if (argc < 2) {
        printf( "uso: %s host\n", argv[0]);
        exit(1);
    }
    
    cl = clnt_create(argv[1], OPSPROG, OPSVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }

    printf("Che servizio vuoi usare? \nO = elimina_occorrenze_file\nL = lista_file_carattere\n");
    while(gets(servizio)){
        if(strcmp(servizio, "O") == 0){
            printf("Su quale file vuoi operare?\n");
            gets(servizio);
            esito = elimina_occorrenze_1(servizio, cl);

            if(*esito == -1){
                printf("Errore di lettura del file %s\n", servizio);
            }
            else{
                printf("In %s sono state effettuate %d eliminazioni di numeri\n\n", servizio, *esito);
            }
        }
        else if(strcmp(servizio, "L") == 0){
            printf("Su quale direttorio vuoi operare?\n");
            gets(in.direttorio);

            printf("che carattere vuoi considerare?\n");
            scanf("%c", &in.carattere);

            printf("Quante volte almeno deve essere ripetuto il carattere '%c' nei file dentro %s\n", in.carattere, in.direttorio);
            scanf("%d", &in.num_occorrenze);

            gets(servizio); //pulisco il buffer

            out = lista_file_carattere_1(&in, cl);

            if(out->errCode == -1){
                printf("Direttorio %s inesistente\n", in.direttorio);
            }
            else{
                printf("Ho trovato %d file:\n", out->errCode);
                for(int i=0; i<out->errCode; i++){
                    printf("%d) %s\n", i+1, out->files[i]);
                }
            }
        }
        else{
            printf("Servizio non riconosciuto\n\n");
        }
        printf("\nChe servizio vuoi usare? \nO = elimina_occorrenze_file\nL = lista_file_carattere\n");
    }
    clnt_destroy(cl);
}