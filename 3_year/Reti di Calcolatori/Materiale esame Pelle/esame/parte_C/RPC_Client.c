// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    int i;
    
    // Output (*)
    Output * out;
    int * risposta;

    // Input
    Input_lista inL;
    Input_conta inC;

    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare ( conta_occorrenze , lista_file_carattere )? ");
    while(gets(servizio)){
        
        if(strcmp(servizio, "conta_occorrenze")==0){
            /*Lettura parametri*/
            printf("Nome del file: "); gets(inC.fileName);
            printf("Linea: "); gets(inC.linea);

            /*Controllo parametri*/

            /*Invio parametri*/
            risposta=conta_occorrenze_1(&inC, cl);
            if (risposta == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(*(risposta) == -1){ printf("File non trovato\n");}
            else{ printf("Ho trovato %d occorrenze della linea '%s' in %s\n", *(risposta), inC.linea, inC.fileName);}
            
        }
        else if(strcmp(servizio, "lista_file_carattere")==0){
            /*Lettura parametri*/
            printf("Direttorio: "); gets(inL.dirName);
            printf("Carattere: "); scanf("%c", &inL.carattere);

            /*Controllo parametri*/

            /*Invio parametri*/
            out=lista_file_carattere_1(&inL, cl);
            if (out == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }

            /*Gestione del risultato*/
            if(out->errNum == -1){printf("Direttorio insesistente\n");}
            else{
                printf("Risultati:\n");
                for(i=0; i<5; i++){
                    if(out->lista[i]!=NULL){ printf("%d) %s\n", i+1, out->lista[i]);}
                }
            }
        }
        else{
            printf("Srgomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare ( conta_occorrenze , lista_file_carattere )? ");
    }
    clnt_destroy(cl);
    exit(0);
}
