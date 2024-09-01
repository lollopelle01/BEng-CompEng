//
//  function.c
//  es. 5 allocDin
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#include "function.h"

int leggiEsamiTxt(char *nomeFile, V_esame* vett){
    FILE *ft;
    if((ft=fopen(nomeFile, "r"))==NULL){
        printf("errore lettura di %s", nomeFile);
        return 0;
    }
    else{
        fscanf(ft, "%d", &(*vett).dim);
        (*vett).v= (Esame*) malloc(sizeof(Esame)*(*vett).dim);
        
        for(int i=0; i<(*vett).dim; i++){
            fscanf(ft, "%35c%d%d", vett->v[i].dicitura, &vett->v[i].numero_crediti, &vett->v[i].voto );
            vett->v[i].dicitura[35]='\0';
        }
        fclose(ft);
        return 1;
    }
}

int leggiEsamiBin(char *nomeFile, V_esame* vett){
    FILE *fb;
    if((fb=fopen(nomeFile, "rb"))==NULL){
        printf("errore lettura di %s", nomeFile);
        return 0;
    }
    else{
        fread(&(*vett).dim, sizeof(int), 1, fb);
        (*vett).v= (Esame*) malloc(sizeof(Esame)*(*vett).dim);
        
        for(int i=0; i<(*vett).dim; i++){
            fread(&vett->v[i], sizeof(Esame), 1, fb);
            vett->v[i].dicitura[35]='\0';
        }
        fclose(fb);
        return 1;
    }
}
    
void stampaEsami(V_esame vett){
    for(int i=0; i<vett.dim; i++){
        printf("Voto dell'esame di %s da %d crediti: %d\n", vett.v[i].dicitura, vett.v[i].numero_crediti, vett.v[i].voto);
    }
}

float media(V_esame vett){
    float num=0, den=0;
    for(int i=0; i<vett.dim; i++){
        num=num+vett.v[i].numero_crediti*vett.v[i].voto;
        den=den+vett.v[i].numero_crediti;
    }
    return num/den;
}

V_esame filtra(V_esame vett, char *pattern){
    int i, j=0, dimfiltro=0;
    V_esame filtro;
    for(i=0; i<vett.dim; i++){
        if(strstr(vett.v[i].dicitura, pattern)!=NULL)
            dimfiltro++;
    }
    filtro.dim=dimfiltro;
    filtro.v= (Esame*) malloc(sizeof(Esame)*filtro.dim);
    for(int i=0; i<dimfiltro; i++){
        if(strstr(vett.v[j].dicitura, pattern)!=NULL){
            filtro.v[j]=vett.v[i];
            j++;
        }
    }
    return filtro;
}

int salvaReport(V_esame vett, char* nomeFile){
    int i;
      FILE *fp = fopen(nomeFile, "w");
      if(fp == NULL)
      {
          perror("errore durante il salvataggio: ");
          return 0;
      }
      else{
          for(i = 0; i < vett.dim; i++){
              fprintf(fp, "Voto dell'esame di %s da %d crediti: %d\n", vett.v->dicitura, vett.v->numero_crediti, vett.v->voto);
          }
            fprintf(fp, "MEDIA: %f", media(vett) );
            fclose(fp);
            return 0;
      }
}
