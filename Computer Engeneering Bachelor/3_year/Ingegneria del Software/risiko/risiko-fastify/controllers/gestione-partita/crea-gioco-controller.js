const ICreaGioco = require('../../interfaces/gestione-partita/i-crea-gioco');
const PartitaCominciata = require('../../entities/partitaCominciata');
const { Worker } = require('worker_threads');


class CreaGiocoController extends ICreaGioco {

    wsThreads;

    constructor(){
        super();
        this.wsThreads = new Map();
    }

    /**
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} needing a WebSocketServerThread to be played.
     * 
     * @returns {!Promise<string>}
     * @override
     */
    creaWebSocketServer(partitaCominciata) {
        
        const host = '127.0.0.1';

        return new Promise((resolve) => {
            //Da fare per ogni metodo dichiarato in webSocketServerThread
        
            // Per ogni richiesta HTTP creerò un thread
            const worker = new Worker(__dirname + '/worker.js');
            
            // Faccio partire il worker
            worker.postMessage({host, partitaCominciata});
            console.log('Worker avviato')
            
            // Gestirò la risposta del worker --> porta su cui lavora
            worker.on('message', (url) => {
                // res.writeHead(200, { 'Content-Type': 'text/plain' });
                console.log(`Figlio avviato all'url ${url}`);

                this.wsThreads.set(partitaCominciata.codice, url);
                resolve(url);
            });
        });
        
    }
}

module.exports = CreaGiocoController