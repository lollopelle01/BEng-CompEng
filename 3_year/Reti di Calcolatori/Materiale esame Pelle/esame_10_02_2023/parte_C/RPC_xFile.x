/*pellegrino lorenzo 0000971455*/
const MAXNAMELENGHT=256;
const N=5;
typedef char nome[MAXNAMELENGHT];

struct Input_conta{
    nome fileName;
    nome linea;
};

struct Imput_lista{
    nome dirName;
    char carattere;
};

struct Output{
    nome lista[N];
};

program EIGHTPROG{
    version EIGHTVERS{
        int CONTA_OCCORRENZE(Input_conta)=1;
        Output LISTA_FILE_CARATTERE(Input_lista)=2;
    }=1;
}=0x20000008;
