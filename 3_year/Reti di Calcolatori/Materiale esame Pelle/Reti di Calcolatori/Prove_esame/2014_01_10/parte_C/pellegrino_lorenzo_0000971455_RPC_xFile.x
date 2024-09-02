const MAXNAMELENGHT=20;
const N=10;
typedef char nome[MAXNAMELENGHT];

struct Riga{
    nome Descrizione;
    nome Tipo;
    nome Data;
    nome Luogo;
    nome Disponibilita;
    nome Prezzo;
};

struct InputBuy{
    nome Descrizione;
    int tiketDesiderati;
};

program RPCPROG{
    version RPCVERS{
        int INSERIMENTO_EVENTO(Riga) = 1;
        int ACQUISTA_BIGLIETTI(InputBuy) = 2;
    }=1;
}=0x20000008;
