//
//  Header.h
//  es. 2 stringhe
//
//  Created by Lorenzo Pellegrino on 19/11/20.
//

#ifndef Header_h
#define Header_h

int copiatura_stringa(char C[], char N[], char dest[], int lung){
    strcpy(dest, "");
    int L_E, L_R, size, result;
    L_E=strlen(C)+strlen(N)+1;
    L_R=strlen(C)+3;
    
    if(L_E<=lung){
        strcat(C, " "); //IMPORTANTE: inserisci una stringa con doppi apici!!!!
        strcat(C, N);
        strcpy(dest, C);
        result=1;
    }
    
    else {
        if(L_R<=lung){
            strcat(C, ' ');
            size=strlen(dest);
            dest[size]=N[0];
            dest[size+1]='.';
            dest[size+2]='\0';
            result=1;
        }
        else
            result=-1;
    }
    
    return result;
}
#endif /* Header_h */
