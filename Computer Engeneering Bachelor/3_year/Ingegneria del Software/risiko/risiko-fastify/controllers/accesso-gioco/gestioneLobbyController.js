const PartitaCominciata = require('../../entities/partitaCominciata');
const IGestioneLobby = require('../../interfaces/accesso-gioco/i-gestione-lobby')
const IPartiteManager = require('../../interfaces/accesso-gioco/i-partite-manager')


class GestioneLobbyController extends IGestioneLobby{

    partiteManager;

    /**
     * @param {IPartiteManager} partiteManager 
     */
    constructor(partiteManager) {
        super()
        this.partiteManager = partiteManager
    }

    /**
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * 
     * @returns {!PartitaCominciata}
     * @override
     */
    avviaPartita(codicePartita) {
        //  1. Comincio la partita
        this.partiteManager.cominciaPartita(codicePartita)
    }

    /**
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode CreatoreDellaPartita} who created the {@linkcode Lobby}.
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} to remove from the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @override
     */
    rimuoviGiocatore(codicePartita, creatoreDellaPartita, giocatore){ 
        //  1. Recupero la lobby
        let lobby = this.partiteManager.getLobbyById(codicePartita);

        //  PS: evitabile secondo me --> il problema si dovrebbe porre prima, non dovrebbe nemmeno partire la chiamata lato client !!!!
        // if(lobby.creatoreDellaPartita !== creatoreDellaPartita){return;} // se non è il creatore non vale 

        //  2. Rimuovo giocatore
        // lobby.giocatori.splice(lobby.giocatori.valueOf(giocatore));
        console.log("--------------------------------------------------", giocatore);
        console.log(lobby.giocatori.length);
        lobby.giocatori = lobby.giocatori.filter(g => g.nickname != giocatore.nickname);
        console.log(lobby.giocatori.length);
    }

    /**
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who wants to exit the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @override
     */
    abbandonaLobby(codicePartita, giocatore){

        console.log("--------------------------------------------------", giocatore);


        //  1. Recupero la lobby
        let lobby = this.partiteManager.getLobbyById(codicePartita);

        // non c'è alcun modo per notificare la volontarietà, l'effetto è uguale e quindi...
        this.rimuoviGiocatore(codicePartita, lobby.creatoreDellaPartita, giocatore);

        if (lobby.giocatori.length === 0) {
            this.partiteManager.rimuoviPartita(codicePartita);
        }
    }

    /**
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode CreatoreDellaPartita} who created the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @override
     */
    sciogliLobby(codicePartita, creatoreDellaPartita){
        //  1. Recupero la lobby
        let lobby = this.partiteManager.getLobbyById(codicePartita);

        //  2. Tolgo tutti
        lobby.giocatori = [];
        lobby.creatoreDellaPartita = null;

        this.partiteManager.rimuoviPartita(codicePartita);
    }

    /**
     * @param {string} codicePartita 
     * @returns {string}
     */
    getWebSocketServerUrl(codicePartita){
        let wsServer =  this.wsServers.get(codicePartita);
        let addressInfo;
        if (wsServer === undefined) {
            return "";
        } else {
            addressInfo = wsServer.address();
            if (addressInfo instanceof string) {
                return addressInfo;
            } else {
                return `ws://${addressInfo.address}:${addressInfo.port}`;
            }
        }
    }
}

module.exports = new GestioneLobbyController(require('./accessoGiocoController').partiteManager);