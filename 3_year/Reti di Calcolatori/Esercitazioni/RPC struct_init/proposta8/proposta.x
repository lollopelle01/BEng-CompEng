const MAXNAMELENGHT=20;
const N=10;
typedef char nome[MAXNAMELENGHT];

struct InputEsprimiVoto{
    nome candidato;
    nome operazione;
};

struct Giudice{
    nome giudice;
    int punteggio_tot;
};

struct Output{
    int num_giudici;
    Giudice giudici[N];
};

program EIGHTPROG{
    version EIGHTVERS{
        Output CLASSIFICA_GIUDICI(void)=1;
        int ESPRIMI_VOTO(InputEsprimiVoto)=2;
    }=1;
}=0x20000008;
