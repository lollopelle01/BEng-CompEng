#include "proposta.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];
    
    void* v; //solo per far combaciare formalmente le firme delle funzioni
    
    int* esitoVoto;
    Output* classificaGiudici;
    InputEsprimiVoto input;
    
    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], EIGHTPROG, EIGHTVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare (voto, classifica)?\n");
    while(scanf("%s", servizio)==1){
        
        if(strcmp(servizio, "voto")==0){
            printf("Su quale candidato vuoi operare?\n");
            scanf("%s", input.candidato);
            
            printf("Quale operazione vuoi usare sul voto (aggiunta, sottrazione)?\n");
            scanf("%s", input.operazione);
            
            esitoVoto=esprimi_voto_1(&input, cl);
            
            if (esitoVoto == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }
            
            switch(*(esitoVoto)){
                case 0: printf("Operazione avvenuta con successo\n"); break;
                case -1: printf("Parametri inseriti sono errati\n"); break;
                case 1: printf("Non puoi sottrarre voto a chi ha 0\n"); break;
                case 2: printf("Non puoi aggiungere voto a chi ha 100\n"); break;
            }
            
            //stampa risultato
            //printf("Aggiornamento:\n");
            //stampa(22);
        }
        else if(strcmp(servizio, "classifica")==0){
            
            classificaGiudici=classifica_giudici_1(v,cl);
            
            if (classificaGiudici == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }
            
            //stampa risultato
            printf("\nI giudici, ordinati per punteggio, sono:\n");
            for(int i=0; i<classificaGiudici->num_giudici; i++){
                printf("%d) %s --> %d\n", i+1, classificaGiudici->giudici[i].giudice, classificaGiudici->giudici[i].punteggio_tot); //problema lettura
            }
        }
        else{
            printf("Srgomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare (voto, classifica)?\n");
    }
    clnt_destroy(cl);
    exit(0);
}
