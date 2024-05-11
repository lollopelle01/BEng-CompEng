/*pellegrino lorenzo 0000971455*/
const MAX_LEN=12;
const N=10;

typedef char nome[MAX_LEN];
struct Output{
    nome ids[6];
};

program EIGHTPROG{
    version EIGHTVERS{
        Output VISUALIZZA_PRENOTAZIONI(nome)=1;
        int ELIMINA_MONOPATTINO(nome)=2;
    }=1;
}=0x20000008;
