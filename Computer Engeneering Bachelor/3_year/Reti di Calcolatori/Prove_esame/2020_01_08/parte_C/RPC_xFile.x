/*pellegrino lorenzo 0000971455*/
const MAXNAMELENGHT=256;
const N=6;
typedef char nome[MAXNAMELENGHT];

struct Input{
   nome dirName;
   char carattere;
   int numOcc;
};

struct Output{
    nome files[N];
    int numErrore;
};

program EIGHTPROG{
    version EIGHTVERS{
        Output LISTA_FILE_CARATTERE(Input)=1;
        int ELIMINA_OCCORRENZE(nome)=2;
    }=1;
}=0x20000008;
