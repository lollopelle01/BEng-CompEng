const Partita = require("./partita");
const Chat = require("./chat");
const MazzoDiGioco = require("./mazzoDiGioco");
const Mazzo = require("./mazzo");
const Turno = require("./turno");
const Colore = require("./colore");

const Territorio = require("./territorio");
const Giocatore = require("./giocatore");

const Combinazione = require("./combinazione");
const Disposizione = require("./disposizione");
const Attacco = require("./attacco");
const Spostamento = require("./spostamento");
const Figura = require("./figura");
const OpzioniPartita = require("./opzioniPartita");
const CartaTerritorio = require("./cartaTerritorio");

class Stream {

    /**
     * @type {Function}
     */
    generator;

    /**
     * @param {Function} generatorFunction 
     */
    constructor(generatorFunction) {
        if (typeof generatorFunction !== 'function') {
            throw new Error('The argument must be a generator function');
        }
        this.generator = generatorFunction();
    }
  
    next() {
        return this.generator.next();
    }
  
}

class PartitaCominciata extends Partita {

    giocatori;
    mappa;
    chat;
    /**
     * @type {Map<Colore, Colore>}
     */
    registroUccisioni;
    /**
     * @type {Stream}
     */
    turniStream;
    /**
     * @type {Turno}
     */
    turnoCorrente;
    /**
     * @type {Giocatore}
     */
    lastRemovedPlayer;

    /**
     * @param {string} codice 
     * @param {OpzioniPartita} opzioniPartita 
     * @param {Giocatore[]} giocatori 
     */
    constructor(codice, opzioniPartita, giocatori) {
        super(codice, opzioniPartita);
        this.giocatori = giocatori;

        this.mappa = this.opzioniPartita.mappa;

        this.chat = new Chat();
        
        this.registroUccisioni = new Map();
    }

    /**
     * @param {Giocatore} giocatore 
     * @override
     */
    rimuoviGiocatore(giocatore) {
        // TODO: gestire il caso di uccisione dall'uscita volontaria
        this.lastRemovedPlayer = giocatore; // serve per ridistribuisciTerritori
        super.rimuoviGiocatore(giocatore);
        this.ridistribuisciTerritori();
    }

    /**
     * This method assigns the {@linkcode Territorio} of the last exited player to the other remaining players.
     * 
     * @returns {void}
     */
    ridistribuisciTerritori() {
        // TODO: scelta giocatori fatta bene: ora prendo il primo giocatore della lista --> ci ho procato io (Pelle)
        const territoriGiocatoreUscito = this.mappa.territoriDiGiocatore(this.lastRemovedPlayer); //prende tutti i territori del giocatore uscito
        // players = this.giocatori.filter(p => p.id != this.lastRemovedPlayer.id)
        // la riga di codice sopra descritta non è necessaria perché rimuoviGiocatore fa la stessa identica cosa
        let i = 0
        for (const t of territoriGiocatoreUscito) {
            t.possessore = this.giocatori[i];
            i = (i + 1) % this.giocatori.length;
        }

        return;
    }

    /**
     * This method generates a new {@linkcode Turno} to be played;
     * 
     * @returns {!Turno}
     */
    prossimoTurno() {
        this.turnoCorrente = this.turniStream.next().value;
        return this.turnoCorrente;
    }

    /**
     * This method executes the given {@linkcode combinazione} and returns the expected number of Armies.
     * @param {!Combinazione} combinazione
     * @returns {!number}
     */
    utilizzaCombinazione(combinazione) {
        const carteCombinazione = combinazione.carteCombinazione;
        let armateCalcolate = 0;
        let jolly;
        let combinazioneValida = false;

        if ((jolly = carteCombinazione.find(carta => carta.figura === Figura.Jolly)) !== undefined) {
            //Le carte combinazione passate contengono un jolly;
            let jollyIndex = carteCombinazione.indexOf(jolly);

            carteCombinazione.splice(jollyIndex, 1); //rimuovo il jolly;
            if (carteCombinazione[0] === carteCombinazione[1]) {
                armateCalcolate +=12; //aumento se le altre due carte sono identiche in figura
            }

            combinazioneValida = true;

        } else { //le carte passate non contengono un jolly

            if (carteCombinazione.every(carta => carta.figura === Figura.Cannone)) {
                armateCalcolate+=4;
            } else if (carteCombinazione.every(carta => carta.figura === Figura.Fante)) {
                armateCalcolate+=6;
            } else if (carteCombinazione.every(carta => carta.figura === Figura.Cavallo)) {
                armateCalcolate+=8;
            } else if (
                carteCombinazione[0].figura !== carteCombinazione[1].figura &&
                carteCombinazione[1].figura !== carteCombinazione[2].figura &&
                carteCombinazione[2].figura !== carteCombinazione[0].figura
            ) {
                armateCalcolate+=10;
            }

            combinazioneValida = true;
        }

        if(combinazioneValida) {
            //si può partire dal presupposto che il jolly viene tolto a prescindere dal codice precedente
            //Non essendo typescript, le type assertions non esistono, quindi niente 'as Carta Territorio'
            const carteTerritorio = carteCombinazione.map(function(cartaCombinazione) {
                if (cartaCombinazione instanceof CartaTerritorio) {
                    return cartaCombinazione;
                }
            });
            const territoriGiocatore = this.mappa.territoriDiGiocatore(this.turnoCorrente.giocatore); //pesco tutti i territori di un giocatore
            let cartaTerritorio, territorio;
            let count = 0;
            

            for (cartaTerritorio of carteTerritorio) {
                for (territorio of territoriGiocatore) {
                    if (cartaTerritorio.nomeTerritorio === territorio.nome) {
                        count++;
                        break; //vado immediatamente alla prossima iterazione per non perdere tempo
                    }
                }
            }

            armateCalcolate+=count*2;
        }

        this.mappa.mazzoDiGioco.reinserisci(combinazione.carteCombinazione); //reinserisco le carte nel mazzo dopo l'uso

        return armateCalcolate;
    }

    /**
     * This method executes the given {@linkcode disposizione}
     * @param {!Disposizione} disposizione
     * @returns {!Territorio}
     */
    effettuaDisposizione(disposizione) {
        const { numeroArmate, territorio } = disposizione;
        const { possessore } = territorio;

        possessore.armateDaDisporre -= numeroArmate;
        territorio.numArmate += numeroArmate;

        console.log(possessore, numeroArmate, territorio);

        return territorio;
    }

    /**
     * This method returns a casual {@linkcode number} from 1 to 6.
     * 
     * @returns {number}
     */
    lancioDado() {
        return Math.floor(Math.random() * 6) + 1;
    }

    /**
     * This method executes the given {@linkcode attacco}
     * @param {!Attacco} attacco
     * @returns {!Territorio[]}
     */
    effettuaAttacco(attacco) { //TODO: discuss usage of attacco
        const {
            armateAttacco,
            armateDifesa,
            territorioAttacco,
            territorioDifesa,
        } = attacco;

        // TODO: magari migliorare (se c'è tempo), ora vince solo l'attaccante (e subito) --> ci ho provato ora (Pelle)

        // PROCEDURA DI ATTACCO
        // Entrambi lanciano tanti dadi quante le armate (entro 3)
        const dadiAttacco = [this.lancioDado(), this.lancioDado(), this.lancioDado()]
        const dadiDifesa = [this.lancioDado(), this.lancioDado(), this.lancioDado()]

        // Si ordinano i dadi in ordine decrescente
        dadiAttacco.sort((a, b) => b - a);
        dadiDifesa.sort((a, b) => b - a);

        // Uno a uno il perdente perde un'armata
        for(let i=0; i<Math.min(armateAttacco, armateDifesa); i++){
            // Attaccante vince solo se >
            if(dadiAttacco[i]>dadiDifesa[i]){
                territorioDifesa.numArmate--;
            } else{ // Vince difesa
                territorioAttacco.numArmate--;
                armateAttacco--;
            }
        }

        // Si gestisce un'eventuale conquista del territorio attaccato
        if(territorioDifesa.numArmate==0){
            // Trasferisco le armate rimaste
            territorioAttacco.numArmate -= armateAttacco;
            territorioDifesa.numArmate += armateAttacco;
            // Cambio il proprietario
            territorioDifesa.giocatore = territorioAttacco.giocatore;
        }

        // territorioAttacco.numArmate -= armateAttacco;
        // territorioDifesa.numArmate = numArmate;
        // territorioDifesa.giocatore = territorioAttacco.giocatore;

        return [territorioAttacco, territorioDifesa];
    }

    /**
     * This method executes the given {@linkcode spostamento}.
     * @param {!Spostamento} spostamento
     * @returns {!Territorio[]}
     */
    effettuaSpostamento(spostamento) {
        const {
            numeroArmate,
            territorioDa,
            territorioA,
        } = spostamento;

        territorioDa.numArmate -= numeroArmate;
        territorioA.numArmate += numeroArmate;

        return [territorioDa, territorioA];
    }

    /**
     * This method adds an entry that keeps track of a pair of killed and killer {@linkcode Giocatore}.
     * 
     * @param {Colore} ucciso 
     * @param {Colore} uccisore 
     * 
     * @returns {void}
     */
    registraUccisione(ucciso, uccisore) {
        this.registroUccisioni.set(ucciso, uccisore);
    }

    /**
     * This method returns true if and only if the {@linkcode Giocatore} with the given {@linkcode colore} has not been killed.
     * 
     * @param {Colore} colore 
     * @returns {boolean}
     */
    isColoreVivo(colore) {
        return !(this.registroUccisioni.has(colore));
    }

    /**
     * This method returns the killer of the given {@linkcode colore}. If the given {@linkcode colore} has no killer, then it returns undefined.
     * 
     * @param {Colore} colore 
     * @returns {Colore | undefined}
     */
    getUccisore(colore) {
        return this.registroUccisioni.get(colore);
    }
}

module.exports = PartitaCominciata;
module.exports.Stream = Stream;
