#include "proposta.h"
#include <rpc/rpc.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    CLIENT *cl;
    char servizio[256], buffer[256], car;
    OutputFileScan *ofs;
    OutputDirScan *ods;
    
    static char *path;
    static IntputDirScan ids;

    if (argc < 2) {
        fprintf(stderr, "uso: %s host\n", argv[0]);
        exit(1);
    }
    
    cl = clnt_create(argv[1], OPSPROG, OPSVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }

    printf("Cosa vuoi scannerizzare? (F=file, D=direttorio)\n");
    
    while(gets(servizio)){
        if(strcmp(servizio, "F")==0){
            path = malloc(sizeof(char)*256);
            
            printf("Quale file vuoi scannerizzare?\n");
            gets(path);
            
            printf("\nRichiedo lettura di: %s\n\n", path);
            
            ofs=file_scan_1(&path, cl);
            if(ofs->errorCode==1){
                printf("Errore di lettura del file %s\n", path);
            }
            else{
                printf("Esito della lettura: caxratteri=%d\t parole=%d\t righe=%d\n", ofs->num_char, ofs->num_parole, ofs->num_linee);
            }
            free(path);
        }
        else if(strcmp(servizio, "D")==0){
            printf("Quale direttorio vuoi scannerizzare?\n");
            scanf("%s", ids.dirName);
            
            printf("Quale soglia vuoi inserire?\n");
            scanf("%d", &ids.num_soglia);
            
            printf("Nome direttorio --> %s\n",  ids.dirName);
            printf("Soglia --> %d\n", ids.num_soglia);
            
            ods=dir_scan_1(&ids, cl);
            printf("\nHo trovato %d file con dimensione superiore a %d:\n", ods->intero, ids.num_soglia);
            for(int i=0; i<ods->intero; i++){
                printf("%d) %s\n", i+1, ods->names[i].name);
            }
        }
        else{
            printf("Servizio <%s> : non disponibile\n", servizio);
            printf("Cosa vuoi scannerizzare? (F=file, D=direttorio)\n");
            continue;
        }
        
        if(ods==NULL && ofs==NULL){
            clnt_perror(cl, argv[1]);
            exit(1);
        }
        printf("\nCosa vuoi scannerizzare? (F=file, D=direttorio)\n");
    }
    clnt_destroy(cl);
}
