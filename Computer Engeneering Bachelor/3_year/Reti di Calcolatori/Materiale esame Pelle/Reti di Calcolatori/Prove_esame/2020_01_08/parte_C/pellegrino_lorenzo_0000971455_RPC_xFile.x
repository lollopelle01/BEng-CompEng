const MAXLENGHT = 256;
const N = 6;

typedef char nome[MAXLENGHT];

struct Input{
	nome direttorio;
    char carattere;
    int num_occorrenze;
};

struct Output{
	nome files[N];
	int errCode;
};

program OPSPROG {
	version OPSVERS {
		int ELIMINA_OCCORRENZE(nome) = 1;
		Output LISTA_FILE_CARATTERE(Input) = 2;
	} = 1;
} = 0x20000013;

