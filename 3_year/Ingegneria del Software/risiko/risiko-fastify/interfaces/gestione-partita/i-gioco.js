const Giocatore = require('../../entities/giocatore');
const Spostamento = require('../../entities/spostamento');
const TuplaTerritori = require('../../entities/tupla-territori');
const Attacco = require('../../entities/attacco');
const Combinazione = require('../../entities/combinazione');
const Disposizione = require('../../entities/disposizione');
const PartitaCominciata = require('../../entities/partitaCominciata');
const Mappa = require('../../entities/mappa');
const CartaObiettivo = require('../../entities/cartaObiettivo');
const Turno = require('../../entities/turno');
const Fase = require('../../entities/fase');
const CartaCombinazione = require('../../entities/cartaCombinazione');
const Territorio = require('../../entities/territorio');

const IAttacco = require('./i-attacco');
const IChat = require('./i-chat');
const ICombinazione = require('./i-combinazione');
const IDisposizione = require('./i-disposizione');
const IEsci = require('./i-esci');
const ISpostamento = require('./i-spostamento');

/**
 * @implements {IAttacco}
 * @implements {IChat}
 * @implements {ICombinazione}
 * @implements {IDisposizione}
 * @implements {IEsci}
 * @implements {ISpostamento}
 * @interface
 */
class IGioco {

    /**
     * This method executes the given {@linkcode attacco} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} launching the {@linkcode attacco}.
     * @param {!Attacco} attacco - The {@linkcode Attacco} launched by the {@linkcode giocatore}
     * 
     * @returns {!TuplaTerritori}
     * @abstract
     * */
    effettuaAttacco(giocatore, attacco) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method writes the given {@linkcode message} of the given {@linkcode giocatore} to the chat.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who has written the {@linkcode message}.
     * @param {!string} message - The {@linkcode string} containing the message of the {@linkcode giocatore}
     * 
     * @returns {void}
     * @abstract
     * */
    scriviMessaggio(giocatore, message) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns all the {@linkcode Combinazione} available to the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} to whom check all {@linkcode Combinazione}.
     * 
     * @returns {!Combinazione[]}
     * @abstract
     * */
    elencaCombinazioniValide(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method uses the given {@linkcode combinazione} of the given {@linkcode giocatore}, returning the corresponding number of armies.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who plays the {@linkcode combinazione}.
     * @param {!Combinazione} combinazione - The {@linkcode Combinazione} played by the {@linkcode giocatore}
     * 
     * @returns {!number}
     * @abstract
     * */
    utilizzaCombinazione(giocatore, combinazione) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method executes the given {@linkcode disposizione} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode disposizione}.
     * @param {!Disposizione} disposizione - The {@linkcode Disposizione} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {!Territorio}
     * @abstract
     * */
    effettuaDisposizione(giocatore, disposizione) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method lets the {@linkcode giocatore} quit the game of Risk.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} wanting to quit the game of Risk.
     * 
     * @returns {void}
     * @abstract
     * */
    esci(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method executes the given {@linkcode spostamento} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode spostamento}.
     * @param {!Spostamento} spostamento - The {@linkcode Spostamento} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {!TuplaTerritori}
     * @abstract
     * */
    effettuaSpostamento(giocatore, spostamento) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrives the started {@linkcode PartitaCominciata}.
     * 
     * @returns {!PartitaCominciata}
     * @abstract
     */
    getPartita() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method ends the started {@linkcode PartitaCominciata}.
     * 
     * @returns {void}
     * @abstract
     */
    terminaPartita() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrives the initialised {@linkcode Mappa}.
     * 
     * @returns {!Mappa}
     * @abstract
     */
    inizializzaMappa() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns all {@linkcode Territorio} for every single {@linkcode Giocatore} with an army already in it.
     * 
     * @returns {!Map<Giocatore, Territorio[]>}
     * @abstract
     */
    distribuisciTerritori() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns a random {@linkcode CartaObiettivo} for every single {@linkcode Giocatore}.
     * 
     * @returns {!Map<Giocatore, CartaObiettivo>}
     * @abstract
     */
    distribuisciCarteObiettivo() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns the right amount of armies for every single {@linkcode Giocatore}.
     * 
     * @returns {!Map<Giocatore, number>}
     * @abstract
     */
    distribuisciArmate() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns the new {@linkcode Turno} to be played.
     * 
     * @returns {!Turno}
     * @abstract
     */
    inizioNuovoTurno() {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method returns the new {@linkcode Fase} to be played in the {@linkcode giocatore}'s {@linkcode Turno}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who requests go to the next {@linkcode Fase}.
     * 
     * @returns {!Fase}
     * @abstract
     */
    passaAllaProssimaFase(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrives the {@linkcode CartaCombinazione} the {@linkcode giocatore} should get when capturing at least one {@linkcode Territorio} per {@linkcode Turno}.
     * {@linkcode null} is returned if the {@linkcode giocatore} hasn't conquered at least one {@linkcode Territorio}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who may receive the {@linkcode CartaCombinazione}.
     * 
     * @returns {?CartaCombinazione}
     * @abstract
     */
    riscuotiCartaCombinazione(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method ends the {@linkcode Turno} of the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore  - The {@linkcode Giocatore} who requests to end his {@linkcode Turno}.
     * 
     * @returns {void}
     * @abstract
     */
    terminaTurno(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = IGioco
