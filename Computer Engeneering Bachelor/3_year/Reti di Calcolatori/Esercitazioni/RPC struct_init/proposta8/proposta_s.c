#include "proposta.h"
#include <fcntl.h>
#include <rpc/rpc.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

//definiamo tabella
typedef struct{
    nome Candidato;
    nome Giudice;
    char Categoria;
    nome fileName;
    char Fase;
    int Voto;
}RigaTabella;

//stato tabella
static int inizializzato=0;
static RigaTabella tabella[N];

//definisco Bubble-sort() --> ok
void bubbleSort(Giudice array[], int size) {

  // loop to access each array element
  for (int step = 0; step < size - 1; ++step) {
      
    // loop to compare array elements
    for (int i = 0; i < size - step - 1; ++i) {
      
      // compare two adjacent elements
      // change > to < to sort in descending order
      if (array[i].punteggio_tot < array[i + 1].punteggio_tot) {
        
        // swapping occurs if elements
        // are not in the intended order
        
        //int temp = array[i];
        Giudice temp;
        temp.punteggio_tot = array[i].punteggio_tot;
        strcpy(temp.giudice, array[i].giudice);
          
        //array[i] = array[i + 1];
        array[i].punteggio_tot = array[i + 1].punteggio_tot;
        strcpy(array[i].giudice, array[i + 1].giudice);
          
        //array[i + 1] = temp;
        array[i + 1].punteggio_tot = temp.punteggio_tot;
        strcpy(array[i + 1].giudice, temp.giudice);
      }
    }
  }
}

void formatta(char* stringa_fin, char* stringa_iniz, int dim_max){
    //printf("Sto fromattando la stringa: %s\n", stringa_iniz);
    strcpy(stringa_fin, stringa_iniz);
    int aggiunta = dim_max - strlen(stringa_fin);
    for(int i=0; i<aggiunta; i++){
        strcat(stringa_fin, " ");
    }
}

void stampa(int dim_max_colonna){
    char buffer[10];
    char stringa[dim_max_colonna+1]; stringa[dim_max_colonna]='\0';
    
    printf("\n\nTabella:\n");
    for(int i=0; i<6; i++){
        switch(i){//ciclo per le voci della tabella
            case 0:
                formatta(stringa, "CANDIDATO", dim_max_colonna); printf("|%s", stringa);
                break;
            case 1:
                formatta(stringa, "GIUDICE", dim_max_colonna); printf("|%s", stringa);
                break;
            case 2:
                formatta(stringa, "CATEGORIA", dim_max_colonna); printf("|%s", stringa);
                break;
            case 3:
                formatta(stringa, "FILENAME", dim_max_colonna); printf("|%s", stringa);
                break;
            case 4:
                formatta(stringa, "FASE", dim_max_colonna); printf("|%s", stringa);
                break;
            case 5:
                formatta(stringa, "VOTO", dim_max_colonna); printf("|%s\n", stringa);
                break;
        }
    }
    
    for(int i=0; i<N; i++){//ciclo per stampare righe
        formatta(stringa, tabella[i].Candidato, dim_max_colonna);
            printf("|%s", stringa);
        
        formatta(stringa, tabella[i].Giudice, dim_max_colonna);
            printf("|%s", stringa);
        
        sprintf(buffer, "%c",  tabella[i].Categoria);
            formatta(stringa, buffer, dim_max_colonna);
                printf("|%s", stringa);
        
        formatta(stringa, tabella[i].fileName, dim_max_colonna);
            printf("|%s", stringa);
        
        sprintf(buffer, "%c",  tabella[i].Fase);
            formatta(stringa, buffer, dim_max_colonna);
                printf("|%s", stringa);
        
        sprintf(buffer, "%d",  tabella[i].Voto);
            formatta(stringa, buffer, dim_max_colonna);
                printf("|%s\n", stringa);
    }
}

void inizializza(){
    printf("Sto inizializzando\n");
    int i, j, categoria;
    char buff[2], stringa[40];
    
    if(inizializzato==1){
        return;
    }
    
    //prima inizializzazione --> tutti posti liberi
    printf("Faccio la prima inizializzazione...\n");
    for(i=0; i<N; i++){
        strcpy(tabella[i].Candidato, "L");
        strcpy(tabella[i].Giudice, "L");
        tabella[i].Categoria='L';
        strcpy(tabella[i].fileName, "L");
        tabella[i].Fase='L';
        tabella[i].Voto=-1;
    }
    
    //seconda inizializzazione --> riempiamo posti dispari (es)
    printf("Faccio la seconda inizializzazione...\n");
    buff[0]='A'; buff[1]='\0';
    //printf("buff=%s\n", buff);
    //printf("Entro nel for\n");
    for(i=0; i<N/2; i++){
        
        buff[0]=(char)(2*i + 48);
        strcpy(stringa, "candidato");
        strcat(stringa, buff);
        //printf("buff=%s\n", buff);
        strcpy(tabella[2*i].Candidato, stringa);
        
        strcat(stringa, "Profile.txt");
        
        strcpy(tabella[2*i].fileName, stringa);
        //printf("file%d fatto\n",2*i );
        
        strcpy(stringa, "giudice");
        strcat(stringa, buff);
        
        buff[0]=(char)(2*i + 48); //facciamo ripetere il giudice ogni 3
        strcpy(tabella[2*i].Giudice, stringa);
        //printf("giudice%d fatto\n",2*i%3 );
        
        switch((2*i)%4){
            case 1:
                    tabella[2*i].Categoria='D';
                    break;
            case 2:
                    tabella[2*i].Categoria='O';
                    break;
            case 3:
                    tabella[2*i].Categoria='B';
                    break;
            default:
                    tabella[2*i].Categoria='U';
                    break;
        }
        
        switch((2*i)%3){
            case 1:
                    tabella[2*i].Fase='B';
                    break;
            case 2:
                    tabella[2*i].Fase='S';
                    break;
            default:
                    tabella[2*i].Fase='A';
                    break;
        }
        
        tabella[2*i].Voto=i*25;
    }
    printf("Inizializzato correttamente\n");
    inizializzato=1;
    
    //int dim_max_colonna=22; //candidatoAProfile.txt
    //stampa(dim_max_colonna);
    //printf("\n\n");
    
    return;
}

Output * classifica_giudici_1_svc(void* v, struct svc_req *rq){
    if(inizializzato==0){ inizializza(); }
    
    static Output result;
    int giudice_trovato;
    
    //riempiamo result con i giudici --> ok
    result.num_giudici=0;
    for(int i=0; i<N; i++){//ciclo per tabella
        giudice_trovato=0;
        for(int j=0; j<N && !giudice_trovato; j++){//ciclo per result
            if(strcmp(result.giudici[j].giudice, tabella[i].Giudice)==0){
                giudice_trovato=1;
            }
        }
        if(!giudice_trovato && strcmp(tabella[i].Giudice, "L")!=0){
            strcpy(result.giudici[result.num_giudici].giudice,tabella[i].Giudice);
            result.num_giudici++;
        }
    }
    
    //riempiamo result con i voti --> ok
    for(int i=0; i<result.num_giudici; i++){//ciclo per result
        result.giudici[i].punteggio_tot=0;
        for(int j=0; j<N; j++){//ciclo per tabella
            if(strcmp(result.giudici[i].giudice, tabella[j].Giudice)==0){
                result.giudici[i].punteggio_tot+=tabella[j].Voto;
            }
        }
    }
    
    //ordiniamo result
    bubbleSort(result.giudici, result.num_giudici);
    
    return (&result);
}

int * esprimi_voto_1_svc(InputEsprimiVoto * input, struct svc_req *rq){
    if(inizializzato==0){ inizializza(); }
    
    static int result = -1;
    //  possibili valori di result:
    //  0 --> tutto ok
    //  -1 --> parametri errati
    //  1 --> sottrarre voti da chi non ne ha
    //  2 --> aggiungere voto a chi ne ha 100
    
    if(strcmp(input->operazione, "aggiunta")==0 || strcmp(input->operazione, "sottrazione")==0){
        for(int i=0; i<N && result==-1; i++){
            if(strcmp(tabella[i].Candidato, input->candidato)==0){
                if(tabella[i].Voto==0 && strcmp(input->operazione, "sottrazione")==0) {result = 1;}
                else if(tabella[i].Voto==100 && strcmp(input->operazione, "aggiunta")==0) {result = 2;}
                else {
                    result = 0;
                    if(strcmp(input->operazione, "sottrazione")==0) {tabella[i].Voto--;}
                    if(strcmp(input->operazione, "aggiunta")==0) {tabella[i].Voto++;}
                }
            }
        }
    }
    else{
        printf("Operazione non valida!\n");
    }
    return (&result);
}
