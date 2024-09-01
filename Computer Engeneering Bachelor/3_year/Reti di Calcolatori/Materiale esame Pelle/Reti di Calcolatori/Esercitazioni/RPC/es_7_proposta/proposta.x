struct OutputFileScan{
	int num_char;
	int num_parole;
	int num_linee;
	int errorCode;
};

struct IntputDirScan{
	char dirName[256];
	int num_soglia;
};

struct FileName{
	char name[256];
};

struct OutputDirScan{
	FileName names[8];
	int intero;
};

program OPSPROG {
	version OPSVERS {
		OutputFileScan FILE_SCAN(string) = 1;
		OutputDirScan DIR_SCAN(IntputDirScan) = 2;
	} = 1;
} = 0x20000013;



