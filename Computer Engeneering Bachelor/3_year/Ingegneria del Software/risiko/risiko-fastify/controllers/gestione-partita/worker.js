const { parentPort, threadId } = require('worker_threads');
const http = require('http');
const WebSocketServerThread = require('./web-socket-server-thread');
const GiocoController = require('./gioco-controller');
const PartitaCominciata = require('../../entities/partitaCominciata');
const Giocatore = require('../../entities/giocatore');

// Crea un server HTTP vuoto --> serve solo istanza per mettere in ascolto wss
const server = http.createServer();

// Gestisci il messaggio dal worker principale
parentPort.on('message', ({ host, partitaCominciata}) => { 

    const giocatori = partitaCominciata.giocatori.map(g => new Giocatore(
        g.id, g.nickname, g.colore
    ));

    const wss = new WebSocketServerThread({ server }, new GiocoController(
        new PartitaCominciata(
            partitaCominciata.codice,
            partitaCominciata.opzioniPartita,
            giocatori,
        )
    ), giocatori);

    // metto la wss in ascolto 
    wss.on('listening', () => {
        let wssPort = wss.address().port;
        console.log(`${threadId}: wss in ascolto sulla porta ${wssPort}`)
        // Dico a papà a che porta mi trovo
        // parentPort.postMessage(wssPort);

        let addressInfo = wss.address();
        let url;

        url = `ws://${addressInfo.address}:${addressInfo.port}`;
        // url = `${addressInfo}`;

        parentPort.postMessage(`${url}`);
    });

    wss.on('connection', (ws) => wss.onOpen(ws));

    // Fai iniziare il server HTTP ad ascoltare su una porta casuale
    server.listen(0, host, () => {
        server.emit('listening'); // l'ascolto del server innesca l'ascolto della wss
    });

    

});