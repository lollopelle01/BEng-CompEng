#include "pellegrino_lorenzo_0000971455_RPC_xFile.h"

int main(int argc, char** argv){
    CLIENT *cl;
    char servizio[20];

    InputBuy richiesta;
    Riga evento;
    
    if (argc != 2) {
        printf("usage: %s server_host\n", argv[0]);
        exit(1);
    }

    cl = clnt_create(argv[1], RPCPROG, RPCVERS, "udp");
    if (cl == NULL) {
        clnt_pcreateerror(argv[1]);
        exit(1);
    }
    
    printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
    while(scanf("%s", servizio)==1){
        
        if(strcmp(servizio, "inserimento_evento")==0){
            int *esito;

            // lettura valori
            printf("Inserisci i valori\n");
            printf("Descrizione: "); scanf("%s", evento.Descrizione);
            printf("Tipo (Concerto, Calcio, Formula1): "); scanf("%s", evento.Tipo);
            printf("Data: "); scanf("%s", evento.Data);
            printf("Luogo: "); scanf("%s", evento.Luogo);
            printf("Disponibilità: "); scanf("%s", evento.Disponibilita);
            printf("Prezzo: "); scanf("%s", evento.Prezzo);

            //controllo valori
            if(strcmp(evento.Tipo, "Concerto")!=0 && strcmp(evento.Tipo, "Calcio")!=0 && strcmp(evento.Tipo, "Formula1")!=0){
                printf("Tipo non esistente\n");
                printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
                continue;
            }
            else if(strlen(evento.Data) > 10 || strlen(evento.Data) < 8){
                printf("Formato Data errato, usa dd/mm/aaaa\n");
                printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
                continue;
            }
            else if(atoi(evento.Disponibilita)<=0){
                printf("La disponibilità non è leggibile o è negativo o è nullo\n");
                printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
                continue;
            }
            else if(atoi(evento.Prezzo)<=0){
                printf("Il prezzo non è leggibile o è negativo o è nullo\n");
                printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
                continue;
            }
            
            esito=inserimento_evento_1(&evento, cl);
            
            if (esito == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }
            
            switch(*(esito)){
                case 0: printf("Inserimento avvenuto con successo\n"); break;
                case -1: printf("Inserimento fallito\n"); break;
            }
            
        }
        else if(strcmp(servizio, "acquisto_biglietti")==0){
            int *esito;
            InputBuy input;

            // lettura valori
            printf("Inserisci Descrizione dell'evento che vuoi acquistare: ");
            scanf("%s", input.Descrizione);
            printf("Quanti ticket desideri acquistare: ");
            scanf("%d", &input.tiketDesiderati);

            //controllo valori
            if(input.tiketDesiderati <= 0){
                printf("Il numero di ticket non deve essere negativo o nullo\n");
                printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
                continue;
            }
            
            esito=acquista_biglietti_1(&input,cl);
            
            if (esito == NULL) {
                clnt_perror(cl, argv[1]);
                exit(1);
            }
            
            switch(*(esito)){
                case 0: printf("Acquisto avvenuto con successo\n"); break;
                case -1: printf("Acquisto fallito\n"); break;
            }
        }
        else{
            printf("Argomento di ingresso errato!\n");
        }
        printf("Quale servizio vuoi usare (inserimento_evento, acquisto_biglietti)?\n");
    }
    clnt_destroy(cl);
    exit(0);
}