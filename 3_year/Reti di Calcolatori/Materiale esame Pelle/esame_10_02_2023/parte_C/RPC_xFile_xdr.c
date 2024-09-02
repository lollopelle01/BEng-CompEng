/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "RPC_xFile.h"

bool_t
xdr_nome (XDR *xdrs, nome objp)
{
	register int32_t *buf;

	 if (!xdr_vector (xdrs, (char *)objp, MAXNAMELENGHT,
		sizeof (char), (xdrproc_t) xdr_char))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Input_conta (XDR *xdrs, Input_conta *objp)
{
	register int32_t *buf;

	 if (!xdr_nome (xdrs, objp->fileName))
		 return FALSE;
	 if (!xdr_nome (xdrs, objp->linea))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Imput_lista (XDR *xdrs, Imput_lista *objp)
{
	register int32_t *buf;

	 if (!xdr_nome (xdrs, objp->dirName))
		 return FALSE;
	 if (!xdr_char (xdrs, &objp->carattere))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Output (XDR *xdrs, Output *objp)
{
	register int32_t *buf;

	int i;
	 if (!xdr_vector (xdrs, (char *)objp->lista, N,
		sizeof (nome), (xdrproc_t) xdr_nome))
		 return FALSE;
	return TRUE;
}
