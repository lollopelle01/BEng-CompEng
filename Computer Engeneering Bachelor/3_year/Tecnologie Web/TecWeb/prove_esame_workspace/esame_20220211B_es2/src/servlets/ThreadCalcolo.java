package servlets;

import beans.Risposta;

public class ThreadCalcolo extends Thread{
    private int[][] matrice;
    private int flag;

    public ThreadCalcolo(int[][] matrice, int flag){
        this.matrice = matrice;
        this.flag = flag;
    }

    @Override
    public void run(){
        Risposta risp;
        int resultPrec, result;
        resultPrec=0; result=0;

        // gestione differenziata del thread
        switch(this.flag){
            case 1:
            { // somma delle righe
                for(int i=0; i<5; i++){
                    for(int j=0; j<5; j++){
                        result += this.matrice[i][j];
                    }
                    if(resultPrec!=0 && resultPrec!=result){ // abbiamo trovato un errore
                        
                    }
                    else{ // continuiamo il controllo
                        resultPrec=result;
                        result=0;
                    }
                }
            }
            break;

            case 2:
            { // somma delle colonne
                for(int i=0; i<5; i++){
                    for(int j=0; j<5; j++){
                        result += this.matrice[j][i];
                    }
                    if(resultPrec!=0 && resultPrec!=result){ // abbiamo trovato un errore

                    }
                    else{ // continuiamo il controllo
                        resultPrec=result;
                        result=0;
                    }
                }
            }
            break;

            case 3:
            { // somma delle diagonali
                for(int i=0; i<5; i++){
                    for(int j=0; j<5; j++){

                        if(i==j || i+j==4){ // sulle diagonali
                            result += this.matrice[i][j];
                        }
                    }
                    if(resultPrec!=0 && resultPrec!=result){ // abbiamo trovato un errore

                    }
                    else{ // continuiamo il controllo
                        resultPrec=result;
                        result=0;
                    }
                }
            }
            break;
        }

        
    }
}
