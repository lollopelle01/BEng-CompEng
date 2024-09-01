/*pellegrino lorenzo 0000971455*/
const N = 6;
typedef char nome[256];

struct InputFile{
	nome fileName;
    nome line;
};

struct InputDir{
	nome dirName;
    char prefisso[10];
};

struct Output{
    nome listaNomi[N];
    int errNum;
};

program OPSPROG {
	version OPSVERS {
		int CONTA_OCCORRENZE_LINEA(InputFile) = 1;
		Output LISTA_FILE_PREFISSO(InputDir) = 2;
	} = 1;
} = 0x20000013;
