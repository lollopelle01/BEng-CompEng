/* giulianelli nicole 0000976239*/
const MAXNAMELENGHT=20;
const N=6;
typedef char nome[MAXNAMELENGHT];



struct Output{
    nome lista[N];
    int errCode;
};

program EIGHTPROG{
    version EIGHTVERS{
        Output VISUALIZZA_PRENOTAZIONE(nome)=1;
        int ELIMINA_PRENOTAZIONI(nome)=2;
    }=1;
}=0x20000008;
