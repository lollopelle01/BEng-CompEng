#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define LENGTH_FILE_NAME 256
#define max(a, b)        ((a) > (b) ? (a) : (b))

int main(int argc, char **argv){
    char string[LENGTH_FILE_NAME];
    
    if(argc != 2){
        printf("Errore argomenti\n");
    }
    
    DIR *dir1, *dir2;
    struct dirent *entry1, *entry2;
    
    dir1 = opendir(argv[1]);
    if(dir1==NULL){
        printf("La directory %s non esiste\n", argv[1]);
    }
    else{
        printf("Nella directory %s :\n", argv[1]);
        
        while((entry1 = readdir(dir1))!=NULL){
            if(entry1->d_type == DT_REG){ //file
                printf("\tFile: %s\n", entry1->d_name);
            }
            if(entry1->d_type == DT_DIR){ //directory
                
                strcpy(string, argv[1]);
                strcat(string, "/");
                strcat(string, entry1->d_name);
                
                dir2 = opendir(string);
                if(dir2==NULL){
                    printf("\tLa subDirectory %s non esiste\n", entry1->d_name);
                }
                else{
                    printf("\n\tNella subDirectory %s :\n", entry1->d_name);
                    while((entry2 = readdir(dir2))!=NULL){
                        if(entry2->d_type == DT_REG){ //file
                            printf("\t\tFile: %s\n", entry2->d_name);
                        }
                        if(entry2->d_type == DT_DIR){ //directory
                            printf("\t\tDirectory: %s\n", entry2->d_name);
                        }
                    }
                }
                closedir(dir2);
            }
        }
        closedir(dir1);
    }
}
