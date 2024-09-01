// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"
int main(int argc, char *argv[]) {
    CLIENT *cl;
    char servizio[256], buffer[256], car;
    
    static InputFile inFile;
    static InputDir inDir;

    int *occorrenze;
    Output *out;

    if (argc < 2) {
        printf("uso: %s host\n", argv[0]);
        exit(1);
    }
    
    cl = clnt_create(argv[1], OPSPROG, OPSVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }

    printf("Che servizio vuoi usare? (conta_occorrenze_linea, lista_file_prefisso)\n");
    
    while(gets(servizio)){
        if(strcmp(servizio, "conta_occorrenze_linea")==0){
            
            printf("Quale file vuoi considerare?\n");
            gets(inFile.fileName);

            printf("Di quale linea vuoi contare le occorrenze?\n");
            gets(inFile.line);
            
            occorrenze=conta_occorrenze_linea_1(&inFile, cl);
            if(*(occorrenze)>0){
                printf("Numero di occorrenze di %s in %s: %d\n", inFile.line, inFile.fileName, *(occorrenze));
            }
            else{
                printf("Lettura fallita\n");
            }
        }
        else if(strcmp(servizio, "lista_file_prefisso")==0){
            printf("Quale direttorio vuoi considerare?\n");
            gets(inDir.dirName);
            
            printf("Che prefisso devono avere i file per essere considerati?\n");
            gets(inDir.prefisso);
            
            out=lista_file_prefisso_1(&inDir, cl);
            if(out->errNum == -1){printf("Direttorio inesistente\n");}
            else{
                printf("Risultato:\n");
                for(int i=0; i<6; i++){
                    printf("%d) %s\n", i+1, out->listaNomi[i]);
                }
            }
        }
        else{
            printf("Servizio <%s> : non disponibile\n", servizio);
            printf("Cosa vuoi scannerizzare? (F=file, D=direttorio)\n");
            continue;
        }
        
        if(occorrenze==NULL && out==NULL){
            clnt_perror(cl, argv[1]);
            exit(1);
        }
    printf("Che servizio vuoi usare? (conta_occorrenze_linea, lista_file_prefisso)\n");
    }
    clnt_destroy(cl);
}
