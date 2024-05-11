#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <regex.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>

#define LINE_LENGTH 128
#define WORD_LENGTH 128
#define max(a, b)   ((a) > (b) ? (a) : (b))
#define N 10

typedef struct{
    char identificatore[LINE_LENGTH];
    char tipo[LINE_LENGTH];
    char carbone;
    char citta[LINE_LENGTH];
    char via[LINE_LENGTH];
    char messaggio[256];
}RigaTabella;

typedef struct{
    RigaTabella righe[N];
}Tabella;

typedef char Nome[LINE_LENGTH];

int isUnico(Tabella tabella, char * identificatore){
    int result=1;
    for(int i=0; i<N && result==1; i++){
        if(strcmp(tabella.righe[i].identificatore, identificatore)==0){
            result=0;
        }
    }
    return result;
}