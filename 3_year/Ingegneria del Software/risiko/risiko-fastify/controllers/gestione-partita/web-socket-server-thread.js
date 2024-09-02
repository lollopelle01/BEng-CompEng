const WebSocket = require('ws');
const IGioco = require('../../interfaces/gestione-partita/i-gioco');
const WorkerThread = require('worker_threads');
const { parentPort, threadId } = require('worker_threads');
const http = require('http');
const Disposizione = require('../../entities/disposizione');

/**
 * @implements {WorkerThread}
 */
class WebSocketServerThread extends WebSocket.Server {

    gioco;
    /**
     * @type {WebSocket[]}
     */
    giocatoriAttivi;
    server;



    /**
     * @param {IGioco} gioco
     */
    constructor(options, gioco){
        super(options);
        this.gioco = gioco;
        this.giocatoriAttivi = [];

        this.server = http.createServer();
    }

    /**
     * This method is called on WebSocket session 'open' event.
     * 
     * @param {WebSocket} sessioneWs 
     * @returns {void}
     */
    onOpen(sessioneWs) {
        this.giocatoriAttivi.push(sessioneWs);

        sessioneWs.on('message', (message) => {
            this.onMessage(sessioneWs, message);
        });

        sessioneWs.on('close', () => {
            this.onClose(sessioneWs);
        });

        console.log('Nuova connessione WebSocket');

        if (this.gioco.getPartita().giocatori.length == this.giocatoriAttivi.length) {
            // la partita può cominciare
            this.gioco.inizializzaMappa();

            this.gioco.distribuisciTerritori();
            
            this.gioco.distribuisciArmate();

            this.broadcast(JSON.stringify({
                azione: {
                    metodo: 'inizializza',
                },
                partitaCominciata: this.gioco.getPartita(),
            }));
        }
    }

    /**
     * This method is called on WebSocket session 'close' event.
     * 
     * @param {WebSocket} sessioneWs 
     * @returns {void}
     */
    onClose(sessioneWs) {
        //TODO
    }

    /**
     * This method is called on WebSocket session 'message' event.
     * 
     * @param {WebSocket} sessioneWs 
     * @param {string} msg 
     * @returns {void}
     */
    onMessage(sessioneWs, msg) {

        let richiesta = JSON.parse(msg);
        let risultato;
        
        switch(richiesta.metodo) {
            case 'effettuaAttacco': {
                risultato = this.gioco.effettuaAttacco(richiesta.parametri.giocatore, richiesta.parametri.attacco);
                break;
            }
            case 'scriviMessaggio': {
                risultato = this.gioco.scriviMessaggio(richiesta.parametri.giocatore, richiesta.parametri.message);
                break;
            }
            case 'elencaCombinazioniValide': {
                risultato = this.gioco.elencaCombinazioniValide(richiesta.parametri.giocatore);
                break;
            }
            case 'utilizzaCombinazione': {
                risultato = this.gioco.utilizzaCombinazione(richiesta.parametri.giocatore, richiesta.parametri.combinazione);
                break;
            }
            case 'effettuaDisposizione': {
                const territorio = this.gioco.getPartita().mappa.getTuttiTerritori().filter(
                    t => t.nome == richiesta.parametri.disposizione.nomeTerritorio
                )[0];
                console.log(
                    'effettuaDisposizione',
                    richiesta.parametri.disposizione.nomeTerritorio,
                    territorio
                );

                risultato = this.gioco.effettuaDisposizione(
                    richiesta.parametri.giocatore,
                    new Disposizione(
                        richiesta.parametri.disposizione.numeroArmate,
                        territorio
                    )
                    
                );
                break;
            }
            case 'esci': {
                risultato = this.gioco.esci(richiesta.parametri.giocatore);
                break;
            }
            case 'effettuaSpostamento': {
                risultato = this.gioco.effettuaSpostamento(richiesta.parametri.giocatore, richiesta.parametri.spostamento);
                break;
            }
            case 'getPartita': {
                risultato = this.gioco.getPartita();
                break;
            }
            case 'terminaPartita': {
                risultato = this.gioco.terminaPartita();
                break;
            }
            case 'inizializzaMappa': {
                risultato = this.gioco.inizializzaMappa();
                break;
            }
            case 'distribuisciTerritori': {
                risultato = this.gioco.distribuisciTerritori();
                break;
            }
            case 'distribuisciCarteObiettivo': {
                risultato = this.gioco.distribuisciCarteObiettivo();
                break;
            }
            case 'distribuisciArmate': {
                risultato = this.gioco.distribuisciArmate();
                break;
            }
            case 'inizioNuovoTurno': {
                risultato = this.gioco.inizioNuovoTurno();
                break;
            }
            case 'passaAllaProssimaFase': {
                risultato = this.gioco.passaAllaProssimaFase(richiesta.parametri.giocatore);
                break;
            }
            case 'riscuotiCartaCombinazione': {
                risultato = this.gioco.riscuotiCartaCombinazione(richiesta.parametri.giocatore);
                break;
            }
            case 'terminaTurno': {
                risultato = this.gioco.terminaTurno(richiesta.parametri.giocatore);
                break;
            }
            default: {
                break;
            }
        }

        this.broadcast(JSON.stringify({
            azione: {
                metodo: richiesta.metodo,
                giocatore: richiesta.parametri.giocatore,
                risultato
            },

            partitaCominciata: this.gioco.getPartita(),
        }))

        console.log(`Messaggio: ${msg}`);
    }

    /**
     * This method is called on WebSocket session 'error' event.
     * 
     * @param {WebSocket} sessioneWs 
     * @returns {void}
     */
    onError(sessioneWs) {
        //TODO
    }

    /**
     * This method is called on WebSocket session 'send' event.
     * 
     * @param {WebSocket} sessioneWs 
     * @param {string} msg 
     * @returns {void}
     */
    send(sessioneWs, msg){
        //TODO
    }

    /**
     * This method is called on WebSocket session 'broadcast' event.
     * 
     * @@param {string} msg 
     * @returns {void}
     */
    broadcast(msg){
        this.giocatoriAttivi.forEach((ws) => {
            ws.send(msg);
        });
    }

}

module.exports = WebSocketServerThread
