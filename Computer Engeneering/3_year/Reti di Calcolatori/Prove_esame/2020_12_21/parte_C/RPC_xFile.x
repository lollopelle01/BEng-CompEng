/*pellegrino lorenzo 0000971455*/
const MAXNAMELENGHT=20;
const N=6;
typedef char nome[MAXNAMELENGHT];

struct Riga{
    nome id;
    nome tipo;
    nome partenza;
    nome arrivo;
    int hh;
    int mm;
    int ritardo;
    nome audio;
};

struct Output{
    nome corse[N];
    int dimL;
};

struct Input{
    nome id;
    int newRitardo;
};

program EIGHTPROG{
    version EIGHTVERS{
        Output VISUALIZZA_LISTA(nome)=1;
        int ESPRIMI_VOTO(Input)=2;
    }=1;
}=0x20000008;
