// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];

    // Utilities
    int i;
    
    // Output (*)
    Output * out;
    int * risposta;

    // Input
    Input in;
    char fileName[256];

    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( lista_file_carattere , elimina_occorrenze )?\n");
    while(gets(servizio)){
        
        if(strcmp(servizio, "lista_file_carattere")==0){
            /*Lettura parametri*/
            printf("Directory: "); gets(in.dirName);
            printf("Carattere: "); scanf("%c", &in.carattere);
            printf("Occorrenze: "); scanf("%d", &in.numOcc);

            /*Controllo parametri*/
            if(in.numOcc <= 0){
                printf("Occorrenze devono essere positive\n");
                printf("Quale servizio vuoi usare ( lista_file_carattere , elimina_occorrenze )?\n");
                continue;
            }

            /*Invio parametri*/
            out=lista_file_carattere_1(&in, cl);
            if (out == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(out->numErrore==-1){printf("%s è inesistente\n", in.dirName);}
            else{
                printf("Risultato:\n");
                for(i=0; i<6; i++){
                    if(out->files[i]!=NULL){printf("%s\n", out->files[i]);}
                }
            }
            gets(in.dirName); //pulizia buffer
        }
        else if(strcmp(servizio, "elimina_occorrenze")==0){
            
            /*Lettura parametri*/
            printf("File: "); gets(fileName);

            /*Controllo parametri*/
            if((i=strlen(fileName))<=4 ||
                (
                    fileName[i-1]!= 't' ||
                    fileName[i-2]!= 'x' ||
                    fileName[i-3]!= 't' ||
                    fileName[i-4]!= '.' 
                )
            ){
                printf("%s deve essere un file .txt\n", fileName);
                printf("Quale servizio vuoi usare ( lista_file_carattere , elimina_occorrenze )?\n");
                continue;
            }
            

            /*Invio parametri*/
            risposta=elimina_occorrenze_1(fileName, cl);
            if (risposta == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(*(risposta)==-1){printf("%s è inesistente\n", fileName);}
            else{printf("Ho effettualto %d eliminazioni\n", *(risposta));}
        }
        else{
            printf("Argomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( lista_file_carattere , elimina_occorrenze )?\n");
    }
    clnt_destroy(cl);
    exit(0);
}
