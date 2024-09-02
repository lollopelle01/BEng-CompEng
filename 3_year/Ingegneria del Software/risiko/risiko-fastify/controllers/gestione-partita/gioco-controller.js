const IGioco = require('../../interfaces/gestione-partita/i-gioco');

const PartitaCominciata = require('../../entities/partitaCominciata');
const Stream = require('../../entities/partitaCominciata').Stream;
const AttaccoController = require('./attacco-controller');
const ChatController = require('./chat-controller');
const CombinazioneController = require('./combinazione-controller');
const DisposizioneController = require('./disposizione-controller');
const EsciDaPartitaController = require('./esci-da-partita-controller');
const SpostamentoController = require('./spostamento-controller');
const Mappa = require('../../entities/mappa');
const Giocatore = require('../../entities/giocatore');
const Turno = require('../../entities/turno');
const Fase = require('../../entities/fase');
const CartaCombinazione = require('../../entities/cartaCombinazione');
const Combinazione = require('../../entities/combinazione');
const Territorio = require('../../entities/territorio');
const Attacco = require('../../entities/attacco');
const TuplaTerritori = require('../../entities/tupla-territori');
const Spostamento = require('../../entities/spostamento');
const Mazzo = require('../../entities/mazzo');
const MazzoDiGioco = require('../../entities/mazzoDiGioco');
const CartaTerritorio = require('../../entities/cartaTerritorio');
const Figura = require('../../entities/figura');
const Regione = require('../../entities/regione');


class GiocoController extends IGioco {

    partita;
    attaccoController;
    chatController;
    combinazioneController;
    disposizioneController;
    esciDaPartitaController;
    spostamentoController;

    /**
     * @param {PartitaCominciata} partita 
     */
    constructor(partita){
        super();
        this.partita = partita;
        this.attaccoController = new AttaccoController();
        this.chatController = new ChatController();
        this.combinazioneController = new CombinazioneController();
        this.disposizioneController = new DisposizioneController();
        this.esciDaPartitaController = new EsciDaPartitaController();
        this.spostamentoController = new SpostamentoController();
    }

    /**
     * @returns {PartitaCominciata}
     * @override
     */
    getPartita() {
        return this.partita;
    }

    /**
     * @returns {void}
     * @override
     */
    terminaPartita(){
        // BOH ??? --> tanto non lo faremo mai 
    }

    getTerritorioById(territori, id){
        for(var t of territori){
            if(t.id === id){return t;}
        }
        return null;
    }

    /**
     * @returns {Mappa}
     * @override
     */
    inizializzaMappa(){
        const fs = require('fs');

        // Leggiamo i file
        var mappa = JSON.parse(fs.readFileSync(__dirname + '/mappe_json/mondo.json', 'utf-8'));
        var territori_json = JSON.parse(fs.readFileSync(__dirname + '/mappe_json/territoriMondo.json', 'utf-8'));
        
        // Istanziamo i territori
        var territori = territori_json.map((t) => {
            var T = new Territorio(
                t.id, 
                t.nome, 
                t.numArmate, 
                null, 
                new CartaTerritorio(
                    t.cartaTerritorio.id, 
                    Figura[t.cartaTerritorio.figura], 
                    t.cartaTerritorio.nomeTerritorio
                )
            );
            T.x = t.x;
            T.y = t.y;

            return T;
        });

        // Recuperiamo le carte territorio
        var carteTerritorio = territori.map((t) => t.cartaTerritorio);

        // Istanziamo la mappa
        const territoriConfinanti = new Map()
        // const regioni = [], mazzo = new Mazzo(), mazzoDiGioco = new MazzoDiGioco();

        // Riempo la mappa
        for(var [key,value] of Object.entries(mappa.territoriConfinanti)){
            // Sostituiamo la chiave
            const key_map = this.getTerritorioById(territori, parseInt(key));
            const values_map = [];
            
            // Sostituiamo i valori
            for(let id of value){ // Per ogni id della lista
                values_map.push(this.getTerritorioById(territori, id));
            }
            
            territoriConfinanti.set(key_map, values_map);
        }

        // Riempo le regioni
        const regioni = [];
        for(var r of mappa.regioni){
            const territoriCaricati = [];
            for(let id of r.territori){
                territoriCaricati.push(this.getTerritorioById(territori, id));
            }
            regioni.push(
                new Regione(
                    r.id,
                    r.nome,
                    r.armateBonus,
                    territoriCaricati,
                    null
                )
            )
        }

        // Scrivo il mazzo
        var mazzo = new Mazzo(1, carteTerritorio); // mancano Jolly e Obiettivi

        // MAPPA
        this.partita.mappa = new Mappa(undefined, territoriConfinanti, regioni, mazzo, "Mondo");

        const players = this.getPartita().giocatori;

        this.partita.turniStream = new Stream(function* () {
            let turnNumber = 1;
            let playerIndex = 0;
          
            while (true) {
                const player = players[playerIndex];
                yield new Turno(turnNumber, player);
                
                // Move to the next player
                playerIndex = (playerIndex + 1) % players.length;
                turnNumber++;
            }
        });

        this.getPartita().turnoCorrente = this.getPartita().turniStream.next().value;

        console.log(this.getPartita().turnoCorrente);
        return this.partita.mappa;
    }

    /**
     * @returns {!Map<Giocatore, Territorio[]>}
     * @override
     */
    distribuisciTerritori(){
        /* NON È ASSEGNAZIONE CASUALE MA SEMPLICE DISTRIBUZIONE */
        let i = 0;
        let num_giocatori = this.partita.giocatori.length;
        for(const territorio of this.partita.mappa.getTuttiTerritori()){
            territorio.possessore = this.partita.giocatori[i % num_giocatori];
            territorio.numArmate = 1;
            i++;
        }
    }

    /**
     * @returns {!Map<Giocatore, CartaObiettivo>}
     * @override
     */
    distribuisciCarteObiettivo(){
        let obietti_estratti = this.partita.mazzo.carteObiettivo;
        let num_giocatori = this.partita.opzioniPartita.numGiocatoriMassimi;
        obietti_estratti.sort(() => Math.random() - 0.5).slice(0, num_giocatori);

        for(const g of this.partita.giocatori){
            g.obiettivo = obietti_estratti.pop();
        }
    }

    /**
     * @returns {!Map<Giocatore, number>}
     * @override
     */
    distribuisciArmate(){
        let numero_armate;

        // Decidiamo quante armate a testa -->  *** DA GENERALIZZARE IN FUNZIONE DELLA MAPPA ***
        switch(this.partita.giocatori.length){
            case 3 : numero_armate = 35; break;
            case 4 : numero_armate = 30; break;
            case 5 : numero_armate = 25; break;
            case 6 : numero_armate = 20; break;
            default: numero_armate = 35; break;

        }

        // Un'armata verrà impostata di default
        numero_armate--;

        for(const g of this.partita.giocatori){
            g.armateDaDisporre = numero_armate;
        }
    }

    /**
     * @returns {!Turno}
     * @override
     */
    inizioNuovoTurno(){
       this.partita.prossimoTurno();
       return this.partita.turnoCorrente;
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who requests go to the next {@linkcode Fase}.
     * 
     * @returns {Fase}
     * @override
     */
    passaAllaProssimaFase(giocatore){
        let fase = this.partita.turnoCorrente.faseAttuale;
        switch(fase.nome){
            case "combinazione" : this.partita.turnoCorrente.passaAFaseDiDisposizione(); break;
            case "disposizione" : this.partita.turnoCorrente.passaAFaseDiAttacco(); break;
            case "attacco" : this.partita.turnoCorrente.passaAFaseDiSpostamento(); break;
            //case "spostamento" : this.inizioNuovoTurno();
        }
        return this.partita.turnoCorrente.faseAttuale;
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who may receive the {@linkcode CartaCombinazione}.
     * 
     * @returns {CartaCombinazione}
     */
    riscuotiCartaCombinazione(giocatore){
        // TODO
        // this.partita.turnoCorrente.giocatore.carteCombinazione.push(this.partita.mazzoDiGioco.pesca());
    }

    /**
     * @param {Giocatore} giocatore  - The {@linkcode Giocatore} who requests to end his {@linkcode Turno}.
     * 
     * @returns {void}
     * @override
     */
    terminaTurno(giocatore){
        this.riscuotiCartaCombinazione(giocatore);
        this.inizioNuovoTurno();
    }

    /**
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} to whom check all {@linkcode Combinazione}.
     * 
     * @returns {!Combinazione[]}
     * @override
     */
    elencaCombinazioniValide(giocatore){
        return this.combinazioneController.elencaCombinazioniValide(this.partita, giocatore);
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who plays the {@linkcode combinazione}.
     * @param {Combinazione} combinazione - The {@linkcode Combinazione} played by the {@linkcode giocatore}
     * 
     * @returns {!number}
     * @override
     */
    utilizzaCombinazione(giocatore, combinazione){
        return this.combinazioneController.utilizzaCombinazione(this.partita, giocatore, combinazione);
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode disposizione}.
     * @param {Disposizione} disposizione - The {@linkcode Disposizione} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {Territorio}
     * @override
     */
    effettuaDisposizione(giocatore, disposizione){
        console.log("Partita cominciata", this.partita);

        return this.disposizioneController.effettuaDisposizione(this.partita, giocatore, disposizione);
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} launching the {@linkcode attacco}.
     * @param {Attacco} attacco - The {@linkcode Attacco} launched by the {@linkcode giocatore}
     * 
     * @returns {TuplaTerritori}
     * @override
     */
    effettuaAttacco(giocatore, attacco){
        return this.attaccoController.effettuaAttacco(this.partita, giocatore, attacco);
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode spostamento}.
     * @param {Spostamento} spostamento - The {@linkcode Spostamento} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {!TuplaTerritori}
     * @override
     */
    effettuaSpostamento(giocatore, spostamento){
        return this.spostamentoController.effettuaSpostamento(this.partita, giocatore, spostamento);
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} wanting to quit the game of Risk.
     * 
     * @returns {void}
     * @override
     */
    esci(giocatore){
        // BOH ??? --> tanto non lo faremo mai 
    }

    /**
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who has written the {@linkcode messaggio}.
     * @param {string} messaggio - The {@linkcode string} containing the message of the {@linkcode giocatore}
     * 
     * @returns {void}
     * @override
     */
    scriviMessaggio(giocatore, messaggio){
        return this.chatController.scriviMessaggio(this.partita, giocatore, messaggio);
    }
}

module.exports = GiocoController
