/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _PELLEGRINO_LORENZO_0000971455_RPC_XFILE_H_RPCGEN
#define _PELLEGRINO_LORENZO_0000971455_RPC_XFILE_H_RPCGEN

#include <rpc/rpc.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <signal.h>
// #include <sys/ttycom.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <syslog.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/errno.h>
#include <rpc/pmap_clnt.h>
#include <dirent.h>
#include <sys/stat.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif

#define MAXNAMELENGHT 20
#define N 10

typedef char nome[MAXNAMELENGHT];

struct Riga {
	nome Descrizione;
	nome Tipo;
	nome Data;
	nome Luogo;
	nome Disponibilita;
	nome Prezzo;
};
typedef struct Riga Riga;

struct InputBuy {
	nome Descrizione;
	int tiketDesiderati;
};
typedef struct InputBuy InputBuy;

#define RPCPROG 0x20000008
#define RPCVERS 1

#if defined(__STDC__) || defined(__cplusplus)
#define INSERIMENTO_EVENTO 1
extern  int * inserimento_evento_1(Riga *, CLIENT *);
extern  int * inserimento_evento_1_svc(Riga *, struct svc_req *);
#define ACQUISTA_BIGLIETTI 2
extern  int * acquista_biglietti_1(InputBuy *, CLIENT *);
extern  int * acquista_biglietti_1_svc(InputBuy *, struct svc_req *);
extern int rpcprog_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define INSERIMENTO_EVENTO 1
extern  int * inserimento_evento_1();
extern  int * inserimento_evento_1_svc();
#define ACQUISTA_BIGLIETTI 2
extern  int * acquista_biglietti_1();
extern  int * acquista_biglietti_1_svc();
extern int rpcprog_1_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_nome (XDR *, nome);
extern  bool_t xdr_Riga (XDR *, Riga*);
extern  bool_t xdr_InputBuy (XDR *, InputBuy*);

#else /* K&R C */
extern bool_t xdr_nome ();
extern bool_t xdr_Riga ();
extern bool_t xdr_InputBuy ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_PELLEGRINO_LORENZO_0000971455_RPC_XFILE_H_RPCGEN */
