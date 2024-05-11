//THREAD SLAVE
/*
Gli slave sono stati implementati come Thread Java: in questo modo 
è consentita l'esecuzione parallela secondo il modello tradizionale 
di java, garantendo anche una buona efficienza ed eleganza di soluzione.
*/


//MODELLO DEPRECATO SERVLET
/*
Il SingleThreadModel e' un modello di concorrenza nella gestione 
delle servlet da parte del container per cui a ogni richiesta 
a una servlet che implementa l'interfaccia SingleThreadModel viene 
istanziato un nuovo oggetto (o ne viene preso uno da un pool), garantendo
che non vi siano mai 2 thread che eseguono sulla stessa istanza della servlet.
Questo puo' essere utile per segnalare al container il fatto che una determinata
Servlet e' unsafe, tuttavia mantiene, ovviamente, il problema di Thread safety 
per variabili statiche (e di sessione). Usando il modello attuale, che prevede
una sola istanza di servlet con un thread per ogni richiesta, si aggiunge anche il
problema di concorrenza sugli attributi di istanza. Non risolvendo del tutto i 
problemi di concorrenza, il SingleThreadModel e' oggi deprecato.
Per risolvere questi problemi nel modello attuale, e' sufficiente impiegare per quanto
possibile variabili sullo stack dei metodi (service, doGet, doPost) ed accedere alle
operazioni critiche di oggetti di sessione/applicazione/globali attraverso 
blocchi synchronized.
Il motivo per cui il SingleThreadModel e' deprecato risiede proprio nel fatto
che non riesce a garantire la Thread-safety, ma nel tentativo di farlo grava molto
sulle risorse del server.
*/